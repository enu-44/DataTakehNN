package com.datatakehnn.models.request_data_sync_model.model_request;

/**
 * Created by usuario on 30/11/2017.
 */

public class Foto_Request {

    public String Titulo;
    public String Descripcion;
    public String Ruta;
    public String FechaCreacion;
    public String Hora;
    public byte[] ImageArray;

    //Relaciones
    public long Novedad_Id;
    public long Elemento_Id;

    //Constructor
    public Foto_Request(String titulo, String descripcion, String ruta, String fechaCreacion, String hora, byte[] imageArray, long novedad_Id, long elemento_Id) {
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

    public byte[] getImageArray() {
        return ImageArray;
    }

    public void setImageArray(byte[] imageArray) {
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
