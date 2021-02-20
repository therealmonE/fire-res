package io.github.therealmone.fireres.unheated.surface.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnheatedSurfaceGroup {

    private final UUID id = UUID.randomUUID();

    private UnheatedSurfaceMeanBound meanBound;
    private UnheatedSurfaceThermocoupleBound thermocoupleBound;
    private UnheatedSurfaceMeanTemperature meanTemperature;
    private List<UnheatedSurfaceThermocoupleTemperature> thermocoupleTemperatures;

}
