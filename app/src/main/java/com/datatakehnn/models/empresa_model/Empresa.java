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

    @SerializedName("Is_Operadora")
    @Column
    public boolean Is_Operadora;

    //Constructor

    public Empresa(long empresa_Id, String nombre, String direccion, String telefono, String nit,boolean is_Operadora ) {
        Empresa_Id = empresa_Id;
        Nombre = nombre;
        Direccion = direccion;
        Telefono = telefono;
        Nit = nit;
        Is_Operadora=is_Operadora;
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

    public boolean isIs_Operadora() {
        return Is_Operadora;
    }

    public void setIs_Operadora(boolean is_Operadora) {
        Is_Operadora = is_Operadora;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
