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
        user.setDepartamento_Id(userNew.getDepartamento_Id());
        user.setCiudad_Id(userNew.getCiudad_Id());
        user.setNombre_Ciudad(userNew.getNombre_Ciudad());
        user.setNombre_Departamento(userNew.getNombre_Departamento());
        user.save();
        return user;
    }

    ///Eliminar Usuarios bd
    public void deleteUsuarios() {
        Delete.table(Usuario.class);
    }

    ///Actualizar
    public Usuario update(Usuario userUpdate){
        SQLite.update(Usuario.class)
                .set(Usuario_Table.Usuario_Id.eq(userUpdate.getUsuario_Id()),
                        Usuario_Table.Nombre.eq(userUpdate.getNombre()),
                        Usuario_Table.Apellido.eq(userUpdate.getApellido()),
                        Usuario_Table.Cedula.eq(userUpdate.getCedula()),
                        Usuario_Table.Telefono.eq(userUpdate.getTelefono()),
                        Usuario_Table.Direccion.eq(userUpdate.getDireccion()),
                        Usuario_Table.Correo_Electronico.eq(userUpdate.getCorreo_Electronico()),
                        Usuario_Table.Password.eq(userUpdate.getPassword()),
                        Usuario_Table.Empresa_Id.eq(userUpdate.getEmpresa_Id()),
                        Usuario_Table.IsRemembered.eq(userUpdate.isRemembered()),
                        Usuario_Table.Ciudad_Id.eq(userUpdate.getCiudad_Id()),
                        Usuario_Table.Nombre_Ciudad.eq(userUpdate.getNombre_Ciudad()),
                        Usuario_Table.Departamento_Id.eq(userUpdate.getDepartamento_Id()),
                        Usuario_Table.Nombre_Departamento.eq(userUpdate.getNombre_Departamento())
                        )
                .where(Usuario_Table.Usuario_Id.is(userUpdate.getUsuario_Id()))
                .and(Usuario_Table.IsRemembered.is(true))
                .async()
                .execute(); // non-UI blocking

        return  getLoggedUser();
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
