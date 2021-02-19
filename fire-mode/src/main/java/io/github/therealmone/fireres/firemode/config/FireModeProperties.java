package io.github.therealmone.fireres.firemode.config;

import io.github.therealmone.fireres.core.config.Interpolation;
import io.github.therealmone.fireres.core.config.ReportProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FireModeProperties extends Interpolation<Integer> implements ReportProperties {

    private Integer thermocoupleCount;

}
