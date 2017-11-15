package com.datatakehnn.services.data_arrays;

import com.datatakehnn.models.tipo_noveda_model.Tipo_Novedad;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 11/11/2017.
 */

public class Tipo_Novedad_List {
    private static Tipo_Novedad[] list_tipo_novedad = {
            new Tipo_Novedad(
                    1,
                    "Codigo Apoyo"
            ),
            new Tipo_Novedad(
                    2,
                    "Resistencia Mecanica"
            ),
            new Tipo_Novedad(
                    3,
                    "Estado"
            )
    };

    /**
     * Obtiene como lista todos los estados de prueba
     *
     * @return Lista de cursos
     */
    public static List<Tipo_Novedad> getListTipoNovedad() {
        return Arrays.asList(list_tipo_novedad);
    }

    /**
     * Obtiene un curso basado en la posición del array
     *
     * @param position Posición en el array
     * @return Curso seleccioando
     */
    public static Tipo_Novedad getTipoNovedadPosition(int position) {
        return list_tipo_novedad[position];
    }
}
