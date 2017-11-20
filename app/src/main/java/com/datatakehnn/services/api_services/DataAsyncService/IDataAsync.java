package com.datatakehnn.services.api_services.DataAsyncService;

import com.datatakehnn.models.reponse_generic.data_async.Response_Request_Data_Sync;

/**
 * Created by user on 20/11/2017.
 */

public interface IDataAsync {

    void processFinishGetDataAsync(Response_Request_Data_Sync response);

}
