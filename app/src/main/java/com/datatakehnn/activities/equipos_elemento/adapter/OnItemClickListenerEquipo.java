package com.datatakehnn.activities.equipos_elemento.adapter;

import com.datatakehnn.models.equipo_elemento_model.Equipo_Elemento;

/**
 * Created by user on 13/11/2017.
 */

public interface OnItemClickListenerEquipo {
    void onItemClick(Equipo_Elemento equipo_elemento);
    // void onClickEdit(Elemento_Cable elemento_cable);
    void onClickDelete(Equipo_Elemento equipo_elemento);
}
