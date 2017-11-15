package com.datatakehnn.models.tipo_cable;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by user on 12/11/2017.
 */
@Table(database = DataSource.class)
public class Tipo_Cable extends BaseModel {
    //Atributes
    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public long Tipo_Cable_Id;

    @SerializedName("Nombre")
    @Column
    public String Nombre;

    //Constructor
    public Tipo_Cable(long tipo_Cable_Id, String nombre) {
        Tipo_Cable_Id = tipo_Cable_Id;
        Nombre = nombre;
    }

    public  Tipo_Cable(){

    }

    //Methods
    public long getTipo_Cable_Id() {
        return Tipo_Cable_Id;
    }

    public void setTipo_Cable_Id(long tipo_Cable_Id) {
        Tipo_Cable_Id = tipo_Cable_Id;
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
