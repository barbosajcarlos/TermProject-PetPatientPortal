package com.app.ppportal.model;


import java.io.Serializable;

public class VisitHistoryData implements Serializable {
    private String reason;
    private long time;

    public VisitHistoryData(){}
    public VisitHistoryData(String reason, long time) {
        this.reason = reason;
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
