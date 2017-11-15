package com.datatakehnn.models.detalle_tipo_novedad;

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
public class Detalle_Tipo_Novedad extends BaseModel {

    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public long Detalle_Tipo_Novedad_Id;

    @SerializedName("Nombre")
    @Column
    public String Nombre;

    @SerializedName("Descripcion")
    @Column
    public String Descripcion;

    @SerializedName("Tipo_Novedad_Id")
    @Column
    public long Tipo_Novedad_Id;


    //Constructor
    public Detalle_Tipo_Novedad(long detalle_Tipo_Novedad_Id, String nombre, String descripcion, long tipo_Novedad_Id) {
        Detalle_Tipo_Novedad_Id = detalle_Tipo_Novedad_Id;
        Nombre = nombre;
        Descripcion = descripcion;
        Tipo_Novedad_Id = tipo_Novedad_Id;
    }

    public Detalle_Tipo_Novedad() {
    }

    //Methods
    public long getDetalle_Tipo_Novedad_Id() {
        return Detalle_Tipo_Novedad_Id;
    }

    public void setDetalle_Tipo_Novedad_Id(long detalle_Tipo_Novedad_Id) {
        Detalle_Tipo_Novedad_Id = detalle_Tipo_Novedad_Id;
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

    public long getTipo_Novedad_Id() {
        return Tipo_Novedad_Id;
    }

    public void setTipo_Novedad_Id(long tipo_Novedad_Id) {
        Tipo_Novedad_Id = tipo_Novedad_Id;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
