package com.example.cf_srf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class status_class_adapter extends RecyclerView.Adapter<status_class_adapter.MyView>{



    private static String status_code;
    private static String status_desc;
    private Context context;
    private List<status_class> status_list;
    private static MainActivity ma;

    public status_class_adapter(Context context, List<status_class> status_list) {
        this.context = context;
        this.status_list = status_list;
    }

    public static String getStatus_code() {
        return status_code;
    }

    public static void setStatus_code(String status_code) {
        status_class_adapter.status_code = status_code;
    }

    public static String getStatus_desc() {
        return status_desc;
    }

    public static void setStatus_desc(String status_desc) {
        status_class_adapter.status_desc = status_desc;
    }

    @NonNull
    @Override
    public status_class_adapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.status_class, parent , false);
        status_class_adapter.MyView viewHolder = new status_class_adapter.MyView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull status_class_adapter.MyView holder, int position) {
        status_class status =  status_list.get(position);
        holder.itemView.setTag(cat.get(position));
        holder.status_name.setText(status.getStatus_desc());
        holder.number.setText(status.getNumber());
        if (status.getNumber().trim().equals("0")){
            holder.red_dot.setVisibility(View.GONE);
        }else{
            holder.red_dot.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return status_list.size();
    }

    public class MyView extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView status_name, number;
        CardView edit, red_dot;
        public MyView(@NonNull View itemView) {
            super(itemView);
            edit = itemView.findViewById(R.id.reqcard);
            edit.setOnClickListener(this);
            status_name = (TextView) itemView.findViewById(R.id.status_name);
            red_dot = (CardView) itemView.findViewById(R.id.card_number);
            number = (TextView) itemView.findViewById(R.id.number);
        }
        @Override
        public void onClick(View v) {
        int position =  this.getAdapterPosition();
        status_class sta = status_list.get(position);
        String code = sta.getStatus_code().trim();
        String name = sta.getStatus_desc().trim();
        setStatus_code(code);
        setStatus_desc(name);
            ma = new MainActivity();
            if (MainActivity.getStatus_trigger().equals(false))
            {

                MainActivity.setStatus_holder(getStatus_code().trim());
                MainActivity.setStatus_holder_name(getStatus_desc().trim());
                ma.to_srflist();

            }else if (MainActivity.getStatus_trigger().equals(true)){
                if (sta.getStatus_code().trim().equals("0001")){
                    ma.to_signature();
                }else{
                    ma.to_details();
                }

            }
        }
    }
}
