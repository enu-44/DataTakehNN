package com.datatakehnn.services.data_arrays;

import com.datatakehnn.models.longitud_elemento_model.Longitud_Elemento;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 11/11/2017.
 */

public class Longitud_Elemento_List {
    private static Longitud_Elemento[] list_longitud = {
            new Longitud_Elemento(1,
                    6,
                    "mts"

            ),
            new Longitud_Elemento(2,
                    8,
                    "mts"

            ),
            new Longitud_Elemento(3,
                    12,
                    "mts"

            ),
            new Longitud_Elemento(4,
                    14,
                    "mts"
            ),
    };

    /**
     * Obtiene como lista todos los estados de prueba
     *
     * @return Lista de cursos
     */
    public static List<Longitud_Elemento> getListLongitudElemento() {
        return Arrays.asList(list_longitud);
    }
    /**
     * Obtiene un curso basado en la posición del array
     *
     * @param position Posición en el array
     * @return Curso seleccioando
     */
    public static Longitud_Elemento getLongitudElementoPosition(int position) {
        return list_longitud[position];
    }
}
