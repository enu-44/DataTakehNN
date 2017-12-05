package com.datatakehnn.activities.sync.post_sync_activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.datatakehnn.R;
import com.datatakehnn.activities.cables_elemento.CablesElementoActivity;
import com.datatakehnn.activities.equipos_elemento.EquipoActivity;
import com.datatakehnn.activities.fotos.FotosActivity;
import com.datatakehnn.activities.perdida.PerdidaActivity;
import com.datatakehnn.activities.poste.PosteActivity;
import com.datatakehnn.controllers.CablesController;
import com.datatakehnn.controllers.ElementoController;
import com.datatakehnn.controllers.EquipoController;
import com.datatakehnn.controllers.FotoController;
import com.datatakehnn.controllers.NovedadController;
import com.datatakehnn.controllers.PerdidaController;
import com.datatakehnn.controllers.SincronizacionGetInformacionController;
import com.datatakehnn.controllers.UsuarioController;
import com.datatakehnn.models.element_model.Elemento;
import com.datatakehnn.models.elemento_cable.Elemento_Cable;
import com.datatakehnn.models.equipo_elemento_model.Equipo_Elemento;
import com.datatakehnn.models.foto_model.Foto;
import com.datatakehnn.models.material_model.Material;
import com.datatakehnn.models.novedad_model.Novedad;
import com.datatakehnn.models.perdida_model.Perdida;
import com.datatakehnn.models.request_data_sync_model.Request_Post_Data_Sync;
import com.datatakehnn.models.request_data_sync_model.Response_Post_Data_Sync;
import com.datatakehnn.models.request_data_sync_model.Sincronizacion;
import com.datatakehnn.models.request_data_sync_model.model_request.Foto_Request;
import com.datatakehnn.models.request_data_sync_model.model_request.Novedad_Request;
import com.datatakehnn.models.usuario_model.Usuario;
import com.datatakehnn.services.api_client.retrofit.ApiClient;
import com.datatakehnn.services.api_client.retrofit.ApiClientInterFace;
import com.datatakehnn.services.api_services.data_async_services.IPostDataSync;
import com.datatakehnn.services.api_services.data_async_services.PostDataSyncApiService;
import com.datatakehnn.services.aplication.DataTakeApp;
import com.datatakehnn.services.connection_internet.ConnectivityReceiver;
import com.datatakehnn.services.progress_sync_service.Constants;
import com.datatakehnn.services.progress_sync_service.ProgressSyncIntentService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class UploadDataActivity extends AppCompatActivity implements IPostDataSync,UploadDataMainView,ConnectivityReceiver.ConnectivityReceiverListener  {
    //UI Elements
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.viewEstateConect)
    View viewEstateConect;
    @BindView(R.id.txtconectividad)
    TextView txtconectividad;

    @BindView(R.id.txt_last_sincronizacion)
    TextView txt_last_sincronizacion;

    @BindView(R.id.txt_count_all_elements)
    TextView txt_count_all_elements;

    @BindView(R.id.txt_count_all_elements_sincronize)
    TextView txt_count_all_elements_sincronize;

    @BindView(R.id.txt_count_all_without_sincronize)
    TextView txt_count_all_without_sincronize;

    @BindView(R.id.progress_text)
    TextView progress_text;

    //Instances
    PostDataSyncApiService postDataSyncApiService;
    EquipoController equipoController;
    CablesController cablesController;
    NovedadController novedadController;
    PerdidaController perdidaController;
    ElementoController elementoController;
    FotoController fotoController;
    UsuarioController usuarioController;
    SincronizacionGetInformacionController sincronizacionGetInformacionController;

    //Variables
    static  boolean History_Sincronizacion_Register =true;
    Sincronizacion sincronizacionGlobal= new Sincronizacion();

    //Api
    ApiClientInterFace apiService = ApiClient.getClientAmazon().create(ApiClientInterFace.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);
        ButterKnife.bind(this);
        setToolbarInjection();
        setupInjection();



        // Filtro de acciones que serán alertadas
        IntentFilter filter = new IntentFilter(
                Constants.ACTION_RUN_ISERVICE);
        filter.addAction(Constants.ACTION_PROGRESS_EXIT);

        // Crear un nuevo ResponseReceiver
        ResponseReceiver receiver =
                new ResponseReceiver();
        // Registrar el receiver y su filtro
        LocalBroadcastManager.getInstance(this).registerReceiver(
                receiver,
                filter);

    }

    //region SETUP INJECTION

    private void setupInjection() {
        this.postDataSyncApiService= PostDataSyncApiService.getInstance(this);
        this.equipoController= EquipoController.getInstance(this);
        this.cablesController= CablesController.getInstance(this);
        this.novedadController= NovedadController.getInstance(this);
        this.perdidaController= PerdidaController.getInstance(this);
        this.elementoController= ElementoController.getInstance(this);
        this.fotoController= FotoController.getInstance(this);
        this.sincronizacionGetInformacionController= SincronizacionGetInformacionController.getInstance(this);
        this.usuarioController= UsuarioController.getInstance(this);
        checkLastSincronizacion();

    }


    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(getString(R.string.title_sincronizacion));
        if (getSupportActionBar() != null)// Habilitar Up Button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //endregion

    //region METHODS
    public void checkLastSincronizacion(){

        if(checkConnection()){
            viewEstateConect.setBackgroundResource(R.drawable.circle);
            txtconectividad.setText(getString(R.string.on_connectividad));
        }else{
            viewEstateConect.setBackgroundResource(R.drawable.circle_red);
            txtconectividad.setText(getString(R.string.off_connectividad));
        }

        txt_count_all_elements.setText(String.valueOf(sincronizacionGetInformacionController.getAllElementsFinished().size())+" Elementos");
        txt_count_all_elements_sincronize.setText(String.valueOf(sincronizacionGetInformacionController.getAllElementsSyncronized().size())+" Elementos");
        txt_count_all_without_sincronize.setText(String.valueOf(sincronizacionGetInformacionController.getAllElementsWithuotSync().size())+" Elementos");


        List<Sincronizacion> list = sincronizacionGetInformacionController.getAllHistorySincronizacion();

        //Si ya no exist e mas informacion por sincronizar se registra datos de la ultima sincronizacion completada
        Sincronizacion sincronizacion = sincronizacionGetInformacionController.getLastSincronizacion();
        if (sincronizacion != null) {
            try {
                SimpleDateFormat sdfStart = new SimpleDateFormat("H:mm");
                Date dateObjStart = sdfStart.parse(sincronizacion.getHora());
                ///System.out.println(dateObjStart);
                txt_last_sincronizacion.setText("Utima sincronizacion: " + sincronizacion.getFecha() + " " + new SimpleDateFormat("KK:mm a").format(dateObjStart));
            }catch (final ParseException e) {
                    e.printStackTrace();
            }
        }

    }


    public void verificateDataSync(){
        if(checkConnection()){
            Elemento elemento= elementoController.getElementoByIdAndBySync(true);
            if(elemento!=null){
                syncData(elemento);
            }else{
                hideProgress();
                onMessageOk(R.color.orange,"Toda la informacion se ha sincronizado correctamente");
            }
        }else{
            hideProgress();
            onMessageOk(R.color.orange,"Sin conexion a internet!");
        }
    }



    //endregion

    //region MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            ///Metodo que permite no recargar la pagina al devolverse
            case android.R.id.home:
                // Obtener intent de la actividad padre
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                // Comprobar si DetailActivity no se creó desde CourseActivity
                if (NavUtils.shouldUpRecreateTask(this, upIntent)
                        || this.isTaskRoot()) {

                    // Construir de nuevo la tarea para ligar ambas actividades
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        TaskStackBuilder.create(this)
                                .addNextIntentWithParentStack(upIntent)
                                .startActivities();
                    }
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Terminar con el método correspondiente para Android 5.x
                    this.finishAfterTransition();
                    return true;
                }

                //Para versiones anterios a 5.x
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    // Terminar con el método correspondiente para Android 5.x
                    onBackPressed();
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region EVENTS

    @OnClick(R.id.btnSyncData)
    public void onViewClicked() {
        showProgresss();
        verificateDataSync();
    }

    @OnClick(R.id.btnSyncDataService)
    public void onViewClickedService() {
        Intent intent = new Intent(this, ProgressSyncIntentService.class);
        intent.setAction(Constants.ACTION_RUN_ISERVICE);
        startService(intent);
    }


    // Broadcast receiver que recibe las emisiones desde los servicios
    private class ResponseReceiver extends BroadcastReceiver {
        // Sin instancias
        private ResponseReceiver() {
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.ACTION_RUN_ISERVICE:

                    progress_text.setText(intent.getExtras().getString("hora")+" - "+intent.getIntExtra(Constants.EXTRA_PROGRESS, -1) + "");
                    break;
                case Constants.ACTION_PROGRESS_EXIT:
                    progress_text.setText("Progreso");
                    break;
            }
        }
    }




    //endregion

    //region Api Service
    /*-----------------------------------------------------------------------------------------------*/
    private void syncData(Elemento elemento) {


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
            }else {
                foto_request.setImageArray(null);
            }

            foto_request.setNovedad_Id(foto.getNovedad_Id());
            foto_request.setElemento_Id(foto.getElemento_Id());

            Fotos.add(foto_request);
        }

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
            postDataSyncApiService.postDataAsync(this,request_post_data_sync);



            /*
            Call<Response_Post_Data_Sync> call;
            call = apiService.postDataSync(request_post_data_sync);
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

            */
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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        //String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imgString;
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
            checkLastSincronizacion();
        } else {
            onMessageError(R.color.orange, getString(R.string.message_not_connection));
            checkLastSincronizacion();
        }
    }


    //endregion

    //region Sync Post
    @Override
    public void processFinishPostDataAsync(Response_Post_Data_Sync response) {
        if(response.isSuccess()){
            registerSincronizacion(response);
            onMessageOk(R.color.orange,"Sincronizado: "+String.valueOf(response.getResult().getElemento_Id()));

        }else{
            onMessageOk(R.color.orange,response.getMessage());
            hideProgress();
        }
    }


    private void registerSincronizacion(Response_Post_Data_Sync response) {

        //Obtener Fecha
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String fecha = dateFormat.format(date);

        //Obtener Hora
        Calendar cal = Calendar.getInstance();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String hora = timeFormat.format(cal.getTime());

        Usuario usuarioLogued= usuarioController.getLoggedUser();
        String poste_id_local= String.valueOf(response.getResult().getElemento_Id());


        if(History_Sincronizacion_Register==true) {

            sincronizacionGlobal= sincronizacionGetInformacionController.getLastSincronizacion();
            if (sincronizacionGlobal == null) {
                sincronizacionGlobal.setSincronizacion_Id(1);
                History_Sincronizacion_Register=false;
            } else {
                sincronizacionGlobal.setSincronizacion_Id(sincronizacionGlobal.getSincronizacion_Id()+1);
                History_Sincronizacion_Register=false;
            }

            sincronizacionGlobal.setUsuario_Id( usuarioLogued.getUsuario_Id());
            sincronizacionGlobal.setFecha( fecha);
            sincronizacionGlobal.setHora( hora);
            sincronizacionGlobal.setCuenta(  usuarioLogued.getCorreo_Electronico());
            sincronizacionGlobal.setUsuario( usuarioLogued.getNombre()+" "+usuarioLogued.getApellido());
            sincronizacionGlobal.setCodigos_Elementos_Sync(poste_id_local);

            sincronizacionGetInformacionController.registerUpdateHistorySinconization(sincronizacionGlobal);
            sincronizacionGlobal= sincronizacionGetInformacionController.getLastSincronizacion();

        }else{
            sincronizacionGlobal.setUsuario_Id( usuarioLogued.getUsuario_Id());
            sincronizacionGlobal.setFecha( fecha);
            sincronizacionGlobal.setHora( hora);
            sincronizacionGlobal.setCuenta(  usuarioLogued.getCorreo_Electronico());
            sincronizacionGlobal.setUsuario( usuarioLogued.getNombre()+" "+usuarioLogued.getApellido());
            sincronizacionGlobal.setCodigos_Elementos_Sync(sincronizacionGlobal.getCodigos_Elementos_Sync()+","+poste_id_local);
            sincronizacionGetInformacionController.registerUpdateHistorySinconization(sincronizacionGlobal);
            sincronizacionGlobal= sincronizacionGetInformacionController.getLastSincronizacion();
        }


        Elemento elemento = elementoController.getElementoById(response.getResult().getElemento_Id());
        elemento.setIs_Sync(false);
        elementoController.update(elemento);
        checkLastSincronizacion();
        verificateDataSync();

    }


    //endregion
}
