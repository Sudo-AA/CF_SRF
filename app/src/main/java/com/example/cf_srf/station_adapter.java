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

    }

    @Override
    public int getItemCount() {
        return stn_list.size();
    }



    public class MyView extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView stn_code, stn_name;
        CardView edit;
        public MyView(@NonNull View itemView) {
            super(itemView);
            edit = itemView.findViewById(R.id.stncard);
            edit.setOnClickListener(this);
            stn_code = (TextView) itemView.findViewById(R.id.station_code);
            stn_name = (TextView) itemView.findViewById(R.id.station_name);

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
            ma = new MainActivity();
            ma.to_menuform();
            ma.emptysearchbar();
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

