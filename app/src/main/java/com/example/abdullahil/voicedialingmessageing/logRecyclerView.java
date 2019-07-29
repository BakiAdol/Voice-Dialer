/**
 *  Abdullhail Baki Adol
 */
package com.example.abdullahil.voicedialingmessageing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>this class use for show all contaucts>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
public class logRecyclerView extends RecyclerView.Adapter<logRecyclerView.MyViewholder> {
    Context cntx;
    private List<getSetLogDetails> lst;

    public logRecyclerView(Context cntx, List<getSetLogDetails> lst) {
        this.cntx = cntx;
        this.lst = lst;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(cntx).inflate(R.layout.log_item_layout,parent,false);
        final MyViewholder vhldr = new MyViewholder(v);

        vhldr.CallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callingAndMessegingClass obj = new callingAndMessegingClass(lst.get(
                        vhldr.getAdapterPosition()).getNumber().toString(),cntx);
                obj.callingMethod();
            }
        });

        vhldr.MessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cntx,MessagingActivity.class);
                intent.putExtra(RecyclerViewClass.ContauctsName,lst.get(
                        vhldr.getAdapterPosition()).getName().toString());
                intent.putExtra(RecyclerViewClass.ContauctsPhone,lst.get(
                        vhldr.getAdapterPosition()).getNumber().toString());
                cntx.startActivity(intent);
            }
        });


        return vhldr;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        try {
            holder.tv_name.setText(lst.get(position).getName().toString());
            holder.tv_phone.setText(lst.get(position).getNumber().toString());
            holder.calDate.setText(lst.get(position).getDate().toString());
            switch (lst.get(position).getType().toString()){
                case "INCOMING":
                    holder.callType.setImageResource(R.drawable.ic_call_received);
                    break;
                case "OUTGOING":
                    holder.callType.setImageResource(R.drawable.ic_call_made);
                    break;
                case "MISSED":
                    holder.callType.setImageResource(R.drawable.ic_call_missed);
                    break;

                    case "EXIT":
                        holder.callType.setImageResource(R.drawable.ic_call_close);
                        break;
            }
        }catch (Exception e){}

    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>this class get all button, textview for show contauct>>>>>>>>>>>
    >>>>>>>>>>>>>>>>>>>>>>>*/public static class MyViewholder extends RecyclerView.ViewHolder{//>>>>>>>>>>>

        private ImageView CallButton,MessageButton,callType;
        private TextView tv_name;
        private TextView tv_phone,calDate;

        public MyViewholder(View itemView) {
            super(itemView);

            CallButton = (ImageView) itemView.findViewById(R.id.log_callButon_id);
            MessageButton = (ImageView) itemView.findViewById(R.id.log_mesagebutton_id);

            tv_name = (TextView) itemView.findViewById(R.id.log_name_contauct);
            tv_phone = (TextView) itemView.findViewById(R.id.log_phone_contauct);
            calDate = (TextView) itemView.findViewById(R.id.call_dat_textview);

            callType = (ImageView) itemView.findViewById(R.id.call_type_id);

        }
    }
}
