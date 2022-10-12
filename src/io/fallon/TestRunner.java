package io.fallon;

import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

public class TestRunner {

    public static void main(String[] args) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        ArrayList<SqrtOutput> outs = new ArrayList<>();

        outs.add(new SqrtOutput("hello", 123567, 3.0));

        HeaderColumnNameMappingStrategy<SqrtOutput> strategy =
                new HeaderColumnNameMappingStrategyBuilder<SqrtOutput>().build();
        strategy.setType(SqrtOutput.class);

        HashMap<String, String> colMap = new HashMap<>();
        colMap.put("tName", "Thread Name");
        colMap.put("duration", "Duration");
        colMap.put("Total", "total");

        HeaderColumnNameTranslateMappingStrategy<SqrtOutput> transStrategy =
                new HeaderColumnNameTranslateMappingStrategyBuilder<SqrtOutput>().build();
        transStrategy.setType(SqrtOutput.class);
        transStrategy.setColumnMapping(colMap);

        Writer w = new FileWriter("test.csv");
        StatefulBeanToCsv<SqrtOutput> b2c = new StatefulBeanToCsvBuilder<SqrtOutput>(w)
                .withMappingStrategy(transStrategy)
                .build();
        b2c.write(outs);
        w.close();
    }
}
