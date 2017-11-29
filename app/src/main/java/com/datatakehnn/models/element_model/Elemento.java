package com.datatakehnn.models.element_model;

import android.os.Parcel;
import android.os.Parcelable;

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
public class Elemento extends BaseModel implements Parcelable {
    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public long Elemento_Id;
    @Column
    public long Usuario_Id;
    @Column
    public String Codigo_Apoyo;
    @Column
    public long Numero_Apoyo;
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

    //Datos Individuales Adicionales Dirección
    @Column
    public String Nombre_Direccion;

    @Column
    public String Via;

    @Column
    public String Con;

    @Column
    public String Descripcion_Direccion;

    @Column
    public String Referencia_Localizacion;


    //Ciudad
    @Column
    public long Ciudad_Id;

    @Column
    public String Nombre_Ciudad;

    @Column
    public long Departamento_Id;

    @Column
    public String Nombre_Departamento;


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

    public long getNumero_Apoyo() {
        return Numero_Apoyo;
    }

    public void setNumero_Apoyo(long numero_Apoyo) {
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

    public String getNombre_Direccion() {
        return Nombre_Direccion;
    }

    public void setNombre_Direccion(String nombre_Direccion) {
        Nombre_Direccion = nombre_Direccion;
    }

    public String getVia() {
        return Via;
    }

    public void setVia(String via) {
        Via = via;
    }

    public String getCon() {
        return Con;
    }

    public void setCon(String con) {
        Con = con;
    }

    public String getDescripcion_Direccion() {
        return Descripcion_Direccion;
    }

    public void setDescripcion_Direccion(String descripcion_Direccion) {
        Descripcion_Direccion = descripcion_Direccion;
    }

    public String getReferencia_Localizacion() {
        return Referencia_Localizacion;
    }

    public void setReferencia_Localizacion(String referencia_Localizacion) {
        Referencia_Localizacion = referencia_Localizacion;
    }


    public long getCiudad_Id() {
        return Ciudad_Id;
    }

    public void setCiudad_Id(long ciudad_Id) {
        Ciudad_Id = ciudad_Id;
    }

    public String getNombre_Ciudad() {
        return Nombre_Ciudad;
    }

    public void setNombre_Ciudad(String nombre_Ciudad) {
        Nombre_Ciudad = nombre_Ciudad;
    }

    public long getDepartamento_Id() {
        return Departamento_Id;
    }

    public void setDepartamento_Id(long departamento_Id) {
        Departamento_Id = departamento_Id;
    }

    public String getNombre_Departamento() {
        return Nombre_Departamento;
    }

    public void setNombre_Departamento(String nombre_Departamento) {
        Nombre_Departamento = nombre_Departamento;
    }


    protected Elemento(Parcel in) {
        Elemento_Id = in.readLong();
        Usuario_Id = in.readLong();
        Codigo_Apoyo = in.readString();
        Numero_Apoyo = in.readLong();
        Fecha_Levantamiento = in.readString();
        Hora_Inicio = in.readString();
        Hora_Fin = in.readString();
        Resistencia_Mecanica = in.readString();
        Retenidas = in.readLong();
        Altura_Disponible = in.readDouble();
        Proyecto_Id = in.readLong();
        Material_Id = in.readLong();
        Estado_Id = in.readLong();
        Longitud_Elemento_Id = in.readLong();
        Nivel_Tension_Elemento_Id = in.readLong();
        Is_Sync = in.readByte() != 0x00;
        Coordenadas = in.readString();
        Latitud = in.readDouble();
        Longitud = in.readDouble();
        Direccion = in.readString();
        Direccion_Aproximada_Gps = in.readString();
        Nombre_Direccion = in.readString();
        Via = in.readString();
        Con = in.readString();
        Descripcion_Direccion = in.readString();
        Referencia_Localizacion = in.readString();
        Ciudad_Id = in.readLong();
        Nombre_Ciudad = in.readString();
        Departamento_Id = in.readLong();
        Nombre_Departamento = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(Elemento_Id);
        dest.writeLong(Usuario_Id);
        dest.writeString(Codigo_Apoyo);
        dest.writeLong(Numero_Apoyo);
        dest.writeString(Fecha_Levantamiento);
        dest.writeString(Hora_Inicio);
        dest.writeString(Hora_Fin);
        dest.writeString(Resistencia_Mecanica);
        dest.writeLong(Retenidas);
        dest.writeDouble(Altura_Disponible);
        dest.writeLong(Proyecto_Id);
        dest.writeLong(Material_Id);
        dest.writeLong(Estado_Id);
        dest.writeLong(Longitud_Elemento_Id);
        dest.writeLong(Nivel_Tension_Elemento_Id);
        dest.writeByte((byte) (Is_Sync ? 0x01 : 0x00));
        dest.writeString(Coordenadas);
        dest.writeDouble(Latitud);
        dest.writeDouble(Longitud);
        dest.writeString(Direccion);
        dest.writeString(Direccion_Aproximada_Gps);
        dest.writeString(Nombre_Direccion);
        dest.writeString(Via);
        dest.writeString(Con);
        dest.writeString(Descripcion_Direccion);
        dest.writeString(Referencia_Localizacion);
        dest.writeLong(Ciudad_Id);
        dest.writeString(Nombre_Ciudad);
        dest.writeLong(Departamento_Id);
        dest.writeString(Nombre_Departamento);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Elemento> CREATOR = new Parcelable.Creator<Elemento>() {
        @Override
        public Elemento createFromParcel(Parcel in) {
            return new Elemento(in);
        }

        @Override
        public Elemento[] newArray(int size) {
            return new Elemento[size];
        }
    };
}