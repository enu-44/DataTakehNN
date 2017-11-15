package com.datatakehnn.models.tipo_direccion_model;

/**
 * Created by user on 15/11/2017.
 */

public class Detalle_Tipo_Direccion {

    public int Id_Detalle_Tipo_Direccion;
    public String Nombre;

    //contructor


    public Detalle_Tipo_Direccion(int id_Detalle_Tipo_Direccion, String nombre) {
        Id_Detalle_Tipo_Direccion = id_Detalle_Tipo_Direccion;
        Nombre = nombre;
    }

    //methods

    public int getId_Detalle_Tipo_Direccion() {
        return Id_Detalle_Tipo_Direccion;
    }

    public void setId_Detalle_Tipo_Direccion(int id_Detalle_Tipo_Direccion) {
        Id_Detalle_Tipo_Direccion = id_Detalle_Tipo_Direccion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
