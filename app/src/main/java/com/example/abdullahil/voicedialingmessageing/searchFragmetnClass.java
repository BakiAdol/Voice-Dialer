/**
 *  Abdullhail Baki Adol
 */
package com.example.abdullahil.voicedialingmessageing;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@SuppressLint("ValidFragment")
public class searchFragmetnClass extends android.support.v4.app.Fragment {
    View v;
    Context cntx;
    private List<getSetLogDetails> getAllLog,tempLog;
    private RecyclerView recyclerView;
    logRecyclerView recyclerViewAdapter;

    public searchFragmetnClass(List<getSetLogDetails> getAllLog,Context cntx) {
        this.cntx = cntx;
        this.getAllLog = getAllLog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.search_fragment, container, false);
        tempLog = new ArrayList<>();
        for (int i=0;i<getAllLog.size();i++){
            tempLog.add(new getSetLogDetails(getAllLog.get(i).getName(),getAllLog.get(i).getNumber(),
                    getAllLog.get(i).getType(),getAllLog.get(i).getDate()));
        }

        recyclerView = (RecyclerView) v.findViewById(R.id.search_reciclerview);
        recyclerViewAdapter = new
                logRecyclerView(getContext(), tempLog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

        return v;

    }
    //----------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



}