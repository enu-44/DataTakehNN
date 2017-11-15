package com.datatakehnn.activities.cables_elemento;

import com.datatakehnn.models.elemento_cable.Elemento_Cable;

import java.util.List;

/**
 * Created by user on 13/11/2017.
 */

public interface MainViewCablesElemento {
    void showProgresss();
    void hideProgress();

    void showUIElements();
    void hideUIElements();

    void onMessageOk(int colorPrimary,String message);
    void onMessageError(int colorPrimary,String message);

    void resultsList(List<Elemento_Cable> listResult);

    void setContent(List<Elemento_Cable> items);
}
