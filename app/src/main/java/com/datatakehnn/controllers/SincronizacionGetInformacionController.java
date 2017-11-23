package com.datatakehnn.controllers;

import android.content.Context;

import com.datatakehnn.models.ciudades_model.Ciudad;
import com.datatakehnn.models.ciudades_model.Ciudad_Table;
import com.datatakehnn.models.departmentos_model.Departamento;
import com.datatakehnn.models.detalle_tipo_cable.Detalle_Tipo_Cable;
import com.datatakehnn.models.detalle_tipo_cable.Detalle_Tipo_Cable_Table;
import com.datatakehnn.models.detalle_tipo_novedad.Detalle_Tipo_Novedad;
import com.datatakehnn.models.empresa_model.Empresa;
import com.datatakehnn.models.estado_model.Estado;
import com.datatakehnn.models.estado_model.Estado_Table;
import com.datatakehnn.models.longitud_elemento_model.Longitud_Elemento;
import com.datatakehnn.models.longitud_elemento_model.Longitud_Elemento_Adapter;
import com.datatakehnn.models.longitud_elemento_model.Longitud_Elemento_Table;
import com.datatakehnn.models.material_model.Material;
import com.datatakehnn.models.material_model.Material_Table;
import com.datatakehnn.models.nivel_tension_elemento_model.Nivel_Tension_Elemento;
import com.datatakehnn.models.nivel_tension_elemento_model.Nivel_Tension_Elemento_Table;
import com.datatakehnn.models.reponse_generic.Response;
import com.datatakehnn.models.tipo_cable.Tipo_Cable;
import com.datatakehnn.models.tipo_equipo_model.Tipo_Equipo;
import com.datatakehnn.models.tipo_noveda_model.Tipo_Novedad;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

/**
 * Created by user on 12/11/2017.
 */

public class SincronizacionGetInformacionController {

    private static Context ourcontext;
    private static SincronizacionGetInformacionController _instance;


    ///Instance
    /*----------------------------------------------------------------------------------------------------------------*/
    public SincronizacionGetInformacionController() {
        _instance = this;
    }
    public static SincronizacionGetInformacionController getInstance(Context c) {
        if (_instance == null) {
            ourcontext = c;
            _instance = new SincronizacionGetInformacionController();
        }
        return _instance;
    }


    ///Eliminar Usuarios bd
    public void deleteInformacionMaster() {
        Delete.table(Estado.class);
        Delete.table(Detalle_Tipo_Novedad.class);
        Delete.table(Tipo_Novedad.class);
        Delete.table(Material.class);
        Delete.table(Nivel_Tension_Elemento.class);
        Delete.table(Longitud_Elemento.class);
        Delete.table(Tipo_Cable.class);
        Delete.table(Detalle_Tipo_Cable.class);
        Delete.table(Empresa.class);
    }

