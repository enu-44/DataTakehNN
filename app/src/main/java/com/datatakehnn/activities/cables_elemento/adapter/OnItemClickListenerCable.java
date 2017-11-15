package com.datatakehnn.activities.cables_elemento.adapter;

import com.datatakehnn.models.elemento_cable.Elemento_Cable;

/**
 * Created by user on 12/11/2017.
 */

public interface OnItemClickListenerCable {
    ///void onItemClick(View view, int position);
    void onItemClick(Elemento_Cable elemento_cable);
   // void onClickEdit(Elemento_Cable elemento_cable);
    void onClickDelete(Elemento_Cable elemento_cable);
}
