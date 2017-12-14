package com.datatakehnn.models.ciudad_empresa;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by usuario on 11/12/2017.
 */

@Table(database = DataSource.class)
public class Ciudad_Empresa extends BaseModel {

    @SerializedName("Ciudad_Empresa_Id")
    @PrimaryKey
    public long Ciudad_Empresa_Id;

    //Relaciones
    @SerializedName("Ciudad_Id")
    @Column
    public long Ciudad_Id;

    @SerializedName("Nombre_Ciudad")
    @Column
    public  String Nombre_Ciudad;

    @SerializedName("Empresa_Id")
    @Column
    public long Empresa_Id;

    @SerializedName("Nombre_Empresa")
    @Column
    public  String Nombre_Empresa;

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
    public Ciudad_Empresa() {

    }

    //Methods

    public long getCiudad_Empresa_Id() {
        return Ciudad_Empresa_Id;
    }

    public void setCiudad_Empresa_Id(long ciudad_Empresa_Id) {
        Ciudad_Empresa_Id = ciudad_Empresa_Id;
    }

    public long getCiudad_Id() {
        return Ciudad_Id;
    }

    public void setCiudad_Id(long ciudad_Id) {
        Ciudad_Id = ciudad_Id;
    }

    public String getNombre_Ciudad() {
        return Nombre_Ciudad;
    }

    public void setNombre_Ciudad(String nombre_Ciudad) {
        Nombre_Ciudad = nombre_Ciudad;
    }

    public long getEmpresa_Id() {
        return Empresa_Id;
    }

    public void setEmpresa_Id(long empresa_Id) {
        Empresa_Id = empresa_Id;
    }

    public String getNombre_Empresa() {
        return Nombre_Empresa;
    }

    public void setNombre_Empresa(String nombre_Empresa) {
        Nombre_Empresa = nombre_Empresa;
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


    public boolean getIs_Operadora() {
        return Is_Operadora;
    }

    public void setIs_Operadora(boolean is_Operadora) {
        Is_Operadora = is_Operadora;
    }

    @Override
    public String toString() {
        return Nombre_Empresa;
    }
}
