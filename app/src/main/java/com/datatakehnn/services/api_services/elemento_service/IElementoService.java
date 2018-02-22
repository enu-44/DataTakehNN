package com.datatakehnn.services.api_services.elemento_service;

import com.datatakehnn.models.reponse_generic.login.Response_Request_Login;
import com.datatakehnn.models.request_data_sync_model.Response_Post_Data_Sync;

/**
 * Created by usuario on 22/02/2018.
 */

public interface IElementoService {
    void processFinishGetElemento(Response_Post_Data_Sync response);
}
