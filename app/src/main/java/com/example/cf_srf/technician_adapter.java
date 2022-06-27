package com.example.cf_srf;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class technician_adapter extends RecyclerView.Adapter<technician_adapter.MyView> {


    private static String empcode;
    private static String empname;
    private Context context;
    private ArrayList<technician> tech;
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public static String getEmpcode() {
        return empcode;
    }

    public static void setEmpcode(String empcode) {
        technician_adapter.empcode = empcode;
    }

    public static String getEmpname() {
        return empname;
    }

    public static void setEmpname(String empname) {
        technician_adapter.empname = empname;
    }

    public technician_adapter(Context context, ArrayList<technician> tech) {
        this.context = context;
        this.tech = tech;
        for (int i = 0; i < tech.size(); i++) {
            selectCheck.add(0);
        }
    }


    @NonNull
    @Override
    public technician_adapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.technician, parent, false);
        technician_adapter.MyView viewHolder = new technician_adapter.MyView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull technician_adapter.MyView holder, @SuppressLint("RecyclerView") int position) {
        technician t = tech.get(position);
        holder.itemView.setTag(t.get(position));
        holder.name.setText(t.getEmpname().trim());
        if (selectCheck.get(position) == 1) {
            holder.name.setChecked(true);
        }else{
            holder.name.setChecked(false);
        }
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int k = 0; k < selectCheck.size(); k++) {
                    if (k == position) {
                        if (selectCheck.get(k).equals(1)){
                            selectCheck.set(k, 0);
                        }else{
                            selectCheck.set(k, 1);
                        }
                    } else {
                        selectCheck.set(k, 0);
                }
                notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return tech.size();
    }

    public class MyView extends RecyclerView.ViewHolder{
        CheckBox name;
        public MyView(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.techname);
            name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = getAdapterPosition();
                    technician te = tech.get(position);
                    if(isChecked==true){
                        empcode = te.getEmpcode().trim();
                        empname = te.getEmpname();
                    }else{
                        empcode = " ";
                        empname = "NO DATA";
                    }
                }
            });
        }


    }

}
