package com.datatakehnn.models.storage_model;

/**
 * Created by usuario on 7/12/2017.
 */

public class Storage {

    public long Storage_Id;
    public String Sigla;
    public String Nombre;
    public String Descripcion;

    //Constructor

    public Storage(long storage_Id, String sigla, String nombre, String descripcion) {
        Storage_Id = storage_Id;
        Sigla = sigla;
        Nombre = nombre;
        Descripcion = descripcion;
    }


    public Storage() {

    }

    @Override
    public String toString() {
        return Nombre;
    }


    public long getStorage_Id() {
        return Storage_Id;
    }

    public void setStorage_Id(long storage_Id) {
        Storage_Id = storage_Id;
    }

    public String getSigla() {
        return Sigla;
    }

    public void setSigla(String sigla) {
        Sigla = sigla;
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
}
