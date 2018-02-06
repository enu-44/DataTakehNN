package com.datatakehnn.controllers;

import android.content.Context;

import com.datatakehnn.models.configuracion_model.Setting;
import com.datatakehnn.models.configuracion_model.Setting_Table;
import com.raizlabs.android.dbflow.sql.language.Select;

/**
 * Created by usuario on 6/12/2017.
 */

public class SettingController {

    private static Context ourcontext;
    private static SettingController _instance;


    ///Instance
    /*----------------------------------------------------------------------------------------------------------------*/
    public SettingController() {
        _instance = this;
    }

    public static SettingController getInstance(Context c) {
        if (_instance == null) {
            ourcontext = c;
            _instance = new SettingController();
        }
        return _instance;
    }

    ///Methods
    ////Registrar
    public Setting registerUpdate(Setting setting) {
        Setting setting1 = new Setting();
        setting1.setSetting_Id(setting.getSetting_Id());
        setting1.setAvailable_Mobile_Data(setting.isAvailable_Mobile_Data());
        setting1.setAvailable_Wifi(setting.isAvailable_Wifi());
        setting1.setFecha(setting.getFecha());
        setting1.setHora(setting.getHora());
        setting1.setDescripcion_Storage_Phone(setting.getDescripcion_Storage_Phone());
        setting1.setSigla_Storage(setting.getSigla_Storage());
        setting1.setRuta_Servicio(setting.getRuta_Servicio());
        setting1.save();
        return setting1;
    }

    ///Obtener el primero
    public Setting getFirst() {
        Setting setting = new Select().from(Setting.class).querySingle();
        return setting;
    }

    ///Obtener el primero
    public Setting getByDate(String Fecha) {
        Setting setting = new Select().from(Setting.class).where(Setting_Table.Fecha.eq(Fecha)).querySingle();
        return setting;
    }


}
