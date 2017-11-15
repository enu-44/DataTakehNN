package com.datatakehnn.services.data_arrays;

import com.datatakehnn.models.detalle_tipo_cable.Detalle_Tipo_Cable;
import com.datatakehnn.models.tipo_cable.Tipo_Cable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 12/11/2017.
 */

public class Detalle_Tipo_Cable_List {

    private static Detalle_Tipo_Cable[] list_detalle_tipo_cables = {
            //Cables de red
            new Detalle_Tipo_Cable(
                    1,
                    1,
                    "Aluminio Desnudo",
                    "ACSR"
            ),
            new Detalle_Tipo_Cable(
                    2,
                    1,
                    "Aluminio Aislado",
                    "ASC"
            ),
            new Detalle_Tipo_Cable(
                    3,
                    1,
                    "Trensado",
                    "TRE"
            ),
            new Detalle_Tipo_Cable(
                    4,
                    2,
                    ".500",
                    ".500"
            ),
            new Detalle_Tipo_Cable(
                    5,
                    2,
                    "RG6",
                    "RG6"
            ),

            new Detalle_Tipo_Cable(
                    6,
                    3,
                    "Fibra Optica",
                    "Fibra Optica"
            ),
            new Detalle_Tipo_Cable(
                    7,
                    3,
                    ".500",
                    ".500"
            )
    };

    /**
     * Obtiene como lista todos los estados de prueba
     *
     * @return Lista de cursos
     */
    public static List<Detalle_Tipo_Cable> getListDetalle_Tipo_Cable() {
        return Arrays.asList(list_detalle_tipo_cables);
    }

    /**
     * Obtiene un curso basado en la posición del array
     *
     * @param position Posición en el array
     * @return Curso seleccioando
     */
    public static Detalle_Tipo_Cable getDetalle_Tipo_CablePosition(int position) {
        return list_detalle_tipo_cables[position];
    }
}
