package com.example.work.calllist;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;

import com.example.work.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class call_log extends AppCompatActivity {

    private ArrayList<callLogModel> callLogModelArrayList;
    private RecyclerView rv_call_logs;
    private calllog_adapter calllogadapter;

    public String str_number,str_contact_name,str_call_type,str_call_date,str_call_time,str_call_duration,str_call_full_date,str_call_time_formatted;
    private SwipeRefreshLayout swipeRefreshLayout;

    private static final int PERMISSION__REQUEST_CODE =999;
    String[] appPermissions = {
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.READ_PHONE_STATE
    };
    private int flag =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);

//        getSupportActionBar().setTitle("Call logs");

        Init();

        if (CheckAndRequestPermission()){
            FetchCallLogs();
        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (CheckAndRequestPermission()){
                    FetchCallLogs();
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public boolean CheckAndRequestPermission(){
        List<String> listPermissionNeeded = new ArrayList<>();
        for (String item:appPermissions){
            if (ContextCompat.checkSelfPermission(this,item)!= PackageManager.PERMISSION_GRANTED)
                listPermissionNeeded.add(item);
        }

        if (!listPermissionNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]),PERMISSION__REQUEST_CODE);
            return false;
        }
        return true;

    }

    private void Init() {

        swipeRefreshLayout = findViewById(R.id.call_swip);
        rv_call_logs = findViewById(R.id.call_recyclerview);
        rv_call_logs.setHasFixedSize(true);
        rv_call_logs.setLayoutManager(new LinearLayoutManager(this));
        callLogModelArrayList = new ArrayList<>();
        calllogadapter = new calllog_adapter(this,callLogModelArrayList);
        rv_call_logs.setAdapter(calllogadapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION__REQUEST_CODE){
            for (int i=0;i<permissions.length;i++){
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    flag=1;
                    break;
                }
            }
            if (flag ==0){
                FetchCallLogs();
            }
        }
    }

    public void FetchCallLogs(){
        String sortOrder = android.provider.CallLog.Calls.DATE + " DESC";
        Cursor cursor = this.getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                null,
                null,
                null,
                sortOrder);
        callLogModelArrayList.clear();
        while (cursor.moveToNext()){
            str_number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            str_contact_name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
            str_contact_name = str_contact_name==null || str_contact_name.equals("")? "Unknown" :str_contact_name;
            str_call_type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
            str_call_full_date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
            str_call_duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
            str_call_date = dateFormat.format(new Date(Long.parseLong(str_call_full_date)));

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            str_call_time= timeFormat.format(new Date(Long.parseLong(str_call_full_date)));

            str_call_time_formatted = getFormatedDateTime(str_call_time,"HH:mm:ss","hh:mm a");

            switch (Integer.parseInt(str_call_type)){
                case CallLog.Calls.INCOMING_TYPE:
                    str_call_type = "Incoming";
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    str_call_type = "Outgoing";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    str_call_type = "Missed";
                    break;
                case CallLog.Calls.VOICEMAIL_TYPE:
                    str_call_type = "Voicemail";
                    break;
                case CallLog.Calls.REJECTED_TYPE:
                    str_call_type = "Rejected";
                    break;
                case CallLog.Calls.BLOCKED_TYPE:
                    str_call_type = "Blocked";
                    break;
                case CallLog.Calls.ANSWERED_EXTERNALLY_TYPE:
                    str_call_type = "Externally Answered";
                    break;
                default:
                    str_call_type = "NA";
            }
            str_call_duration = DuraTionFormat(str_call_duration);
            callLogModel callLogItem= new callLogModel(str_number,str_contact_name,str_call_type,str_call_date,str_call_time_formatted,str_call_duration);

            callLogModelArrayList.add(callLogItem);

        }
        calllogadapter.notifyDataSetChanged();

    }
    private String getFormatedDateTime(String dateStr,String strInputFormat,String strOutputFormat){
        String formattedDate = dateStr;
        DateFormat inputFormat = new SimpleDateFormat(strInputFormat, Locale.getDefault());
        DateFormat outputFormat = new SimpleDateFormat(strOutputFormat, Locale.getDefault());
        Date date = null;

        try {
            date = inputFormat.parse(dateStr);

        }catch (ParseException e){

        }
        if (date!=null){
            formattedDate = outputFormat.format(date);
        }
        return formattedDate;
    }

    private String DuraTionFormat(String duration){
        String durationFormatted = null;
        if (Integer.parseInt(duration)<60){
            durationFormatted = duration+"sec";
        } else {
            int min = Integer.parseInt(duration)/60;
            int sec = Integer.parseInt(duration)%60;

            if (sec ==0){
                durationFormatted = min + "min";

            }else {
                durationFormatted = min+"min"+sec+"sec";
            }

        }
        return durationFormatted;
    }
}