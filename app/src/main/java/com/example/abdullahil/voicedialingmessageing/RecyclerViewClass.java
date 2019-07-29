/**
 *  Abdullhail Baki Adol
 */
package com.example.abdullahil.voicedialingmessageing;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>this class use for show all contaucts>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
public class RecyclerViewClass extends RecyclerView.Adapter<RecyclerViewClass.MyViewholder> {
        private Context cntx;
        private List<getSetContaucts> lst;
        public static final String ContauctsName = "com.example.abdullahil.voicedialingmessageing.ContauctsName";
        public static final String ContauctsPhone = "com.example.abdullahil.voicedialingmessageing.ContauctsPhone";

    public RecyclerViewClass(Context cntx, List<getSetContaucts> b) {
        this.cntx = cntx;
        this.lst = b;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(cntx).inflate(R.layout.item_contauct,parent,false);
        final MyViewholder vhldr = new MyViewholder(v);

        vhldr.CallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         /*       Activity activity = (Activity) cntx;
                activity.finish();
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent); */

                callingAndMessegingClass obj = new callingAndMessegingClass(lst.get(
                        vhldr.getAdapterPosition()).getPC_phone().toString(),cntx);
                obj.callingMethod();
            }
        });

        vhldr.MessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(cntx,MessagingActivity.class);

                intent.putExtra(ContauctsName,lst.get(
                        vhldr.getAdapterPosition()).getPC_name().toString());
                intent.putExtra(ContauctsPhone,lst.get(
                        vhldr.getAdapterPosition()).getPC_phone().toString());
                cntx.startActivity(intent);

            }
        });


        return vhldr;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {


      /*  if(lst.size()==noCount){

            holder.tv_name.setText("\t\t\t"+"\t\t Total "+lst.size()+" Contauts");
            holder.tv_phone.setText("\n\n");
            holder.persionIcn.setImageBitmap(null);
            holder.CallButton.setImageBitmap(null);
            holder.MessageButton.setImageBitmap(null);
        }*/
            holder.tv_name.setText(lst.get(position).getPC_name().toString());
            holder.tv_phone.setText(lst.get(position).getPC_phone().toString());

    }

    @Override
    public int getItemCount() {
        return lst.size();
    }
    public void updateContaucts(List<getSetContaucts> conts)
    {
        lst = new ArrayList<>();
        lst.addAll(conts);
        notifyDataSetChanged();
        if (lst.isEmpty()){
            Toast.makeText(cntx,"Not found",Toast.LENGTH_SHORT).show();
        }
    }
    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>this class get all button, textview for show contauct>>>>>>>>>>>
    >>>>>>>>>>>>>>>>>>>>>>>*/public static class MyViewholder extends RecyclerView.ViewHolder{//>>>>>>>>>>>
        private LinearLayout item_contact;
        private TextView tv_name;
        private TextView tv_phone;
        private ImageView CallButton,MessageButton;
        private ImageView persionIcn;

        public MyViewholder(View itemView) {
            super(itemView);
            item_contact = (LinearLayout) itemView.findViewById(R.id.contauct_item_id);
            tv_name = (TextView) itemView.findViewById(R.id.name_contauct);
            tv_phone = (TextView) itemView.findViewById(R.id.phone_contauct);
            CallButton = (ImageView) itemView.findViewById(R.id.callButon_id);
            MessageButton = (ImageView) itemView.findViewById(R.id.mesagebutton_id);
            persionIcn = (ImageView) itemView.findViewById(R.id.person__icon);
        }
    }
}
