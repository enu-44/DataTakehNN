package com.datatakehnn.models.equipo_elemento_model;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by user on 13/11/2017.
 */
@Table(database = DataSource.class)
public class Equipo_Elemento  extends BaseModel {

    //Atributes
    @PrimaryKey(autoincrement = true)
    public long Equipo_Elemento_Id;
    @Column
    public long Cantidad;
    @Column
    public long Tipo_Equipo_Id;
    @Column
    public long Elemento_Id;
    @Column
    public long Empresa_Id;
    @Column
    public boolean Conectado_Red_Electrica;
    @Column
    public boolean Medidor_Red;

    //Externos
    @Column
    public String Nombre_Tipo_Equipo;
    @Column
    public String Nombre_Empresa;


    //Constructor
    public Equipo_Elemento(long cantidad, long tipo_Equipo_Id, long elemento_Id, long empresa_Id, boolean conectado_Red_Electrica, boolean medidor_Red, String nombre_Tipo_Equipo, String nombre_Empresa) {

        Cantidad = cantidad;
        Tipo_Equipo_Id = tipo_Equipo_Id;
        Elemento_Id = elemento_Id;
        Empresa_Id = empresa_Id;
        Conectado_Red_Electrica = conectado_Red_Electrica;
        Medidor_Red = medidor_Red;
        Nombre_Tipo_Equipo = nombre_Tipo_Equipo;
        Nombre_Empresa = nombre_Empresa;
    }

    public Equipo_Elemento(){

    }

    public long getEquipo_Elemento_Id() {
        return Equipo_Elemento_Id;
    }

    public void setEquipo_Elemento_Id(long equipo_Elemento_Id) {
        Equipo_Elemento_Id = equipo_Elemento_Id;
    }

    public long getCantidad() {
        return Cantidad;
    }

    public void setCantidad(long cantidad) {
        Cantidad = cantidad;
    }

    public long getTipo_Equipo_Id() {
        return Tipo_Equipo_Id;
    }

    public void setTipo_Equipo_Id(long tipo_Activo_Id) {
        Tipo_Equipo_Id = tipo_Activo_Id;
    }

    public long getElemento_Id() {
        return Elemento_Id;
    }

    public void setElemento_Id(long elemento_Id) {
        Elemento_Id = elemento_Id;
    }

    public long getEmpresa_Id() {
        return Empresa_Id;
    }

    public void setEmpresa_Id(long empresa_Id) {
        Empresa_Id = empresa_Id;
    }

    public boolean isConectado_Red_Electrica() {
        return Conectado_Red_Electrica;
    }

    public void setConectado_Red_Electrica(boolean conectado_Red) {
        Conectado_Red_Electrica = conectado_Red;
    }

    public boolean isMedidor_Red() {
        return Medidor_Red;
    }

    public void setMedidor_Red(boolean medidor_Red) {
        Medidor_Red = medidor_Red;
    }


    public String getNombre_Tipo_Equipo() {
        return Nombre_Tipo_Equipo;
    }

    public void setNombre_Tipo_Equipo(String nombre_Tipo_Equipo) {
        Nombre_Tipo_Equipo = nombre_Tipo_Equipo;
    }

    public String getNombre_Empresa() {
        return Nombre_Empresa;
    }

    public void setNombre_Empresa(String nombre_Empresa) {
        Nombre_Empresa = nombre_Empresa;
    }
}
