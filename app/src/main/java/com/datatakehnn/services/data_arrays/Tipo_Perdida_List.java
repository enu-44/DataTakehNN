package com.datatakehnn.services.data_arrays;

import com.datatakehnn.models.tipo_perdida_model.Tipo_Perdida;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 27/11/2017.
 */

public class Tipo_Perdida_List {
    private static Tipo_Perdida[] list_tipo_perdida = {
            new Tipo_Perdida(
                    1,
                    "Lampara Adicional"
            ),
            new Tipo_Perdida(
                    2,
                    "Lampara Encendida"
            ),
            new Tipo_Perdida(
                    3,
                    "Conexion Ilicita"
            ),
            new Tipo_Perdida(
                    4,
                    "Requiere Poda"
            )

    };

    /**
     * Obtiene como lista todos los estados de prueba
     *
     * @return Lista de cursos
     */
    public static List<Tipo_Perdida> getListTipoPerdida() {
        return Arrays.asList(list_tipo_perdida);
    }

    /**
     * Obtiene un curso basado en la posición del array
     *
     * @param position Posición en el array
     * @return Curso seleccioando
     */
    public static Tipo_Perdida getTipoPerdidaPosition(int position) {
        return list_tipo_perdida[position];
    }
}
