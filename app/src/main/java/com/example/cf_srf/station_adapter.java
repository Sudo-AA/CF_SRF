package com.example.cf_srf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class station_adapter extends RecyclerView.Adapter<station_adapter.MyView>{


    private static String uni_stnname;
    private static String uni_stncode;
    private static Context context;
    private static MainActivity ma;
    private static ArrayList<station> stn_list;

    public station_adapter(Context context,ArrayList<station> stn_list) {
        this.context = context;
        this.stn_list = stn_list;
    }

    public station_adapter() {

    }


    public static String getUni_stnname() {
        return uni_stnname;
    }

    public static String getUni_stncode() {
        return uni_stncode;
    }



    @NonNull
    @Override
    public station_adapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.station_style, parent , false);
        station_adapter.MyView viewHolder = new station_adapter.MyView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull station_adapter.MyView holder, int position) {
        station station =  stn_list.get(position);
        holder.itemView.setTag(station.get(position));
        holder.stn_code.setText(station.getStn_code());
        holder.stn_name.setText(station.getStn_name());

        if (MainActivity.getRegistration().equals(true)){
            holder.IT.setVisibility(View.GONE);
            holder.ME.setVisibility(View.GONE);
            holder.MT.setVisibility(View.GONE);
        }else{
            holder.IT.setVisibility(View.VISIBLE);
            holder.ME.setVisibility(View.VISIBLE);
            holder.MT.setVisibility(View.VISIBLE);
            holder.text_it.setText(station.getPenIT());
            holder.text_me.setText(station.getPenME());
            holder.text_mt.setText(station.getPenMT());
            if(station.getPenIT().equals("")){
                holder.IT.setVisibility(View.GONE);
            }else{
                holder.IT.setVisibility(View.VISIBLE);
            }
            if(station.getPenME().equals("")){
                holder.ME.setVisibility(View.GONE);
            }else{
                holder.ME.setVisibility(View.VISIBLE);
            }if(station.getPenMT().equals("")){
                holder.MT.setVisibility(View.GONE);
            }else{
                holder.MT.setVisibility(View.VISIBLE);
            }




        }

    }

    @Override
    public int getItemCount() {
        return stn_list.size();
    }



    public class MyView extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView stn_code, stn_name;
        CardView edit;
        CardView IT, ME, MT;
        TextView text_it, text_me, text_mt;
        public MyView(@NonNull View itemView) {
            super(itemView);
            edit = itemView.findViewById(R.id.stncard);
            edit.setOnClickListener(this);
            stn_code = (TextView) itemView.findViewById(R.id.station_code);
            stn_name = (TextView) itemView.findViewById(R.id.station_name);

            // notifier

            text_it = (TextView) itemView.findViewById(R.id.up_pending_IT);
            text_me = (TextView) itemView.findViewById(R.id.up_pending_ME);
            text_mt = (TextView) itemView.findViewById(R.id.up_pending_MT);

            IT = (CardView) itemView.findViewById(R.id.card_IT);
            ME = (CardView) itemView.findViewById(R.id.card_ME);
            MT = (CardView) itemView.findViewById(R.id.card_MT);

        }
        @Override
        public void onClick(View v) {
        int position =  this.getAdapterPosition();
        station station = stn_list.get(position);
        String code = station.getStn_code();
        String name = station.getStn_name();
        uni_stncode = code;
        uni_stnname = name;
        if(MainActivity.getRegistration().equals(false)){
            if(MainActivity.getMenutrigger().equals(true)){
                ma = new MainActivity();
                ma.to_catselect();
                ma.emptysearchbar();
            }else{
                ma = new MainActivity();
                ma.to_dept();
                ma.emptysearchbar();
            }

        }else if(MainActivity.getRegistration().equals(true))
        {
            ma = new MainActivity();
            ma.to_newacc();
            ma.emptysearchbar();
        }

        }
    }
    public void filterList(ArrayList<station> filteredList){
        stn_list = filteredList;
        notifyDataSetChanged();
    }
}

