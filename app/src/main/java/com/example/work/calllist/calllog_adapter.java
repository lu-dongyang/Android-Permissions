package com.example.work.calllist;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.work.R;

import java.util.ArrayList;

public class calllog_adapter extends RecyclerView.Adapter<calllog_adapter.MyViewHolder> {
    private int px;

    Context context;
    ArrayList<callLogModel> callLogModelArrayList;

    public  calllog_adapter(Context context,ArrayList<callLogModel> callLogModelArrayList){
        this.context = context;
        this.callLogModelArrayList = callLogModelArrayList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Resources r = parent.getResources();
        px = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,r.getDisplayMetrics()));
        View v = LayoutInflater.from(context).inflate(R.layout.layout_call_log,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int i = position;
        if (i ==0){
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)holder.cardView.getLayoutParams();
            layoutParams.topMargin = px;
            holder.cardView.requestLayout();
        }
        callLogModel currentLog = callLogModelArrayList.get(position);
        holder.tv_call_duration.setText(currentLog.getCallDuration());
        holder.tv_call_time.setText(currentLog.getCallTime());
        holder.tv_call_date.setText(currentLog.getCallDate());
        holder.tv_call_type.setText(currentLog.getCallType());
        holder.tv_contact_name.setText(currentLog.getContactName());
        holder.tv_ph_name.setText(currentLog.getPhNumber());


    }

    @Override
    public int getItemCount() {

        return callLogModelArrayList == null?0:callLogModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView tv_ph_name,tv_contact_name,tv_call_type,tv_call_date,tv_call_time,tv_call_duration;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_ph_name = itemView.findViewById(R.id.call_number);
            tv_contact_name = itemView.findViewById(R.id.call_name);
            tv_call_type = itemView.findViewById(R.id.call_type);
            tv_call_date = itemView.findViewById(R.id.call_date);
            tv_call_time = itemView.findViewById(R.id.call_time);
            tv_call_duration = itemView.findViewById(R.id.call_duration);
            cardView = itemView.findViewById(R.id.call_cardview);
        }
    }
}
