package com.datatakehnn.models.usuario_model;

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
public class Usuario extends BaseModel {

    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public long Usuario_Id;

    @SerializedName("Nombre")
    @Column
    public String Nombre;

    @SerializedName("Apellido")
    @Column
    public String Apellido;

    @SerializedName("Cedula")
    @Column
    public String Cedula;

    @SerializedName("Telefono")
    @Column
    public String Telefono;

    @SerializedName("Direccion")
    @Column
    public String Direccion;

    @SerializedName("Correo_Electronico")
    @Column
    public String Correo_Electronico;

    @SerializedName("Password")
    @Column
    public String Password;

    @SerializedName("Empresa_Id")
    @Column
    public long Empresa_Id;

    @Column
    public boolean IsRemembered;



    ///Contructor

    public Usuario(long usuario_Id, String nombre, String apellido, String cedula, String telefono, String direccion, String correo_Electronico, String password, long empresa_Id, boolean isRemembered) {
        Usuario_Id = usuario_Id;
        Nombre = nombre;
        Apellido = apellido;
        Cedula = cedula;
        Telefono = telefono;
        Direccion = direccion;
        Correo_Electronico = correo_Electronico;
        Password = password;
        Empresa_Id = empresa_Id;
        IsRemembered = isRemembered;
    }

    public Usuario() {

    }

    ///Methods


    public long getUsuario_Id() {
        return Usuario_Id;
    }

    public void setUsuario_Id(long usuario_Id) {
        Usuario_Id = usuario_Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String cedula) {
        Cedula = cedula;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getCorreo_Electronico() {
        return Correo_Electronico;
    }

    public void setCorreo_Electronico(String correo_Electronico) {
        Correo_Electronico = correo_Electronico;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public long getEmpresa_Id() {
        return Empresa_Id;
    }

    public void setEmpresa_Id(long empresa_Id) {
        Empresa_Id = empresa_Id;
    }

    public boolean isRemembered() {
        return IsRemembered;
    }

    public void setRemembered(boolean remembered) {
        IsRemembered = remembered;
    }
}
