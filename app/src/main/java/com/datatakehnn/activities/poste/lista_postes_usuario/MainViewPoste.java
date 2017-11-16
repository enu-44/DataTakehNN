package com.datatakehnn.activities.poste.lista_postes_usuario;

import com.datatakehnn.models.element_model.Elemento;
import com.datatakehnn.models.elemento_cable.Elemento_Cable;

import java.util.List;

/**
 * Created by user on 16/11/2017.
 */

public interface MainViewPoste {
    void showProgresss();
    void hideProgress();

    void showUIElements();
    void hideUIElements();

    void onMessageOk(int colorPrimary,String message);
    void onMessageError(int colorPrimary,String message);

    void resultsList(List<Elemento> listResult);

    void setContent(List<Elemento> items);
}
