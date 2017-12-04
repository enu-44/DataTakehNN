package com.datatakehnn.models.request_data_sync_model.model_request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by usuario on 30/11/2017.
 */

public class Novedad_Request {

    @SerializedName("Elemento_Id")
    public long Elemento_Id;

    @SerializedName("Descripcion")
    public String Descripcion;

    @SerializedName("ImageArray")
    public byte[] ImageArray;

    @SerializedName("Detalle_Tipo_Novedad_Id")
    public long Detalle_Tipo_Novedad_Id;

    @SerializedName("FechaCreacion")
    public String FechaCreacion;

    @SerializedName("Hora")
    public String Hora;

    //Constructor
    public Novedad_Request(long elemento_Id, String descripcion, byte[] imageArray, long detalle_Tipo_Novedad_Id, String fechaCreacion, String hora) {
        Elemento_Id = elemento_Id;
        Descripcion = descripcion;
        ImageArray = imageArray;
        Detalle_Tipo_Novedad_Id = detalle_Tipo_Novedad_Id;
        FechaCreacion = fechaCreacion;
        Hora = hora;
    }

    public Novedad_Request() {

    }


    //Methods
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

    public byte[] getImageArray() {
        return ImageArray;
    }

    public void setImageArray(byte[] imageArray) {
        ImageArray = imageArray;
    }

    public long getDetalle_Tipo_Novedad_Id() {
        return Detalle_Tipo_Novedad_Id;
    }

    public void setDetalle_Tipo_Novedad_Id(long detalle_Tipo_Novedad_Id) {
        Detalle_Tipo_Novedad_Id = detalle_Tipo_Novedad_Id;
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
}
