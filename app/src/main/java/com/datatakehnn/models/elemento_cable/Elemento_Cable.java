package com.datatakehnn.models.elemento_cable;

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
public class Elemento_Cable extends BaseModel{

    //Atributes
    @SerializedName("Id")
    @PrimaryKey(autoincrement = true)
    public  long Elemento_Cable_Id;
    @Column
    public  long Detalle_Tipo_Cable_Id;
    @Column
    public  long Elemento_Id;
    @Column
    public  String Codigo;
    @Column
    public  long Empresa_Id;
    @Column
    public  long Cantidad;
    @Column
    public  boolean Sobre_Rbt;

    //Externos
    @Column
    public String Nombre_Detalle_Tipo_Cable;
    @Column
    public String Nombre_Tipo_Cable;
    @Column
    public String Nombre_Empresa;


    ///Constructor
  public Elemento_Cable( long detalle_Tipo_Cable_Id, long elemento_Id, String codigo, long empresa_Id, long cantidad, boolean sobre_Rbt, String nombre_Detalle_Tipo_Cable, String nombre_Tipo_Cable, String nombre_Empresa) {

        Detalle_Tipo_Cable_Id = detalle_Tipo_Cable_Id;
        Elemento_Id = elemento_Id;
        Codigo = codigo;
        Empresa_Id = empresa_Id;
        Cantidad = cantidad;
        Sobre_Rbt = sobre_Rbt;
        Nombre_Detalle_Tipo_Cable = nombre_Detalle_Tipo_Cable;
        Nombre_Tipo_Cable = nombre_Tipo_Cable;
        Nombre_Empresa = nombre_Empresa;
    }

    public Elemento_Cable() {


    }


    //Methods
    public long getElemento_Cable_Id() {
        return Elemento_Cable_Id;
    }

    public void setElemento_Cable_Id(long elemento_Cable_Id) {
        Elemento_Cable_Id = elemento_Cable_Id;
    }

    public long getDetalle_Tipo_Cable_Id() {
        return Detalle_Tipo_Cable_Id;
    }

    public void setDetalle_Tipo_Cable_Id(long detalle_Tipo_Cable_Id) {
        Detalle_Tipo_Cable_Id = detalle_Tipo_Cable_Id;
    }

    public long getElemento_Id() {
        return Elemento_Id;
    }

    public void setElemento_Id(long elemento_Id) {
        Elemento_Id = elemento_Id;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public long getEmpresa_Id() {
        return Empresa_Id;
    }

    public void setEmpresa_Id(long empresa_Id) {
        Empresa_Id = empresa_Id;
    }

    public long getCantidad() {
        return Cantidad;
    }

    public void setCantidad(long cantidad) {
        Cantidad = cantidad;
    }

    public boolean isSobre_Rbt() {
        return Sobre_Rbt;
    }

    public void setSobre_Rbt(boolean sobre_Rbt) {
        Sobre_Rbt = sobre_Rbt;
    }


    //Externos

    public String getNombre_Detalle_Tipo_Cable() {
        return Nombre_Detalle_Tipo_Cable;
    }

    public void setNombre_Detalle_Tipo_Cable(String nombre_Detalle_Tipo_Cable) {
        Nombre_Detalle_Tipo_Cable = nombre_Detalle_Tipo_Cable;
    }

    public String getNombre_Tipo_Cable() {
        return Nombre_Tipo_Cable;
    }

    public void setNombre_Tipo_Cable(String nombre_Tipo_Cable) {
        Nombre_Tipo_Cable = nombre_Tipo_Cable;
    }

    public String getNombre_Empresa() {
        return Nombre_Empresa;
    }

    public void setNombre_Empresa(String nombre_Empresa) {
        Nombre_Empresa = nombre_Empresa;
    }
}
