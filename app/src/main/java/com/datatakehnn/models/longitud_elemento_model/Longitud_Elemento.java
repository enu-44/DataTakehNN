package com.datatakehnn.models.longitud_elemento_model;

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
public class Longitud_Elemento extends BaseModel {

    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public long Longitud_Elemento_Id;
    @SerializedName("Valor")
    @Column
    public double Valor;
    @SerializedName("Unidad_Medida")
    @Column
    public String Unidad_Medida;

    ///Constructor

    public Longitud_Elemento(long longitud_Elemento_Id, double valor, String unidad_Medida) {
        Longitud_Elemento_Id = longitud_Elemento_Id;
        Valor = valor;
        Unidad_Medida = unidad_Medida;
    }

    public Longitud_Elemento() {

    }


    //Methods


    public long getLongitud_Elemento_Id() {
        return Longitud_Elemento_Id;
    }

    public void setLongitud_Elemento_Id(long longitud_Elemento_Id) {
        Longitud_Elemento_Id = longitud_Elemento_Id;
    }

    public double getValor() {
        return Valor;
    }

    public void setValor(double valor) {
        Valor = valor;
    }

    public String getUnidad_Medida() {
        return Unidad_Medida;
    }

    public void setUnidad_Medida(String unidad_Medida) {
        Unidad_Medida = unidad_Medida;
    }


    @Override
    public String toString() {
        return String.valueOf(Valor);
    }
}
