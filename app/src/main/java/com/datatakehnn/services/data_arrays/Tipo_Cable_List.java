package com.datatakehnn.services.data_arrays;

import com.datatakehnn.models.estado_model.Estado;
import com.datatakehnn.models.tipo_cable.Tipo_Cable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 12/11/2017.
 */

public class Tipo_Cable_List
{
    private static Tipo_Cable[] list_tipo_cables = {
            new Tipo_Cable(1,
                    "Cable Red"
            ),
            new Tipo_Cable(2,
                    "Cable Comunicacion"
            ),
            new Tipo_Cable(3,
                    "Reserva"
            ),
    };

    /**
     * Obtiene como lista todos los estados de prueba
     *
     * @return Lista de cursos
     */
    public static List<Tipo_Cable> getListTipo_Cable() {
        return Arrays.asList(list_tipo_cables);
    }

    /**
     * Obtiene un curso basado en la posición del array
     *
     * @param position Posición en el array
     * @return Curso seleccioando
     */
    public static Tipo_Cable getTipo_CablePosition(int position) {
        return list_tipo_cables[position];
    }
}
