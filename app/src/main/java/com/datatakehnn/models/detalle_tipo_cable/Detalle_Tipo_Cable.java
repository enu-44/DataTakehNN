package com.datatakehnn.models.detalle_tipo_cable;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by user on 12/11/2017.
 */
@Table(database = DataSource.class)
public class Detalle_Tipo_Cable  extends BaseModel {

    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public  long Detalle_Tipo_Cable_Id;

    @SerializedName("Tipocable_Id")
    @Column
    public long Tipo_Cable_Id;

    @SerializedName("Cable_Id")
    @Column
    public long Cable_Id;

    @SerializedName("NombreCable")
    @Column
    public  String Nombre;

    @SerializedName("SiglaCable")
    @Column
    public  String Sigla;

    //Constructor
    public Detalle_Tipo_Cable(long detalle_Tipo_Cable_Id, long tipo_Cable_Id,long cable_Id, String nombre, String sigla) {
        Detalle_Tipo_Cable_Id = detalle_Tipo_Cable_Id;
        Tipo_Cable_Id = tipo_Cable_Id;
        Cable_Id = cable_Id;
        Nombre = nombre;
        Sigla = sigla;
    }

    public Detalle_Tipo_Cable(){

    }

    //Methods
    public long getDetalle_Tipo_Cable_Id() {
        return Detalle_Tipo_Cable_Id;
    }

    public void setDetalle_Tipo_Cable_Id(long detalle_Tipo_Cable_Id) {
        Detalle_Tipo_Cable_Id = detalle_Tipo_Cable_Id;
    }

    public long getTipo_Cable_Id() {
        return Tipo_Cable_Id;
    }

    public void setTipo_Cable_Id(long tipo_Cable_Id) {
        Tipo_Cable_Id = tipo_Cable_Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getSigla() {
        return Sigla;
    }

    public void setSigla(String sigla) {
        Sigla = sigla;
    }

    public long getCable_Id() {
        return Cable_Id;
    }

    public void setCable_Id(long cable_Id) {
        Cable_Id = cable_Id;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
