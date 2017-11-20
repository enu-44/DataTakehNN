package com.datatakehnn.models.departmentos_model;

import com.datatakehnn.config.DataSource;
import com.datatakehnn.models.ciudades_model.Ciudad;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;


/**
 * Created by user on 20/11/2017.
 */
@Table(database = DataSource.class)
public class Departamento extends BaseModel {

    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public  long Departamento_Id;

    @SerializedName("codigodpto")
    @Column
    public  long Codigo_Dpto;

    @Column
    public  String Nombre;

    @SerializedName("ciudades")
    public List<Ciudad> Ciudades;

    //Constructor


    public Departamento() {

    }

    //Methods
    public long getDepartamento_Id() {
        return Departamento_Id;
    }

    public void setDepartamento_Id(long departamento_Id) {
        Departamento_Id = departamento_Id;
    }

    public long getCodigo_Dpto() {
        return Codigo_Dpto;
    }

    public void setCodigo_Dpto(long codigo_Dpto) {
        Codigo_Dpto = codigo_Dpto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public List<Ciudad> getCiudades() {
        return Ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        Ciudades = ciudades;
    }
}
