package com.datatakehnn.models.foto_model;

import android.graphics.Bitmap;
import android.net.Uri;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.data.Blob;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by TECNOLOGIA on 13/11/2017.
 */

@Table(database = DataSource.class)
public class Foto extends BaseModel {

    //Atributes
    @SerializedName("Id")
    @PrimaryKey(autoincrement = true)
    public long Foto_Id;

    @SerializedName("Elemento_Id")
    @Column
    public long Elemento_Id;

    @SerializedName("Novedad_Id")
    @Column
    public long Novedad_Id;

    @SerializedName("Descripcion")
    @Column
    public String Descripcion;

    @SerializedName("Ruta_Foto")
    @Column
    public String Ruta_Foto;

    @SerializedName("Image")
    @Column
    public Blob Image;

    public Foto(long foto_Id, long elemento_Id, long novedad_Id, String descripcion, String ruta_Foto, Blob image) {
        Foto_Id = foto_Id;
        Elemento_Id = elemento_Id;
        Novedad_Id = novedad_Id;
        Descripcion = descripcion;
        Ruta_Foto = ruta_Foto;
        Image = image;
    }

    public Foto() {

    }

    public long getFoto_Id() {
        return Foto_Id;
    }

    public void setFoto_Id(long foto_Id) {
        Foto_Id = foto_Id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public long getElemento_Id() {
        return Elemento_Id;
    }

    public void setElemento_Id(long elemento_Id) {
        Elemento_Id = elemento_Id;
    }

    public long getNovedad_Id() {
        return Novedad_Id;
    }

    public void setNovedad_Id(long novedad_Id) {
        Novedad_Id = novedad_Id;
    }

    public String getRuta_Foto() {
        return Ruta_Foto;
    }

    public void setRuta_Foto(String ruta_Foto) {
        Ruta_Foto = ruta_Foto;
    }

    public Blob getImage() {
        return Image;
    }

    public void setImage(Blob image) {
        Image = image;
    }
}
