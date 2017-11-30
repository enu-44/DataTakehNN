package com.datatakehnn.models.reponse_generic.data_async;

import com.datatakehnn.models.departmentos_model.Departamento;
import com.datatakehnn.models.detalle_tipo_cable.Detalle_Tipo_Cable;
import com.datatakehnn.models.detalle_tipo_novedad.Detalle_Tipo_Novedad;
import com.datatakehnn.models.empresa_model.Empresa;
import com.datatakehnn.models.estado_model.Estado;
import com.datatakehnn.models.longitud_elemento_model.Longitud_Elemento;
import com.datatakehnn.models.material_model.Material;
import com.datatakehnn.models.nivel_tension_elemento_model.Nivel_Tension_Elemento;
import com.datatakehnn.models.tipo_cable.Tipo_Cable;
import com.datatakehnn.models.tipo_equipo_model.Tipo_Equipo;
import com.datatakehnn.models.tipo_noveda_model.Tipo_Novedad;
import com.datatakehnn.models.tipo_perdida_model.Tipo_Perdida;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 20/11/2017.
 */

public class Response_Data_Sync {

    @SerializedName("tipocable")
    public List<Tipo_Cable> Tipo_Cable;

    @SerializedName("detalletipocable")
    public List<Detalle_Tipo_Cable> Detalle_Tipo_Cable;

    @SerializedName("tiponovedad")
    public List<Tipo_Novedad> Tipo_Novedad;

    @SerializedName("TipoEquipo")
    public List<Tipo_Equipo> Tipo_Equipo;

    @SerializedName("detalletiponovedad")
    public List<Detalle_Tipo_Novedad> Detalle_Tipo_Novedad;

    @SerializedName("empresa")
    public List<Empresa> Empresa;

    @SerializedName("estados")
    public List<Estado> Estados;

    @SerializedName("nivelTensionElementos")
    public List<Nivel_Tension_Elemento> Nivel_Tension_Elementos;

    @SerializedName("longitudElementos")
    public List<Longitud_Elemento> Longitud_Elementos;

    @SerializedName("materiales")
    public List<Material> Materiales;

    @SerializedName("tipo_Perdidas")
    public List<Tipo_Perdida> Tipo_Perdidas;


    @SerializedName("departCiudades")
    public List<Departamento> Departamento;

    //Constructor
    public Response_Data_Sync(){

    }

    //Methods


    public List<com.datatakehnn.models.tipo_cable.Tipo_Cable> getTipo_Cable() {
        return Tipo_Cable;
    }

    public void setTipo_Cable(List<com.datatakehnn.models.tipo_cable.Tipo_Cable> tipo_Cable) {
        Tipo_Cable = tipo_Cable;
    }

    public List<com.datatakehnn.models.detalle_tipo_cable.Detalle_Tipo_Cable> getDetalle_Tipo_Cable() {
        return Detalle_Tipo_Cable;
    }

    public void setDetalle_Tipo_Cable(List<com.datatakehnn.models.detalle_tipo_cable.Detalle_Tipo_Cable> detalle_Tipo_Cable) {
        Detalle_Tipo_Cable = detalle_Tipo_Cable;
    }

    public List<com.datatakehnn.models.tipo_noveda_model.Tipo_Novedad> getTipo_Novedad() {
        return Tipo_Novedad;
    }

    public void setTipo_Novedad(List<com.datatakehnn.models.tipo_noveda_model.Tipo_Novedad> tipo_Novedad) {
        Tipo_Novedad = tipo_Novedad;
    }

    public List<com.datatakehnn.models.tipo_equipo_model.Tipo_Equipo> getTipo_Equipo() {
        return Tipo_Equipo;
    }

    public void setTipo_Equipo(List<com.datatakehnn.models.tipo_equipo_model.Tipo_Equipo> tipo_Equipo) {
        Tipo_Equipo = tipo_Equipo;
    }

    public List<com.datatakehnn.models.detalle_tipo_novedad.Detalle_Tipo_Novedad> getDetalle_Tipo_Novedad() {
        return Detalle_Tipo_Novedad;
    }

    public void setDetalle_Tipo_Novedad(List<com.datatakehnn.models.detalle_tipo_novedad.Detalle_Tipo_Novedad> detalle_Tipo_Novedad) {
        Detalle_Tipo_Novedad = detalle_Tipo_Novedad;
    }

    public List<com.datatakehnn.models.empresa_model.Empresa> getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(List<com.datatakehnn.models.empresa_model.Empresa> empresa) {
        Empresa = empresa;
    }

    public List<Estado> getEstados() {
        return Estados;
    }

    public void setEstados(List<Estado> estados) {
        Estados = estados;
    }

    public List<Nivel_Tension_Elemento> getNivel_Tension_Elementos() {
        return Nivel_Tension_Elementos;
    }

    public void setNivel_Tension_Elementos(List<Nivel_Tension_Elemento> nivel_Tension_Elementos) {
        Nivel_Tension_Elementos = nivel_Tension_Elementos;
    }

    public List<Longitud_Elemento> getLongitud_Elementos() {
        return Longitud_Elementos;
    }

    public void setLongitud_Elementos(List<Longitud_Elemento> longitud_Elementos) {
        Longitud_Elementos = longitud_Elementos;
    }

    public List<Material> getMateriales() {
        return Materiales;
    }

    public void setMateriales(List<Material> materiales) {
        Materiales = materiales;
    }

    public List<Tipo_Perdida> getTipo_Perdidas() {
        return Tipo_Perdidas;
    }

    public void setTipo_Perdidas(List<Tipo_Perdida> tipo_Perdidas) {
        Tipo_Perdidas = tipo_Perdidas;
    }

    public List<Departamento> getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(List<Departamento> departamento) {
        Departamento = departamento;
    }
}
