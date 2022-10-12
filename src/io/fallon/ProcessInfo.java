package io.fallon;

import com.opencsv.bean.CsvBindByName;

public class ProcessInfo {

    @CsvBindByName
    private long wallDuration;

    @CsvBindByName
    private final int threadTotal = Main.numChild;

    @CsvBindByName
    private final int totalN = Main.total;

    private boolean started;
    private boolean stopped;

    /**
     * May only be called once. will start the thrdinfo timers to be stopped later.
     */
    public void start(){
        if (!started) {
            started = true;
            wallDuration = System.nanoTime();
        }
    }

    /**
     * May only be called once after started to stop the timers and assign times to this class for
     * printing and retrieval.
     */
    public void stop(){
        if(started && !stopped){
            stopped = true;
            wallDuration = System.nanoTime() - wallDuration;
        }
    }

    @Override
    public String toString() {
        if (started && stopped) {
            return String.format("Thread Number: %s\tTotal N: %s\tWall Duration: %fms",
                    threadTotal, totalN, wallDuration /1000000.0);
        } else return "Still must be timed";
    }

    public long getWallDuration() {
        return wallDuration;
    }

    public int getThreadTotal() {
        return threadTotal;
    }

    public int getTotalN() {
        return totalN;
    }
}
