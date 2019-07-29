package com.example.abdullahil.voicedialingmessageing;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//-->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//------------------------------this class use for show all contaucts------------------------------------
public class contauctFragmentClass extends android.support.v4.app.Fragment
        implements SearchView.OnQueryTextListener{ //-->>>>>>>>>>>>>>>>>>>>>>>>>
//-->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    View v;
    private List<getSetContaucts> allCnts; //this ArrayList store all phone book contaucts.
    Context cntx;
    private RecyclerView recyclerView;
    RecyclerViewClass recyclerViewAdapter;

    private String name="";
    SearchView searchCnt;

    public contauctFragmentClass() {

    }

    @SuppressLint("ValidFragment")
    public contauctFragmentClass(List<getSetContaucts> allCnts, Context cntx) {
        this.allCnts = allCnts;
        this.cntx = cntx;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_contauct, container, false);

        ImageButton getVoice = (ImageButton) v.findViewById(R.id.voice_search_contauct_id);
        getVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNameByVoice(view);
            }
        });

        searchCnt = (SearchView) v.findViewById(R.id.searchName);
        searchCnt.setOnQueryTextListener(this);

        recyclerView = (RecyclerView) v.findViewById(R.id.contauct_reciclerview);
        recyclerViewAdapter = new
                RecyclerViewClass(getContext(), allCnts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);


        return v;

    }
/*--------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
*-----------------------Both of used of search contauct using search view-----------------------------------
*---------------------------------------------------------------------------------------------------------*/
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        List<getSetContaucts> SearchLst = new ArrayList<>();

        for (int i=0;i<allCnts.size();i++)
        {
            if(!newText.isEmpty()){
                if(allCnts.get(i).getPC_name().toLowerCase().contains(newText.toLowerCase())&&
                        allCnts.get(i).getPC_name().toLowerCase().charAt(0)==newText.toLowerCase().charAt(0)){
                    SearchLst.add(new getSetContaucts(allCnts.get(i).getPC_id(),allCnts.get(i).getPC_name(),
                            allCnts.get(i).getPC_phone()));
                }
            }else if(allCnts.get(i).getPC_name().toLowerCase().contains(newText.toLowerCase())){
                SearchLst.add(new getSetContaucts(allCnts.get(i).getPC_id(),allCnts.get(i).getPC_name(),
                        allCnts.get(i).getPC_phone()));
            }
        }
        recyclerViewAdapter.updateContaucts(SearchLst);
        return true;
    }
    //----------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /*-----------------------------------get voice from user methods-------------------------------------
    ---------------------------------------------------------------------------------------------------*/
    public void getNameByVoice(View view)
    {
        try{
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

            if(intent.resolveActivity(cntx.getPackageManager()) != null){
                startActivityForResult(intent,10);
            }else{
                Toast.makeText(cntx, "Your device don't support Speech input",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){}

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode){
                case 10:
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //txtv.setText(result.get(0));
                    name = "";
                    name = result.get(0);

                    break;
            }
        }catch (Exception e){
            name = "";
        }
        if (name!=""){

            //searchCnt.setQuery(name,false);
            //searchCnt.clearFocus();
            List<getSetContaucts> lst = new ArrayList<>();

         //******************************************************************************************
         //----------------------check name matching or not contauct list----------------------------
            for (int i=0;i<allCnts.size();i++)
            {
                if(allCnts.get(i).getPC_name().toLowerCase().contains(name.toLowerCase()) ||
                        name.toLowerCase().contains(allCnts.get(i).getPC_name().toLowerCase()) ||
                        allCnts.get(i).getPC_name().toLowerCase()==(name.toLowerCase()) ){
                    lst.add(new getSetContaucts(allCnts.get(i).getPC_id(),allCnts.get(i).getPC_name(),
                            allCnts.get(i).getPC_phone()));
                }
            }
          //******************************************************************************************

            //*********************************************
         //-------searching contaucts list send --------

            searchCnt.setQuery(name,false);
            recyclerViewAdapter.updateContaucts(lst);
            searchCnt.setFocusable(true);
            searchCnt.setIconified(false);
            searchCnt.requestFocusFromTouch();
         //-------------focus search view --------------
         //*********************************************
        }
    }
    /*-------------------------------------end of get voice from user------------------------------------
    ---------------------------------------------------------------------------------------------------*/

//----------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
