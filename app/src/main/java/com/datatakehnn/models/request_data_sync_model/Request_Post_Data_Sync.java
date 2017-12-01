package com.datatakehnn.models.request_data_sync_model;

import android.graphics.Bitmap;

import com.datatakehnn.models.elemento_cable.Elemento_Cable;
import com.datatakehnn.models.equipo_elemento_model.Equipo_Elemento;
import com.datatakehnn.models.foto_model.Foto;
import com.datatakehnn.models.novedad_model.Novedad;
import com.datatakehnn.models.perdida_model.Perdida;
import com.datatakehnn.models.request_data_sync_model.model_request.Foto_Request;
import com.datatakehnn.models.request_data_sync_model.model_request.Novedad_Request;

import java.util.List;

/**
 * Created by usuario on 30/11/2017.
 */

public class Request_Post_Data_Sync {

    public long Elemento_Id;
    public String CodigoApoyo;
    public long  NumeroApoyo;
    public String FechaLevantamiento;
    public String HoraInicio;
    public String HoraFin;
    public String ResistenciaMecanica;
    public long Retenidas;
    public double AlturaDisponible;
    public long Usuario_Id;
    public long Estado_id;
    public long Longitud_Elemento_Id;
    public long Material_Id;
    public long Proyecto_Id;
    public long Nivel_Tension_Id;
    public long Ciudad_Id;

    public List<Elemento_Cable> Cables;

    public List<Perdida> Perdidas;

    public List<Equipo_Elemento> Equipos;

    public List<Novedad_Request> Novedades;

    public List<Foto_Request> Fotos;

    //Constructor
    public Request_Post_Data_Sync(long elemento_Id, String codigoApoyo, long numeroApoyo, String fechaLevantamiento, String horaInicio, String horaFin, String resistenciaMecanica, long retenidas, double alturaDisponible, long usuario_Id, long estado_id, long longitud_Elemento_Id, long material_Id, long proyecto_Id, long nivel_Tension_Id, long ciudad_Id, List<Elemento_Cable> cables, List<Perdida> perdidas, List<Equipo_Elemento> equipos, List<Novedad_Request> novedades, List<Foto_Request> fotos) {
        Elemento_Id = elemento_Id;
        CodigoApoyo = codigoApoyo;
        NumeroApoyo = numeroApoyo;
        FechaLevantamiento = fechaLevantamiento;
        HoraInicio = horaInicio;
        HoraFin = horaFin;
        ResistenciaMecanica = resistenciaMecanica;
        Retenidas = retenidas;
        AlturaDisponible = alturaDisponible;
        Usuario_Id = usuario_Id;
        Estado_id = estado_id;
        Longitud_Elemento_Id = longitud_Elemento_Id;
        Material_Id = material_Id;
        Proyecto_Id = proyecto_Id;
        Nivel_Tension_Id = nivel_Tension_Id;
        Ciudad_Id = ciudad_Id;
        Cables = cables;
        Perdidas = perdidas;
        Equipos = equipos;
        Novedades = novedades;
        Fotos = fotos;
    }

    //Methods


    public long getElemento_Id() {
        return Elemento_Id;
    }

    public void setElemento_Id(long elemento_Id) {
        Elemento_Id = elemento_Id;
    }

    public String getCodigoApoyo() {
        return CodigoApoyo;
    }

    public void setCodigoApoyo(String codigoApoyo) {
        CodigoApoyo = codigoApoyo;
    }

    public long getNumeroApoyo() {
        return NumeroApoyo;
    }

    public void setNumeroApoyo(long numeroApoyo) {
        NumeroApoyo = numeroApoyo;
    }

    public String getFechaLevantamiento() {
        return FechaLevantamiento;
    }

    public void setFechaLevantamiento(String fechaLevantamiento) {
        FechaLevantamiento = fechaLevantamiento;
    }

    public String getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(String horaInicio) {
        HoraInicio = horaInicio;
    }

    public String getHoraFin() {
        return HoraFin;
    }

    public void setHoraFin(String horaFin) {
        HoraFin = horaFin;
    }

    public String getResistenciaMecanica() {
        return ResistenciaMecanica;
    }

    public void setResistenciaMecanica(String resistenciaMecanica) {
        ResistenciaMecanica = resistenciaMecanica;
    }

    public long getRetenidas() {
        return Retenidas;
    }

    public void setRetenidas(long retenidas) {
        Retenidas = retenidas;
    }

    public double getAlturaDisponible() {
        return AlturaDisponible;
    }

    public void setAlturaDisponible(double alturaDisponible) {
        AlturaDisponible = alturaDisponible;
    }

    public long getUsuario_Id() {
        return Usuario_Id;
    }

    public void setUsuario_Id(long usuario_Id) {
        Usuario_Id = usuario_Id;
    }

    public long getEstado_id() {
        return Estado_id;
    }

    public void setEstado_id(long estado_id) {
        Estado_id = estado_id;
    }

    public long getLongitud_Elemento_Id() {
        return Longitud_Elemento_Id;
    }

    public void setLongitud_Elemento_Id(long longitud_Elemento_Id) {
        Longitud_Elemento_Id = longitud_Elemento_Id;
    }

    public long getMaterial_Id() {
        return Material_Id;
    }

    public void setMaterial_Id(long material_Id) {
        Material_Id = material_Id;
    }

    public long getProyecto_Id() {
        return Proyecto_Id;
    }

    public void setProyecto_Id(long proyecto_Id) {
        Proyecto_Id = proyecto_Id;
    }

    public long getNivel_Tension_Id() {
        return Nivel_Tension_Id;
    }

    public void setNivel_Tension_Id(long nivel_Tension_Id) {
        Nivel_Tension_Id = nivel_Tension_Id;
    }

    public long getCiudad_Id() {
        return Ciudad_Id;
    }

    public void setCiudad_Id(long ciudad_Id) {
        Ciudad_Id = ciudad_Id;
    }

    public List<Elemento_Cable> getCables() {
        return Cables;
    }

    public void setCables(List<Elemento_Cable> cables) {
        Cables = cables;
    }

    public List<Perdida> getPerdidas() {
        return Perdidas;
    }

    public void setPerdidas(List<Perdida> perdidas) {
        Perdidas = perdidas;
    }

    public List<Equipo_Elemento> getEquipos() {
        return Equipos;
    }

    public void setEquipos(List<Equipo_Elemento> equipos) {
        Equipos = equipos;
    }

    public List<Novedad_Request> getNovedades() {
        return Novedades;
    }

    public void setNovedades(List<Novedad_Request> novedades) {
        Novedades = novedades;
    }

    public List<Foto_Request> getFotos() {
        return Fotos;
    }

    public void setFotos(List<Foto_Request> fotos) {
        Fotos = fotos;
    }
}
