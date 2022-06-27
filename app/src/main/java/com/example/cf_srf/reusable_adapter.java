package com.example.cf_srf;

import android.content.Context;
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


public class reusable_adapter extends RecyclerView.Adapter<reusable_adapter.MyView> {



    private static Context context;
    private static ArrayList<img_loc> img_list;
    private static Boolean trigger = false;


    public reusable_adapter(Context context, ArrayList<img_loc> img_list) {
        this.img_list = img_list;
        this.context = context;
    }



    @NonNull
    @Override
    public reusable_adapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.reusablelist, parent, false);
        reusable_adapter.MyView viewHolder = new reusable_adapter.MyView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull reusable_adapter.MyView holder, int position) {
        img_loc img = img_list.get(position);
        holder.itemView.setTag(img.get(position));
        holder.filename.setText(img.getFilename().trim());



    }

    @Override
    public int getItemCount() {
        return img_list.size();
    }

    public class MyView extends RecyclerView.ViewHolder{
        TextView filename;

        public MyView(@NonNull View itemView) {
            super(itemView);

            filename = (TextView) itemView.findViewById(R.id.file_name);



        }


    }

}
