package com.datatakehnn.controllers;

import android.content.Context;

import com.datatakehnn.models.foto_model.Foto;
import com.datatakehnn.models.foto_model.Foto_Table;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by TECNOLOGIA on 13/11/2017.
 */

public class FotoController {

    private static Context ourcontext;
    private static FotoController _instance;


    ///Instance
    /*----------------------------------------------------------------------------------------------------------------*/
    public FotoController() {
        _instance = this;
    }

    public static FotoController getInstance(Context c) {
        if (_instance == null) {
            ourcontext = c;
            _instance = new FotoController();
        }
        return _instance;
    }

    ///Methods

    ////Registrar

    public Foto register(Foto fotoNew) {
        Foto foto = new Foto();
        foto.setFoto_Id(fotoNew.getFoto_Id());
        foto.setElemento_Id(fotoNew.getElemento_Id());
        foto.setNovedad_Id(fotoNew.getNovedad_Id());
        foto.setDescripcion(fotoNew.getDescripcion());
        foto.setRuta_Foto(fotoNew.getRuta_Foto());
        //foto.setImage(fotoNew.getImage());
        foto.save();
        return foto;
    }

    ///Eliminar Fotos bd
    public void deleteElementos() {
        Delete.table(Foto.class);
    }

    //Actualizar
    public Foto update(Foto fotoNew) {
        Foto foto = new Foto();
        foto.setFoto_Id(fotoNew.getFoto_Id());
        foto.setElemento_Id(fotoNew.getElemento_Id());
        foto.setNovedad_Id(fotoNew.getNovedad_Id());
        foto.setDescripcion(fotoNew.getDescripcion());
        foto.setRuta_Foto(fotoNew.getRuta_Foto());
        // foto.setImage(fotoNew.getImage());
        foto.save();
        return foto;
    }

    public Foto getByRutaFotoAndElementoId(String ruta_foto, long elemento_id) {
        Foto foto = SQLite.select().from(Foto.class).where(Foto_Table.Ruta_Foto.eq(ruta_foto))
                .and(Foto_Table.Elemento_Id.eq(elemento_id)).querySingle();
        return foto;
    }

}
