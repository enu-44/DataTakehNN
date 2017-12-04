package com.datatakehnn.models.perdida_model;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = DataSource.class)
public class Perdida extends BaseModel {

    //Atributes
    @SerializedName("Perdida_Id")
    @PrimaryKey(autoincrement = true)
    public long Perdida_Id;

    @SerializedName("Concepto")
    @Column
    public String Concepto;

    @SerializedName("Cantidad")
    @Column
    public long Cantidad;

    @SerializedName("Descripcion")
    @Column
    public String Descripcion;

    @SerializedName("Valor")
    @Column
    public double Valor;

    @SerializedName("Response_Checked")
    @Column
    public boolean Response_Checked;


    @SerializedName("Elemento_Id")
    @Column
    public long Elemento_Id;

    @SerializedName("Tipo_Perdida_Id")
    @Column
    public long Tipo_Perdida_Id;


    public Perdida() {

    }

    public Perdida(String concepto, long cantidad, String descripcion, double valor, boolean response_Checked, long elemento_Id, long tipo_Perdida_Id) {
        Concepto = concepto;
        Cantidad = cantidad;
        Descripcion = descripcion;
        Valor = valor;
        Response_Checked = response_Checked;
        Elemento_Id = elemento_Id;
        Tipo_Perdida_Id = tipo_Perdida_Id;
    }

    public long getPerdida_Id() {
        return Perdida_Id;
    }

    public void setPerdida_Id(long perdida_Id) {
        Perdida_Id = perdida_Id;
    }

    public String getConcepto() {
        return Concepto;
    }

    public void setConcepto(String concepto) {
        Concepto = concepto;
    }

    public long getCantidad() {
        return Cantidad;
    }

    public void setCantidad(long cantidad) {
        Cantidad = cantidad;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public double getValor() {
        return Valor;
    }

    public void setValor(double valor) {
        Valor = valor;
    }

    public boolean isResponse_Checked() {
        return Response_Checked;
    }

    public void setResponse_Checked(boolean response_Checked) {
        Response_Checked = response_Checked;
    }

    public long getElemento_Id() {
        return Elemento_Id;
    }

    public void setElemento_Id(long elemento_Id) {
        Elemento_Id = elemento_Id;
    }

    public long getTipo_Perdida_Id() {
        return Tipo_Perdida_Id;
    }

    public void setTipo_Perdida_Id(long tipo_Perdida_Id) {
        Tipo_Perdida_Id = tipo_Perdida_Id;
    }
}
