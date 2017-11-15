package com.datatakehnn.services.data_arrays;

import com.datatakehnn.models.estado_model.Estado;
import com.datatakehnn.models.tipo_equipo_model.Tipo_Equipo;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 13/11/2017.
 */

public class Tipo_Equipo_List {
    private static Tipo_Equipo[] list_tipo_equipos = {
            new Tipo_Equipo(1,
                    "Fuente"
            ),
            new Tipo_Equipo(2,
                    "Amplificador"
            ),
            new Tipo_Equipo(3,
                    "Nodo"
            )

    };

    /**
     * Obtiene como lista todos los estados de prueba
     *
     * @return Lista de cursos
     */
    public static List<Tipo_Equipo> getListTipoEquipo() {
        return Arrays.asList(list_tipo_equipos);
    }

    /**
     * Obtiene un curso basado en la posición del array
     *
     * @param position Posición en el array
     * @return Curso seleccioando
     */
    public static Tipo_Equipo getTipoEquipoPosition(int position) {
        return list_tipo_equipos[position];
    }
}
