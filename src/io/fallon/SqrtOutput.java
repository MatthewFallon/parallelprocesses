package io.fallon;

import com.opencsv.bean.CsvBindByName;

public class SqrtOutput {
    @CsvBindByName(column = "Thread Name", required = true)
    private String tName;

    @CsvBindByName(column = "Duration", required = true)
    private long duration;

    @CsvBindByName(column = "Total", required = true)
    private double total;

    public SqrtOutput(String tName, long duration, double total) {
        this.tName = tName;
        this.duration = duration;
        this.total = total;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }
}
