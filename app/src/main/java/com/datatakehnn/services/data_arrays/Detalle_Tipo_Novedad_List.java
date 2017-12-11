package com.datatakehnn.services.data_arrays;

import com.datatakehnn.models.detalle_tipo_novedad.Detalle_Tipo_Novedad;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 11/11/2017.
 */

public class Detalle_Tipo_Novedad_List {
    private static Detalle_Tipo_Novedad[] list_detalle_tipo_novedad = {

            //Codigo de apoyo
            new Detalle_Tipo_Novedad(
                    1,
                    "Codigo Borroso",
                    "",
                    1
            ),
            new Detalle_Tipo_Novedad(
                    2,
                    "Codigo No Visible",
                    "",
                    1
            ),
            new Detalle_Tipo_Novedad(
                    3,
                    "Otro",
                    "",
                    1
            ),
            //Resistencia Mecanica
            new Detalle_Tipo_Novedad(
                    4,
                    "No hay placa",
                    "",
                    2
            ),
            new Detalle_Tipo_Novedad(
                    5,
                    "Placa sin dato",
                    "",
                    2
            ),
            new Detalle_Tipo_Novedad(
                    6,
                    "Otro",
                    "",
                    2
            ),

            //Estado del poste
            new Detalle_Tipo_Novedad(
                    7,
                    "Agrietado",
                    "",
                    3
            ),
            new Detalle_Tipo_Novedad(
                    8,
                    "Descompuesto",
                    "",
                    3
            ),
            new Detalle_Tipo_Novedad(
                    9,
                    "Estropeado",
                    "",
                    3
            ),
            new Detalle_Tipo_Novedad(
                    10,
                    "Lampara Adicional",
                    "",
                    4
            ),
            new Detalle_Tipo_Novedad(
                    11,
                    "Lampara Encendida",
                    "",
                    5
            ),
            new Detalle_Tipo_Novedad(
                    12,
                    "Conexion Ilicita",
                    "",
                    6
            ),
            new Detalle_Tipo_Novedad(
                    13,
                    "Requiere Poda",
                    "",
                    7
            )
    };

    /**
     * Obtiene como lista todos los estados de prueba
     *
     * @return Lista de cursos
     */
    public static List<Detalle_Tipo_Novedad> getListDetalleTipoNovedad() {
        return Arrays.asList(list_detalle_tipo_novedad);
    }

    /**
     * Obtiene un curso basado en la posición del array
     *
     * @param position Posición en el array
     * @return Curso seleccioando
     */
    public static Detalle_Tipo_Novedad getTipoDetalleNovedadPosition(int position) {
        return list_detalle_tipo_novedad[position];
    }
}
