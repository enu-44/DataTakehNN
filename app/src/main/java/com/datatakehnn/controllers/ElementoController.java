package com.datatakehnn.controllers;

import android.content.Context;

import com.datatakehnn.models.element_model.Elemento;
import com.datatakehnn.models.element_model.Elemento_Table;
import com.datatakehnn.models.elemento_cable.Elemento_Cable;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

/**
 * Created by user on 13/11/2017.
 */

public class ElementoController {

    private static Context ourcontext;
    private static ElementoController _instance;


    ///Instance
    /*----------------------------------------------------------------------------------------------------------------*/
    public ElementoController() {
        _instance = this;
    }

    public static ElementoController getInstance(Context c) {
        if (_instance == null) {
            ourcontext = c;
            _instance = new ElementoController();
        }
        return _instance;
    }

    ///Methods

    ////Registrar
    public Elemento register(Elemento elementoNew) {
        Elemento elemento = new Elemento();
        elemento.setElemento_Id(elementoNew.getElemento_Id());
        elemento.setUsuario_Id(elementoNew.getUsuario_Id());
        elemento.setCodigo_Apoyo(elementoNew.getCodigo_Apoyo());
        elemento.setNumero_Apoyo(elementoNew.getNumero_Apoyo());
        elemento.setFecha_Levantamiento(elementoNew.getFecha_Levantamiento());
        elemento.setHora_Inicio(elementoNew.getHora_Inicio());
        elemento.setHora_Fin(elementoNew.getHora_Fin());
        elemento.setResistencia_Mecanica(elementoNew.getResistencia_Mecanica());
        elemento.setRetenidas(elementoNew.getRetenidas());
        elemento.setAltura_Disponible(elementoNew.getAltura_Disponible());
        elemento.setProyecto_Id(elementoNew.getProyecto_Id());
        elemento.setMaterial_Id(elementoNew.getMaterial_Id());
        elemento.setEstado_Id(elementoNew.getEstado_Id());
        elemento.setLongitud_Elemento_Id(elementoNew.getLongitud_Elemento_Id());
        elemento.setNivel_Tension_Elemento_Id(elementoNew.getNivel_Tension_Elemento_Id());
        elemento.setIs_Sync(elementoNew.isIs_Sync());
        elemento.setCoordenadas(elementoNew.getCoordenadas());
        elemento.setLatitud(elementoNew.getLatitud());
        elemento.setLongitud(elementoNew.getLongitud());
        elemento.setDireccion(elementoNew.getDireccion());
        elemento.setDireccion_Aproximada_Gps(elementoNew.getDireccion_Aproximada_Gps());
        elemento.setNombre_Direccion(elementoNew.getNombre_Direccion());
        elemento.setVia(elementoNew.getVia());
        elemento.setCon(elementoNew.getCon());
        elemento.setDescripcion_Direccion(elementoNew.getDescripcion_Direccion());
        elemento.setReferencia_Localizacion(elementoNew.getReferencia_Localizacion());
        elemento.setDepartamento_Id(elementoNew.getDepartamento_Id());
        elemento.setCiudad_Id(elementoNew.getCiudad_Id());
        elemento.setNombre_Ciudad(elementoNew.getNombre_Ciudad());
        elemento.setNombre_Departamento(elementoNew.getNombre_Departamento());
        elemento.setIs_Finished(elementoNew.isIs_Finished());
        elemento.save();
        return elemento;
    }

    ///Eliminar Usuarios bd
    public void deleteElementos() {
        Delete.table(Elemento.class);
    }

