package com.example.mussabkamhieh.galgeleg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

// DENNE DEL ER INSPIRERET AF:
// https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<spiller> Dataa;
    private LayoutInflater Inflaterr;

    MyRecyclerViewAdapter(Context context, List<spiller> data) {
        this.Inflaterr = LayoutInflater.from(context);
        this.Dataa = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = Inflaterr.inflate(R.layout.list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        spiller spiller = Dataa.get(position);
        holder.navn.setText(spiller.getSpillerNavnn());
        holder.score.setText(String.valueOf(spiller.forkerte()));
    }

    @Override
    public int getItemCount() {
        return Dataa.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView navn;
        TextView score;

        ViewHolder(View itemView) {
            super(itemView);
            navn = itemView.findViewById(R.id.navn);
            score = itemView.findViewById(R.id.score);
        }

    }
}