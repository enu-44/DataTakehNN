package com.datatakehnn.activities.sync.post_sync_activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.datatakehnn.R;
import com.datatakehnn.controllers.CablesController;
import com.datatakehnn.controllers.ElementoController;
import com.datatakehnn.controllers.EquipoController;
import com.datatakehnn.controllers.FotoController;
import com.datatakehnn.controllers.NovedadController;
import com.datatakehnn.controllers.PerdidaController;
import com.datatakehnn.models.element_model.Elemento;
import com.datatakehnn.models.elemento_cable.Elemento_Cable;
import com.datatakehnn.models.equipo_elemento_model.Equipo_Elemento;
import com.datatakehnn.models.foto_model.Foto;
import com.datatakehnn.models.material_model.Material;
import com.datatakehnn.models.novedad_model.Novedad;
import com.datatakehnn.models.perdida_model.Perdida;
import com.datatakehnn.models.request_data_sync_model.Request_Post_Data_Sync;
import com.datatakehnn.models.request_data_sync_model.Response_Post_Data_Sync;
import com.datatakehnn.models.request_data_sync_model.model_request.Foto_Request;
import com.datatakehnn.models.request_data_sync_model.model_request.Novedad_Request;
import com.datatakehnn.services.api_client.retrofit.ApiClient;
import com.datatakehnn.services.api_client.retrofit.ApiClientInterFace;
import com.datatakehnn.services.api_services.data_async_services.IPostDataSync;
import com.datatakehnn.services.api_services.data_async_services.PostDataSyncApiService;
import com.datatakehnn.services.aplication.DataTakeApp;
import com.datatakehnn.services.connection_internet.ConnectivityReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class UploadDataActivity extends AppCompatActivity implements IPostDataSync,UploadDataMainView,ConnectivityReceiver.ConnectivityReceiverListener  {
    //UI Elements
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    //Instances
    PostDataSyncApiService postDataSyncApiService;
    EquipoController equipoController;
    CablesController cablesController;
    NovedadController novedadController;
    PerdidaController perdidaController;
    ElementoController elementoController;
    FotoController fotoController;

    //Last Sincronize

    //Api
    ApiClientInterFace apiService = ApiClient.getClientAmazon().create(ApiClientInterFace.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);
        ButterKnife.bind(this);
        setToolbarInjection();
        setupInjection();

    }

    private void setupInjection() {
        this.postDataSyncApiService= PostDataSyncApiService.getInstance(this);
        this.equipoController= EquipoController.getInstance(this);
        this.cablesController= CablesController.getInstance(this);
        this.novedadController= NovedadController.getInstance(this);
        this.perdidaController= PerdidaController.getInstance(this);
        this.elementoController= ElementoController.getInstance(this);
        this.fotoController= FotoController.getInstance(this);

    }

    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(getString(R.string.title_sincronizacion));
        if (getSupportActionBar() != null)// Habilitar Up Button
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    //region EVENTS

    @OnClick(R.id.btnSyncData)
    public void onViewClicked() {
        showProgresss();
        syncData();
    }

    private void syncData() {



        Elemento elemento= elementoController.getElementoByIdAndBySync(false);
        List<Elemento_Cable> Cables= cablesController.getList_Cable_Element(elemento.getElemento_Id());
        List<Equipo_Elemento> Equipos= equipoController.getListEquipoElement(elemento.getElemento_Id());
        List<Perdida> Perdidas= perdidaController.getListPerdida(elemento.getElemento_Id());
        List<Novedad> novedades= novedadController.getListNovedadesByElementoId(elemento.getElemento_Id());
        List<Foto> fotos= fotoController.getListFotoByElemento(elemento.getElemento_Id());





        List<Novedad_Request> Novedades=new ArrayList<>();
        for (Novedad novedad:novedades){

            Novedad_Request novedad_request=  new Novedad_Request();
            novedad_request.setElemento_Id(novedad.getElemento_Id());
            novedad_request.setDescripcion(novedad.getDescripcion());
            novedad_request.setFechaCreacion(novedad.getFecha_Creacion());
            novedad_request.setHora(novedad.getHora());
            novedad_request.setDetalle_Tipo_Novedad_Id(novedad.getDetalle_Tipo_Novedad_Id());



            if(novedad.getImage_Novedad()!=null){

                //novedad_request.setImageArray(null);
                File imgFile = new  File(novedad.getRuta_Foto());
                if(imgFile.exists()){
                    Bitmap bitmap = BitmapFactory.decodeFile(novedad.getRuta_Foto());
                    novedad_request.setImageArray(getEncoded64ImageStringFromBitmap(bitmap));
                }else{
                    byte[] data = novedad.getImage_Novedad().getBlob();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    novedad_request.setImageArray(getEncoded64ImageStringFromBitmap(bitmap));
                }


                //byte[] decodedString = Base64.decode(data, Base64.DEFAULT);
                //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                /*
                byte[] foto= null;
                foto=novedad.getImage_Novedad().getBlob();
                Gson gson = new Gson();
                String festmengeStr = gson.toJson(foto);
                Type collectionType = new TypeToken<byte[]>(){}.getType();
                byte[] festmengeNew = gson.fromJson(festmengeStr, collectionType);*/
            }else{
                novedad_request.setImageArray(null);
            }
            Novedades.add(novedad_request);
        }

        List<Foto_Request> Fotos=new ArrayList<>();
        for (Foto foto:fotos){

            Foto_Request foto_request= new Foto_Request();
            foto_request.setDescripcion(foto.getDescripcion());
            foto_request.setFechaCreacion(foto.getFecha_Creacion());
            foto_request.setHora(foto.getHora());
            foto_request.setTitulo(foto.getDescripcion());
            if(foto.getImage()!=null){
                //foto_request.setImageArray(foto.getImage().getBlob());
                //foto_request.setImageArray(null);//TODO Verificar upload foto


                File imgFile = new  File(foto.getRuta_Foto());
                if(imgFile.exists()){
                    Bitmap bitmap = BitmapFactory.decodeFile(foto.getRuta_Foto());
                    foto_request.setImageArray(getEncoded64ImageStringFromBitmap(bitmap));
                }else{
                    byte[] data = foto.getImage().getBlob();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    foto_request.setImageArray(getEncoded64ImageStringFromBitmap(bitmap));
                }
                //


            }else {
                foto_request.setImageArray(null);
            }

            foto_request.setNovedad_Id(foto.getNovedad_Id());
            foto_request.setElemento_Id(foto.getElemento_Id());

            Fotos.add(foto_request);
        }

        /*
        Request_Post_Data_Sync post_data_sync= new Request_Post_Data_Sync(
                elemento.getElemento_Id(),
                elemento.getCodigo_Apoyo(),
                elemento.getNumero_Apoyo(),
                elemento.getFecha_Levantamiento(),
                elemento.getHora_Inicio(),
                elemento.getHora_Fin(),
                elemento.getResistencia_Mecanica(),
                elemento.getRetenidas(),
                elemento.getAltura_Disponible(),
                elemento.getUsuario_Id(),
                elemento.getEstado_Id(),
                elemento.getLongitud_Elemento_Id(),
                elemento.getMaterial_Id(),
                elemento.getProyecto_Id(),
                elemento.getNivel_Tension_Elemento_Id(),
                elemento.getCiudad_Id(),
                cables,
                perdidas,
                equipos,
                novedad_requests,
                foto_requests
        );
        */








        if(checkConnection()){
            //FooResponse = apiService.postAppoinments(new ListAppointmentRequest(numero_doc_user))
            Request_Post_Data_Sync request_post_data_sync= new Request_Post_Data_Sync(
                    elemento.getElemento_Id(),
                    elemento.getCodigo_Apoyo(),
                    elemento.getNumero_Apoyo(),
                    elemento.getFecha_Levantamiento(),
                    elemento.getHora_Inicio(),
                    elemento.getHora_Fin(),
                    elemento.getResistencia_Mecanica(),
                    elemento.getRetenidas(),
                    elemento.getAltura_Disponible(),
                    elemento.getUsuario_Id(),
                    elemento.getEstado_Id(),
                    elemento.getLongitud_Elemento_Id(),
                    elemento.getMaterial_Id(),
                    elemento.getProyecto_Id(),
                    elemento.getNivel_Tension_Elemento_Id(),
                    elemento.getCiudad_Id(),
                    Cables,
                    Equipos,
                    Perdidas,
                    Novedades,
                    Fotos
            );

            Call<Response_Post_Data_Sync> call;
            call = apiService.postDataSync(request_post_data_sync);
            /*
            Call<Response_Post_Data_Sync> call;   call = apiService.postDataSync(new Request_Post_Data_Sync(
                    elemento.getElemento_Id(),
                    elemento.getCodigo_Apoyo(),
                    1,
                    elemento.getFecha_Levantamiento(),
                    elemento.getHora_Inicio(),
                    elemento.getHora_Fin(),
                    elemento.getResistencia_Mecanica(),
                    elemento.getRetenidas(),
                    elemento.getAltura_Disponible(),
                    1,
                    elemento.getEstado_Id(),
                    elemento.getLongitud_Elemento_Id(),
                    elemento.getMaterial_Id(),
                    elemento.getProyecto_Id(),
                    elemento.getNivel_Tension_Elemento_Id(),
                    elemento.getCiudad_Id(),
                    Cables,
                    Perdidas,
                    Equipos,
                    Novedades,
                    Fotos
            ));*/

            call.enqueue(new Callback<Response_Post_Data_Sync>() {
                @Override
                public void onResponse(Call<Response_Post_Data_Sync> call, retrofit2.Response<Response_Post_Data_Sync> response) {
                    int statusCode = response.code();
                    if(statusCode==200){

                        Toast.makeText(UploadDataActivity.this,"Correcto",Toast.LENGTH_SHORT).show();
                        hideProgress();

                    }else{
                        //  progressBarIndeterminate.setVisibility(View.GONE);
                        hideProgress();
                        Toast.makeText(UploadDataActivity.this,"Error en la peticion",Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Response_Post_Data_Sync> call, Throwable t) {
                    // Log error here since request failed
                    //Log.e(TAG, t.toString());
                    hideProgress();
                    Toast.makeText(UploadDataActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
                }
            });



           /*
            Call<Material> call;   call = apiService.postMaterial(new Material(
                    0,
                    "David",
                    "D"

            ));

            call.enqueue(new Callback<Material>() {
                @Override
                public void onResponse(Call<Material> call, retrofit2.Response<Material> response) {
                    int statusCode = response.code();
                    if(statusCode==200){

                        Toast.makeText(UploadDataActivity.this,"Correcto",Toast.LENGTH_SHORT).show();

                        hideProgress();

                    }else{
                        //  progressBarIndeterminate.setVisibility(View.GONE);
                        hideProgress();
                        Toast.makeText(UploadDataActivity.this,"Error en la peticion",Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Material> call, Throwable t) {
                    // Log error here since request failed
                    //Log.e(TAG, t.toString());
                    hideProgress();
                    Toast.makeText(UploadDataActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
                }
            });
            */

        }

        else{
            onMessageOk(R.color.colorPrimary,"Verifique su conexion a Internet !");
            hideProgress();
        }


        //postDataAsync();
        //postDataSyncApiService.postDataAsync(this,post_data_sync);
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        //String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imgString;
    }


    //region Api Service
    /*-----------------------------------------------------------------------------------------------*/
    private void postDataAsync() {

    }




    //endregion


    //region Implements Methods Interface UploadDataMainView
    @Override
    public void showProgresss() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void showUIElements() {

    }

    @Override
    public void hideUIElements() {

    }

    @Override
    public void onMessageOk(int colorPrimary, String message) {
        int color = Color.WHITE;
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.container), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, colorPrimary));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done, 0, 0, 0);
        // textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin));
        textView.setTextColor(color);
        snackbar.show();
    }

    @Override
    public void onMessageError(int colorPrimary, String message) {
        onMessageOk(colorPrimary, message);
    }

    //endregion


    //region CHECK CONNECTION INTERNET
    // Method to manually check connection status
    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        return isConnected;
        //showSnack(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        DataTakeApp.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            onMessageOk(R.color.orange, getString(R.string.message_connection));
        } else {
            onMessageError(R.color.orange, getString(R.string.message_not_connection));
        }
    }


    //endregion

    //region Sync Post
    @Override
    public void processFinishPostDataAsync(Response_Post_Data_Sync response) {

        if(response.isSuccess()){

            onMessageOk(R.color.orange,"Sincronizado");
        }else{
            onMessageOk(R.color.orange,"Error Sincronizacion");
        }

        hideProgress();

    }


    //endregion
}
