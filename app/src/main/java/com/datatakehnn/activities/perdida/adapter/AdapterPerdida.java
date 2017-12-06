package com.datatakehnn.activities.perdida.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.datatakehnn.R;
import com.datatakehnn.models.perdida_model.Perdida;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 27/11/2017.
 */

public class AdapterPerdida extends RecyclerView.Adapter<AdapterPerdida.PerdidaViewHolder> {

    public List<Perdida> dataset;
    public OnItemClickListenerPerdida onItemClickListener;
    public Context context;

    public AdapterPerdida(Context context, List<Perdida> dataset, OnItemClickListenerPerdida onItemClickListener) {
        this.dataset = dataset;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    public void setItems(List<Perdida> newItems) {
        dataset.addAll(newItems);
        notifyDataSetChanged();

    }

    public void clear() {
        dataset.clear();
        notifyDataSetChanged();
    }

    @Override
    public AdapterPerdida.PerdidaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_perdidas_elemento, parent, false);
        return new AdapterPerdida.PerdidaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterPerdida.PerdidaViewHolder holder, final int position) {
        String isPerdida;
        Perdida list = dataset.get(position);
        holder.txtTipoPerdida.setText(list.getConcepto());
        holder.txtCantidad.setText(String.valueOf(list.getCantidad()));
        if (!list.getDescripcion().equals("")) {
            holder.txtDescripcion.setText(list.getDescripcion());
        } else {
            holder.txtDescripcion.setText("Sin Descripción");
        }

        if (list.isResponse_Checked()) {
            isPerdida = "SI";
        } else {
            isPerdida = "NO";
        }
        holder.txtIsPerdida.setText(isPerdida);
        holder.setOnItemClickListener(list, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public class PerdidaViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtTipoPerdida)
        TextView txtTipoPerdida;
        @BindView(R.id.txtCantidad)
        TextView txtCantidad;
        @BindView(R.id.txtDescripcion)
        TextView txtDescripcion;
        @BindView(R.id.btnDeletePerdida)
        ImageButton btnDeletePerdida;
        @BindView(R.id.txtIsPerdida)
        TextView txtIsPerdida;

        private View view;

        public PerdidaViewHolder(View v) {
            super(v);
            this.view = v;
            ButterKnife.bind(this, v);
        }

        public void setOnItemClickListener(final Perdida responseNotify, final OnItemClickListenerPerdida onItemClickListener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(responseNotify);
                }
            });


            btnDeletePerdida.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClickDelete(responseNotify);
                }
            });
        }
    }
}
