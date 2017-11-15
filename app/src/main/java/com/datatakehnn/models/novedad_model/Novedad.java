package com.datatakehnn.models.novedad_model;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.data.Blob;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by user on 11/11/2017.
 */

@Table(database = DataSource.class)
public class Novedad extends BaseModel {

    //Atributes
    @SerializedName("Id")
    @PrimaryKey(autoincrement = true)
    public long Novedad_Id;

    @SerializedName("Detalle_Tipo_Novedad_Id")
    @Column
    public long Detalle_Tipo_Novedad_Id;

    @SerializedName("Detalle_Tipo_Novedad_Nombre")
    @Column
    public String Detalle_Tipo_Novedad_Nombre;

    @SerializedName("Elemento_Id")
    @Column
    public long Elemento_Id;

    @SerializedName("Descripcion")
    @Column
    public String Descripcion;

    @SerializedName("Image_Novedad")
    @Column
    public Blob Image_Novedad;

    public Novedad() {
    }

    public Novedad(long novedad_Id, long detalle_Tipo_Novedad_Id, String detalle_Tipo_Novedad_Nombre, long elemento_Id, String descripcion, Blob image_Novedad) {
        Novedad_Id = novedad_Id;
        Detalle_Tipo_Novedad_Id = detalle_Tipo_Novedad_Id;
        Detalle_Tipo_Novedad_Nombre = detalle_Tipo_Novedad_Nombre;
        Elemento_Id = elemento_Id;
        Descripcion = descripcion;
        Image_Novedad = image_Novedad;
    }

    //Methods
    public long getNovedad_Id() {
        return Novedad_Id;
    }

    public void setNovedad_Id(long novedad_Id) {
        Novedad_Id = novedad_Id;
    }

    public long getDetalle_Tipo_Novedad_Id() {
        return Detalle_Tipo_Novedad_Id;
    }

    public void setDetalle_Tipo_Novedad_Id(long detalle_Tipo_Novedad_Id) {
        Detalle_Tipo_Novedad_Id = detalle_Tipo_Novedad_Id;
    }

    public long getElemento_Id() {
        return Elemento_Id;
    }

    public void setElemento_Id(long elemento_Id) {
        Elemento_Id = elemento_Id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getDetalle_Tipo_Novedad_Nombre() {
        return Detalle_Tipo_Novedad_Nombre;
    }

    public void setDetalle_Tipo_Novedad_Nombre(String detalle_Tipo_Novedad_Nombre) {
        Detalle_Tipo_Novedad_Nombre = detalle_Tipo_Novedad_Nombre;
    }

    public Blob getImage_Novedad() {
        return Image_Novedad;
    }

    public void setImage_Novedad(Blob image_Novedad) {
        Image_Novedad = image_Novedad;
    }
}
