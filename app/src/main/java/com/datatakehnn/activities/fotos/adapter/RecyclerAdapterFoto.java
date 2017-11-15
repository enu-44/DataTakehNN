package com.datatakehnn.activities.fotos.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.datatakehnn.R;
import com.datatakehnn.models.novedad_model.Novedad;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TECNOLOGIA on 13/11/2017.
 */

public class RecyclerAdapterFoto extends RecyclerView.Adapter<RecyclerAdapterFoto.ViewHolder> {


    Context context;
    public List<Novedad> novedades;
    public OnItemClickListenerFoto mOnItemClickListenerFoto;

    public RecyclerAdapterFoto(Context context, List<Novedad> novedades, OnItemClickListenerFoto mOnItemClickListenerFoto) {
        this.context = context;
        this.novedades = novedades;
        this.mOnItemClickListenerFoto = mOnItemClickListenerFoto;
    }

    public void setItems(List<Novedad> newItems) {
        novedades.addAll(newItems);
        notifyDataSetChanged();

    }

    public void clear() {
        novedades.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_fotos, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterFoto.ViewHolder holder, final int position) {
        Novedad list = novedades.get(position);
        holder.tvDescripcionFoto.setText(list.getDetalle_Tipo_Novedad_Nombre());
        if (list.getImage_Novedad() != null) {
            byte[] foto = list.getImage_Novedad().getBlob();
            Bitmap bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
            holder.ivFotoRecycler.setImageBitmap(bitmap);
        }

        holder.hacerClickListener(list, mOnItemClickListenerFoto);

    }

    @Override
    public int getItemCount() {
        return novedades.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivFotoRecycler)
        ImageView ivFotoRecycler;
        @BindView(R.id.tvDescripcionFoto)
        TextView tvDescripcionFoto;
        @BindView(R.id.ibTomarFotoRecycler)
        ImageButton ibTomarFotoRecycler;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void hacerClickListener(final Novedad respuestaNovedad, OnItemClickListenerFoto onItemClickListenerFoto) {
            ibTomarFotoRecycler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListenerFoto.onItemClick(respuestaNovedad);
                }
            });
        }


    }
}
