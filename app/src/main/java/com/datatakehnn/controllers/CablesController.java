package com.datatakehnn.controllers;

import android.content.Context;

import com.datatakehnn.models.detalle_tipo_cable.Detalle_Tipo_Cable;
import com.datatakehnn.models.detalle_tipo_cable.Detalle_Tipo_Cable_Table;
import com.datatakehnn.models.elemento_cable.Elemento_Cable;
import com.datatakehnn.models.elemento_cable.Elemento_Cable_Table;
import com.datatakehnn.models.reponse_generic.Response;
import com.datatakehnn.models.usuario_model.Usuario;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

/**
 * Created by user on 13/11/2017.
 */

public class CablesController {
    private static Context ourcontext;
    private static CablesController _instance;


    ///Instance
    /*----------------------------------------------------------------------------------------------------------------*/
    public CablesController() {
        _instance = this;
    }

    public static CablesController getInstance(Context c) {
        if (_instance == null) {
            ourcontext = c;
            _instance = new CablesController();
        }
        return _instance;
    }


    ////Registrar
    public Response register(Elemento_Cable elemento_cable) {
        Response response = new Response();
        try {
            Elemento_Cable elementoCable = new Elemento_Cable();
            elementoCable.setCantidad(elemento_cable.getCantidad());
            elementoCable.setCodigo(elemento_cable.getCodigo());
            elementoCable.setDetalle_Tipo_Cable_Id(elemento_cable.getDetalle_Tipo_Cable_Id());
            elementoCable.setElemento_Id(elemento_cable.getElemento_Id());
            elementoCable.setEmpresa_Id(elemento_cable.getEmpresa_Id());
            elementoCable.setNombre_Detalle_Tipo_Cable(elemento_cable.getNombre_Detalle_Tipo_Cable());
            elementoCable.setNombre_Tipo_Cable(elemento_cable.getNombre_Tipo_Cable());
            elementoCable.setNombre_Empresa(elemento_cable.getNombre_Empresa());
            elementoCable.setSobre_Rbt(elemento_cable.isSobre_Rbt());
            elementoCable.setIs_Marquilla(elemento_cable.isIs_Marquilla());
            elementoCable.save();

            response.setMessage("Ok");
            response.setResult(elementoCable);
            response.setSuccess(true);
            return response;


        } catch (Exception ex) {
            response.setMessage(ex.toString());
            response.setSuccess(false);
            return response;
        }
    }

    //Cables por elemento y usuario
    public List<Elemento_Cable> getList_Cable_Element(long element_Id) {
        List<Elemento_Cable> lisFilter = SQLite.select().from(Elemento_Cable.class).where(Elemento_Cable_Table.Elemento_Id.eq(element_Id)).queryList();
        return lisFilter;
    }

    public Response DeleteCableByElemento(long elemento_cable_id) {
        Response response = new Response();

        ///Elemento_Cable elementoCable= new Elemento_Cable();

        SQLite.delete(Elemento_Cable.class).where(Elemento_Cable_Table.Elemento_Cable_Id.eq(elemento_cable_id)).async().execute();


        Elemento_Cable listee = new Select().from(Elemento_Cable.class).where(Elemento_Cable_Table.Elemento_Cable_Id.eq(elemento_cable_id)).querySingle();

        response.setMessage("Ok");
        response.setSuccess(true);
        return response;
    }

    /*
    public Response register(long detalle_tipo_cable_id, long elemento_id, String codigo, long empresa_id, long cantidad, boolean sobre_redes_bt, String nombre_detalle_tipo_cable, String nombre_tipo_cable, String nombre_empresa) {

        Response response= new Response();
        try{
            Elemento_Cable elementoCable= new Elemento_Cable();

            elementoCable.setCantidad(cantidad);
            elementoCable.setCodigo(codigo);
            elementoCable.setDetalle_Tipo_Cable_Id(detalle_tipo_cable_id);
            elementoCable.setElemento_Id(elemento_id);
            elementoCable.setEmpresa_Id(empresa_id);
            elementoCable.setNombre_Detalle_Tipo_Cable(nombre_detalle_tipo_cable);
            elementoCable.setNombre_Tipo_Cable(nombre_tipo_cable);
            elementoCable.setNombre_Empresa(nombre_empresa);
            elementoCable.setSobre_Rbt(sobre_redes_bt);
            elementoCable.save();

            response.setMessage("Ok");
            response.setResult(elementoCable);
            response.setSuccess(true);
            return  response;


        }catch (Exception ex){
            response.setMessage(ex.toString());
            response.setSuccess(false);
            return  response;
        }
    }
    */
}
