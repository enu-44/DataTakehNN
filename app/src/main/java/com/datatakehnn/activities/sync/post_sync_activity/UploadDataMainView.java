package com.datatakehnn.activities.sync.post_sync_activity;

/**
 * Created by usuario on 30/11/2017.
 */

public interface UploadDataMainView {
    void showProgresss();
    void hideProgress();

    void showUIElements();
    void hideUIElements();

    void onMessageOk(int colorPrimary,String message);
    void onMessageError(int colorPrimary,String message);
}
