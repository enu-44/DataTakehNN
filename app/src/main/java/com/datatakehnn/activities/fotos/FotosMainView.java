package com.datatakehnn.activities.fotos;

import com.datatakehnn.models.elemento_cable.Elemento_Cable;
import com.datatakehnn.models.novedad_model.Novedad;

import java.util.List;

/**
 * Created by usuario on 22/11/2017.
 */

public interface FotosMainView {
    void showProgresss();
    void hideProgress();

    void onMessageOk(int colorPrimary,String message);

    void onMessageError(int colorPrimary,String message);

    void resultsList(List<Novedad> listResult);

    void setContent(List<Novedad> items);
}
