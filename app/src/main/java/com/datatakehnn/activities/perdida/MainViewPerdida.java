package com.datatakehnn.activities.perdida;

import com.datatakehnn.models.perdida_model.Perdida;

import java.util.List;

/**
 * Created by user on 27/11/2017.
 */

public interface MainViewPerdida {

    void showProgresss();

    void hideProgress();

    void showUIElements();

    void hideUIElements();

    void onMessageOk(int colorPrimary, String message);

    void onMessageError(int colorPrimary, String message);

    void resultsList(List<Perdida> listResult);

    void setContent(List<Perdida> items);
}
