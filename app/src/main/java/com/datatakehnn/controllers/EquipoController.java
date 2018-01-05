package com.datatakehnn.controllers;

import android.content.Context;

import com.datatakehnn.models.equipo_elemento_model.Equipo_Elemento;
import com.datatakehnn.models.equipo_elemento_model.Equipo_Elemento_Table;
import com.datatakehnn.models.reponse_generic.Response;
import com.datatakehnn.models.tipo_equipo_model.Tipo_Equipo;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by user on 13/11/2017.
 */

public class EquipoController {

    private static Context ourcontext;
    private static EquipoController _instance;


    ///Instance
    /*----------------------------------------------------------------------------------------------------------------*/
    public EquipoController() {
        _instance = this;
    }

    public static EquipoController getInstance(Context c) {
        if (_instance == null) {
            ourcontext = c;
            _instance = new EquipoController();
        }
        return _instance;
    }

    ////Registrar
    public Response register(Equipo_Elemento tipo_equipo) {
        Response response = new Response();
        try {
            Equipo_Elemento equipo_elemento = new Equipo_Elemento();
            equipo_elemento.setDescripcion(tipo_equipo.getDescripcion());
            equipo_elemento.setCantidad(1);
            equipo_elemento.setTipo_Equipo_Id(tipo_equipo.getTipo_Equipo_Id());
            equipo_elemento.setConectado_Red_Electrica(tipo_equipo.isConectado_Red_Electrica());
            equipo_elemento.setElemento_Id(tipo_equipo.getElemento_Id());
            equipo_elemento.setEmpresa_Id(tipo_equipo.getEmpresa_Id());
            equipo_elemento.setMedidor_Red(tipo_equipo.isMedidor_Red());
            equipo_elemento.setNombre_Empresa(tipo_equipo.getNombre_Empresa());
            equipo_elemento.setNombre_Tipo_Equipo(tipo_equipo.getNombre_Tipo_Equipo());
            equipo_elemento.setCiudad_Id(tipo_equipo.getCiudad_Id());
            equipo_elemento.setCiudad_Empresa_Id(tipo_equipo.getCiudad_Empresa_Id());
            equipo_elemento.setCodigo(tipo_equipo.getCodigo());
            equipo_elemento.save();
            response.setMessage("Ok");
            response.setResult(equipo_elemento);
            response.setSuccess(true);
            return response;
        } catch (Exception ex) {
            response.setMessage(ex.toString());
            response.setSuccess(false);
            return response;
        }
    }

    //Cables por elemento y usuario
    public List<Equipo_Elemento> getListEquipoElement(long element_Id) {
        List<Equipo_Elemento> lisFilter = SQLite.select().from(Equipo_Elemento.class).where(Equipo_Elemento_Table.Elemento_Id.eq(element_Id)).queryList();
        return lisFilter;
    }

    public Response DeleteEquipoByElemento(long equipo_elemento_id) {
        Response response = new Response();
        ///Elemento_Cable elementoCable= new Elemento_Cable();
        SQLite.delete(Equipo_Elemento.class).where(Equipo_Elemento_Table.Equipo_Elemento_Id.eq(equipo_elemento_id)).async().execute();
        ///Elemento_Cable listee= new Select().from(Elemento_Cable.class).where(Elemento_Cable_Table.Elemento_Cable_Id.eq(elemento_cable_id)).querySingle();
        response.setMessage("Ok");
        response.setSuccess(true);
        return response;
    }
}
