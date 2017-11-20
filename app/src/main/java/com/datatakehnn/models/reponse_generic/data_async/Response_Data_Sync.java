package com.datatakehnn.models.reponse_generic.data_async;

import com.datatakehnn.models.departmentos_model.Departamento;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 20/11/2017.
 */

public class Response_Data_Sync {

    @SerializedName("departCiudades")
    public List<Departamento> Departamento;

    //Constructor
    public Response_Data_Sync(){

    }

    //Methods
    public List<Departamento> getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(List<Departamento> departamento) {
        Departamento = departamento;
    }
}
