package com.datatakehnn.activities.cables_elemento.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.datatakehnn.R;
import com.datatakehnn.models.elemento_cable.Elemento_Cable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 12/11/2017.
 */


public class AdapterCablesElemento extends RecyclerView.Adapter<AdapterCablesElemento.CableElementViewHolder> {

    public List<Elemento_Cable> dataset;
    public OnItemClickListenerCable onItemClickListener;
    public Context context;


    public AdapterCablesElemento(Context context, List<Elemento_Cable> dataset, OnItemClickListenerCable onItemClickListener) {
        this.dataset = dataset;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    public void setItems(List<Elemento_Cable> newItems) {
        dataset.addAll(newItems);
        notifyDataSetChanged();

    }

    public void clear() {
        dataset.clear();
        notifyDataSetChanged();
    }

    @Override
    public AdapterCablesElemento.CableElementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_cables_elemento, parent, false);
        return new AdapterCablesElemento.CableElementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterCablesElemento.CableElementViewHolder holder, final int position) {
        Elemento_Cable list = dataset.get(position);
        holder.txtOperador.setText(list.getNombre_Empresa());

        String cantidad = String.format(context.getString(R.string.cantidad_global),
                list.getCantidad());
        holder.txtCantidad.setText(cantidad);
        holder.txtDetalleTipo.setText(list.getNombre_Detalle_Tipo_Cable());
        holder.txtTipo.setText(list.getNombre_Tipo_Cable());
        String sobreBT = "";
        if (list.isSobre_Rbt()) {
            sobreBT = "SI";
        } else {
            sobreBT = "NO";
        }
        holder.txtSobreRbt.setText(sobreBT);

        String marquilla = "";
        if (list.isIs_Marquilla()) {
            marquilla = "SI";
        } else {
            marquilla = "NO";
        }
        holder.txtMarquilla.setText(marquilla);
        //statusIndicator.setBackgroundResource(R.color.GreenColor);
        holder.setOnItemClickListener(list, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public class CableElementViewHolder extends RecyclerView.ViewHolder {
        //se especifica lo que existe dentro del ViewHolder
        @BindView(R.id.txtOperador)
        TextView txtOperador;
        @BindView(R.id.txtTipo)
        TextView txtTipo;
        @BindView(R.id.txtDetalleTipo)
        TextView txtDetalleTipo;
        @BindView(R.id.txtSobreRbt)
        TextView txtSobreRbt;
        @BindView(R.id.txtCantidad)
        TextView txtCantidad;
        @BindView(R.id.txtMarquilla)
        TextView txtMarquilla;

        //@BindView(R.id.btnEditCable)
        ///ImageButton btnEditCable;
        @BindView(R.id.btnDeleteCable)
        ImageButton btnDeleteCable;


        private View view;

        public CableElementViewHolder(View v) {
            super(v);
            this.view = v;
            ButterKnife.bind(this, v);
        }

        public void setOnItemClickListener(final Elemento_Cable responseNotify, final OnItemClickListenerCable onItemClickListener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(responseNotify);
                }
            });

          /*  btnEditCable.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClickEdit(responseNotify);
                }
            });*/

            btnDeleteCable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClickDelete(responseNotify);
                }
            });
        }
    }
}


