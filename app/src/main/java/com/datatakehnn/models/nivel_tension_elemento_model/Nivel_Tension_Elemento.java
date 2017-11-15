package com.datatakehnn.models.nivel_tension_elemento_model;

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
public class Nivel_Tension_Elemento extends BaseModel {

    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public long Nivel_Tension_Elemento_Id;

    @SerializedName("Nombre")
    @Column
    public String Nombre;

    @SerializedName("Sigla")
    @Column
    public String Sigla;

    @SerializedName("Valor")
    @Column
    public  long Valor;

    //Constructor

    public Nivel_Tension_Elemento(long nivel_Tension_Elemento_Id, String nombre, String sigla, long valor) {
        Nivel_Tension_Elemento_Id = nivel_Tension_Elemento_Id;
        Nombre = nombre;
        Sigla = sigla;
        Valor = valor;
    }

    public Nivel_Tension_Elemento() {

    }


    ///Methods

    public long getNivel_Tension_Elemento_Id() {
        return Nivel_Tension_Elemento_Id;
    }

    public void setNivel_Tension_Elemento_Id(long nivel_Tension_Elemento_Id) {
        Nivel_Tension_Elemento_Id = nivel_Tension_Elemento_Id;
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

    public long getValor() {
        return Valor;
    }

    public void setValor(long valor) {
        Valor = valor;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
