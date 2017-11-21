package com.datatakehnn.models.localizcion_model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JAQUELINE on 21/11/2017.
 */

public class Localizacion implements Parcelable {
    public double latitud;
    public double longitud;
    public String direccion_gps;


    //constructor
    public Localizacion(){

    }

    //Methods
    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion_gps() {
        return direccion_gps;
    }

    public void setDireccion_gps(String direccion_gps) {
        this.direccion_gps = direccion_gps;
    }

    protected Localizacion(Parcel in) {
        latitud = in.readDouble();
        longitud = in.readDouble();
        direccion_gps = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
        dest.writeString(direccion_gps);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Localizacion> CREATOR = new Parcelable.Creator<Localizacion>() {
        @Override
        public Localizacion createFromParcel(Parcel in) {
            return new Localizacion(in);
        }

        @Override
        public Localizacion[] newArray(int size) {
            return new Localizacion[size];
        }
    };
}