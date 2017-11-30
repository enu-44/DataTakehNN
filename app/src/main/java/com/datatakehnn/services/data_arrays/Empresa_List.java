package com.datatakehnn.services.data_arrays;

import com.datatakehnn.models.empresa_model.Empresa;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 12/11/2017.
 */

public class Empresa_List {
    private static Empresa[] list_empresas = {
            new Empresa(1,
                    "Claro",
                    "Carrera 6 # 3-23",
                    "123123123",
                    "23432434-3",
                    true
            ),
            new Empresa(2,
                    "Movistar",
                    "Transversal 5 # 3-23",
                    "123123123",
                    "23432434-3",
                    true
            ),
            new Empresa(3,
                    "Alpavision",
                    "Calle 6 # 3-23",
                    "123123123",
                    "23432434-3",
                    true
            ),
            new Empresa(4,
                    "ETB",
                    "Calle 6 # 3-23",
                    "123123123",
                    "23432434-3",
                    true
            ),
            new Empresa(5,
                    "Azteca",
                    "Calle 6 # 3-23",
                    "123123123",
                    "23432434-3",
                    true
            ),
            new Empresa(6,
                    "Media Commerce",
                    "Calle 6 # 3-23",
                    "123123123",
                    "23432434-3",
                    true
            ),
            new Empresa(7,
                    "No Identificado",
                    "",
                    "",
                    "",
                    true
            )
    };

    /**
     * Obtiene como lista todos los estados de prueba
     *
     * @return Lista de cursos
     */
    public static List<Empresa> getListEmpresa() {
        return Arrays.asList(list_empresas);
    }

    /**
     * Obtiene un curso basado en la posición del array
     *
     * @param position Posición en el array
     * @return Curso seleccioando
     */
    public static Empresa getEmpresaPosition(int position) {
        return list_empresas[position];
    }
}
