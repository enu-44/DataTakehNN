package com.datatakehnn.models.tipo_noveda_model;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by user on 11/11/2017.
 */
@Table(database = DataSource.class)
public class Tipo_Novedad extends BaseModel {

    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public long Tipo_Novedad_Id;

    @SerializedName("Nombre")
    @Column
    public String Nombre;

    //Constructor


    public Tipo_Novedad(long tipo_Novedad_Id, String nombre) {
        Tipo_Novedad_Id = tipo_Novedad_Id;
        Nombre = nombre;
    }

    public Tipo_Novedad() {

    }

    //Methods
    public long getTipo_Novedad_Id() {
        return Tipo_Novedad_Id;
    }

    public void setTipo_Novedad_Id(long tipo_Novedad_Id) {
        Tipo_Novedad_Id = tipo_Novedad_Id;
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
