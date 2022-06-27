package com.example.cf_srf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class cat_adapter extends RecyclerView.Adapter<cat_adapter.MyView>{



    private static String uni_catcode;
    private static Context context;
    private List<cat> cat_list;
    private static MainActivity ma;
    public cat_adapter(Context context, List<cat> cat_list) {
        this.cat_list = cat_list;
        this.context = context;

    }

    private static String uni_catname;

    public static String getUni_catname() {
        return uni_catname;
    }

    public static String getUni_catcode() {
        return uni_catcode;
    }



    @NonNull
    @Override
    public cat_adapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.requestor_style, parent , false);
        cat_adapter.MyView viewHolder = new cat_adapter.MyView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull cat_adapter.MyView holder, int position) {
        cat cat =  cat_list.get(position);
        holder.itemView.setTag(cat.get(position));
        holder.cat_code.setText(cat.getCat_code());
        holder.cat_name.setText(cat.getCat_name());

    }

    @Override
    public int getItemCount() {
        return cat_list.size();
    }

    public class MyView extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView cat_code, cat_name;
        CardView edit;
        public MyView(@NonNull View itemView) {
            super(itemView);
            edit = itemView.findViewById(R.id.reqcard);
            edit.setOnClickListener(this);
            cat_code = (TextView) itemView.findViewById(R.id.cat_code);
            cat_name = (TextView) itemView.findViewById(R.id.cat_name);

        }
        @Override
        public void onClick(View v) {
        int position =  this.getAdapterPosition();
        cat cat = cat_list.get(position);
        String code = cat.getCat_code();
        String name = cat.getCat_name();
        uni_catcode = code;
        uni_catname = name;
            ma = new MainActivity();
            ma.to_request();
        }
    }
}
