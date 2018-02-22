package com.datatakehnn.models.request_data_sync_model;

import android.graphics.Bitmap;

import com.datatakehnn.models.elemento_cable.Elemento_Cable;
import com.datatakehnn.models.equipo_elemento_model.Equipo_Elemento;
import com.datatakehnn.models.foto_model.Foto;
import com.datatakehnn.models.novedad_model.Novedad;
import com.datatakehnn.models.perdida_model.Perdida;
import com.datatakehnn.models.request_data_sync_model.model_request.Foto_Request;
import com.datatakehnn.models.request_data_sync_model.model_request.Novedad_Request;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

/**
 * Created by usuario on 30/11/2017.
 */

public class Request_Post_Data_Sync {

    @SerializedName("Elemento_Id")
    public long Elemento_Id;
    @SerializedName("CodigoApoyo")
    public String CodigoApoyo;
    @SerializedName("NumeroApoyo")
    public long  NumeroApoyo;
    @SerializedName("FechaLevantamiento")
    public String FechaLevantamiento;
    @SerializedName("HoraInicio")
    public String HoraInicio;
    @SerializedName("HoraFin")
    public String HoraFin;
    @SerializedName("ResistenciaMecanica")
    public String ResistenciaMecanica;
    @SerializedName("Retenidas")
    public long Retenidas;
    @SerializedName("AlturaDisponible")
    public double AlturaDisponible;
    @SerializedName("Usuario_Id")
    public long Usuario_Id;
    @SerializedName("Estado_id")
    public long Estado_id;
    @SerializedName("Longitud_Elemento_Id")
    public long Longitud_Elemento_Id;
    @SerializedName("Material_Id")
    public long Material_Id;
    @SerializedName("Proyecto_Id")
    public long Proyecto_Id;
    @SerializedName("Nivel_Tension_Id")
    public long Nivel_Tension_Id;
    @SerializedName("Ciudad_Id")
    public long Ciudad_Id;

    //Localizacion
    @SerializedName("Coordenadas")
    public String Coordenadas;
    @SerializedName("Latitud")
    public double Latitud;
    @SerializedName("Longitud")
    public double Longitud;
    @SerializedName("Direccion")
    public String Direccion;
    @SerializedName("DireccionAproximadaGps")
    public String DireccionAproximadaGps;
    @SerializedName("Barrio")
    public String Barrio;
    @SerializedName("Localidad")
    public String Localidad;
    @SerializedName("Sector")
    public String Sector;
    @SerializedName("ReferenciaLocalizacion")
    public String ReferenciaLocalizacion;

    @SerializedName("Imei_Device")
    public String Imei_Device;

    @SerializedName("Token_Elemento")
    public String Token_Elemento;


    @SerializedName("Cables")
    public List<Elemento_Cable> Cables;

    @SerializedName("Equipos")
    public List<Equipo_Elemento> Equipos;

    @SerializedName("Perdidas")
    public List<Perdida> Perdidas;

    @SerializedName("Novedades")
    public List<Novedad_Request> Novedades;

    @SerializedName("Fotos")
    public List<Foto_Request> Fotos;


    //Constructor
    /*public Request_Post_Data_Sync(long elemento_Id, String codigoApoyo, long numeroApoyo, String fechaLevantamiento, String horaInicio, String horaFin, String resistenciaMecanica, long retenidas, double alturaDisponible, long usuario_Id, long estado_id, long longitud_Elemento_Id, long material_Id, long proyecto_Id, long nivel_Tension_Id, long ciudad_Id, List<Elemento_Cable> cables, List<Perdida> perdidas, List<Equipo_Elemento> equipos, List<Novedad_Request> novedades, List<Foto_Request> fotos) {
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
    }*/



    public Request_Post_Data_Sync(long elemento_Id, String codigoApoyo, long numeroApoyo, String fechaLevantamiento, String horaInicio, String horaFin, String resistenciaMecanica, long retenidas, double alturaDisponible, long usuario_Id, long estado_id, long longitud_Elemento_Id, long material_Id, long proyecto_Id, long nivel_Tension_Id, long ciudad_Id, String coordenadas, double latitud, double longitud, String direccion, String direccionAproximadaGps, String barrio, String localidad, String sector, String referenciaLocalizacion, String imei_Device, String token_Elemento, List<Elemento_Cable> cables, List<Equipo_Elemento> equipos, List<Perdida> perdidas, List<Novedad_Request> novedades, List<Foto_Request> fotos) {
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
        Coordenadas = coordenadas;
        Latitud = latitud;
        Longitud = longitud;
        Direccion = direccion;
        DireccionAproximadaGps = direccionAproximadaGps;
        Barrio = barrio;
        Localidad = localidad;
        Sector = sector;
        ReferenciaLocalizacion = referenciaLocalizacion;
        Imei_Device = imei_Device;
        Token_Elemento = token_Elemento;
        Cables = cables;
        Equipos = equipos;
        Perdidas = perdidas;
        Novedades = novedades;
        Fotos = fotos;
    }

    public Request_Post_Data_Sync() {

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

    public String getCoordenadas() {
        return Coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        Coordenadas = coordenadas;
    }

    public double getLatitud() {
        return Latitud;
    }

    public void setLatitud(double latitud) {
        Latitud = latitud;
    }

    public double getLongitud() {
        return Longitud;
    }

    public void setLongitud(double longitud) {
        Longitud = longitud;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getDireccionAproximadaGps() {
        return DireccionAproximadaGps;
    }

    public void setDireccionAproximadaGps(String direccionAproximadaGps) {
        DireccionAproximadaGps = direccionAproximadaGps;
    }

    public String getBarrio() {
        return Barrio;
    }

    public void setBarrio(String barrio) {
        Barrio = barrio;
    }

    public String getLocalidad() {
        return Localidad;
    }

    public void setLocalidad(String localidad) {
        Localidad = localidad;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String sector) {
        Sector = sector;
    }

    public String getReferenciaLocalizacion() {
        return ReferenciaLocalizacion;
    }

    public void setReferenciaLocalizacion(String referenciaLocalizacion) {
        ReferenciaLocalizacion = referenciaLocalizacion;
    }

    public String getImei_Device() {
        return Imei_Device;
    }

    public void setImei_Device(String imei_Device) {
        Imei_Device = imei_Device;
    }


    public String getToken_Elemento() {
        return Token_Elemento;
    }

    public void setToken_Elemento(String token_Elemento) {
        Token_Elemento = token_Elemento;
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

    /*


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

    */
}
