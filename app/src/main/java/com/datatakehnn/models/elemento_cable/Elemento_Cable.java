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
public class Elemento_Cable extends BaseModel {

    //Atributes
    @SerializedName("Elemento_Cable_Id")
    @PrimaryKey(autoincrement = true)
    public long Elemento_Cable_Id;

    @SerializedName("Codigo")
    @Column
    public String Codigo;
    @SerializedName("Cantidad")
    @Column
    public long Cantidad;

    @SerializedName("SobreRbt")
    @Column
    public boolean SobreRbt;

    @SerializedName("Tiene_Marquilla")
    @Column
    public boolean Tiene_Marquilla;

    @SerializedName("Empresa_Id")
    @Column
    public long Empresa_Id;

    @SerializedName("Ciudad_Id")
    @Column
    public long Ciudad_Id;

    @SerializedName("Ciudad_Empresa_Id")
    @Column
    public long Ciudad_Empresa_Id;

    @SerializedName("DetalleTipocable_Id")
    @Column
    public long DetalleTipocable_Id;

    @SerializedName("Elemento_Id")
    @Column
    public long Elemento_Id;




    //Externos
    @Column
    public String Nombre_Detalle_Tipo_Cable;
    @Column
    public String Nombre_Tipo_Cable;
    @Column
    public String Nombre_Empresa;


    ///Constructor

    public Elemento_Cable( String codigo, long cantidad, boolean sobreRbt, boolean tiene_Marquilla, long empresa_Id, long ciudad_Id, long ciudad_Empresa_Id, long detalleTipocable_Id, long elemento_Id, String nombre_Detalle_Tipo_Cable, String nombre_Tipo_Cable, String nombre_Empresa) {

        Codigo = codigo;
        Cantidad = cantidad;
        SobreRbt = sobreRbt;
        Tiene_Marquilla = tiene_Marquilla;
        Empresa_Id = empresa_Id;
        Ciudad_Id = ciudad_Id;
        Ciudad_Empresa_Id = ciudad_Empresa_Id;
        DetalleTipocable_Id = detalleTipocable_Id;
        Elemento_Id = elemento_Id;
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
        return DetalleTipocable_Id;
    }

    public void setDetalle_Tipo_Cable_Id(long detalle_Tipo_Cable_Id) {
        DetalleTipocable_Id = detalle_Tipo_Cable_Id;
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
        return SobreRbt;
    }

    public void setSobre_Rbt(boolean sobre_Rbt) {
        SobreRbt = sobre_Rbt;
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

    public boolean isIs_Marquilla() {
        return Tiene_Marquilla;
    }

    public void setIs_Marquilla(boolean is_Marquilla) {
        Tiene_Marquilla = is_Marquilla;
    }


    public long getCiudad_Id() {
        return Ciudad_Id;
    }

    public void setCiudad_Id(long ciudad_Id) {
        Ciudad_Id = ciudad_Id;
    }

    public long getCiudad_Empresa_Id() {
        return Ciudad_Empresa_Id;
    }

    public void setCiudad_Empresa_Id(long ciudad_Empresa_Id) {
        Ciudad_Empresa_Id = ciudad_Empresa_Id;
    }
}
