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
                    1,
                    "Aluminio Desnudo",
                    "ACSR"
            ),
            new Detalle_Tipo_Cable(
                    2,
                    1,
                    1,
                    "Aluminio Aislado",
                    "ASC"
            ),
            new Detalle_Tipo_Cable(
                    3,
                    1,
                    1,
                    "Trensado",
                    "TRE"
            ),
            new Detalle_Tipo_Cable(
                    4,
                    1,
                    1,
                    "Cobre Aislado",
                    "CUA"
            ),
            new Detalle_Tipo_Cable(
                    5,
                    1,
                    1,
                    "Cobre Desnudo",
                    "CUD"
            ),
            new Detalle_Tipo_Cable(
                    6,
                    2,
                    1,
                    ".500",
                    ".500"
            ),
            new Detalle_Tipo_Cable(
                    7,
                    2,
                    1,
                    "RG6",
                    "RG6"
            ),
            new Detalle_Tipo_Cable(
                    8,
                    2,
                    1,
                    "RG11",
                    "RG11"
            ),
            new Detalle_Tipo_Cable(
                    9,
                    2,
                    1,
                    "Fibra Optica",
                    "FO"
            ),

            new Detalle_Tipo_Cable(
                    10,
                    3,
                    1,
                    "Fibra Optica",
                    "Fibra Optica"
            ),
            new Detalle_Tipo_Cable(
                    11,
                    3,
                    1,
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
