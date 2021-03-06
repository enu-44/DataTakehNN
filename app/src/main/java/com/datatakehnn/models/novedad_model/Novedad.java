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
    @SerializedName("Novedad_Id")
    @PrimaryKey(autoincrement = true)
    public long Novedad_Id;

    @SerializedName("Detalle_Tipo_Novedad_Id")
    @Column
    public long Detalle_Tipo_Novedad_Id;

    @SerializedName("Detalle_Tipo_Novedad_Nombre")
    @Column
    public String Detalle_Tipo_Novedad_Nombre;

    @SerializedName("Nombre_Tipo_Novedad")
    @Column
    public String Nombre_Tipo_Novedad;

    @SerializedName("Elemento_Id")
    @Column
    public long Elemento_Id;

    @SerializedName("Descripcion")
    @Column
    public String Descripcion;

    @SerializedName("Image_Novedad")
    @Column
    public Blob ImageArray;

    @SerializedName("Tipo_Novedad_Id")
    @Column
    public long Tipo_Novedad_Id;

    @SerializedName("Ruta_Foto")
    @Column
    public String Ruta_Foto;

    @SerializedName("FechaCreacion")
    @Column
    public String FechaCreacion;

    @SerializedName("Hora")
    @Column
    public String Hora;


    public Novedad() {
    }

    public Novedad(long novedad_Id, long detalle_Tipo_Novedad_Id, String detalle_Tipo_Novedad_Nombre, String nombre_Tipo_Novedad, long elemento_Id, String descripcion, Blob image_Novedad, long tipo_Novedad_Id, String ruta_Foto, String fecha_Creacion, String hora) {
        Novedad_Id = novedad_Id;
        Detalle_Tipo_Novedad_Id = detalle_Tipo_Novedad_Id;
        Detalle_Tipo_Novedad_Nombre = detalle_Tipo_Novedad_Nombre;
        Nombre_Tipo_Novedad = nombre_Tipo_Novedad;
        Elemento_Id = elemento_Id;
        Descripcion = descripcion;
        ImageArray = image_Novedad;
        Tipo_Novedad_Id = tipo_Novedad_Id;
        Ruta_Foto = ruta_Foto;
        FechaCreacion = fecha_Creacion;
        Hora = hora;
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

    public String getNombre_Tipo_Novedad() {
        return Nombre_Tipo_Novedad;
    }

    public void setNombre_Tipo_Novedad(String nombre_Tipo_Novedad) {
        Nombre_Tipo_Novedad = nombre_Tipo_Novedad;
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
        return ImageArray;
    }

    public void setImage_Novedad(Blob image_Novedad) {
        ImageArray = image_Novedad;
    }

    public long getTipo_Novedad_Id() {
        return Tipo_Novedad_Id;
    }

    public void setTipo_Novedad_Id(long tipo_Novedad_Id) {
        Tipo_Novedad_Id = tipo_Novedad_Id;
    }

    public String getRuta_Foto() {
        return Ruta_Foto;
    }

    public void setRuta_Foto(String ruta_Foto) {
        Ruta_Foto = ruta_Foto;
    }

    public String getFecha_Creacion() {
        return FechaCreacion;
    }

    public void setFecha_Creacion(String fecha_Creacion) {
        FechaCreacion = fecha_Creacion;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }
}