    ////Login Usuario_List
    public Response registerDataGetInformacion(List<Estado> Estados,
                                               List<Detalle_Tipo_Novedad> Detalle_Tipo_Novedades,
                                               List<Longitud_Elemento> Longitud_Elementos,
                                               List<Material> Materiales,
                                               List<Nivel_Tension_Elemento> Nivel_Tension_Elementos,
                                               List<Tipo_Novedad> Tipo_Novedades,
                                               List<Tipo_Cable> tipo_cables,
                                               List<Detalle_Tipo_Cable> detalle_tipo_cables,
                                               List<Empresa> empresaList,
                                               List<Tipo_Equipo> tipo_equipos,
                                               List<Departamento> departamentos,
                                               List<Ciudad> ciudades) {

        Response response= new Response();
        try{

            //Registrar Materiales
            for(Nivel_Tension_Elemento items: Nivel_Tension_Elementos){
                Nivel_Tension_Elemento nivel_tension_elemento= new Nivel_Tension_Elemento();
                nivel_tension_elemento.setNivel_Tension_Elemento_Id(items.getNivel_Tension_Elemento_Id());
                nivel_tension_elemento.setNombre(items.getNombre());
                nivel_tension_elemento.setSigla(items.getSigla());
                nivel_tension_elemento.setValor(items.getValor());
                nivel_tension_elemento.save();
            }


            //Registrar Materiales
            for(Material items: Materiales){
                Material material= new Material();
                material.setMaterial_Id(items.getMaterial_Id());
                material.setNombre(items.getNombre());
                material.setSigla(items.getSigla());
                material.save();
            }

            //Registrar Estados
            for(Estado items: Estados){
                Estado estado= new Estado();
                estado.setEstado_Id(items.getEstado_Id());
                estado.setNombre(items.getNombre());
                estado.setSigla(items.getSigla());
                estado.save();
            }

            //registrar Tipo de Novedades
            for(Tipo_Novedad items: Tipo_Novedades){
                Tipo_Novedad tipo_novedad= new Tipo_Novedad();
                tipo_novedad.setTipo_Novedad_Id(items.getTipo_Novedad_Id());
                tipo_novedad.setNombre(items.getNombre());
                tipo_novedad.save();
            }

            //Registrar Detalle Tipo Novedades
            for(Detalle_Tipo_Novedad items: Detalle_Tipo_Novedades){
                Detalle_Tipo_Novedad detalle_tipo_novedad= new Detalle_Tipo_Novedad();
                detalle_tipo_novedad.setDetalle_Tipo_Novedad_Id(items.getDetalle_Tipo_Novedad_Id());
                detalle_tipo_novedad.setNombre(items.getNombre());
                detalle_tipo_novedad.setDescripcion(items.getDescripcion());
                detalle_tipo_novedad.setTipo_Novedad_Id(items.getTipo_Novedad_Id());
                detalle_tipo_novedad.save();
            }


            //Registrar Longitud Elementos
            for(Longitud_Elemento items: Longitud_Elementos){
                Longitud_Elemento longitud_elemento= new Longitud_Elemento();
                longitud_elemento.setLongitud_Elemento_Id(items.getLongitud_Elemento_Id());
                longitud_elemento.setUnidad_Medida(items.getUnidad_Medida());
                longitud_elemento.setValor(items.getValor());
                longitud_elemento.save();
            }


            ///CABLES
            //Registrar Tipo Cables
            for(Tipo_Cable items: tipo_cables){
                Tipo_Cable tipo_cable= new Tipo_Cable();
                tipo_cable.setTipo_Cable_Id(items.getTipo_Cable_Id());
                tipo_cable.setNombre(items.getNombre());
                tipo_cable.save();
            }

            //Registrar Longitud Elementos
            for(Detalle_Tipo_Cable items: detalle_tipo_cables){
                Detalle_Tipo_Cable detalle_tipo_cable= new Detalle_Tipo_Cable();
                detalle_tipo_cable.setDetalle_Tipo_Cable_Id(items.getDetalle_Tipo_Cable_Id());
                detalle_tipo_cable.setNombre(items.getNombre());
                detalle_tipo_cable.setSigla(items.getSigla());
                detalle_tipo_cable.setTipo_Cable_Id(items.getTipo_Cable_Id());
                detalle_tipo_cable.save();
            }

            //EMPRESAS
            for(Empresa items: empresaList){
                Empresa empresa= new Empresa();
                empresa.setEmpresa_Id(items.getEmpresa_Id());
                empresa.setNombre(items.getNombre());
                empresa.setDireccion(items.getTelefono());
                empresa.setNit(items.getNit());
                empresa.setTelefono(items.getDireccion());
                empresa.save();
            }


            //TIPO EQUIPOS
            for(Tipo_Equipo items: tipo_equipos){
                Tipo_Equipo tipo_equipo= new Tipo_Equipo();
                tipo_equipo.setTipo_Equipo_Id(items.getTipo_Equipo_Id());
                tipo_equipo.setNombre(items.getNombre());
                tipo_equipo.save();
            }


            //DEPARTAMENTOS
            for(Departamento items: departamentos){
                Departamento departamento= new Departamento();
                departamento.setDepartamento_Id(items.getDepartamento_Id());
                departamento.setCodigo_Dpto(items.getCodigo_Dpto());
                departamento.setNombre(items.getNombre());
                departamento.save();
            }

            //CIUDADES
            for(Ciudad items: ciudades){
                Ciudad ciudad= new Ciudad();
                ciudad.setCiudad_Id(items.getCiudad_Id());
                ciudad.setDepartamnento_Id(items.getDepartamnento_Id());
                ciudad.setNombre(items.getNombre());
                ciudad.save();
            }



            response.setMessage("Informacion registrada");
            response.setSuccess(true);

        }catch (Exception ex){
            response.setMessage(ex.toString());
            response.setSuccess(false);
        }


        return  response;
    }