    ///Actualizar
    public Elemento update(Elemento elementoNew) {
        Elemento elemento = new Elemento();
        elemento.setElemento_Id(elementoNew.getElemento_Id());
        elemento.setUsuario_Id(elementoNew.getUsuario_Id());
        elemento.setCodigo_Apoyo(elementoNew.getCodigo_Apoyo());
        elemento.setNumero_Apoyo(elementoNew.getNumero_Apoyo());
        elemento.setFecha_Levantamiento(elementoNew.getFecha_Levantamiento());
        elemento.setHora_Inicio(elementoNew.getHora_Inicio());
        elemento.setHora_Fin(elementoNew.getHora_Fin());
        elemento.setResistencia_Mecanica(elementoNew.getResistencia_Mecanica());
        elemento.setRetenidas(elementoNew.getRetenidas());
        elemento.setAltura_Disponible(elementoNew.getAltura_Disponible());
        elemento.setProyecto_Id(elementoNew.getProyecto_Id());
        elemento.setMaterial_Id(elementoNew.getMaterial_Id());
        elemento.setEstado_Id(elementoNew.getEstado_Id());
        elemento.setLongitud_Elemento_Id(elementoNew.getLongitud_Elemento_Id());
        elemento.setNivel_Tension_Elemento_Id(elementoNew.getNivel_Tension_Elemento_Id());
        elemento.setIs_Sync(elementoNew.isIs_Sync());
        elemento.setCoordenadas(elementoNew.getCoordenadas());
        elemento.setLatitud(elementoNew.getLatitud());
        elemento.setLongitud(elementoNew.getLongitud());
        elemento.setDireccion(elementoNew.getDireccion());
        elemento.setDireccion_Aproximada_Gps(elementoNew.getDireccion_Aproximada_Gps());
        elemento.setNombre_Direccion(elementoNew.getNombre_Direccion());
        elemento.setVia(elementoNew.getVia());
        elemento.setCon(elementoNew.getCon());
        elemento.setDescripcion_Direccion(elementoNew.getDescripcion_Direccion());
        elemento.setReferencia_Localizacion(elementoNew.getReferencia_Localizacion());
        elemento.setDepartamento_Id(elementoNew.getDepartamento_Id());
        elemento.setCiudad_Id(elementoNew.getCiudad_Id());
        elemento.setNombre_Ciudad(elementoNew.getNombre_Ciudad());
        elemento.setNombre_Departamento(elementoNew.getNombre_Departamento());
        elemento.setIs_Finished(elementoNew.isIs_Finished());
        elemento.save();
        return elemento;
    }


    ///Obtener el primero
    public Elemento getFirst() {
        Elemento elemento = new Select().from(Elemento.class).querySingle();
        return elemento;
    }

    public Elemento getLast() {

        //List<Elemento> elementos = new Select().from(Elemento.class).queryList();
        Elemento elemento = new Select().from(Elemento.class).where().orderBy(Elemento_Table.Elemento_Id, false).querySingle();
        return elemento;
    }

    public Elemento getElementoById(long Elemento_Id) {
        //List<Elemento> elementos = new Select().from(Elemento.class).queryList();
        Elemento elemento = new Select().from(Elemento.class).where(Elemento_Table.Elemento_Id.eq(Elemento_Id)).querySingle();
        return elemento;
    }

    public Elemento getElementoByIdAndBySync(boolean Is_Sync) {
        //List<Elemento> elementos = new Select().from(Elemento.class).queryList();
        Elemento elemento = new Select().from(Elemento.class).where(Elemento_Table.Is_Sync.eq(Is_Sync)).querySingle();
        return elemento;
    }

    public List<Elemento> getElementosByUserAndSync(long user_id, boolean is_sync) {
        List<Elemento> elementos = new Select().from(Elemento.class).where(Elemento_Table.Usuario_Id.eq(user_id))
                .and(Elemento_Table.Is_Sync.eq(is_sync)).queryList();
        return elementos;
    }


    public List<Elemento> getListElementsByUserLogued(long Usuario_Id) {
        //  List<Elemento> elementos = new Select().from(Elemento.class).queryList();
        List<Elemento> elemento = new Select().from(Elemento.class).where(Elemento_Table.Usuario_Id.eq(Usuario_Id)).orderBy(Elemento_Table.Elemento_Id, false).queryList();
        return elemento;
    }
}

