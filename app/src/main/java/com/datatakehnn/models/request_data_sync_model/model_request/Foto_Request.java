package com.datatakehnn.models.request_data_sync_model.model_request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by usuario on 30/11/2017.
 */

public class Foto_Request {


    @SerializedName("Titulo")
    public String Titulo;
    @SerializedName("Descripcion")
    public String Descripcion;
    @SerializedName("Ruta")
    public String Ruta;
    @SerializedName("FechaCreacion")
    public String FechaCreacion;
    @SerializedName("Hora")
    public String Hora;
    @SerializedName("ImageArray")
    public String ImageArray;

    //Relaciones
    @SerializedName("Novedad_Id")
    public long Novedad_Id;

    @SerializedName("Elemento_Id")
    public long Elemento_Id;

    //Constructor
    public Foto_Request(String titulo, String descripcion, String ruta, String fechaCreacion, String hora, String imageArray, long novedad_Id, long elemento_Id) {
        Titulo = titulo;
        Descripcion = descripcion;
        Ruta = ruta;
        FechaCreacion = fechaCreacion;
        Hora = hora;
        ImageArray = imageArray;
        Novedad_Id = novedad_Id;
        Elemento_Id = elemento_Id;
    }

    public Foto_Request() {

    }

    //Methods

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getRuta() {
        return Ruta;
    }

    public void setRuta(String ruta) {
        Ruta = ruta;
    }

    public String getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getImageArray() {
        return ImageArray;
    }

    public void setImageArray(String imageArray) {
        ImageArray = imageArray;
    }

    public long getNovedad_Id() {
        return Novedad_Id;
    }

    public void setNovedad_Id(long novedad_Id) {
        Novedad_Id = novedad_Id;
    }

    public long getElemento_Id() {
        return Elemento_Id;
    }

    public void setElemento_Id(long elemento_Id) {
        Elemento_Id = elemento_Id;
    }
}
