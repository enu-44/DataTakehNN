package com.datatakehnn.controllers;

import android.content.Context;

import com.datatakehnn.models.detalle_tipo_novedad.Detalle_Tipo_Novedad;
import com.datatakehnn.models.detalle_tipo_novedad.Detalle_Tipo_Novedad_Table;
import com.datatakehnn.models.novedad_model.Novedad;
import com.datatakehnn.models.novedad_model.Novedad_Table;
import com.datatakehnn.models.reponse_generic.Response;
import com.datatakehnn.models.tipo_noveda_model.Tipo_Novedad;
import com.datatakehnn.models.tipo_noveda_model.Tipo_Novedad_Table;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 12/11/2017.
 */

public class NovedadController {

    private static Context ourcontext;
    private static NovedadController _instance;


    ///Instance
    /*----------------------------------------------------------------------------------------------------------------*/
    public NovedadController() {
        _instance = this;
    }

    public static NovedadController getInstance(Context c) {
        if (_instance == null) {
            ourcontext = c;
            _instance = new NovedadController();
        }
        return _instance;
    }

    ////Registrar
    public Novedad register(Novedad novedadNew) {
        Novedad novedad = new Novedad();
        novedad.setNovedad_Id(novedadNew.getNovedad_Id());
        novedad.setDetalle_Tipo_Novedad_Id(novedadNew.getDetalle_Tipo_Novedad_Id());
        novedad.setElemento_Id(novedadNew.getElemento_Id());
        novedad.setDescripcion(novedadNew.getDescripcion());
        novedad.setDetalle_Tipo_Novedad_Nombre(novedadNew.getDetalle_Tipo_Novedad_Nombre());
        novedad.setNombre_Tipo_Novedad(novedadNew.getNombre_Tipo_Novedad());
        novedad.setTipo_Novedad_Id(novedadNew.getTipo_Novedad_Id());
        novedad.save();
        return novedad;
    }

    ///Eliminar Usuarios bd
    public void deleteNovedades() {
        Delete.table(Novedad.class);
    }

    ///Actualizar
    public Novedad update(Novedad novedadNew) {
        Novedad novedad = new Novedad();
        novedad.setNovedad_Id(novedadNew.getNovedad_Id());
        novedad.setDetalle_Tipo_Novedad_Id(novedadNew.getDetalle_Tipo_Novedad_Id());
        novedad.setElemento_Id(novedadNew.getElemento_Id());
        novedad.setDescripcion(novedadNew.getDescripcion());
        novedad.setDetalle_Tipo_Novedad_Nombre(novedadNew.getDetalle_Tipo_Novedad_Nombre());
        novedad.setTipo_Novedad_Id(novedadNew.getTipo_Novedad_Id());
        novedad.setNombre_Tipo_Novedad(novedadNew.getNombre_Tipo_Novedad());
        novedad.setRuta_Foto(novedadNew.getRuta_Foto());
        novedad.setFecha_Creacion(novedadNew.getFecha_Creacion());
        novedad.setHora(novedadNew.getHora());
        novedad.save();
        return novedad;
    }

    public Novedad updateWithFoto(Novedad novedadNew) {
        Novedad novedad = new Novedad();
        novedad.setNovedad_Id(novedadNew.getNovedad_Id());
        novedad.setDetalle_Tipo_Novedad_Id(novedadNew.getDetalle_Tipo_Novedad_Id());
        novedad.setElemento_Id(novedadNew.getElemento_Id());
        novedad.setDescripcion(novedadNew.getDescripcion());
        novedad.setDetalle_Tipo_Novedad_Nombre(novedadNew.getDetalle_Tipo_Novedad_Nombre());
        novedad.setNombre_Tipo_Novedad(novedadNew.getNombre_Tipo_Novedad());
        novedad.setTipo_Novedad_Id(novedadNew.getTipo_Novedad_Id());
        novedad.setImage_Novedad(novedadNew.getImage_Novedad());
        novedad.setRuta_Foto(novedadNew.getRuta_Foto());
        novedad.setFecha_Creacion(novedadNew.getFecha_Creacion());
        novedad.setHora(novedadNew.getHora());
        novedad.save();
        return novedad;
    }

