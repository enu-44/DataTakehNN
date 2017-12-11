package com.datatakehnn.models.equipo_elemento_model;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by user on 13/11/2017.
 */
@Table(database = DataSource.class)
public class Equipo_Elemento  extends BaseModel {

    //Atributes

    @PrimaryKey(autoincrement = true)
    public long Equipo_Elemento_Id;

    @SerializedName("Codigo")
    @Column
    public String Codigo;

    @SerializedName("Descripcion")
    @Column
    public String Descripcion;

    @SerializedName("Cantidad")
    @Column
    public long Cantidad;

    @SerializedName("EmpresaId")
    @Column
    public long EmpresaId;

    @SerializedName("Ciudad_Id")
    @Column
    public long Ciudad_Id;

    @SerializedName("Ciudad_Empresa_Id")
    @Column
    public long Ciudad_Empresa_Id;

    @SerializedName("ConectadoRbt")
    @Column
    public boolean ConectadoRbt;

    @SerializedName("MedidorBt")
    @Column
    public boolean MedidorBt;

    @SerializedName("Consumo")
    @Column
    public long Consumo;

    @SerializedName("UnidadMedida")
    @Column
    public String UnidadMedida;

    @SerializedName("TipoEquipo_Id")
    @Column
    public long TipoEquipo_Id;

    @SerializedName("Elemento_Id")
    @Column
    public long Elemento_Id;



    //Externos
    @Column
    public String Nombre_Tipo_Equipo;
    @Column
    public String Nombre_Empresa;


    //Constructor

    public Equipo_Elemento( String codigo, String descripcion, long cantidad, long empresaId, boolean conectadoRbt, boolean medidorBt, long consumo, String unidadMedida, long tipoEquipo_Id, long elemento_Id, String nombre_Tipo_Equipo, String nombre_Empresa) {
        Codigo = codigo;
        Descripcion = descripcion;
        Cantidad = cantidad;
        EmpresaId = empresaId;
        ConectadoRbt = conectadoRbt;
        MedidorBt = medidorBt;
        Consumo = consumo;
        UnidadMedida = unidadMedida;
        TipoEquipo_Id = tipoEquipo_Id;
        Elemento_Id = elemento_Id;
        Nombre_Tipo_Equipo = nombre_Tipo_Equipo;
        Nombre_Empresa = nombre_Empresa;
    }

    public Equipo_Elemento( String codigo, String descripcion, long cantidad, long empresaId, long ciudad_Id, long ciudad_Empresa_Id, boolean conectadoRbt, boolean medidorBt, long consumo, String unidadMedida, long tipoEquipo_Id, long elemento_Id, String nombre_Tipo_Equipo, String nombre_Empresa) {

        Codigo = codigo;
        Descripcion = descripcion;
        Cantidad = cantidad;
        EmpresaId = empresaId;
        Ciudad_Id = ciudad_Id;
        Ciudad_Empresa_Id = ciudad_Empresa_Id;
        ConectadoRbt = conectadoRbt;
        MedidorBt = medidorBt;
        Consumo = consumo;
        UnidadMedida = unidadMedida;
        TipoEquipo_Id = tipoEquipo_Id;
        Elemento_Id = elemento_Id;
        Nombre_Tipo_Equipo = nombre_Tipo_Equipo;
        Nombre_Empresa = nombre_Empresa;
    }

    public Equipo_Elemento(){

    }

    public long getEquipo_Elemento_Id() {
        return Equipo_Elemento_Id;
    }

    public void setEquipo_Elemento_Id(long equipo_Elemento_Id) {
        Equipo_Elemento_Id = equipo_Elemento_Id;
    }

    public long getCantidad() {
        return Cantidad;
    }

    public void setCantidad(long cantidad) {
        Cantidad = cantidad;
    }

    public long getTipo_Equipo_Id() {
        return TipoEquipo_Id;
    }

    public void setTipo_Equipo_Id(long tipo_Activo_Id) {
        TipoEquipo_Id = tipo_Activo_Id;
    }

    public long getElemento_Id() {
        return Elemento_Id;
    }

    public void setElemento_Id(long elemento_Id) {
        Elemento_Id = elemento_Id;
    }

    public long getEmpresa_Id() {
        return EmpresaId;
    }

    public void setEmpresa_Id(long empresa_Id) {
        EmpresaId = empresa_Id;
    }

    public boolean isConectado_Red_Electrica() {
        return ConectadoRbt;
    }

    public void setConectado_Red_Electrica(boolean conectado_Red) {
        ConectadoRbt = conectado_Red;
    }

    public boolean isMedidor_Red() {
        return MedidorBt;
    }

    public void setMedidor_Red(boolean medidor_Red) {
        MedidorBt = medidor_Red;
    }


    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public long getEmpresaId() {
        return EmpresaId;
    }

    public void setEmpresaId(long empresaId) {
        EmpresaId = empresaId;
    }

    public boolean isConectadoRbt() {
        return ConectadoRbt;
    }

    public void setConectadoRbt(boolean conectadoRbt) {
        ConectadoRbt = conectadoRbt;
    }

    public boolean isMedidorBt() {
        return MedidorBt;
    }

    public void setMedidorBt(boolean medidorBt) {
        MedidorBt = medidorBt;
    }

    public long getConsumo() {
        return Consumo;
    }

    public void setConsumo(long consumo) {
        Consumo = consumo;
    }

    public String getUnidadMedida() {
        return UnidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        UnidadMedida = unidadMedida;
    }

    public long getTipoEquipo_Id() {
        return TipoEquipo_Id;
    }

    public void setTipoEquipo_Id(long tipoEquipo_Id) {
        TipoEquipo_Id = tipoEquipo_Id;
    }

    public String getNombre_Tipo_Equipo() {
        return Nombre_Tipo_Equipo;
    }

    public void setNombre_Tipo_Equipo(String nombre_Tipo_Equipo) {
        Nombre_Tipo_Equipo = nombre_Tipo_Equipo;
    }

    public String getNombre_Empresa() {
        return Nombre_Empresa;
    }

    public void setNombre_Empresa(String nombre_Empresa) {
        Nombre_Empresa = nombre_Empresa;
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
