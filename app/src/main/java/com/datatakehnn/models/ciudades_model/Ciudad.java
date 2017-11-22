package com.datatakehnn.models.ciudades_model;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by user on 20/11/2017.
 */
@Table(database = DataSource.class)
public class Ciudad extends BaseModel {

    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public  long Ciudad_Id;

    @Column
    public String Nombre;

    @SerializedName("departmentoId")
    @Column
    public long Departamnento_Id;


    //Constructor
    public Ciudad() {
    }

    public long getCiudad_Id() {
        return Ciudad_Id;
    }

    public void setCiudad_Id(long ciudad_Id) {
        Ciudad_Id = ciudad_Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public long getDepartamnento_Id() {
        return Departamnento_Id;
    }

    public void setDepartamnento_Id(long departamnento_Id) {
        Departamnento_Id = departamnento_Id;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
