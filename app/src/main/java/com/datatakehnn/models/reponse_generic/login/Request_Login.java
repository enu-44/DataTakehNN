package com.datatakehnn.models.reponse_generic.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by usuario on 13/12/2017.
 */

public class Request_Login {

    @SerializedName("Cedula")
    private String Cedula;

    @SerializedName("Passsword")
    private String Passsword;


    @SerializedName("Imei")
    private String Imei;

    @SerializedName("Phone_Type_Device")
    private String Phone_Type_Device;

    @SerializedName("Android_Id")
    private String Android_Id;

    @SerializedName("Software_Version")
    private String Software_Version;

    @SerializedName("Local_Ip_Address")
    private String Local_Ip_Address;

    @SerializedName("Android_Version")
    private String Android_Version;

    @SerializedName("MacAddr")
    private String MacAddr;

    @SerializedName("Device_Name")
    private String Device_Name;

    @SerializedName("Direccion_Ip")
    private String Direccion_Ip;

    @SerializedName("Estado")
    private boolean Estado;


    public Request_Login(String cedula, String passsword, String imei, String phone_Type_Device, String android_Id, String software_Version, String local_Ip_Address, String android_Version, String macAddr, String device_Name, String direccion_Ip, boolean estado) {
        Cedula = cedula;
        Passsword = passsword;
        Imei = imei;
        Phone_Type_Device = phone_Type_Device;
        Android_Id = android_Id;
        Software_Version = software_Version;
        Local_Ip_Address = local_Ip_Address;
        Android_Version = android_Version;
        MacAddr = macAddr;
        Device_Name = device_Name;
        Direccion_Ip = direccion_Ip;
        Estado = estado;
    }
}
