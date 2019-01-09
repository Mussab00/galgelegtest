package com.example.mussabkamhieh.galgeleg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// DENNE DEL ER INSPIRERET AF:
// https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example

public class adapterClass extends RecyclerView.Adapter<adapterClass.VH> {

    private List<spiller> spillerListe;
    private LayoutInflater li;
    private ItemClickListener OCL;

    adapterClass(Context context, List<spiller> data) {
        this.li = LayoutInflater.from(context);
        this.spillerListe = data;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup prnt, int vT) {
        View view = li.inflate(R.layout.list_items, prnt, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int pstn) {
        spiller spiller = spillerListe.get(pstn);
        holder.navn.setText(spiller.getSpillerNavnn());
        holder.score.setText(String.valueOf(spiller.forkerte()));
    }

    @Override
    public int getItemCount() {
        return spillerListe.size();
    }

    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView navn;
        TextView score;

        VH(View itemView) {
            super(itemView);
            navn = itemView.findViewById(R.id.navn);
            score = itemView.findViewById(R.id.score);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (OCL != null) OCL.onItemClick(view, getAdapterPosition());
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.OCL = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int pstn);
    }
}