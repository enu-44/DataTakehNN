package com.datatakehnn.controllers;

import android.content.Context;

import com.datatakehnn.models.perdida_model.Perdida;
import com.datatakehnn.models.perdida_model.Perdida_Table;
import com.datatakehnn.models.reponse_generic.Response;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

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

    ////Registrar
    public Perdida register(Perdida perdidaNew) {
        Perdida perdida = new Perdida();
        perdida.setPerdida_Id(perdidaNew.getPerdida_Id());
        perdida.setConcepto(perdidaNew.getConcepto());
        perdida.setCantidad(perdidaNew.getCantidad());
        perdida.setDescripcion(perdidaNew.getDescripcion());
        perdida.setValor(perdidaNew.getValor());
        perdida.setResponse_Checked(perdidaNew.isResponse_Checked());
        perdida.setElemento_Id(perdidaNew.getElemento_Id());
        perdida.setTipo_Perdida_Id(perdidaNew.getTipo_Perdida_Id());
        perdida.save();
        return perdida;
    }

    ///Eliminar Usuarios bd
    public void deletePerdidas() {
        Delete.table(Perdida.class);
    }

    public Perdida update(Perdida perdidaNew) {
        Perdida perdida = new Perdida();
        perdida.setPerdida_Id(perdidaNew.getPerdida_Id());
        perdida.setConcepto(perdidaNew.getConcepto());
        perdida.setCantidad(perdidaNew.getCantidad());
        perdida.setDescripcion(perdidaNew.getDescripcion());
        perdida.setValor(perdidaNew.getValor());
        perdida.setResponse_Checked(perdidaNew.isResponse_Checked());
        perdida.setElemento_Id(perdidaNew.getElemento_Id());
        perdida.setTipo_Perdida_Id(perdidaNew.getTipo_Perdida_Id());
        perdida.save();
        return perdida;
    }


    public List<Perdida> getListPerdida(long element_Id) {
        List<Perdida> lisFilter = SQLite.select().from(Perdida.class).where(Perdida_Table.Elemento_Id.eq(element_Id)).queryList();
        return lisFilter;
    }

    public Perdida getPerdidaByElementoIdAndTipo(long element_Id,long tipo_perdida_id){
        Perdida perdida = SQLite.select().from(Perdida.class).where(Perdida_Table.Elemento_Id.eq(element_Id))
                .and(Perdida_Table.Tipo_Perdida_Id.eq(tipo_perdida_id)).querySingle();
        return perdida;
    }

    public Response DeletePerdidaById(long perdida_id) {
        Response response = new Response();
        ///Elemento_Cable elementoCable= new Elemento_Cable();
        SQLite.delete(Perdida.class).where(Perdida_Table.Perdida_Id.eq(perdida_id)).async().execute();
        ///Elemento_Cable listee= new Select().from(Elemento_Cable.class).where(Elemento_Cable_Table.Elemento_Cable_Id.eq(elemento_cable_id)).querySingle();
        response.setMessage("Ok");
        response.setSuccess(true);
        return response;
    }
    /*
    ///Actualizar
    public Perdida update(Perdida perdidaUpdate){
        SQLite.update(Perdida.class)
                .set(Perdida_Table.Perdida_Id.eq(perdidaUpdate.getPerdida_Id()),
                        Perdida_Table.Concepto.eq(perdidaUpdate.getConcepto()),
                        Perdida_Table.Cantidad.eq(perdidaUpdate.getCantidad()),
                        Perdida_Table.Descripcion.eq(perdidaUpdate.getDescripcion()),
                        Perdida_Table.Valor.eq(perdidaUpdate.getValor()),
                        Perdida_Table.Response_Checked.eq(perdidaUpdate.isResponse_Checked()),
                        Perdida_Table.Elemento_Id.eq(perdidaUpdate.getElemento_Id()),
                        Perdida_Table.Tipo_Perdida_Id.eq(perdidaUpdate.getTipo_Perdida_Id())
                )
                .where(Perdida_Table.Perdida_Id.is(perdidaUpdate.getPerdida_Id()))
                //   .and(Usuario_Table.IsRemembered.is(true))
                .async()
                .execute(); // non-UI blocking

        Usuario user=  getLoggedUser();

        return user;
    }*/


}
