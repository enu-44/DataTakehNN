package com.datatakehnn.models.retenidas_model;

/**
 * Created by user on 12/11/2017.
 */

public class Cantidad_Retenidas {
    public long Cantidad_Retenidas;

    public Cantidad_Retenidas(long cantidad_Retenidas) {
        Cantidad_Retenidas = cantidad_Retenidas;
    }

    public long getCantidad_Retenidas() {
        return Cantidad_Retenidas;
    }

    public void setCantidad_Retenidas(long cantidad_Retenidas) {
        Cantidad_Retenidas = cantidad_Retenidas;
    }

    @Override
    public String toString() {
        return String.valueOf(Cantidad_Retenidas);
    }
}
