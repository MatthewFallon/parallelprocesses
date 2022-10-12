package io.fallon;

public class Factor {

    public static void main(String[] args) {

        if(args.length < 2) {System.out.println("Not enough Args"); return;}
        int numChild = Integer.parseInt(args[0]);
        int total = Integer.parseInt(args[1]);

        int range = total/numChild; // May require they be divisible by each other to cover all.
        Thread[] threads = new Thread[numChild];
        for (int i = 0; i < numChild; i++) {
            int begin = (i * range) + i;
            threads[i] = new Thread(new FactorThrd(begin, begin + range));
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

        System.err.printf("All children done: %d\n", numChild);

    }

    private static class FactorThrd implements Runnable{
        private final int begin;
        private final int end;

        public FactorThrd(int begin, int end){
            this.begin = begin;
            this.end = end;
        }

        @Override
        public void run() {
            String tName = Thread.currentThread().getName();

            long start = System.nanoTime();
            double total = 0.0;
            for(int i=begin; i <= end; i++) {
                for (int j = 2; j < i/2; j++) {

                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i % 5 == 0) System.out.println();
            }
            long duration = System.nanoTime() - start;

            System.err.printf("Thread: %5s\tDuration: %d\tTotal: %f\n", tName, duration, total);

        }
    }
}
