package com.datatakehnn.models.proyectos_model;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by user on 30/11/2017.
 */

@Table(database = DataSource.class)
public class Proyecto extends BaseModel {

    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public long Id;

    @SerializedName("Nombre")
    @Column
    public String Nombre;

    @SerializedName("Descripcion")
    @Column
    public String Descripcion;

    @SerializedName("FechaInicio")
    @Column
    public String FechaInicio;

    @SerializedName("FechaFin")
    @Column
    public String FechaFin;

    @SerializedName("OrdenTrabajo")
    @Column
    public String OrdenTrabajo;

    @SerializedName("IsActivo")
    @Column
    public boolean IsActivo;

    @SerializedName("Proyecto_Empresa_Id")
    @Column
    public long Proyecto_Empresa_Id;

    @SerializedName("IsOperadora")
    @Column
    public boolean IsOperadora;

    @SerializedName("IsPropietaria")
    @Column
    public boolean IsPropietaria;

    @SerializedName("IsInterventora")
    @Column
    public boolean IsInterventora;

    @SerializedName("Empresa_Id")
    @Column
    public long Empresa_Id;

    @SerializedName("Proyecto_Id")
    @Column
    public long Proyecto_Id;

    @SerializedName("Ciudad_Id")
    @Column
    public long Ciudad_Id;

    public Proyecto() {

    }

    public Proyecto(long id, String nombre, String descripcion, String fechaInicio, String fechaFin, String ordenTrabajo, boolean isActivo, long proyecto_Empresa_Id, boolean isOperadora, boolean isPropietaria, boolean isInterventora, long empresa_Id, long proyecto_Id, long ciudad_Id) {
        Id = id;
        Nombre = nombre;
        Descripcion = descripcion;
        FechaInicio = fechaInicio;
        FechaFin = fechaFin;
        OrdenTrabajo = ordenTrabajo;
        IsActivo = isActivo;
        Proyecto_Empresa_Id = proyecto_Empresa_Id;
        IsOperadora = isOperadora;
        IsPropietaria = isPropietaria;
        IsInterventora = isInterventora;
        Empresa_Id = empresa_Id;
        Proyecto_Id = proyecto_Id;
        Ciudad_Id = ciudad_Id;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(String fechaFin) {
        FechaFin = fechaFin;
    }

    public String getOrdenTrabajo() {
        return OrdenTrabajo;
    }

    public void setOrdenTrabajo(String ordenTrabajo) {
        OrdenTrabajo = ordenTrabajo;
    }

    public boolean isActivo() {
        return IsActivo;
    }

    public void setActivo(boolean activo) {
        IsActivo = activo;
    }

    public long getProyecto_Empresa_Id() {
        return Proyecto_Empresa_Id;
    }

    public void setProyecto_Empresa_Id(long proyecto_Empresa_Id) {
        Proyecto_Empresa_Id = proyecto_Empresa_Id;
    }

    public boolean isOperadora() {
        return IsOperadora;
    }

    public void setOperadora(boolean operadora) {
        IsOperadora = operadora;
    }

    public boolean isPropietaria() {
        return IsPropietaria;
    }

    public void setPropietaria(boolean propietaria) {
        IsPropietaria = propietaria;
    }

    public boolean isInterventora() {
        return IsInterventora;
    }

    public void setInterventora(boolean interventora) {
        IsInterventora = interventora;
    }

    public long getEmpresa_Id() {
        return Empresa_Id;
    }

    public void setEmpresa_Id(long empresa_Id) {
        Empresa_Id = empresa_Id;
    }

    public long getProyecto_Id() {
        return Proyecto_Id;
    }

    public void setProyecto_Id(long proyecto_Id) {
        Proyecto_Id = proyecto_Id;
    }

    public long getCiudad_Id() {
        return Ciudad_Id;
    }

    public void setCiudad_Id(long ciudad_Id) {
        Ciudad_Id = ciudad_Id;
    }
}
