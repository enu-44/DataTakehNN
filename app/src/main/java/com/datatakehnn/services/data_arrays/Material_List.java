package com.datatakehnn.services.data_arrays;

import com.datatakehnn.models.material_model.Material;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 11/11/2017.
 */

public class Material_List {
    private static Material[] list_material = {
            new Material(1,
                    "Madera" ,
                    "M"
            ),
            new Material(2,
                    "Tubo" ,
                    "T"
            ),
            new Material(3,
                    "Concreto" ,
                    "C"
            ),

    };

    /**
     * Obtiene como lista todos los estados de prueba
     *
     * @return Lista de cursos
     */
    public static List<Material> getListMaterial() {
        return Arrays.asList(list_material);
    }

    /**
     * Obtiene un curso basado en la posición del array
     *
     * @param position Posición en el array
     * @return Curso seleccioando
     */
    public static Material getEstadoPosition(int position) {
        return list_material[position];
    }
}
