package io.github.therealmone.fireres.excel.column.firemode;

import io.github.therealmone.fireres.excel.column.Column;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EightTimeColumn extends Column {

    private static final String HEADER = "8t + 1";

    public EightTimeColumn(Integer time) {
        super(HEADER, false, IntStream.range(0, time)
                .mapToObj(t -> 8 * t + 1)
                .collect(Collectors.toList()));
    }

}
