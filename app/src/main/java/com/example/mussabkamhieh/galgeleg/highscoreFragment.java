package com.example.mussabkamhieh.galgeleg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class highscoreFragment extends Fragment implements adapterClass.ItemClickListener {

    adapterClass adpt;
    RecyclerView RCV;
    spiller spiller;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_highscore, container, false);


        final ArrayList<spiller> spillerInfo = new ArrayList<>();


        RCV = view.findViewById(R.id.recyclerView);
        RCV.setLayoutManager(new LinearLayoutManager(getActivity()));
        adpt = new adapterClass(getActivity(), spillerInfo);
        adpt.setClickListener(this);


        Query q = FirebaseDatabase.getInstance().getReference("spillere").orderByChild("forkerte");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    spiller = ds.getValue(spiller.class);
                    spillerInfo.add(new spiller(spiller.getSpillerNavnn(), spiller.forkerte()));
                }
                RCV.setAdapter(adpt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    @Override
    public void onItemClick(View view, int pstn) {
        Toast.makeText(getActivity(), (pstn + 1) + " pladsen.", Toast.LENGTH_SHORT).show();
    }
}




//((AppCompatActivity) getActivity()).getSupportActionBar().hide();