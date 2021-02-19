package io.github.therealmone.fireres.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Point<T extends Number> {

    private Integer time;
    private T value;

    public abstract T getNormalizedValue();

    public Integer getIntValue() {
        return value.intValue();
    }

}
