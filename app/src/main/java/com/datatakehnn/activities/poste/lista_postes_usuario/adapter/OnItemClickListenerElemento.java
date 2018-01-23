package com.datatakehnn.activities.poste.lista_postes_usuario.adapter;

import com.datatakehnn.models.element_model.Elemento;

/**
 * Created by user on 16/11/2017.
 */

public interface OnItemClickListenerElemento {
    void onItemClick(Elemento elemento);

    void onSwitchChanged(Elemento elemento, boolean isSync);
}
