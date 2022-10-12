package io.fallon;

import com.opencsv.bean.CsvBindByName;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * This will be an output instance class of info about the threads to capture that info
 * and print it out quickly. This class must be created by and used by the concerned thread.
 */
public class ThrdInfo {
    private static final ThreadMXBean threadMx = ManagementFactory.getThreadMXBean();
    private final Thread thread = Thread.currentThread();

    /**
     * Time duration of total process time before thread completion.
     */
    @CsvBindByName
    private long threadWallDuration;

    /**
     * Time duration of running time for thread.
     */
    @CsvBindByName
    private long threadCpuTime;

    /**
     * Threads unique ID
     */
    private final long threadId = thread.getId();

    /**
     * Threads Name for display.
     */
    @CsvBindByName
    private final int threadNum = Integer.parseInt(thread.getName());

    private boolean started = false;
    private boolean stopped = false;

    @CsvBindByName
    private final int threadTotal = Main.numChild;

    @CsvBindByName
    private final int totalN = Main.total;

    /**
     * May only be called once. will start the thrdinfo timers to be stopped later.
     */
    public void start(){
        if (!started) {
            started = true;
            threadWallDuration = System.nanoTime();
            threadCpuTime = threadMx.getThreadCpuTime(threadId);
        }
    }

    /**
     * May only be called once after started to stop the timers and assign times to this class for
     * printing and retrieval.
     */
    public void stop(){
        if(started && !stopped){
            stopped = true;
            threadWallDuration = System.nanoTime() - threadWallDuration;
            threadCpuTime = threadMx.getThreadCpuTime(threadId) - threadCpuTime;
        }
    }

    @Override
    public String toString() {
        if (started && stopped) {
            return String.format("Thread Number: %s/%s\tTotal N: %s\tWall Duration: %fms\tCpu Time: %fms",
                     threadNum, threadTotal, totalN, threadWallDuration /1000000.0, threadCpuTime/1000000.0);
        } else return "Still must be timed";
    }

    public int getThreadNum() {
        return threadNum;
    }

    public long getThreadId() {
        return threadId;
    }

    public long getThreadWallDuration() {
        return threadWallDuration;
    }

    public long getThreadCpuTime() {
        return threadCpuTime;
    }

    public int getThreadTotal(){
        return threadTotal;
    }

    public int getTotalN() {
        return totalN;
    }
}
