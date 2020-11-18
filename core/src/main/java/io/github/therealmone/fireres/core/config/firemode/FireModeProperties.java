package io.github.therealmone.fireres.core.config.firemode;

import io.github.therealmone.fireres.core.config.Interpolation;
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
public class FireModeProperties extends Interpolation {

    private Integer thermocoupleCount;

}
