package com.datatakehnn.models.tipo_equipo_model;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by user on 13/11/2017.
 */
@Table(database = DataSource.class)
public class Tipo_Equipo extends BaseModel {
    @SerializedName("Id")
    @PrimaryKey
    public long Tipo_Equipo_Id;

    @SerializedName("Nombre")
    @Column
    public String Nombre;


    public Tipo_Equipo(long tipo_Equipo_Id, String nombre) {
        Tipo_Equipo_Id = tipo_Equipo_Id;
        Nombre = nombre;
    }

    public Tipo_Equipo() {

    }

    //Methods

    public long getTipo_Equipo_Id() {
        return Tipo_Equipo_Id;
    }

    public void setTipo_Equipo_Id(long tipo_Equipo_Id) {
        Tipo_Equipo_Id = tipo_Equipo_Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
