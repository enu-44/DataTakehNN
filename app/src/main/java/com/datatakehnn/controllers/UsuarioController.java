package com.datatakehnn.controllers;

import android.content.Context;
import com.datatakehnn.models.usuario_model.Usuario;
import com.datatakehnn.models.reponse_generic.Response;
import com.datatakehnn.models.usuario_model.Usuario_Table;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

/**
 * Created by user on 11/11/2017.
 */

public class UsuarioController {

    private static Context ourcontext;
    private static UsuarioController _instance;


    ///Instance
    /*----------------------------------------------------------------------------------------------------------------*/
    public UsuarioController() {
        _instance = this;
    }
    public static UsuarioController getInstance(Context c) {
        if (_instance == null) {
            ourcontext = c;
            _instance = new UsuarioController();
        }
        return _instance;
    }


    ///Methods

    ////Registrar
    public Usuario register(Usuario userNew){
        Usuario user= new Usuario();
        user.setUsuario_Id(userNew.getUsuario_Id());
        user.setNombre(userNew.getNombre());
        user.setApellido(userNew.getApellido());
        user.setCedula(userNew.getCedula());
        user.setCorreo_Electronico(userNew.getCorreo_Electronico());
        user.setDireccion(userNew.getDireccion());
        user.setEmpresa_Id(userNew.getEmpresa_Id());
        user.setPassword(userNew.getPassword());
        user.setTelefono(userNew.getTelefono());
        user.setRemembered(userNew.isRemembered());
        user.save();
        return user;
    }

    ///Eliminar Usuarios bd
    public void deleteUsuarios() {
        Delete.table(Usuario.class);
    }

    ///Actualizar
    public Usuario update(Usuario userNew){
        Usuario user= new Usuario();
        user.setUsuario_Id(userNew.getUsuario_Id());
        user.setNombre(userNew.getNombre());
        user.setApellido(userNew.getApellido());
        user.setCedula(userNew.getCedula());
        user.setCorreo_Electronico(userNew.getCorreo_Electronico());
        user.setDireccion(userNew.getDireccion());
        user.setEmpresa_Id(userNew.getEmpresa_Id());
        user.setPassword(userNew.getPassword());
        user.setTelefono(userNew.getTelefono());
        user.setRemembered(userNew.isRemembered());
        user.save();
        return user;
    }


    ///Cerrar Sesion
    public Usuario logoutLogin(){
        Usuario userfirst= SQLite.select().from(Usuario.class).where(Usuario_Table.IsRemembered.eq(true)).querySingle();
        userfirst.setRemembered(false);
        userfirst.save();
        Usuario userUpdate=SQLite.select().from(Usuario.class).querySingle();
        return userUpdate;
    }

    ///Obtener el primero
    public Usuario getFirst() {
        Usuario user=new Select().from(Usuario.class).querySingle();
        return  user;
    }

    //Get User Logueado
    public Usuario getLoggedUser() {
        Usuario loggedUser = SQLite.select().from(Usuario.class).where(Usuario_Table.IsRemembered.eq(true)).querySingle();
        return loggedUser;
    }

    ////Login Usuario_List
    public Response loginUsuario(String Cedula, String Password) {
        Usuario user= new Usuario();
        user=new Select().from(Usuario.class).where(Usuario_Table.Cedula.eq(Cedula), Usuario_Table.Password.eq(Password)).querySingle();
        Response response= new Response();

        if(user!=null){
            response.setMessage("login Ok");
            response.setResult(user);
            response.setSuccess(true);
        }else{
            response.setMessage("Cedula o contraseña incorrectos");
            response.setResult(user);
            response.setSuccess(false);
        }
        return  response;
    }




}
