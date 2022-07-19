package com.example.cf_srf;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class approval_adapter extends RecyclerView.Adapter<approval_adapter.MyView> {


    private  Context context;
    private  ArrayList<approval> aprovallist;

    public approval_adapter(Context context, ArrayList<approval> aprovallist) {
        this.context = context;
        this.aprovallist = aprovallist;
    }

    @NonNull
    @Override
    public approval_adapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.admin_approval_style, parent, false);
        approval_adapter.MyView viewHolder = new approval_adapter.MyView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull approval_adapter.MyView holder, int position) {
        approval ap = aprovallist.get(position);
        holder.itemView.setTag(ap.get(position));
        String dept = null;
        if (ap.getEmpdept().trim().equals("")){
            dept = "NO DEPARTMENT";
        }else{
            dept = ap.getEmpdept();
        }
        holder.details.setText(Html.fromHtml("" +
                "EMP NO:  <font color='#323332'>#"+ap.getEmpcode().trim()+"</font><br>" +
                "FULLNAME:  <font color='#323332'>"+ap.getFirstname().trim()+" "+ ap.getLastname().trim()+"</font><br>" +
                 "USERNAME:  <font color='#323332'>@"+ap.getUsername()+"</font><br>"+
                "DEPARTMENT:  <font color='#323332'>"+dept.trim()+"</font><br>"+
                "DATE MADE:  <font color='#323332'>"+ap.getEmpcode().trim()+"</font>"), TextView.BufferType.SPANNABLE);
    }
    @Override
    public int getItemCount() {
        return aprovallist.size();
    }

    public class MyView extends RecyclerView.ViewHolder{
        TextView details;
        Button approve, decline;

        public MyView(@NonNull View itemView) {
            super(itemView);
            decline = itemView.findViewById(R.id.decline);
            approve = itemView.findViewById(R.id.approve);
            details = itemView.findViewById(R.id.user_details);



            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    MainActivity ma = new MainActivity();

                    if(aprovallist.size() != 0){
                        approval ap= aprovallist.get(position);

                        ma.del_image();
                        ma.image_refresh();
                        ma.addsrfimages_adapter();
                    }
                }
            });
            approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    approval ap = aprovallist.get(position);

                    MainActivity ma = new MainActivity();
                    ma.dialog_imageviewer();
                }
            });
        }


    }

}
