package io.github.therealmone.fireres.core.common.report;

import io.github.therealmone.fireres.core.common.config.GenerationProperties;
import io.github.therealmone.fireres.core.firemode.report.FireModeReportBuilder;
import io.github.therealmone.fireres.core.pressure.report.ExcessPressureReportBuilder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class FullReportBuilder implements ReportBuilder<FullReport> {

    public FullReport build(GenerationProperties properties) {
        log.info("Building full report");

        val fireMode = new FireModeReportBuilder().build(properties);
        val excessPressure = new ExcessPressureReportBuilder().build(properties);

        log.info("Full report was built successfully");
        return FullReport.builder()
                .time(properties.getGeneral().getTime())
                .environmentTemperature(properties.getGeneral().getEnvironmentTemperature())
                .fireMode(fireMode)
                .excessPressure(excessPressure)
                .build();
    }

}
