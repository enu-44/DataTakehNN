package com.datatakehnn.models.perdida_model;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = DataSource.class)
public class Perdida extends BaseModel {

    //Atributes
    @SerializedName("Id")
    @PrimaryKey(autoincrement = true)
    public long Perdida_Id;

    @SerializedName("Elemento_Id")
    @Column
    public long Elemento_Id;

    @SerializedName("Lampara_Adicional")
    @Column
    public boolean Is_Lampara_Adicional;

    @SerializedName("Cantidad_Lampara_Adicional")
    @Column
    public long Cantidad_Lampara_Adicional;

    @SerializedName("Lampara_Encendida_Dia")
    @Column
    public boolean Is_Lampara_Encendida_Dia;

    @SerializedName("Conexion_Ilicita")
    @Column
    public boolean Is_Conexion_Ilicita;

    @SerializedName("Poda")
    @Column
    public boolean Is_Poda;

    public Perdida() {

    }

    public Perdida(long perdida_Id, long elemento_Id, boolean is_Lampara_Adicional, long cantidad_Lampara_Adicional, boolean is_Lampara_Encendida_Dia, boolean is_Conexion_Ilicita, boolean is_Poda) {
        Perdida_Id = perdida_Id;
        Elemento_Id = elemento_Id;
        Is_Lampara_Adicional = is_Lampara_Adicional;
        Cantidad_Lampara_Adicional = cantidad_Lampara_Adicional;
        Is_Lampara_Encendida_Dia = is_Lampara_Encendida_Dia;
        Is_Conexion_Ilicita = is_Conexion_Ilicita;
        Is_Poda = is_Poda;
    }

    public long getPerdida_Id() {
        return Perdida_Id;
    }

    public void setPerdida_Id(long perdida_Id) {
        Perdida_Id = perdida_Id;
    }

    public long getElemento_Id() {
        return Elemento_Id;
    }

    public void setElemento_Id(long elemento_Id) {
        Elemento_Id = elemento_Id;
    }

    public boolean isIs_Lampara_Adicional() {
        return Is_Lampara_Adicional;
    }

    public void setIs_Lampara_Adicional(boolean is_Lampara_Adicional) {
        Is_Lampara_Adicional = is_Lampara_Adicional;
    }

    public long getCantidad_Lampara_Adicional() {
        return Cantidad_Lampara_Adicional;
    }

    public void setCantidad_Lampara_Adicional(long cantidad_Lampara_Adicional) {
        Cantidad_Lampara_Adicional = cantidad_Lampara_Adicional;
    }

    public boolean isIs_Lampara_Encendida_Dia() {
        return Is_Lampara_Encendida_Dia;
    }

    public void setIs_Lampara_Encendida_Dia(boolean is_Lampara_Encendida_Dia) {
        Is_Lampara_Encendida_Dia = is_Lampara_Encendida_Dia;
    }

    public boolean isIs_Conexion_Ilicita() {
        return Is_Conexion_Ilicita;
    }

    public void setIs_Conexion_Ilicita(boolean is_Conexion_Ilicita) {
        Is_Conexion_Ilicita = is_Conexion_Ilicita;
    }

    public boolean isIs_Poda() {
        return Is_Poda;
    }

    public void setIs_Poda(boolean is_Poda) {
        Is_Poda = is_Poda;
    }
}
