package com.datatakehnn.services.progress_sync_service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.datatakehnn.R;
import com.datatakehnn.activities.sync.post_sync_activity.UploadDataMainView;
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
import com.datatakehnn.models.novedad_model.Novedad;
import com.datatakehnn.models.perdida_model.Perdida;
import com.datatakehnn.models.request_data_sync_model.Request_Post_Data_Sync;
import com.datatakehnn.models.request_data_sync_model.Response_Post_Data_Sync;
import com.datatakehnn.models.request_data_sync_model.Sincronizacion;
import com.datatakehnn.models.request_data_sync_model.model_request.Foto_Request;
import com.datatakehnn.models.request_data_sync_model.model_request.Novedad_Request;
import com.datatakehnn.models.usuario_model.Usuario;
import com.datatakehnn.services.api_services.data_async_services.IPostDataSync;
import com.datatakehnn.services.api_services.data_async_services.PostDataSyncApiService;
import com.datatakehnn.services.aplication.DataTakeApp;
import com.datatakehnn.services.connection_internet.ConnectivityReceiver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by usuario on 5/12/2017.
 */

public class ProgressSyncIntentService extends IntentService implements IPostDataSync,ConnectivityReceiver.ConnectivityReceiverListener   {
    private static final String TAG = ProgressSyncIntentService.class.getSimpleName();

    PostDataSyncApiService postDataSyncApiService;
    EquipoController equipoController;
    CablesController cablesController;
    NovedadController novedadController;
    PerdidaController perdidaController;
    ElementoController elementoController;
    FotoController fotoController;
    UsuarioController usuarioController;
    SincronizacionGetInformacionController sincronizacionGetInformacionController;

    static  boolean History_Sincronizacion_Register =true;
    Sincronizacion sincronizacionGlobal= new Sincronizacion();


    NotificationCompat.Builder builder= new NotificationCompat.Builder(this);
    private NotificationManager mNotificationManager;


    int FilesAllCount=0;
    int CurrentFile=0;

