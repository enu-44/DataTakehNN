package com.datatakehnn.models.master;

import com.datatakehnn.config.DataSource;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by usuario on 13/12/2017.
 */
@Table(database = DataSource.class)
public class Device_Master extends BaseModel {

    //Atributes
    @SerializedName("Id")
    @PrimaryKey
    public long Id_Device_Master;

    @SerializedName("Imei")
    @Column
    public String Imei;

    @SerializedName("Phone_Type_Device")
    @Column
    public String Phone_Type_Device;

    @SerializedName("Android_Id")
    @Column
    public String Android_Id;

    @SerializedName("Software_Version")
    @Column
    public String Software_Version;

    @SerializedName("Local_Ip_Address")
    @Column
    public String Local_Ip_Address;

    @SerializedName("Android_Version")
    @Column
    public String Android_Version;

    @SerializedName("MacAddr")
    @Column
    public String MacAddr;

    @SerializedName("Device_Name")
    @Column
    public String Device_Name;

    @SerializedName("Direccion_Ip")
    @Column
    public String Direccion_Ip;

    @SerializedName("Estado")
    @Column
    public boolean Estado_Device;

    ///Construtor

    public Device_Master(long id_Device_Master, String imei, String phone_Type_Device, String android_Id, String software_Version, String local_Ip_Address, String android_Version, String macAddr, String device_Name, String direccion_Ip, boolean estado_Device) {
        Id_Device_Master = id_Device_Master;
        Imei = imei;
        Phone_Type_Device = phone_Type_Device;
        Android_Id = android_Id;
        Software_Version = software_Version;
        Local_Ip_Address = local_Ip_Address;
        Android_Version = android_Version;
        MacAddr = macAddr;
        Device_Name = device_Name;
        Direccion_Ip = direccion_Ip;
        Estado_Device = estado_Device;
    }

    public  Device_Master(){

    }


    //Methods


    public long getId_Device_Master() {
        return Id_Device_Master;
    }

    public void setId_Device_Master(long id_Device_Master) {
        Id_Device_Master = id_Device_Master;
    }

    public String getImei() {
        return Imei;
    }

    public void setImei(String imei) {
        Imei = imei;
    }

    public String getPhone_Type_Device() {
        return Phone_Type_Device;
    }

    public void setPhone_Type_Device(String phone_Type_Device) {
        Phone_Type_Device = phone_Type_Device;
    }

    public String getAndroid_Id() {
        return Android_Id;
    }

    public void setAndroid_Id(String android_Id) {
        Android_Id = android_Id;
    }

    public String getSoftware_Version() {
        return Software_Version;
    }

    public void setSoftware_Version(String software_Version) {
        Software_Version = software_Version;
    }

    public String getLocal_Ip_Address() {
        return Local_Ip_Address;
    }

    public void setLocal_Ip_Address(String local_Ip_Address) {
        Local_Ip_Address = local_Ip_Address;
    }

    public String getAndroid_Version() {
        return Android_Version;
    }

    public void setAndroid_Version(String android_Version) {
        Android_Version = android_Version;
    }

    public String getMacAddr() {
        return MacAddr;
    }

    public void setMacAddr(String macAddr) {
        MacAddr = macAddr;
    }

    public String getDevice_Name() {
        return Device_Name;
    }

    public void setDevice_Name(String device_Name) {
        Device_Name = device_Name;
    }

    public String getDireccion_Ip() {
        return Direccion_Ip;
    }

    public void setDireccion_Ip(String direccion_Ip) {
        Direccion_Ip = direccion_Ip;
    }

    public boolean isEstado_Device() {
        return Estado_Device;
    }

    public void setEstado_Device(boolean estado_Device) {
        Estado_Device = estado_Device;
    }
}
