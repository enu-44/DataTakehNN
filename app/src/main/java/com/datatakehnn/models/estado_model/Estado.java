package com.datatakehnn.models.estado_model;

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
public class Estado extends BaseModel {

    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public long Estado_Id;

    @SerializedName("Nombre")
    @Column
    public String Nombre;

    @SerializedName("Sigla")
    @Column
    public String Sigla;


    //Constructor


    public Estado(long estado_Id, String nombre, String sigla) {
        Estado_Id = estado_Id;
        Nombre = nombre;
        Sigla = sigla;
    }

    public Estado() {

    }



    //Methods


    public long getEstado_Id() {
        return Estado_Id;
    }

    public void setEstado_Id(long estado_Id) {
        Estado_Id = estado_Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getSigla() {
        return Sigla;
    }

    public void setSigla(String sigla) {
        Sigla = sigla;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
