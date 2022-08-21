package com.example.cf_srf;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class srf_adapter extends RecyclerView.Adapter<srf_adapter.MyView>{


    private static String uni_stn;
    private static String uni_stncode;
    private static String uni_file;
    private static String uni_date;
    private static String uni_catcode;
    private static String uni_catdesc;
    private static String uni_problem;
    private static String uni_user;
    private static String uni_status;
    private static String uni_srfcode;
    private static String uni_srfupdateby;
    private static String uni_srfupdate_date;
    private static String uni_srfaction;
    private static String uni_srfclosed;
    private static String uni_age;



    public srf_adapter() {

    }

    public static String getUni_srfclosed() {
        return uni_srfclosed;
    }

    public static String getUni_srfupdateby() {
        return uni_srfupdateby;
    }

    public static String getUni_srfupdate_date() {
        return uni_srfupdate_date;
    }

    public static String getUni_srfaction() {
        return uni_srfaction;
    }

    public static String getUni_file() {
        return uni_file;
    }

    public static String getUni_srfcode() {
        return uni_srfcode;
    }

    public static String getUni_stn() {
        return uni_stn;
    }

    public static String getUni_stncode() {
        return uni_stncode;
    }


    public static String getUni_date() {
        return uni_date;
    }

    public static String getUni_catcode() {
        return uni_catcode;
    }

    public static String getUni_catdesc() {
        return uni_catdesc;
    }

    public static String getUni_problem() {
        return uni_problem;
    }

    public static String getUni_user() {
        return uni_user;
    }

    public static String getUni_status() {
        return uni_status;
    }

    public static String getUni_age() {
        return uni_age;
    }

    private static Context context;
    private static MainActivity ma;
    private static ArrayList<srf> srf_list;
    public srf_adapter(Context context, ArrayList<srf> srf_list) {
        this.context = context;
        this.srf_list = srf_list;
    }


    @NonNull
    @Override
    public srf_adapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.srf_style, parent , false);
        srf_adapter.MyView viewHolder = new srf_adapter.MyView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull srf_adapter.MyView holder, int position) {
        String status = null;
        srf srf =  srf_list.get(position);
        holder.itemView.setTag(srf.get(position));

        holder.srfdate.setText(Html.fromHtml("SRF DATE:  <font color='#323332'>"+srf.getSRF_Date().trim()+"</font>"), TextView.BufferType.SPANNABLE);
        holder.srfuser.setText(Html.fromHtml("ENCODED BY:  <font color='#323332'>"+srf.getSRF_User().trim()+"</font>"), TextView.BufferType.SPANNABLE);
        holder.srfstn.setText(Html.fromHtml("BRANCH:  <font color='#323332'>"+srf.getSRF_Stn().trim()+" ("+srf.getSRF_StnCode().trim()+") "+"</font>"), TextView.BufferType.SPANNABLE);
        holder.srfcat.setText(Html.fromHtml("CATEGORY:  <font color='#323332'>"+srf.getSRF_Desc().trim()+"("+srf.getSRF_CatCode().trim()+")"+"</font>"), TextView.BufferType.SPANNABLE);
        holder.srf_prob.setText(Html.fromHtml("REPORTED PROBLEM:  <br><br><font color='red'>"+srf.getSRF_Problem().trim()+"<br></font>"), TextView.BufferType.SPANNABLE);
        holder.srfstatus.setText(Html.fromHtml("SRF STATUS:  <font color='#005eb8'>"+srf.getSRF_Status().trim()+"</font>"), TextView.BufferType.SPANNABLE);
        if(srf.getSRF_attach().trim().equals("FALSE")){
            //holder.srfattach.setText(Html.fromHtml("IMAGE ATTACHMENT/S:  <font color='#323332'>NO ATTACHMENTS</font>"), TextView.BufferType.SPANNABLE);
            holder.hideimage.setVisibility(View.GONE);
        }else if(srf.getSRF_attach().trim().equals("TRUE")){
            holder.hideimage.setVisibility(View.VISIBLE);
            holder.srfattach.setText(Html.fromHtml(" <font color='#323332'>WITH IMAGE ATTACHMENT/S</font>"), TextView.BufferType.SPANNABLE);
        }

        holder.srfupdateby.setText(Html.fromHtml("LAST UPDATE BY:  <font color='#323332'>"+srf.getSRF_updated_by().trim()+"</font>"), TextView.BufferType.SPANNABLE);

        if (srf.getSRF_actions().trim().equals("FALSE")){
            holder.srfaction.setText(Html.fromHtml("ACTION(S):  <font color='#323332'>"+"NO ACTIONS YET"+"</font>"), TextView.BufferType.SPANNABLE);
        }else if(srf.getSRF_actions().trim().equals("TRUE")){
            holder.srfaction.setText(Html.fromHtml("ACTION(S):  <font color='#005eb8'>"+"WITH ACTION(S)"+"</font>"), TextView.BufferType.SPANNABLE);
        }

        if(srf.getSRF_close_date().trim().equals("NOT CLOSED YET")){
            holder.closed_date.setText(Html.fromHtml("CLOSED DATE:  <font color='#323332'>"+srf.getSRF_close_date().trim()+"</font>"), TextView.BufferType.SPANNABLE);
            holder.srf_age.setVisibility(View.VISIBLE);
            if (srf.getSRF_age().trim().equals("0")){// newly added
                holder.srf_age.setText("NEW");
            }else{
                holder.srf_age.setText(srf.getSRF_age().trim()+" Day(s)");
            }
        }else{

            holder.closed_date.setText(Html.fromHtml("CLOSED DATE:  <font color='red'>"+srf.getSRF_close_date().trim()+"</font>"), TextView.BufferType.SPANNABLE);
            holder.srf_age.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return srf_list.size();
    }

    public class MyView extends RecyclerView.ViewHolder{
        TextView srfdate, srfuser, srfstn, srfcat, srf_prob,srfstatus,srfattach,srfupdateby, srfaction, closed_date, srf_age;
        CardView srf_Card;
        LinearLayout hideimage, header;
        public MyView(@NonNull View itemView) {
            super(itemView);
            srf_Card = itemView.findViewById(R.id.srfcard);
            srfdate = (TextView) itemView.findViewById(R.id.srf_date);
            srfuser = (TextView) itemView.findViewById(R.id.srf_user);
            srfstn = (TextView) itemView.findViewById(R.id.stn);
            srfcat = (TextView) itemView.findViewById(R.id.cat);
            srf_prob = (TextView) itemView.findViewById(R.id.report);
            srfstatus = (TextView) itemView.findViewById(R.id.status);
            srfattach = (TextView) itemView.findViewById(R.id.attach);
            srfupdateby = (TextView) itemView.findViewById(R.id.updated_by);
            srfaction = (TextView) itemView.findViewById(R.id.sr_action);
            closed_date = (TextView) itemView.findViewById(R.id.sr_close_date);
            hideimage = (LinearLayout) itemView.findViewById(R.id.hide_image);
            // newly add
            header = (LinearLayout) itemView.findViewById(R.id.header);
            srf_age = (TextView) itemView.findViewById(R.id.srf_age);

            srf_Card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    srf srf = srf_list.get(position);
                    uni_age =  srf.getSRF_age();
                    uni_stn = srf.getSRF_Stn();
                    uni_stncode = srf.getSRF_StnCode().trim();
                    uni_file = srf.getSRF_attach().trim();
                    uni_date = srf.getSRF_Date().trim();
                    uni_catcode = srf.getSRF_CatCode().trim();
                    uni_catdesc = srf.getSRF_Desc().trim();
                    uni_problem = srf.getSRF_Problem().trim();
                    uni_user = srf.getSRF_User().trim();
                    uni_status = srf.getSRF_Status().trim();
                    uni_srfcode = srf.getSRF_No().trim();
                    uni_srfupdateby =  srf.getSRF_updated_by().trim();
                    uni_srfupdate_date = srf.getSRF_update_date().trim();
                    if (srf.getSRF_actions().trim().equals("FALSE")){
                        uni_srfaction ="NO ACTIONS YET";
                    }else if(srf.getSRF_actions().trim().equals("TRUE")){
                        uni_srfaction ="WITH ACTION(S)";
                    }
                    if(srf.getSRF_close_date().trim().equals("0")){
                        uni_srfclosed = "NOT CLOSED YET";
                    }else{

                        uni_srfclosed = srf.getSRF_close_date().trim();
                    }

                    ma = new MainActivity();
                    MainActivity.setTriger(true);
                    ma.to_viewing();
                }
            });
        }

    }
    public void filterList(ArrayList<srf> filteredList){
        srf_list = filteredList;
        notifyDataSetChanged();
    }
}
