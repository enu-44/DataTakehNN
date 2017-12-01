package com.datatakehnn.models.request_data_sync_model.model_request;

/**
 * Created by usuario on 30/11/2017.
 */

public class Novedad_Request {

    public long Elemento_Id;

    public String Descripcion;

    public byte[] ImageArray;

    public long Tipo_Novedad_Id;

    public String FechaCreacion;

    public String Hora;

    //Constructor
    public Novedad_Request(long elemento_Id, String descripcion, byte[] imageArray, long tipo_Novedad_Id, String fechaCreacion, String hora) {
        Elemento_Id = elemento_Id;
        Descripcion = descripcion;
        ImageArray = imageArray;
        Tipo_Novedad_Id = tipo_Novedad_Id;
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

    public long getTipo_Novedad_Id() {
        return Tipo_Novedad_Id;
    }

    public void setTipo_Novedad_Id(long tipo_Novedad_Id) {
        Tipo_Novedad_Id = tipo_Novedad_Id;
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
