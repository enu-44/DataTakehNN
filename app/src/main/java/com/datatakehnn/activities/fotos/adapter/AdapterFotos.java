package com.datatakehnn.activities.fotos.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.datatakehnn.R;
import com.datatakehnn.models.novedad_model.Novedad;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by usuario on 23/11/2017.
 */


public class AdapterFotos extends RecyclerView.Adapter<AdapterFotos.FotosViewHolder> {

    Context context;
    public List<Novedad> novedades;
    public OnItemClickListenerFoto mOnItemClickListenerFoto;


    public AdapterFotos(Context context, List<Novedad> novedades, OnItemClickListenerFoto mOnItemClickListenerFoto) {
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

    public void setFilter(List<Novedad> filters) {
        this.novedades = new ArrayList<>();
        this.novedades.addAll(filters);
        notifyDataSetChanged();

    }

    @Override
    public AdapterFotos.FotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_fotos, parent, false);
        return new FotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FotosViewHolder holder, final int position) {
        // Obtiene el curso ha detallar basado en la posición
        Novedad list = novedades.get(position);
        holder.tvDescripcionFoto.setText(list.getDetalle_Tipo_Novedad_Nombre());
        holder.tvTipoFoto.setText(list.getNombre_Tipo_Novedad());
        //holder.tvRutaFoto.setText(list.getRuta_Foto());
        try{

            if(list.getImage_Novedad()!=null){
                byte[] foto = list.getImage_Novedad().getBlob();
                Bitmap bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
                holder.ivFotoRecycler.setImageBitmap(bitmap);

                //Se pasa la ruta de la foto
                /*File imgFile = new  File(list.getRuta_Foto());
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    holder.ivFotoRecycler.setImageBitmap(myBitmap);
                }*/
            }else{
                holder.ivFotoRecycler.setImageResource(R.drawable.boton_foto_gris);
            }




        }catch (Exception ex){

            String error = ex.toString();
        }


        holder.setOnItemClickListener(list, mOnItemClickListenerFoto);
    }

    @Override
    public int getItemCount() {
        return novedades.size();
    }


    public class FotosViewHolder extends RecyclerView.ViewHolder {
        //se especifica lo que existe dentro del ViewHolder


        @BindView(R.id.ivFotoRecycler)
        ImageView ivFotoRecycler;
        @BindView(R.id.tvDescripcionFoto)
        TextView tvDescripcionFoto;
        @BindView(R.id.tvTipoFoto)
        TextView tvTipoFoto;
       /* @BindView(R.id.tvRutaFoto)
        TextView tvRutaFoto;*/

        private View view;

        public FotosViewHolder(View v) {
            super(v);
            this.view = v;
            ButterKnife.bind(this, v);
        }

        public void setOnItemClickListener(final Novedad respuestaNovedad, final OnItemClickListenerFoto mOnItemClickListenerFoto) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mOnItemClickListenerFoto.onItemClick(respuestaNovedad);
                }
            });


        }
    }
}

