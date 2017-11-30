package com.datatakehnn.models.tipo_usuario_model;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by user on 30/11/2017.
 */

@Table(database = DataSource.class)
public class Tipo_Usuario extends BaseModel {

    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public long Tipo_Usuario_Id;

    @SerializedName("Nombre")
    @Column
    public String Nombre;

    public Tipo_Usuario() {

    }

    public Tipo_Usuario(long tipo_Usuario_Id, String nombre) {
        Tipo_Usuario_Id = tipo_Usuario_Id;
        Nombre = nombre;
    }

    public long getTipo_Usuario_Id() {
        return Tipo_Usuario_Id;
    }

    public void setTipo_Usuario_Id(long tipo_Usuario_Id) {
        Tipo_Usuario_Id = tipo_Usuario_Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
