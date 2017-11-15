package com.datatakehnn.models.empresa_model;

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
public class Empresa  extends BaseModel {

    @SerializedName("Id")
    @PrimaryKey
    public long Empresa_Id;

    @SerializedName("Nombre")
    @Column
    public  String Nombre;

    @SerializedName("Direccion")
    @Column
    public String Direccion;

    @SerializedName("Telefono")
    @Column
    public String Telefono;

    @SerializedName("Nit")
    @Column
    public String Nit;

    //Constructor

    public Empresa(long empresa_Id, String nombre, String direccion, String telefono, String nit) {
        Empresa_Id = empresa_Id;
        Nombre = nombre;
        Direccion = direccion;
        Telefono = telefono;
        Nit = nit;
    }

    public Empresa() {
    }


    //Methods


    public long getEmpresa_Id() {
        return Empresa_Id;
    }

    public void setEmpresa_Id(long empresa_Id) {
        Empresa_Id = empresa_Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getNit() {
        return Nit;
    }

    public void setNit(String nit) {
        Nit = nit;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