    public ProgressSyncIntentService() {
        super("ProgressIntentService");

        this.postDataSyncApiService= PostDataSyncApiService.getInstance(this);
        this.equipoController= EquipoController.getInstance(this);
        this.cablesController= CablesController.getInstance(this);
        this.novedadController= NovedadController.getInstance(this);
        this.perdidaController= PerdidaController.getInstance(this);
        this.elementoController= ElementoController.getInstance(this);
        this.fotoController= FotoController.getInstance(this);
        this.sincronizacionGetInformacionController= SincronizacionGetInformacionController.getInstance(this);
        this.usuarioController= UsuarioController.getInstance(this);

    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (Constants.ACTION_RUN_ISERVICE.equals(action)) {
                handleActionRun();
            }
        }
    }


    /**
     * Maneja la acción de ejecución del servicio
     */
    private void handleActionRun() {
                   // Se construye la notificación
                 /*  builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(android.R.drawable.stat_sys_download_done)
                    .setContentTitle("Servicio en segundo plano")
                    .setContentText("Procesando...");*/
      /*  Notification notify = new Notification.Builder(this)
                .setContentTitle("Servicio en segundo plano")
                .setContentText("Procesando...")
                .setSmallIcon(R.drawable.logo_datatakeh)
                ///.setLargeIcon(aBitmap)
                .build();
                */

       // mBuilder.setContentIntent(contentIntent);
        ////mNotificationManager.notify(1, mBuilder.build());
        FilesAllCount=0;


        Usuario usuarioLogued= usuarioController.getLoggedUser();
        FilesAllCount= elementoController.getListElementoSync(false,usuarioLogued.getUsuario_Id(),true).size();

        //register Progress
        // Poner en primer plano
       /* builder.setProgress(FilesAllCount, CurrentFile, false);
        startForeground(1, builder.build());*/

        /*Intent localIntent = new Intent(Constants.ACTION_RUN_ISERVICE)
                .putExtra("MESSAGE_RUN","Iniciando Sincronizacion");
        // Emisión de {@code localIntent}
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);*/


        // Retardo de 1 segundo en la iteración
        verificateDataSync();

    }

    private void closedNotify() {
        // Quitar de primer plano
        stopForeground(true);
    }


    @Override
    public void onDestroy() {
        // Emisión para avisar que se terminó el servicio
       // Intent localIntent = new Intent(Constants.ACTION_PROGRESS_EXIT);
      //  LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }


    //region METHODS
    public void verificateDataSync(){
        if(checkConnection()){
            Usuario usuarioLogued= usuarioController.getLoggedUser();
            Elemento elemento= elementoController.getElementoByIdAndBySync(false,usuarioLogued.getUsuario_Id(),true);
            if(elemento!=null){
                syncData(elemento);
            }else{
                if(FilesAllCount==0){
                    restartVariuablesGloablService("Sin elementos para sincronizar");
                }else{
                    restartVariuablesGloablService("Elementos Sincronizados");
                }
            }
        }else{
            restartVariuablesGloablService("Sin conexion a internet!");
        }
    }

    public void restartVariuablesGloablService(String message){
        FilesAllCount=0;
        CurrentFile=0;
        History_Sincronizacion_Register =true;
        sincronizacionGlobal= new Sincronizacion();
        closedNotify();
        ///Toast.makeText(this, "Servicio destruido...", Toast.LENGTH_SHORT).show();
        //onMessageOk(R.color.orange,"Servicio destruido...");
        // Emisión para avisar que se terminó el servicio
        Intent localIntent = new Intent(Constants.ACTION_PROGRESS_EXIT).putExtra("MESSAGE_EXIT",message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        Log.d(TAG, "Servicio destruido...");
    }

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
                    String.format(elemento.getLatitud()+","+elemento.getLongitud()),
                    elemento.getLatitud(),
                    elemento.getLongitud(),
                    elemento.getDireccion(),
                    getAddressGps(elemento.getLatitud(),elemento.getLongitud()),
                    "",
                    "",
                    "",
                    elemento.getReferencia_Localizacion(),
                    elemento.getImei_Device(),
                    elemento.getToken_Elemento(),
                    Cables,
                    Equipos,
                    Perdidas,
                    Novedades,
                    Fotos
            );

            postDataSyncApiService.postDataAsync(this,request_post_data_sync);

        }
        else{
            restartVariuablesGloablService("Verifique su conexion a Internet !");
        }
    }


    public String getAddressGps(double latitud, double longitud) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        String direccion_gps="";
        if (latitud != 0.0 && longitud != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        latitud, longitud, 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    direccion_gps=DirCalle.getAddressLine(0);

                    //String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                   // String city = addresses.get(0).getLocality();
                    //String state = addresses.get(0).getAdminArea();
                    //String country = addresses.get(0).getCountryName();
                   // String postalCode = addresses.get(0).getPostalCode();
                   // String knownName = addresses.get(0).getFeatureName();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return  direccion_gps;
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

    //region CHECK CONNECTION INTERNET
    // Method to manually check connection status
    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        return isConnected;
        //showSnack(isConnected);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
        } else {
        }
    }

    //endregion


    //region Sync Post
    @Override
    public void processFinishPostDataAsync(Response_Post_Data_Sync response) {

        if(response.isSuccess()){
            registerSincronizacion(response);
            //onMessageOk(R.color.orange,"Sincronizado: "+String.valueOf(response.getResult().getElemento_Id()));
        }else{
            restartVariuablesGloablService(response.getMessage());
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

            sincronizacionGlobal= sincronizacionGetInformacionController.getLastSincronizacion(usuarioLogued.getUsuario_Id());
            if (sincronizacionGlobal == null) {
                sincronizacionGlobal=new Sincronizacion();
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
            sincronizacionGlobal= sincronizacionGetInformacionController.getLastSincronizacion(usuarioLogued.getUsuario_Id());

        }else{
            sincronizacionGlobal.setUsuario_Id( usuarioLogued.getUsuario_Id());
            sincronizacionGlobal.setFecha( fecha);
            sincronizacionGlobal.setHora( hora);
            sincronizacionGlobal.setCuenta(  usuarioLogued.getCorreo_Electronico());
            sincronizacionGlobal.setUsuario( usuarioLogued.getNombre()+" "+usuarioLogued.getApellido());
            sincronizacionGlobal.setCodigos_Elementos_Sync(sincronizacionGlobal.getCodigos_Elementos_Sync()+","+poste_id_local);
            sincronizacionGetInformacionController.registerUpdateHistorySinconization(sincronizacionGlobal);
            sincronizacionGlobal= sincronizacionGetInformacionController.getLastSincronizacion(usuarioLogued.getUsuario_Id());
        }


        Elemento elemento = elementoController.getElementoById(response.getResult().getElemento_Id());
        elemento.setIs_Sync(true);
        elementoController.update(elemento);


        CurrentFile=CurrentFile+1;
        //register Progress
        // Poner en primer plano
       // builder.setProgress(FilesAllCount, CurrentFile, false);
       // startForeground(1, builder.build());
        Intent localIntent = new Intent(Constants.ACTION_RUN_ISERVICE)
                .putExtra("MESSAGE_RUN","Sincronizados:")
                .putExtra(Constants.EXTRA_PROGRESS, CurrentFile);

        // Emisión de {@code localIntent}
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        // Retardo de 1 segundo en la iteración
        verificateDataSync();

    }
    //endregion

}
