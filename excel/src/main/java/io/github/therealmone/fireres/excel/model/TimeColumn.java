package io.github.therealmone.fireres.excel.model;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TimeColumn extends Column {

    private static final String HEADER = "Время, мин";

    public TimeColumn(Integer time) {
        super(HEADER, false, IntStream.range(0, time)
                .boxed()
                .collect(Collectors.toList()));
    }

}
