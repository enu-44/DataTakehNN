package com.datatakehnn.activities.fotos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.datatakehnn.R;
import com.datatakehnn.activities.cables_elemento.CablesElementoActivity;
import com.datatakehnn.activities.equipos_elemento.EquipoActivity;
import com.datatakehnn.activities.poste.PosteActivity;
import com.datatakehnn.activities.poste.lista_postes_usuario.Poste_Usuario_Activity;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;

public class CamaraActivity extends AppCompatActivity {

    //UI Elemenets
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivFoto1)
    ImageView ivFoto1;
    @BindView(R.id.edtDescripcionFoto)
    EditText edtDescripcionFoto;

    // Create global camera reference in an activity or fragment

    //PERMISSION CAMARA
    private MagicalPermissions magicalPermissions;

    //Configuration Camara
    //The pixel percentage is declare like an percentage of 100, if your value is 50, the photo will have the middle quality of your camera.
    // this value could be only 1 to 100.
    private int RESIZE_PHOTO_PIXELS_PERCENTAGE = 50;
    MagicalCamera magicalCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
        ButterKnife.bind(this);
        setToolbarInjection();
        checkPermissionCamera();
    }


    //region SETUP INJECTION
    private void setToolbarInjection() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Camara");
    }

    //endregion

    //region PERMISSION
    private void checkPermissionCamera() {
        String[] permissions = new String[] {
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

    //region EVENTS
    @OnClick({R.id.ivFoto1})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.ivFoto1:
                iniciarCamara();
                break;
        }
    }

    //endregion

    //region METHODS
    private void iniciarCamara() {
        magicalCamera= new MagicalCamera(this,RESIZE_PHOTO_PIXELS_PERCENTAGE, magicalPermissions);
        //take photo
        magicalCamera.takePhoto();

        //select picture
        ///magicalCamera.selectedPicture("my_header_name");
    }

    //endregion


    //region OVERRIDES METHODS
    // Get the bitmap and image path onActivityResult of an activity or fragment
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //CALL THIS METHOD EVER
            //magicalCamera.setResizePhoto(100);
            magicalCamera.resultPhoto(requestCode, resultCode, data);
            Bitmap bitmap  = magicalCamera.getPhoto();
            //this is for rotate picture in this method
            //magicalCamera.resultPhoto(requestCode, resultCode, data, MagicalCamera.ORIENTATION_ROTATE_180);
            //with this form you obtain the bitmap (in this example set this bitmap in image view)
            ivFoto1.setImageBitmap(magicalCamera.getPhoto());

            //if you need save your bitmap in device use this method and return the path if you need this
            //You need to send, the bitmap picture, the photo name, the directory name, the picture type, and autoincrement photo name if           //you need this send true, else you have the posibility or realize your standard name for your pictures.
            String ruta = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(),"foto","DataTakeCamara", MagicalCamera.JPEG, false);
            File file =saveBitmap(bitmap,ruta);

            Bitmap compressedImage = null;

            try {
                compressedImage = new Compressor(this)
                        .setMaxHeight(400)
                        .setQuality(100)
                        .compressToBitmap(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String path = magicalCamera.savePhotoInMemoryDevice(compressedImage,"foto","DataTakeCamara", MagicalCamera.JPEG, false);

            if(path != null){
                Toast.makeText(this, "The photo is save in device, please check this path: " + path, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Sorry your photo dont write in devide", Toast.LENGTH_SHORT).show();
            }

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



    // The bitmap is saved in the app's folder
    //  If the saved bitmap is not required use following code
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    //endregion

    //region MENU
    /*-------------------------------------------------------------------------------------------------------*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_camara, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
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

    //endregion
}
