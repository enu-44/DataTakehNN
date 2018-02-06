package com.datatakehnn.models.configuracion_model;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by usuario on 6/12/2017.
 */
@Table(database = DataSource.class)
public class Setting extends BaseModel {

    //Atributes
    @PrimaryKey
    public  long Setting_Id;

    @Column
    public  boolean Available_Wifi;

    @Column
    public  boolean Available_Mobile_Data;

    @Column
    public  String Descripcion_Storage_Phone;

    @Column
    public  String Sigla_Storage;

    @Column
    public  String Fecha;

    @Column
    public  String Hora;

    @Column
    public String Ruta_Servicio;



    //Construtor

    public Setting() {

    }

    public Setting(long setting_Id, boolean available_Wifi, boolean available_Mobile_Data, String descripcion_Storage_Phone, String sigla_Storage, String fecha, String hora,String ruta_Servicio) {
        Setting_Id = setting_Id;
        Available_Wifi = available_Wifi;
        Available_Mobile_Data = available_Mobile_Data;
        Descripcion_Storage_Phone = descripcion_Storage_Phone;
        Sigla_Storage = sigla_Storage;
        Fecha = fecha;
        Hora = hora;
        Ruta_Servicio = ruta_Servicio;
    }
//Methods


    public long getSetting_Id() {
        return Setting_Id;
    }

    public void setSetting_Id(long setting_Id) {
        Setting_Id = setting_Id;
    }

    public boolean isAvailable_Wifi() {
        return Available_Wifi;
    }

    public void setAvailable_Wifi(boolean available_Wifi) {
        Available_Wifi = available_Wifi;
    }

    public boolean isAvailable_Mobile_Data() {
        return Available_Mobile_Data;
    }

    public void setAvailable_Mobile_Data(boolean available_Mobile_Data) {
        Available_Mobile_Data = available_Mobile_Data;
    }




    public String getDescripcion_Storage_Phone() {
        return Descripcion_Storage_Phone;
    }

    public void setDescripcion_Storage_Phone(String descripcion_Storage_Phone) {
        Descripcion_Storage_Phone = descripcion_Storage_Phone;
    }

    public String getSigla_Storage() {
        return Sigla_Storage;
    }

    public void setSigla_Storage(String sigla_Storage) {
        Sigla_Storage = sigla_Storage;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getRuta_Servicio() {
        return Ruta_Servicio;
    }

    public void setRuta_Servicio(String ruta_Servicio) {
        Ruta_Servicio = ruta_Servicio;
    }
}
