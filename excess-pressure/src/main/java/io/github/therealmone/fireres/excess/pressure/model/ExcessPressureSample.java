package io.github.therealmone.fireres.excess.pressure.model;

import io.github.therealmone.fireres.core.model.ReportSample;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class ExcessPressureSample implements ReportSample {

    private final UUID id;

    private SamplePressure pressure;

}
