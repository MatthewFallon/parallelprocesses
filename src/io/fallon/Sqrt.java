package io.fallon;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

public class Sqrt {
    private static ThrdInfo[] results;
    public static Writer tw;
    private static StatefulBeanToCsv<ThrdInfo> csvTw;
    public static Writer pw;
    private static StatefulBeanToCsv<ProcessInfo> csvPw;

    public static void main(String[] args) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        if(csvTw == null && csvPw == null){ // Singleton FileWriter
            tw = new FileWriter("sqrtThread.csv");
            csvTw = new StatefulBeanToCsvBuilder<ThrdInfo>(tw)
                    .withProfile("")
                    .build();
            pw = new FileWriter("sqrtProcess.csv");
            csvPw = new StatefulBeanToCsvBuilder<ProcessInfo>(pw)
                    .withProfile("")
                    .build();
        }

        int numChild = Main.numChild;
        int total = Main.total;

        ProcessInfo processResults = new ProcessInfo();
        processResults.start();

        int range = total/numChild; // May require they be divisible by each other to cover all.
        Thread[] threads = new Thread[numChild];
        results = new ThrdInfo[numChild];
        for (int i = 0; i < numChild; i++) {
            int begin = (i * range) + i;
            threads[i] = new Thread(new SqrtThrd(begin, begin + range, i), "" + (i + 1));
            threads[i].start();
        }

        for (int i = 0; i < numChild; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.err.println("Interrupted and Exiting.");
                return;
            }
        }

        processResults.stop();
        System.err.println();
        System.err.println(processResults);
        System.out.println();
        for (ThrdInfo info :
                results) {
            System.err.println(info);
        }


        System.err.printf("All children done: %d\n", numChild);

        csvTw.write(Arrays.asList(results));
        csvPw.write(processResults);

    }

    private static class SqrtThrd implements Runnable{
        private final int begin;
        private final int end;
        private final int threadIndex;

        public SqrtThrd(int begin, int end, int threadIndex){
            this.begin = begin;
            this.end = end;
            this.threadIndex = threadIndex;
        }

        @Override
        public void run() {
            Sqrt.results[threadIndex] = new ThrdInfo();
            Sqrt.results[threadIndex].start();
            double total = 0.0;
            for(int i=begin; i <= end; i++) {
                double root = Math.sqrt(i);
                total += root;
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.printf("%d:%f ", i, root);
//                if (i % 5 == 0) System.out.println();
            }
            System.out.printf("total:%s", total);
            Sqrt.results[threadIndex].stop();
        }
    }
}