    ///Obtener el primero
    public Longitud_Elemento getFirstLongitudElemento() {
        Longitud_Elemento longitud_elemento=new Select().from(Longitud_Elemento.class).querySingle();
        return  longitud_elemento;
    }


    ///Listas
    //POSTES
    ////Lista de estados
    public List<Estado> getListEstados() {
        List<Estado> lis= SQLite.select().from(Estado.class).queryList();
        return  lis;
    }


    public List<Longitud_Elemento> getListLongitudElemento() {
        List<Longitud_Elemento> lis= SQLite.select().from(Longitud_Elemento.class).queryList();
        return  lis;
    }


    public List<Material> getListMaterial() {
        List<Material> lis= SQLite.select().from(Material.class).queryList();
        return  lis;
    }

    public List<Nivel_Tension_Elemento> getListNivel_Tension_Elemento() {
        List<Nivel_Tension_Elemento> lis= SQLite.select().from(Nivel_Tension_Elemento.class).queryList();
        return  lis;
    }


    ///CABLES
    public List<Empresa> getListEmpresas() {
        List<Empresa> lis= SQLite.select().from(Empresa.class).queryList();
        return  lis;
    }

    public List<Tipo_Cable> getListTipo_Cable() {
        List<Tipo_Cable> lis= SQLite.select().from(Tipo_Cable.class).queryList();
        return  lis;
    }

    public List<Detalle_Tipo_Cable> getListDetalleTipo_Cable(long tipo_Cable_Id) {

        List<Detalle_Tipo_Cable> list= new Select().from(Detalle_Tipo_Cable.class).queryList();

        List<Detalle_Tipo_Cable> lisFilter= new Select().from(Detalle_Tipo_Cable.class).where(Detalle_Tipo_Cable_Table.Tipo_Cable_Id.eq(tipo_Cable_Id)).queryList();
        return  lisFilter;
    }

    //EQUIPOS
    public List<Tipo_Equipo> getListTipoEquipo() {
        List<Tipo_Equipo> lis= SQLite.select().from(Tipo_Equipo.class).queryList();
        return  lis;
    }

    //CIUDAD
    public List<Departamento> getDepartamentos() {
        List<Departamento> lis= SQLite.select().from(Departamento.class).queryList();
        return  lis;
    }

    public List<Ciudad> getListCiudadByDepartamento(long departamento_id) {
        List<Ciudad> lisFilter= new Select().from(Ciudad.class).where(Ciudad_Table.Departamnento_Id.eq(departamento_id)).queryList();
        return  lisFilter;
    }


    //METODOS
    public Longitud_Elemento getLongitudByLongitud_Elemento_Id(long Longitud_Elemento_Id){
        Longitud_Elemento longitud_elemento = SQLite.select().from(Longitud_Elemento.class).where(Longitud_Elemento_Table.Longitud_Elemento_Id.eq(Longitud_Elemento_Id)).querySingle();
        return longitud_elemento;
    }

    public Nivel_Tension_Elemento getNivelTensionByNivel_Tension_Elemento_Id(long Nivel_Tension_Elemento_Id){
        Nivel_Tension_Elemento nivel_tension_elemento = SQLite.select().from(Nivel_Tension_Elemento.class).where(Nivel_Tension_Elemento_Table.Nivel_Tension_Elemento_Id.eq(Nivel_Tension_Elemento_Id)).querySingle();
        return nivel_tension_elemento;
    }

    public Material getMaterialById(long Material_Id){
        Material material = SQLite.select().from(Material.class).where(Material_Table.Material_Id.eq(Material_Id)).querySingle();
        return material;
    }

    public Estado getEstadoById(long Estadol_Id){
        Estado estado = SQLite.select().from(Estado.class).where(Estado_Table.Estado_Id.eq(Estadol_Id)).querySingle();
        return estado;
    }





}
