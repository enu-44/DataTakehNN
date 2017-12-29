package com.datatakehnn.activities.equipos_elemento.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.datatakehnn.R;
import com.datatakehnn.activities.cables_elemento.adapter.AdapterCablesElemento;
import com.datatakehnn.models.equipo_elemento_model.Equipo_Elemento;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 13/11/2017.
 */


public class AdapterEquipo extends RecyclerView.Adapter<AdapterEquipo.EquipoElementViewHolder> {

    public List<Equipo_Elemento> dataset;
    public OnItemClickListenerEquipo onItemClickListener;
    public Context context;


    public AdapterEquipo(Context context, List<Equipo_Elemento> dataset, OnItemClickListenerEquipo onItemClickListener) {
        this.dataset = dataset;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    public void setItems(List<Equipo_Elemento> newItems) {
        dataset.addAll(newItems);
        notifyDataSetChanged();

    }

    public void clear() {
        dataset.clear();
        notifyDataSetChanged();
    }

    @Override
    public AdapterEquipo.EquipoElementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_equipos_elemento, parent, false);
        return new AdapterEquipo.EquipoElementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterEquipo.EquipoElementViewHolder holder, final int position) {
        Equipo_Elemento list = dataset.get(position);
        holder.txtOperador.setText(list.getNombre_Empresa());
        holder.txtTipo.setText(list.getNombre_Tipo_Equipo());
        String conectadoRedElectrica = "";
        if (list.isConectado_Red_Electrica()) {
            conectadoRedElectrica = "SI";
        } else {
            conectadoRedElectrica = "NO";
        }
        holder.txtConectadoRedElectrica.setText(conectadoRedElectrica);

        String medidor = "";
        if (list.isMedidor_Red()) {
            medidor = "SI";
        } else {
            medidor = "NO";
        }
        holder.txtMedidor.setText(medidor);
        if (list.getNombre_Tipo_Equipo().equals("Otros")) {
            holder.txtDescripcionOtro.setVisibility(View.VISIBLE);
            holder.txtDescripcionOtro.setText(list.getDescripcion());
        } else {
            holder.txtDescripcionOtro.setVisibility(View.GONE);
        }
        //statusIndicator.setBackgroundResource(R.color.GreenColor);
        holder.setOnItemClickListener(list, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public class EquipoElementViewHolder extends RecyclerView.ViewHolder {
        //se especifica lo que existe dentro del ViewHolder
        @BindView(R.id.txtOperador)
        TextView txtOperador;
        @BindView(R.id.txtTipo)
        TextView txtTipo;
        @BindView(R.id.txtConectadoRedElectrica)
        TextView txtConectadoRedElectrica;
        @BindView(R.id.txtMedidor)
        TextView txtMedidor;
        @BindView(R.id.txtDescripcionOtro)
        TextView txtDescripcionOtro;


        //@BindView(R.id.btnEditCable)
        ///ImageButton btnEditCable;
        @BindView(R.id.btnDeleteEquipo)
        ImageButton btnDeleteEquipo;


        private View view;

        public EquipoElementViewHolder(View v) {
            super(v);
            this.view = v;
            ButterKnife.bind(this, v);
        }

        public void setOnItemClickListener(final Equipo_Elemento responseNotify, final OnItemClickListenerEquipo onItemClickListener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(responseNotify);
                }
            });


            btnDeleteEquipo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClickDelete(responseNotify);
                }
            });
        }
    }
}
