package com.example.work.calllist;

public class callLogModel {
    String phNumber,contactName,callType,callDate,callTime,callDuration;

    public callLogModel(String phNumber,String contactName,String callType,String callDate,String callTime,String callDuration){
        this.callDate = callDate;
        this.callDuration = callDuration;
        this.callTime = callTime;
        this.callType =callType;
        this.contactName =contactName;
        this.phNumber = phNumber;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

}
