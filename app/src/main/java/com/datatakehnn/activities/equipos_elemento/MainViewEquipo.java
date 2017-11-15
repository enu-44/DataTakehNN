package com.datatakehnn.activities.equipos_elemento;

import com.datatakehnn.models.elemento_cable.Elemento_Cable;
import com.datatakehnn.models.equipo_elemento_model.Equipo_Elemento;

import java.util.List;

/**
 * Created by user on 13/11/2017.
 */

public interface MainViewEquipo {
    void showProgresss();
    void hideProgress();

    void showUIElements();
    void hideUIElements();

    void onMessageOk(int colorPrimary,String message);
    void onMessageError(int colorPrimary,String message);

    void resultsList(List<Equipo_Elemento> listResult);

    void setContent(List<Equipo_Elemento> items);
}
