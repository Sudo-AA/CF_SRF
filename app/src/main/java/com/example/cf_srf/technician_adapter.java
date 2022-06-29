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


    private static String empcode = null;
    private static String empname = null;
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
        if (t.getEmpname().trim().equals(MainActivity.getRequest_name_holder().trim()+" "+MainActivity.getRequest_lname_holder().trim())){
            holder.name.setChecked(true);
        }
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
                    if(isChecked){
                        if (empcode==null&&empname==null){
                            empcode = te.getEmpcode().trim()+" ";
                            empname = te.getEmpname().trim()+"<br>";
                        }else{
                            empcode = empcode + te.getEmpcode().trim()+" ";
                            empname = empname +te.getEmpname().trim()+"<br>";
                        }

                    }else{
                        empcode = empcode.replaceAll(te.getEmpcode().trim()+" ", "");
                        empname = empname.replaceAll(te.getEmpname().trim()+"<br>", "");
                    }
                }
            });
        }


    }

}
