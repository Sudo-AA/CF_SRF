package com.example.cf_srf;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class sra_adapter extends RecyclerView.Adapter<sra_adapter.MyView>{


    private static Context context;
    private static MainActivity ma;
    private List<sra> sra_list;
    public sra_adapter(Context context, List<sra> sra_list) {
        this.context = context;
        this.sra_list = sra_list;
    }


    @NonNull
    @Override
    public sra_adapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.sra_style, parent , false);
        sra_adapter.MyView viewHolder = new sra_adapter.MyView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull sra_adapter.MyView holder, int position) {
        sra sra =  sra_list.get(position);
        holder.itemView.setTag(srf.get(position));

        holder.date.setText(Html.fromHtml("DATE:  <font color='#323332'>"+sra.getSraDate().trim()+"</font>"), TextView.BufferType.SPANNABLE);
        holder.by.setText(Html.fromHtml("ACTION BY:  <font color='#323332'>"+sra.getSraUserID().trim()+"</font>"), TextView.BufferType.SPANNABLE);
        holder.act.setText(Html.fromHtml("ACTION TAKEN:  <br><br><font color='red'>"+sra.getAction().trim()+"<br></font>"), TextView.BufferType.SPANNABLE);
        holder.tech.setText(Html.fromHtml("TECHNICIAN:  <font color='#323332'>"+sra.getSraTechID().toString().trim().replaceAll("\\;", "<br>")+"</font>"), TextView.BufferType.SPANNABLE);
        holder.status.setText(Html.fromHtml("STATUS:  <font color='#005eb8'>"+sra.getStatus().trim()+"</font>"), TextView.BufferType.SPANNABLE);
    }

    @Override
    public int getItemCount() {
        return sra_list.size();
    }

    public class MyView extends RecyclerView.ViewHolder{
        TextView date, by, act, tech, status;
        public MyView(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            by = (TextView) itemView.findViewById(R.id.updated_by);
            act = (TextView) itemView.findViewById(R.id.action);
            tech = (TextView) itemView.findViewById(R.id.tech);
            status = (TextView) itemView.findViewById(R.id.status);
        }

    }
}
