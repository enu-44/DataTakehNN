package com.datatakehnn.activities.fotos;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.datatakehnn.R;
import com.datatakehnn.activities.cables_elemento.adapter.AdapterCablesElemento;
import com.datatakehnn.activities.fotos.adapter.AdapterFotos;
import com.datatakehnn.activities.fotos.adapter.OnItemClickListenerFoto;
import com.datatakehnn.activities.fotos.adapter.RecyclerAdapterFoto;
import com.datatakehnn.activities.menu.MainMenuActivity;
import com.datatakehnn.controllers.ElementoController;
import com.datatakehnn.controllers.FotoController;
import com.datatakehnn.controllers.NovedadController;
import com.datatakehnn.models.element_model.Elemento;
import com.datatakehnn.models.elemento_cable.Elemento_Cable;
import com.datatakehnn.models.foto_model.Foto;
import com.datatakehnn.models.novedad_model.Novedad;
import com.datatakehnn.models.tipo_noveda_model.Tipo_Novedad;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.raizlabs.android.dbflow.data.Blob;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;

public class FotosActivity extends AppCompatActivity implements OnItemClickListenerFoto, FotosMainView, SwipeRefreshLayout.OnRefreshListener {

    //UI Elemenets
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivFoto1)
    ImageView ivFoto1;
    @BindView(R.id.edtDescripcionFoto1)
    EditText edtDescripcionFoto1;
    @BindView(R.id.ivFoto2)
    ImageView ivFoto2;
    @BindView(R.id.edtDescripcionFoto2)
    EditText edtDescripcionFoto2;
    @BindView(R.id.ibVerFoto1)
    ImageButton ibVerFoto1;
    @BindView(R.id.ibVerFoto2)
    ImageButton ibVerFoto2;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.txtResults)
    TextView txtResults;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    //PERMISSION CAMARA
    private MagicalPermissions magicalPermissions;
    private int RESIZE_PHOTO_PIXELS_PERCENTAGE = 50;
    MagicalCamera magicalCamera;

    //Variables globales
    boolean tomarFoto1 = false;
    boolean tomarFoto2 = false;
    boolean tomarFotoNovedad = false;
    boolean yaTomoFoto1 = false;
    boolean yaTomoFoto2 = false;
    //String ruta, path;
    Double Latitud, Longitud;
    String latitud, longitud;
    String ruta_foto_1;
    String ruta_foto_2;
    String ruta_foto_novedad;
    //byte[] image;
    //Bitmap compressedImage = null;

    //Instancias
    ElementoController elementoController;
    FotoController fotoController;
    NovedadController novedadController;


    //Dataset
    List<Novedad> novedades = new ArrayList<>();
    Novedad novedad = new Novedad();
    public String Nombre_Novedad;
    public String Nombre_Tipo_Novedad;

    //contador de fotos
    int contador = 0;

    //Adapter
    AdapterFotos adapter;

    //Accion
    boolean ACCION_ADD;
    boolean ACCION_UPDATE;

    //Variables
    long Elemento_Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);
        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        checkPermissionCamera();
        setupInjection();
        setToolbarInjection();
        initAdapter();
        initRecyclerView();
        loadNovedades();
        verificateDataAction();
    }


    //region SETUP INJECTION
    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (ACCION_UPDATE) {
            if (getSupportActionBar() != null)// Habilitar Up Button
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);//devolver
        }
        toolbar.setTitle(getString(R.string.title_fotos));
    }

    private void setupInjection() {
        //Actualizar o Eliminar
        ACCION_ADD = getIntent().getExtras().getBoolean("ACCION_ADD");
        ACCION_UPDATE = getIntent().getExtras().getBoolean("ACCION_UPDATE");
        Elemento_Id = getIntent().getExtras().getLong("Elemento_Id");

        this.elementoController = ElementoController.getInstance(this);
        this.fotoController = FotoController.getInstance(this);
        this.novedadController = NovedadController.getInstance(this);
        //novedades = novedadController.getListNovedadesByElementoId(elemento_id);
        Elemento elemento = elementoController.getElementoById(Elemento_Id);
        Latitud = elemento.getLatitud();
        Longitud = elemento.getLongitud();
        latitud = String.valueOf(Latitud);
        longitud = String.valueOf(Longitud);
    }

    private void verificateDataAction() {
        if (ACCION_UPDATE) {
            contador = novedades.size();
            List<Foto> listFotoByElemento = fotoController.getListFotoByElemento(Elemento_Id);
            int i = 0;
            for (Foto item : listFotoByElemento) {
                i = i + 1;
                ///Asignar foto 1
                if (i == 1) {
                    try {
                        if (item.getImage() != null) {
                            yaTomoFoto1 = true;
                            byte[] foto = item.getImage().getBlob();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
                            ivFoto1.setImageBitmap(bitmap);
                            edtDescripcionFoto1.setText(item.getDescripcion());
                            ruta_foto_1 = item.getRuta_Foto();
                        }
                    } catch (Exception ex) {
                        String error = ex.toString();
                    }
                    ///Asignar foto 2
                } else {
                    try {
                        if (item.getImage() != null) {
                            yaTomoFoto2 = true;
                            byte[] foto = item.getImage().getBlob();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
                            ivFoto2.setImageBitmap(bitmap);
                            edtDescripcionFoto2.setText(item.getDescripcion());
                            ruta_foto_2 = item.getRuta_Foto();
                        }
                    } catch (Exception ex) {

                        String error = ex.toString();
                    }
                }

            }
        } else {
            contador = 0;
        }

    }

    //endregion

    //region PERMISSION
    private void checkPermissionCamera() {
        String[] permissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        magicalPermissions = new MagicalPermissions(this, permissions);

        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Map<String, Boolean> map = magicalPermissions.permissionResult(requestCode, permissions, grantResults);
        for (String permission : map.keySet()) {
            Log.d("PERMISSIONS", permission + " was: " + map.get(permission));
        }
        //Following the example you could also
        //locationPermissions(requestCode, permissions, grantResults);
    }

    //endregion

    //region EVENTS
    @OnClick({R.id.ivFoto1, R.id.ivFoto2, R.id.ibVerFoto1, R.id.ibVerFoto2})
    public void onViewClicked(View view) {

        boolean cancel_ = false;
        View focusView_ = null;
        switch (view.getId()) {
            case R.id.ivFoto1:
                tomarFoto1 = true;
                tomarFoto2 = false;
                tomarFotoNovedad = false;
                if (TextUtils.isEmpty(edtDescripcionFoto1.getText().toString())) {
                    edtDescripcionFoto1.setError(getString(R.string.error_field_required));
                    focusView_ = edtDescripcionFoto1;
                    cancel_ = true;
                } else {
                    iniciarCamara();
                }
                break;
            case R.id.ivFoto2:
                tomarFoto2 = true;
                tomarFoto1 = false;
                tomarFotoNovedad = false;
                if (TextUtils.isEmpty(edtDescripcionFoto2.getText().toString())) {
                    edtDescripcionFoto2.setError(getString(R.string.error_field_required));
                    focusView_ = edtDescripcionFoto2;
                    cancel_ = true;
                } else {
                    iniciarCamara();
                }
                break;
            case R.id.ibVerFoto1:
                if (ACCION_ADD == true) {
                    if (yaTomoFoto1 == true) {
                        //String ruta_foto = "IMG_POSTE_" + "Lat:" + latitud + "_Lng:" + longitud + "_" + edtDescripcionFoto1.getText().toString();
                        verFoto1();
                    } else {
                        Snackbar.make(container, "No ha tomado Foto", Snackbar.LENGTH_SHORT).show();
                    }
                } else if (ACCION_UPDATE == true) {
                    verFoto1();
                }

                break;
            case R.id.ibVerFoto2:
                if (ACCION_ADD == true) {
                    if (yaTomoFoto2 == true) {
                        //String ruta_foto = "IMG_POSTE_" + "Lat:" + latitud + "_Lng:" + longitud + "_" + edtDescripcionFoto1.getText().toString();
                        verFoto2();
                    } else {
                        Snackbar.make(container, "No ha tomado Foto", Snackbar.LENGTH_SHORT).show();
                    }
                } else if (ACCION_UPDATE == true) {
                    verFoto2();
                }
                break;
        }
    }

    //endregion

    //region Methods
    private void verFoto1() {
        Foto verFoto1 = fotoController.getByRutaFotoAndElementoId(ruta_foto_1, Elemento_Id);
        if (verFoto1 != null) {
            String rutaFoto1 = verFoto1.getRuta_Foto();
            File fileFoto1 = new File(rutaFoto1);
            Uri uriFoto1 = Uri.fromFile(fileFoto1);
            Intent viewFoto1 = new Intent(android.content.Intent.ACTION_VIEW, uriFoto1);
            viewFoto1.setDataAndType(uriFoto1, "image/*");
            startActivity(viewFoto1);
        } else {
            Snackbar.make(container, "No hay foto Registrada", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void verFoto2() {
        Foto verFoto2 = fotoController.getByRutaFotoAndElementoId(ruta_foto_2, Elemento_Id);
        if (verFoto2 != null) {
            String rutaFoto2 = verFoto2.getRuta_Foto();
            File fileFoto2 = new File(rutaFoto2);
            Uri uriFoto2 = Uri.fromFile(fileFoto2);
            Intent viewFoto2 = new Intent(android.content.Intent.ACTION_VIEW, uriFoto2);
            viewFoto2.setDataAndType(uriFoto2, "image/*");
            startActivity(viewFoto2);
        } else {
            Snackbar.make(container, "No hay foto Registrada", Snackbar.LENGTH_SHORT).show();
        }
    }


    private void iniciarCamara() {
        magicalCamera = new MagicalCamera(this, RESIZE_PHOTO_PIXELS_PERCENTAGE, magicalPermissions);
        //take photo
        try {
            magicalCamera.takePhoto();
        } catch (Exception e) {
            e.getMessage().toString();
        }
        //select picture
        ///magicalCamera.selectedPicture("my_header_name");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {


            //CALL THIS METHOD EVER
            //magicalCamera.setResizePhoto(100);
            if (data == null) {
                //  Snackbar.make(container, "data Null", Snackbar.LENGTH_SHORT).show();
            }

            if (magicalCamera == null) {
                Snackbar.make(container, "magicalCamera Null", Snackbar.LENGTH_SHORT).show();
                return;
            }

            magicalCamera.resultPhoto(requestCode, resultCode, data);

            Bitmap bitmap = magicalCamera.getPhoto();
            //this is for rotate picture in this method
            //magicalCamera.resultPhoto(requestCode, resultCode, data, MagicalCamera.ORIENTATION_ROTATE_180);
            //with this form you obtain the bitmap (in this example set this bitmap in image view)
            String ruta = "";
            String path = "";
            if (tomarFoto1 == true) {
                ivFoto1.setImageBitmap(magicalCamera.getPhoto());
                //if you need save your bitmap in device use this method and return the path if you need this
                //You need to send, the bitmap picture, the photo name, the directory name, the picture type, and autoincrement photo name if           //you need this send true, else you have the posibility or realize your standard name for your pictures.
                ruta = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(), "IMG_POSTE_" + "Lat:" + latitud + "_Lng:" + longitud + "_" + edtDescripcionFoto1.getText().toString(), "DataTakeCamara/" + Elemento_Id, MagicalCamera.JPEG, false);
            } else if (tomarFoto2 == true) {
                ivFoto2.setImageBitmap(magicalCamera.getPhoto());
                //if you need save your bitmap in device use this method and return the path if you need this
                //You need to send, the bitmap picture, the photo name, the directory name, the picture type, and autoincrement photo name if           //you need this send true, else you have the posibility or realize your standard name for your pictures.
                ruta = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(), "IMG_POSTE_" + "Lat:" + latitud + "_Lng:" + longitud + "_" + edtDescripcionFoto2.getText().toString(), "DataTakeCamara/" + Elemento_Id, MagicalCamera.JPEG, false);
            } else if (tomarFotoNovedad == true) {
                ruta = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(), "IMG_NOVEDAD_" + "Lat:" + latitud + "_Lng:" + longitud + novedad.getNovedad_Id() + Nombre_Novedad, "DataTakeCamara/" + Elemento_Id, MagicalCamera.JPEG, false);
            }

            File file = saveBitmap(bitmap, ruta);
            try {
                Bitmap compressedImage = new Compressor(this)
                        .setMaxHeight(400)
                        .setQuality(100)
                        .compressToBitmap(file);

                compressedImage = getEncoded64ImageStringFromBitmap(compressedImage);


                if (tomarFoto1 == true) {
                    path = magicalCamera.savePhotoInMemoryDevice(compressedImage, "IMG_POSTE_" + "Lat:" + latitud + "_Lng:" + longitud + "_" + edtDescripcionFoto1.getText().toString(), "DataTakeCamara/" + Elemento_Id, MagicalCamera.JPEG, false);
                    registerFoto1(compressedImage, path);


                } else if (tomarFoto2 == true) {
                    path = magicalCamera.savePhotoInMemoryDevice(compressedImage, "IMG_POSTE_" + "Lat:" + latitud + "_Lng:" + longitud + "_" + edtDescripcionFoto2.getText().toString(), "DataTakeCamara/" + Elemento_Id, MagicalCamera.JPEG, false);
                    registerFoto2(compressedImage, path);
                } else if (tomarFotoNovedad == true) {

                    path = magicalCamera.savePhotoInMemoryDevice(compressedImage, "IMG_NOVEDAD_" + "Lat:" + latitud + "_Lng:" + longitud + novedad.getNovedad_Id() + Nombre_Novedad, "DataTakeCamara/" + Elemento_Id, MagicalCamera.JPEG, false);
                    updateFotoNovedad(compressedImage, path);
                }

                if (path != null) {
                    //  Toast.makeText(this, "La foto se guardó en la siguiente ruta: " + path, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Sorry your photo dont write in device", Toast.LENGTH_SHORT).show();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            Snackbar.make(container, "Error de cámara", Snackbar.LENGTH_SHORT).show();
        }

    }


    public Bitmap getEncoded64ImageStringFromBitmap(Bitmap bitmapCompressed) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapCompressed.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] byteFormat = stream.toByteArray();
        // let this be your byte array
        Bitmap newBitmap = BitmapFactory.decodeByteArray(byteFormat, 0, byteFormat.length);
        return newBitmap;
        // get the base 64 string
       /* String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        //String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imgString;*/
    }


    private void updateFotoNovedad(Bitmap compressedImage, String path) {
        String fecha = obtenerFecha();
        String hora = obtenerHora();
        byte[] image = convertBitmapToByte(compressedImage);
        Blob imagenBlob = new Blob(image);
        ruta_foto_novedad = path;
        novedad.setRuta_Foto(ruta_foto_novedad);
        novedad.setImage_Novedad(imagenBlob);
        novedad.setFecha_Creacion(fecha);
        novedad.setHora(hora);
        novedadController.updateWithFoto(novedad);
        loadNovedades();
    }

    private void registerFoto1(Bitmap compressedImage, String path) {
        String fecha = obtenerFecha();
        String hora = obtenerHora();
        ruta_foto_1 = path;
        Foto hayFoto = fotoController.getByRutaFotoAndElementoId(ruta_foto_1, Elemento_Id);
        if (hayFoto != null) {
            hayFoto.setDescripcion(edtDescripcionFoto1.getText().toString());
            byte[] image = convertBitmapToByte(compressedImage);
            Blob imagenBlob = new Blob(image);
            hayFoto.setImage(imagenBlob);
            hayFoto.setFecha_Creacion(fecha);
            hayFoto.setHora(hora);
            fotoController.update(hayFoto);
            yaTomoFoto1 = true;
        } else {
            Foto foto = new Foto();
            foto.setElemento_Id(Elemento_Id);
            foto.setDescripcion(edtDescripcionFoto1.getText().toString());
            byte[] image = convertBitmapToByte(compressedImage);
            Blob imagenBlob = new Blob(image);
            foto.setImage(imagenBlob);
            foto.setRuta_Foto(ruta_foto_1);
            foto.setFecha_Creacion(fecha);
            foto.setHora(hora);
            fotoController.register(foto);
            yaTomoFoto1 = true;
        }
    }

    private void registerFoto2(Bitmap compressedImage, String path) {
        String fecha = obtenerFecha();
        String hora = obtenerHora();
        ruta_foto_2 = path;
        Foto hayFoto = fotoController.getByRutaFotoAndElementoId(ruta_foto_2, Elemento_Id);
        if (hayFoto != null) {
            hayFoto.setDescripcion(edtDescripcionFoto2.getText().toString());
            byte[] image = convertBitmapToByte(compressedImage);
            Blob imagenBlob = new Blob(image);
            hayFoto.setImage(imagenBlob);
            hayFoto.setFecha_Creacion(fecha);
            hayFoto.setHora(hora);
            fotoController.update(hayFoto);
            yaTomoFoto2 = true;
        } else {
            Foto foto = new Foto();
            foto.setElemento_Id(Elemento_Id);
            foto.setDescripcion(edtDescripcionFoto2.getText().toString());
            byte[] image = convertBitmapToByte(compressedImage);
            Blob imagenBlob = new Blob(image);
            foto.setImage(imagenBlob);
            foto.setFecha_Creacion(fecha);
            foto.setHora(hora);
            foto.setRuta_Foto(ruta_foto_2);
            fotoController.register(foto);
            yaTomoFoto2 = true;
        }
    }

    //Convertir bitmap to File
    private File saveBitmap(Bitmap bitmap, String path) {
        File file = null;
        if (bitmap != null) {
            file = new File(path);
        }
        return file;
    }

    private void validarCampos() {
        if (yaTomoFoto1 == false) {
            onMessageError(R.color.colorPrimary, "Debe tomar Foto 1");
        } else if (yaTomoFoto2 == false) {
            onMessageError(R.color.colorPrimary, "Debe tomar Foto 2");
        } else if (contador < novedades.size()) {
            onMessageError(R.color.colorPrimary, "Debe registrar todas las fotos de novedades");
        } else {
            actualizarPoste();
        }
    }

    private void actualizarPoste() {

        //Snackbar.make(container, "Fotos Registradas", Snackbar.LENGTH_SHORT).show();
        final AlertDialog.Builder builder = new AlertDialog.Builder(FotosActivity.this);
        builder.setTitle("Notificación");
        builder.setMessage("¿Confirma todos los datos?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*try {
                    ((SyncActivity) syncActivity).iniciarServicioUbicacion();
                } catch (Exception e) {
                    e.getMessage().toString();
                }*/

                if (ACCION_UPDATE) {
                    Elemento elemento = elementoController.getElementoById(Elemento_Id);
                    String horaFin = obtenerHora();
                    elemento.setHora_Fin(horaFin);
                    elemento.setIs_Finished(true);
                    elementoController.update(elemento);
                    onReturnActivity();

                } else {

                    Elemento elemento = elementoController.getElementoById(Elemento_Id);
                    String horaFin = obtenerHora();
                    elemento.setHora_Fin(horaFin);
                    elemento.setIs_Finished(true);
                    elementoController.update(elemento);
                    startActivity(new Intent(getBaseContext(), MainMenuActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                }


            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String obtenerHora() {
        //Obtener Hora
        Calendar cal = Calendar.getInstance();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String hora = timeFormat.format(cal.getTime());
        return hora;
    }

    private String obtenerFecha() {
        //Obtener Fecha
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String fecha = dateFormat.format(date);
        return fecha;
    }

    public byte[] convertBitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new AdapterFotos(this, new ArrayList<Novedad>(), this);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadNovedades() {
        adapter.clear();
        //novedades.clear();
        novedades = new ArrayList<>();
        novedades = novedadController.getListNovedadesByElementoId(Elemento_Id);
        setContent(novedades);
        resultsList(novedades);
        hideProgress();


    }

    //endregion

    //region MENU
    /*-------------------------------------------------------------------------------------------------------*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_foto, menu);
        if (ACCION_UPDATE) {

            Elemento elemento = elementoController.getElementoById(Elemento_Id);
            if (!elemento.isIs_Finished()) {
                MenuItem item = menu.findItem(R.id.action_done);
                item.setVisible(true);
            } else {
                MenuItem item = menu.findItem(R.id.action_done);
                item.setVisible(false);
            }

        } else {
            MenuItem item = menu.findItem(R.id.action_done);
            item.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_done:
                validarCampos();
                break;

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

    private void onReturnActivity() {
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

        }

        //Para versiones anterios a 5.x
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Terminar con el método correspondiente para Android 5.x
            onBackPressed();

        }
    }


    //endregion

    //region IMPLEMENTS ONITEM CLICK LISTENER
    @Override
    public void onItemClick(Novedad item) {
        tomarFoto2 = false;
        tomarFoto1 = false;
        tomarFotoNovedad = true;
        Nombre_Novedad = item.getDetalle_Tipo_Novedad_Nombre();
        Nombre_Tipo_Novedad = novedadController.getTipoNovedadById(item.getTipo_Novedad_Id()).getNombre();
        novedad = item;
        if (item.getImage_Novedad() == null) {
            contador = contador + 1;
        }
        iniciarCamara();
    }

    @Override
    public void onItemClickVisor(Novedad item) {
        novedad = item;
        String rutaFotoNovedad = novedad.getRuta_Foto();
        if (rutaFotoNovedad != null) {
            File fileFotoNovedad = new File(rutaFotoNovedad);
            Uri uriFotoNovedad = Uri.fromFile(fileFotoNovedad);
            Intent viewFotoNovedad = new Intent(android.content.Intent.ACTION_VIEW, uriFotoNovedad);
            viewFotoNovedad.setDataAndType(uriFotoNovedad, "image/*");
            startActivity(viewFotoNovedad);
        } else {
            Snackbar.make(container, "No hay Foto registrada", Snackbar.LENGTH_SHORT).show();
        }

    }

    //endregion

    //region IMPLEMENTS METHODS FotosMainView
    @Override
    public void showProgresss() {
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
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

    @Override
    public void resultsList(List<Novedad> listResult) {
        String results = String.format(getString(R.string.results_global_search),
                listResult.size());
        txtResults.setText(results);
    }

    @Override
    public void setContent(List<Novedad> items) {
        adapter.setItems(items);
    }
    //endregion


    //region METHODS OVERRIDES
    @Override
    public void onRefresh() {
        showProgresss();
        loadNovedades();
    }

    //endregion

}
