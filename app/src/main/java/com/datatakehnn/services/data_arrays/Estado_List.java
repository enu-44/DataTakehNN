package com.datatakehnn.services.data_arrays;

import com.datatakehnn.models.estado_model.Estado;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 11/11/2017.
 */

public class Estado_List {

    private static Estado[] list_estados = {
            new Estado(1,
                    "Bueno" ,
                    "B"
                   ),
            new Estado(2,
                    "Malo" ,
                    "B"
            ),
    };

    /**
     * Obtiene como lista todos los estados de prueba
     *
     * @return Lista de cursos
     */
    public static List<Estado> getListEstado() {
        return Arrays.asList(list_estados);
    }

    /**
     * Obtiene un curso basado en la posición del array
     *
     * @param position Posición en el array
     * @return Curso seleccioando
     */
    public static Estado getEstadoPosition(int position) {
        return list_estados[position];
    }
}
