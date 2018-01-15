package com.datatakehnn.activities.master;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.datatakehnn.R;
import com.datatakehnn.services.device_information.Equipment_Identifier;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceMasterActivity extends AppCompatActivity {

    Equipment_Identifier equipment_Identifier;

    //Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txtAndroid_Id)
    TextView txtAndroid_Id;

    @BindView(R.id.txtDevice_Id)
    TextView txtDevice_Id;

    @BindView(R.id.txtSoftware_Version)
    TextView txtSoftware_Version;

    @BindView(R.id.txtLocal_Ip_Address)
    TextView txtLocal_Ip_Address;

    @BindView(R.id.txtAndroidVersion)
    TextView txtAndroidVersion;

    @BindView(R.id.txtMacAddr)
    TextView txtMacAddr;

    @BindView(R.id.txtDeviceName)
    TextView txtDeviceName;

    @BindView(R.id.txtIpRemote)
    TextView txtIpRemote;

    //Permission
    public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_master);

        ButterKnife.bind(this);
        setToolbar();
        loadInstances();

        //requestReadPhoneStatePermission();
        checkPermission();
    }

    private void loadInstances() {
        equipment_Identifier = new Equipment_Identifier(this);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)// Habilitar Up Button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    ///Update Properties Device Master
    ///Permiso para obtener informacion del dispositivo
    /*-------------------------------------------------------------------------------------------------------*/
    public void getPropertiesEquipoMasterPermission() {
        // Check if the READ_PHONE_STATE permission is already available.
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // READ_PHONE_STATE permission has not been granted.
            requestReadPhoneStatePermission();
        } else {
            // READ_PHONE_STATE permission is already been granted.
            doPermissionGrantedStuffs();
        }*/
    }

    private void checkPermission() {
/*
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);

            //doPermissionGrantedStuffs();
        }else{
            doPermissionGrantedStuffs();
        } */


        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_PHONE_STATE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else{
            doPermissionGrantedStuffs();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    //Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    doPermissionGrantedStuffs();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * Requests the READ_PHONE_STATE permission.
     * If the permission has been denied previously, a dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            new AlertDialog.Builder(DeviceMasterActivity.this)
                    .setTitle("Permission Request")
                    .setMessage("Permiso")
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //re-request
                            ActivityCompat.requestPermissions(DeviceMasterActivity.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                            doPermissionGrantedStuffs();
                        }
                    })
                    .setIcon(R.drawable.logo_datatakeh_nuevo)
                    .show();
        } else {
            // READ_PHONE_STATE permission has not been granted yet. Request it directly.
           // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
              //      MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

            doPermissionGrantedStuffs();
        }
    }

    public void doPermissionGrantedStuffs() {
        //Have an  object of TelephonyManager
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // Now read the desired content to a textview.
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String Device_Id = equipment_Identifier.getDeviceID(telephonyManager, DeviceMasterActivity.this);
        String Android_Id = equipment_Identifier.getDeviceUniqueID(DeviceMasterActivity.this);
        /// String SIMSerialNumber=tm.getSimSerialNumber();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        String Software_Version = tm.getDeviceSoftwareVersion();
        /*
        String SIMCountryISO=tm.getSimCountryIso();*/
        String Remote_Ip_Address=equipment_Identifier.NetwordDetect();
        String Local_Ip_Address=equipment_Identifier.getLocalIpAddress();
        String Android_Version=equipment_Identifier.getAndroidVersion();
        String MacAddr=equipment_Identifier.getMacAddr();
        String Device_Name=equipment_Identifier.getDeviceName();
        ////Insertar Datos Equipo Maestro (Equipment master)



        txtAndroid_Id.setText(Android_Id);
        txtDevice_Id.setText(Device_Id);
        txtSoftware_Version.setText(Software_Version);
        txtLocal_Ip_Address.setText(Local_Ip_Address);
        txtAndroidVersion.setText(Android_Version);
        txtMacAddr.setText(MacAddr);
        txtDeviceName.setText(Device_Name);
        txtIpRemote.setText(Remote_Ip_Address);

       // Toast.makeText(DeviceMasterActivity.this, "Se actualizo correctamente",Toast.LENGTH_SHORT).show();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
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

}
