package com.datatakehnn.activities.poste.lista_postes_usuario.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datatakehnn.R;
import com.datatakehnn.controllers.NovedadController;
import com.datatakehnn.models.element_model.Elemento;
import com.datatakehnn.models.novedad_model.Novedad;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 16/11/2017.
 */

public class AdapterElemento extends RecyclerView.Adapter<AdapterElemento.ElementViewHolder>
{

    public List<Elemento> dataset;
    public OnItemClickListenerElemento onItemClickListener;
    public Context context;
    public NovedadController novedadController;


    public AdapterElemento(Context context, List<Elemento> dataset, OnItemClickListenerElemento onItemClickListener) {
        this.dataset = dataset;
        this.onItemClickListener = onItemClickListener;
        this.context= context;
        this.novedadController= NovedadController.getInstance(context);
    }

    public void setItems(List<Elemento> newItems) {
        dataset.addAll(newItems);
        notifyDataSetChanged();

    }

    public void clear(){
        dataset.clear();
        notifyDataSetChanged();
    }

    @Override
    public AdapterElemento.ElementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_poste_usuario, parent, false);
        return new AdapterElemento.ElementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterElemento.ElementViewHolder holder, final int position) {
        Elemento elemento = dataset.get(position);

        if(elemento.getCodigo_Apoyo().equals("")){
            Novedad novedadElemento=novedadController.getNovedadByElementoId(elemento.getElemento_Id());
            if(novedadElemento!=null){
                holder.txtCodigoApoyo.setText(novedadElemento.getDetalle_Tipo_Novedad_Nombre());
            }
        }else{
            holder.txtCodigoApoyo.setText(elemento.getCodigo_Apoyo());
        }

        holder.txtDireccion.setText(elemento.getDireccion());
/*
        String cantidad= String.format(context.getString(R.string.cantidad_global),
                list.getCantidad());
        holder.txtCantidad.setText(cantidad);
        holder.txtDetalleTipo.setText(list.getNombre_Detalle_Tipo_Cable());
        holder.txtTipo.setText(list.getNombre_Tipo_Cable());
        String sobreBT= "";
        if(list.isSobre_Rbt()){
            sobreBT="SI";
        }else{
            sobreBT="NO";
        }
        holder.txtSobreRbt.setText(sobreBT);
        //statusIndicator.setBackgroundResource(R.color.GreenColor);
        */
        holder.setOnItemClickListener(elemento,onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public  class ElementViewHolder extends RecyclerView.ViewHolder   {
        //se especifica lo que existe dentro del ViewHolder
        @BindView(R.id.txtCodigoApoyo)
        TextView txtCodigoApoyo;
        @BindView(R.id.txtLongitud)
        TextView txtLongitud;
        @BindView(R.id.txtDireccion)
        TextView txtDireccion;
        @BindView(R.id.txtHoraInicioFin)
        TextView txtHoraInicioFin;
        @BindView(R.id.txtTiempo)
        TextView txtTiempo;
        @BindView(R.id.txtNivelTension)
        TextView txtNivelTension;



        //@BindView(R.id.btnEditCable)
        ///ImageButton btnEditCable;
       // @BindView(R.id.btnDeleteCable)
      //  ImageButton btnDeleteCable;

        private View view;
        public ElementViewHolder(View v) {
            super(v);
            this.view=v;
            ButterKnife.bind(this, v);
        }

        public  void setOnItemClickListener(final Elemento responseNotify, final OnItemClickListenerElemento onItemClickListener){
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(responseNotify);
                }
            });
        }
    }
}