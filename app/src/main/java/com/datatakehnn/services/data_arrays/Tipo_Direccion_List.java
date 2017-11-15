package com.datatakehnn.services.data_arrays;

import com.datatakehnn.models.estado_model.Estado;
import com.datatakehnn.models.tipo_direccion_model.Tipo_Direccion;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 15/11/2017.
 */

public class Tipo_Direccion_List {
    private static Tipo_Direccion[] list_tipo_direccion = {
            new Tipo_Direccion(1,
                    "Calle"
            ),
            new Tipo_Direccion(2,
                    "Carrera"
            ),
            new Tipo_Direccion(3,
                    "Transversal"
            ),
            new Tipo_Direccion(4,
                    "Avenida"
            ),
            new Tipo_Direccion(5,
                    "Diagonal"
            ),
            new Tipo_Direccion(6,
                    "Kilometro"
            )

    };

    /**
     * Obtiene como lista todos los estados de prueba
     *
     * @return Lista de cursos
     */
    public static List<Tipo_Direccion> getListTipo_Direccion() {
        return Arrays.asList(list_tipo_direccion);
    }

    /**
     * Obtiene un curso basado en la posición del array
     *
     * @param position Posición en el array
     * @return Curso seleccioando
     */
    public static Tipo_Direccion getTipo_DireccionPosition(int position) {
        return list_tipo_direccion[position];
    }
}
