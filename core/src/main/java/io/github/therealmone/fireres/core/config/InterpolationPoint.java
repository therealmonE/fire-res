package io.github.therealmone.fireres.core.config;

import com.typesafe.config.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static io.github.therealmone.fireres.core.utils.RandomUtils.generateValueInInterval;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterpolationPoint {

    public InterpolationPoint(Integer time, Integer value) {
        this.time = time;
        this.value = value;
    }

    private Integer time;

    @Optional
    private Integer value;

    @Optional
    private Interval interval;

    public void setInterval(Interval interval) {
        this.value = generateValueInInterval(interval.getFrom(), interval.getTo());
        this.interval = interval;
    }

}
