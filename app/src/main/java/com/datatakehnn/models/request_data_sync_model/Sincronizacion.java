package com.datatakehnn.models.request_data_sync_model;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by usuario on 30/11/2017.
 */
@Table(database = DataSource.class)
public class Sincronizacion extends BaseModel {
    //Atributes
    @PrimaryKey(autoincrement = true)
    public long Sincronizacion_Id;
    @Column
    public long Usuario_Id;
    @Column
    public String Fecha;
    @Column
    public String Hora;
    @Column
    public String Cuenta;
    @Column
    public String Usuario;
    @Column
    public String Codigos_Elementos_Sync;

    //Contructor

    public Sincronizacion(long usuario_Id, String fecha, String hora, String cuenta, String usuario, String codigos_Elementos_Sync) {

        Usuario_Id = usuario_Id;
        Fecha = fecha;
        Hora = hora;
        Cuenta = cuenta;
        Usuario = usuario;
        Codigos_Elementos_Sync = codigos_Elementos_Sync;
    }

    public Sincronizacion() {

    }

    //Methods
    public long getSincronizacion_Id() {
        return Sincronizacion_Id;
    }

    public void setSincronizacion_Id(long sincronizacion_Id) {
        Sincronizacion_Id = sincronizacion_Id;
    }

    public long getUsuario_Id() {
        return Usuario_Id;
    }

    public void setUsuario_Id(long usuario_Id) {
        Usuario_Id = usuario_Id;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getCuenta() {
        return Cuenta;
    }

    public void setCuenta(String cuenta) {
        Cuenta = cuenta;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getCodigos_Elementos_Sync() {
        return Codigos_Elementos_Sync;
    }

    public void setCodigos_Elementos_Sync(String codigos_Elementos_Sync) {
        Codigos_Elementos_Sync = codigos_Elementos_Sync;
    }
}
