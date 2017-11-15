package com.datatakehnn.services.data_arrays;

import com.datatakehnn.models.usuario_model.Usuario;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 11/11/2017.
 */

public class Usuario_List {
    private static Usuario[] list_user = {
            new Usuario(1,
                    "Oscar" ,
                    "Calvache",
                    "12345",
                    "3244676576876",
                    "Calle 6c # 23-3",
                    "coordinador@interedes.com.co",
                    "12345",
                    1,
                    false
            )
    };

    /**
     * Obtiene como lista todos los estados de prueba
     *
     * @return Lista de cursos
     */
    public static List<Usuario> getListUsuario() {
        return Arrays.asList(list_user);
    }

    /**
     * Obtiene un curso basado en la posición del array
     *
     * @param position Posición en el array
     * @return Curso seleccioando
     */
    public static Usuario getUsuarioPosition(int position) {
        return list_user[position];
    }
}
