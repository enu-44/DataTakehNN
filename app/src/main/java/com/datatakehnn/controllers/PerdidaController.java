package com.datatakehnn.controllers;

import android.content.Context;

import com.datatakehnn.models.perdida_model.Perdida;

public class PerdidaController {

    private static Context ourcontext;
    private static PerdidaController _instance;


    ///Instance
    /*----------------------------------------------------------------------------------------------------------------*/
    public PerdidaController() {
        _instance = this;
    }

    public static PerdidaController getInstance(Context c) {
        if (_instance == null) {
            ourcontext = c;
            _instance = new PerdidaController();
        }
        return _instance;
    }

    //Registro
    public Perdida register(Perdida perdidaNew) {
        Perdida perdida = new Perdida();
        perdida.setPerdida_Id(perdidaNew.getPerdida_Id());
        perdida.setElemento_Id(perdidaNew.getElemento_Id());
        perdida.setIs_Lampara_Adicional(perdidaNew.isIs_Lampara_Adicional());
        perdida.setCantidad_Lampara_Adicional(perdidaNew.getCantidad_Lampara_Adicional());
        perdida.setIs_Lampara_Encendida_Dia(perdidaNew.isIs_Lampara_Encendida_Dia());
        perdida.setIs_Conexion_Ilicita(perdidaNew.isIs_Conexion_Ilicita());
        perdida.setIs_Poda(perdidaNew.isIs_Poda());
        perdida.save();
        return perdida;
    }

    //Actualizar
    public Perdida update(Perdida perdidaNew) {
        Perdida perdida = new Perdida();
        perdida.setPerdida_Id(perdidaNew.getPerdida_Id());
        perdida.setElemento_Id(perdidaNew.getElemento_Id());
        perdida.setIs_Lampara_Adicional(perdidaNew.isIs_Lampara_Adicional());
        perdida.setCantidad_Lampara_Adicional(perdidaNew.getCantidad_Lampara_Adicional());
        perdida.setIs_Lampara_Encendida_Dia(perdidaNew.isIs_Lampara_Encendida_Dia());
        perdida.setIs_Conexion_Ilicita(perdidaNew.isIs_Conexion_Ilicita());
        perdida.setIs_Poda(perdidaNew.isIs_Poda());
        perdida.save();
        return perdida;
    }
}
