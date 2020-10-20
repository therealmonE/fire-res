package io.github.therealmone.fireres.core.config;

import io.github.therealmone.fireres.core.config.sample.SampleProperties;
import io.github.therealmone.fireres.core.config.temperature.TemperatureProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerationProperties {

    private Integer time;
    private TemperatureProperties temperature;
    private List<SampleProperties> samples;

}
