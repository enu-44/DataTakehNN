package com.datatakehnn.models.material_model;

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
public class Material extends BaseModel {
    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public long Material_Id;

    @SerializedName("Nombre")
    @Column
    public String Nombre;

    @SerializedName("Sigla")
    @Column
    public String Sigla;


    ///Constructor


    public Material(long material_Id, String nombre, String sigla) {
        Material_Id = material_Id;
        Nombre = nombre;
        Sigla = sigla;
    }

    public Material() {

    }

    //Methods
    public long getMaterial_Id() {
        return Material_Id;
    }

    public void setMaterial_Id(long material_Id) {
        Material_Id = material_Id;
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