    //Devuelve un Detalle_Tipo_Novedad por Id
    public Detalle_Tipo_Novedad getDetalleById(long detalle_novedad_id) {
        Detalle_Tipo_Novedad detalle_tipo_novedad = SQLite.select().from(Detalle_Tipo_Novedad.class).where(Detalle_Tipo_Novedad_Table.Detalle_Tipo_Novedad_Id.eq(detalle_novedad_id)).querySingle();
        return detalle_tipo_novedad;
    }


    //Devuelve una novedad por Tipo_Novedad y por Id de Poste
    public Novedad getNovedadByTipoAndElementoId(long tipo_novedad_id, long elemento_id) {
        Novedad novedad = SQLite.select().from(Novedad.class).where(Novedad_Table.Tipo_Novedad_Id.eq(tipo_novedad_id))
                .and(Novedad_Table.Elemento_Id.eq(elemento_id)).querySingle();
        return novedad;
    }

    public Novedad getNovedadByTipoNombreAndElementoId(String nombre_tipo_novedad, long elemento_id) {
        Novedad novedad = SQLite.select().from(Novedad.class).where(Novedad_Table.Nombre_Tipo_Novedad.eq(nombre_tipo_novedad))
                .and(Novedad_Table.Elemento_Id.eq(elemento_id)).querySingle();
        return novedad;
    }

    public void deleteNovedad(long idDeletedNovedad) {
        Novedad borrarNovedad = SQLite.select().from(Novedad.class).where(Novedad_Table.Novedad_Id.eq(idDeletedNovedad)).querySingle();
        if (borrarNovedad != null) {
            borrarNovedad.delete();
        }
    }


    public Tipo_Novedad getTipoNovedadById(long tipo_novedad_id) {
        Tipo_Novedad tipo_novedad = SQLite.select().from(Tipo_Novedad.class).where(Tipo_Novedad_Table.Tipo_Novedad_Id.eq(tipo_novedad_id))
                .querySingle();
        return tipo_novedad;
    }

    public Response DeleteNovedadById(long novedad_id) {
        Response response = new Response();
        ///Elemento_Cable elementoCable= new Elemento_Cable();
        SQLite.delete(Novedad.class).where(Novedad_Table.Novedad_Id.eq(novedad_id)).async().execute();
        ///Elemento_Cable listee= new Select().from(Elemento_Cable.class).where(Elemento_Cable_Table.Elemento_Cable_Id.eq(elemento_cable_id)).querySingle();
        response.setMessage("Ok");
        response.setSuccess(true);
        return response;
    }


    //Devuelve un Listado de Novedades por Id del Poste
    public List<Novedad> getListNovedadesByElementoId(long elemento_id) {
        List<Novedad> novedades = SQLite.select().from(Novedad.class).where
                (Novedad_Table.Elemento_Id.eq(elemento_id))
                .queryList();

        return novedades;
    }

    //Get List detalle novedad
    public List<Detalle_Tipo_Novedad> getListNovedades(String Nombre,Boolean perdida) {
        List<Detalle_Tipo_Novedad> lis=new ArrayList<Detalle_Tipo_Novedad>();
        if(perdida){
            //retorna la lista del tipo de novedad
            lis = SQLite.select().from(Detalle_Tipo_Novedad.class).where(Detalle_Tipo_Novedad_Table.Nombre.eq(Nombre)).queryList();

        }else{
            //Busca el id del tipo de novedad
            Tipo_Novedad tipo_novedad = SQLite.select().from(Tipo_Novedad.class).where(Tipo_Novedad_Table.Nombre.eq(Nombre)).querySingle();
            //retorna la lista del tipo de novedad
             lis = SQLite.select().from(Detalle_Tipo_Novedad.class).where(Detalle_Tipo_Novedad_Table.Tipo_Novedad_Id.eq(tipo_novedad.getTipo_Novedad_Id())).queryList();

        }

        return lis;

    }

    public Novedad getLast() {
        Novedad novedad = new Select().from(Novedad.class).where().orderBy(Novedad_Table.Novedad_Id, false).querySingle();
        return novedad;
    }


    public Novedad getNovedadByElementoId(long elemento_id) {
        Novedad novedad = SQLite.select().from(Novedad.class).where(Novedad_Table.Elemento_Id.eq(elemento_id)).querySingle();
        return novedad;
    }


}

