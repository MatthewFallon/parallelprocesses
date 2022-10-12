package io.fallon;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;

public class Main {
    public static int numChild;
    public static int total;

    public static void main(String[] args) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        int topChild = 16;
        int bottomChild = 1;
        int topTotal = 1000000;
        int bottomTotal = 1000;
        for (int i = bottomChild; i <= topChild; i *= 2) {
            for (int j = bottomTotal; j <= topTotal; j *= 10) {
                numChild = i;
                total = j;
                Factor.main(new String[]{});
            }
        }
        Factor.tw.close();
        Factor.pw.close();

        for (int i = bottomChild; i <= topChild; i *= 2) {
            for (int j = bottomTotal; j <= topTotal; j *= 10) {
                numChild = i;
                total = j;
                Sqrt.main(new String[]{});
            }
        }
        Sqrt.tw.close();
        Sqrt.pw.close();
    }
}
