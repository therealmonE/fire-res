package io.github.therealmone.fireres.unheated.surface.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnheatedSurfaceGroup {

    private UnheatedSurfaceMeanBound meanBound;
    private UnheatedSurfaceThermocoupleBound thermocoupleBound;
    private UnheatedSurfaceMeanTemperature meanTemperature;
    private List<UnheatedSurfaceThermocoupleTemperature> thermocoupleTemperatures;

}
