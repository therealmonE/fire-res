package io.github.therealmone.fireres.excel.model;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EnvTempColumn extends Column {

    private static final String HEADER = "Т0";

    public EnvTempColumn(Integer time, Integer envTemp) {
        super(HEADER, IntStream.range(0, time)
                .mapToObj(t -> envTemp)
                .collect(Collectors.toList()));
    }

}
