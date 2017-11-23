package com.datatakehnn.services.data_arrays;

import com.datatakehnn.models.estado_model.Estado;
import com.datatakehnn.models.retenidas_model.Cantidad_Retenidas;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 12/11/2017.
 */

public class Cantidad_Retenidas_List {

    private static Cantidad_Retenidas[] list_cantidad_retenidas = {
            new Cantidad_Retenidas(0
            ),
            new Cantidad_Retenidas(1
            ),
            new Cantidad_Retenidas(2
            ),
            new Cantidad_Retenidas(3
            ),
            new Cantidad_Retenidas(4
            ),
            new Cantidad_Retenidas(5
            ),
            new Cantidad_Retenidas(6
            ),
            new Cantidad_Retenidas(7
            ),
    };

    /**
     * Obtiene como lista todos los estados de prueba
     *
     * @return Lista de cursos
     */
    public static List<Cantidad_Retenidas> getListCantidadRetenidas() {
        return Arrays.asList(list_cantidad_retenidas);
    }

    /**
     * Obtiene un curso basado en la posición del array
     *
     * @param position Posición en el array
     * @return Curso seleccioando
     */
    public static Cantidad_Retenidas getCantidadRetenidasPosition(int position) {
        return list_cantidad_retenidas[position];
    }
}
