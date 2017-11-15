package com.datatakehnn.services.data_arrays;

import com.datatakehnn.models.nivel_tension_elemento_model.Nivel_Tension_Elemento;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 11/11/2017.
 */

public class Nivel_Tension_Elemento_List {
    private static Nivel_Tension_Elemento[] list_Nivel_Tension = {
            new Nivel_Tension_Elemento(1,
                    "Baja tension" ,
                    "BT",
                    1
            ),
            new Nivel_Tension_Elemento(2,
                    "Media tension" ,
                    "MT",
                    2
            ),
            new Nivel_Tension_Elemento(3,
                    "Alta tension" ,
                    "AT",
                    3
            ),
    };

    /**
     * Obtiene como lista todos los estados de prueba
     *
     * @return Lista de cursos
     */
    public static List<Nivel_Tension_Elemento> getListNivelTension() {
        return Arrays.asList(list_Nivel_Tension);
    }

    /**
     * Obtiene un curso basado en la posición del array
     *
     * @param position Posición en el array
     * @return Curso seleccioando
     */
    public static Nivel_Tension_Elemento getNiveTensionPosition(int position) {
        return list_Nivel_Tension[position];
    }
}
