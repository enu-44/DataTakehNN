package com.datatakehnn.models.element_model;

import com.datatakehnn.config.DataSource;
import com.datatakehnn.models.nivel_tension_elemento_model.Nivel_Tension_Elemento;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * Created by user on 11/11/2017.
 */

/**
 * Created by user on 11/11/2017.
 */
@Table(database = DataSource.class)
public class Elemento extends BaseModel {
    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public long Elemento_Id;
    @Column
    public long Usuario_Id;
    @Column
    public String Codigo_Apoyo;
    @Column
    public String Numero_Apoyo;
    @Column
    public String Fecha_Levantamiento;
    @Column
    public String Hora_Inicio;
    @Column
    public String Hora_Fin;
    @Column
    public String Resistencia_Mecanica;
    @Column
    public long Retenidas;
    @Column
    public double Altura_Disponible;
    @Column
    public long Proyecto_Id;
    @Column
    public long Material_Id;
    @Column
    public long Estado_Id;
    @Column
    public long Longitud_Elemento_Id;
    @Column
    public long Nivel_Tension_Elemento_Id;

    //Sincronizacion
    @Column
    public boolean Is_Sync;

    ///LOcalizacion Elemento
    @Column
    public String Coordenadas;
    @Column
    public double Latitud;
    @Column
    public double Longitud;
    @Column
    public String Direccion;
    @Column
    public String Direccion_Aproximada_Gps;
    @Column
    public String Referencia_Localizacion;


    ///Contructor

    public Elemento() {

    }

    ///Methods
    public long getElemento_Id() {
        return Elemento_Id;
    }

    public void setElemento_Id(long elemento_Id) {
        Elemento_Id = elemento_Id;
    }

    public long getUsuario_Id() {
        return Usuario_Id;
    }

    public void setUsuario_Id(long usuario_Id) {
        Usuario_Id = usuario_Id;
    }

    public String getCodigo_Apoyo() {
        return Codigo_Apoyo;
    }

    public void setCodigo_Apoyo(String codigo_Apoyo) {
        Codigo_Apoyo = codigo_Apoyo;
    }

    public String getNumero_Apoyo() {
        return Numero_Apoyo;
    }

    public void setNumero_Apoyo(String numero_Apoyo) {
        Numero_Apoyo = numero_Apoyo;
    }

    public String getFecha_Levantamiento() {
        return Fecha_Levantamiento;
    }

    public void setFecha_Levantamiento(String fecha_Levantamiento) {
        Fecha_Levantamiento = fecha_Levantamiento;
    }

    public String getHora_Inicio() {
        return Hora_Inicio;
    }

    public void setHora_Inicio(String hora_Inicio) {
        Hora_Inicio = hora_Inicio;
    }

    public String getHora_Fin() {
        return Hora_Fin;
    }

    public void setHora_Fin(String hora_Fin) {
        Hora_Fin = hora_Fin;
    }

    public String getResistencia_Mecanica() {
        return Resistencia_Mecanica;
    }

    public void setResistencia_Mecanica(String resistencia_Mecanica) {
        Resistencia_Mecanica = resistencia_Mecanica;
    }

    public long getRetenidas() {
        return Retenidas;
    }

    public void setRetenidas(long retenidas) {
        Retenidas = retenidas;
    }

    public double getAltura_Disponible() {
        return Altura_Disponible;
    }

    public void setAltura_Disponible(double altura_Disponible) {
        Altura_Disponible = altura_Disponible;
    }

    public long getProyecto_Id() {
        return Proyecto_Id;
    }

    public void setProyecto_Id(long proyecto_Id) {
        Proyecto_Id = proyecto_Id;
    }

    public long getMaterial_Id() {
        return Material_Id;
    }

    public void setMaterial_Id(long material_Id) {
        Material_Id = material_Id;
    }

    public long getEstado_Id() {
        return Estado_Id;
    }

    public void setEstado_Id(long estado_Id) {
        Estado_Id = estado_Id;
    }

    public long getLongitud_Elemento_Id() {
        return Longitud_Elemento_Id;
    }

    public void setLongitud_Elemento_Id(long longitud_Elemento_Id) {
        Longitud_Elemento_Id = longitud_Elemento_Id;
    }

    public long getNivel_Tension_Elemento_Id() {
        return Nivel_Tension_Elemento_Id;
    }

    public void setNivel_Tension_Elemento_Id(long nivel_Tension_Elemento_Id) {
        Nivel_Tension_Elemento_Id = nivel_Tension_Elemento_Id;
    }


    //Verificar sincronizacion

    public boolean isIs_Sync() {
        return Is_Sync;
    }

    public void setIs_Sync(boolean is_Sync) {
        Is_Sync = is_Sync;
    }

    //Methods Localizacion

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

    public String getDireccion_Aproximada_Gps() {
        return Direccion_Aproximada_Gps;
    }

    public void setDireccion_Aproximada_Gps(String direccion_Aproximada_Gps) {
        Direccion_Aproximada_Gps = direccion_Aproximada_Gps;
    }

    public String getReferencia_Localizacion() {
        return Referencia_Localizacion;
    }

    public void setReferencia_Localizacion(String referencia_Localizacion) {
        Referencia_Localizacion = referencia_Localizacion;
    }
}
