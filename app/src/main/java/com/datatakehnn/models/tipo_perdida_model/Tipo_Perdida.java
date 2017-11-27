package com.datatakehnn.models.tipo_perdida_model;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by user on 27/11/2017.
 */
@Table(database = DataSource.class)
public class Tipo_Perdida extends BaseModel {

    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public long Tipo_Perdida_Id;

    @SerializedName("Nombre")
    @Column
    public String Nombre;

    public Tipo_Perdida() {
        
    }

    public Tipo_Perdida(long tipo_Perdida_Id, String nombre) {
        Tipo_Perdida_Id = tipo_Perdida_Id;
        Nombre = nombre;
    }

    public long getTipo_Perdida_Id() {
        return Tipo_Perdida_Id;
    }

    public void setTipo_Perdida_Id(long tipo_Perdida_Id) {
        Tipo_Perdida_Id = tipo_Perdida_Id;
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
