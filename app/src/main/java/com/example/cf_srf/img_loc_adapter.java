package com.example.cf_srf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class img_loc_adapter extends RecyclerView.Adapter<img_loc_adapter.MyView> {

    private static String file_name;
    private static String uni_stn;
    private static Context context;
    private static ArrayList<img_loc> img_list;



    public img_loc_adapter(Context context, ArrayList<img_loc> img_list) {
        this.img_list = img_list;
        this.context = context;
    }

    public static String getFile_name() {
        return file_name;
    }

    public static String getUni_stn() {
        return uni_stn;
    }

    public static Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public img_loc_adapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.img_holder, parent, false);
        img_loc_adapter.MyView viewHolder = new img_loc_adapter.MyView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull img_loc_adapter.MyView holder, int position) {
        img_loc img = img_list.get(position);
        holder.itemView.setTag(img.get(position));
        holder.filename.setText(img.getFilename().trim());

        if(MainActivity.getDetails_viewing_trigger().equals(true)){
            holder.delete.setVisibility(View.GONE);
        } else if (MainActivity.getDetails_viewing_trigger().equals(false)){
            holder.delete.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return img_list.size();
    }

    public class MyView extends RecyclerView.ViewHolder{
        TextView filename;
        ImageView viewerimg;
        Button delete;
        CardView viewer;

        public MyView(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.del_image);
            filename = (TextView) itemView.findViewById(R.id.file_name);
            viewerimg = (ImageView) itemView.findViewById(R.id.imageView);
            viewer = (CardView) itemView.findViewById(R.id.imgcard);


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    MainActivity ma = new MainActivity();
                    ma.image_refresh();
                    if(img_list.size() != 0){
                        img_loc img = img_list.get(position);
                        String code = img.getFilename();
                        file_name = code;
                        ma.del_image();
                        ma.image_refresh();
                        ma.addsrfimages_adapter();
                    }
                }
            });
            viewer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    img_loc img = img_list.get(position);
                    String code = img.getFilename();
                    String stn = img.getStn();
                    file_name = code;
                    uni_stn =  stn;
                    MainActivity ma = new MainActivity();
                    ma.dialog_imageviewer();
                }
            });
        }


    }

}
