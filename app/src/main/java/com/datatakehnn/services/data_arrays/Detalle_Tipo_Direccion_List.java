package com.datatakehnn.services.data_arrays;

import com.datatakehnn.models.tipo_direccion_model.Detalle_Tipo_Direccion;
import com.datatakehnn.models.tipo_direccion_model.Tipo_Direccion;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 15/11/2017.
 */

class Detalle_Tipo_Direccion_List {
    private static Detalle_Tipo_Direccion[] list_detalle_tipo_direccion = {
            new Detalle_Tipo_Direccion(1,
                    "#"
            ),
            new Detalle_Tipo_Direccion(2,
                    "Calle"
            ),
            new Detalle_Tipo_Direccion(3,
                    "Carrera"
            ),
            new Detalle_Tipo_Direccion(4,
                    "Via"
            ),
            new Detalle_Tipo_Direccion(5,
                    "Bis"
            )
    };

    /**
     * Obtiene como lista todos los estados de prueba
     *
     * @return Lista de cursos
     */
    public static List<Detalle_Tipo_Direccion> getListDetalle_Tipo_Direccion() {
        return Arrays.asList(list_detalle_tipo_direccion);
    }

    /**
     * Obtiene un curso basado en la posición del array
     *
     * @param position Posición en el array
     * @return Curso seleccioando
     */
    public static Detalle_Tipo_Direccion getDetalle_Tipo_DireccionPosition(int position) {
        return list_detalle_tipo_direccion[position];
    }
}
