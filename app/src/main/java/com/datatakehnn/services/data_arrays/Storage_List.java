package com.datatakehnn.services.data_arrays;

import com.datatakehnn.models.storage_model.Storage;
import com.datatakehnn.models.tipo_cable.Tipo_Cable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by usuario on 7/12/2017.
 */

public class Storage_List {
    private static Storage[] list_storage = {
            new Storage(1,
                    "Interno",
                    "Almacenamiento Interno",
                    "Almacenamiento Interno"
            ),
            new Storage(2,
                    "Externo",
                    "Tarjeta SD",
                    "Tarjeta SD"
            ),
    };

    /**
     * Obtiene como lista todos los estados de prueba
     *
     * @return Lista de cursos
     */
    public static List<Storage> getListStorage() {
        return Arrays.asList(list_storage);
    }

    /**
     * Obtiene un curso basado en la posición del array
     *
     * @param position Posición en el array
     * @return Curso seleccioando
     */
    public static Storage getStoragePosition(int position) {
        return list_storage[position];
    }
}
