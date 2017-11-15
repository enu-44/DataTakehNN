package com.datatakehnn.models.tipo_direccion_model;

/**
 * Created by user on 15/11/2017.
 */

public class Tipo_Direccion {

    public int Id_Tipo_Direccion;
    public String Nombre;


    //Contructor


    public Tipo_Direccion(int id_Tipo_Direccion, String nombre) {
        Id_Tipo_Direccion = id_Tipo_Direccion;
        Nombre = nombre;
    }


    //Methods
    public int getId_Tipo_Direccion() {
        return Id_Tipo_Direccion;
    }

    public void setId_Tipo_Direccion(int id_Tipo_Direccion) {
        Id_Tipo_Direccion = id_Tipo_Direccion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
