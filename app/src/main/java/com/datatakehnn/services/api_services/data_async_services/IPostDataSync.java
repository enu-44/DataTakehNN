package com.datatakehnn.services.api_services.data_async_services;

import com.datatakehnn.models.reponse_generic.Response;
import com.datatakehnn.models.reponse_generic.data_async.Response_Request_Data_Sync;
import com.datatakehnn.models.request_data_sync_model.Response_Post_Data_Sync;

/**
 * Created by usuario on 30/11/2017.
 */

public interface IPostDataSync {
    void processFinishPostDataAsync(Response_Post_Data_Sync response);
}
