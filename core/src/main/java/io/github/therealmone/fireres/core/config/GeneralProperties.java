package io.github.therealmone.fireres.core.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralProperties {

    private String fileName;

    private String outputPath;

    private Integer time;

    private Integer environmentTemperature;

    @Builder.Default
    private List<ReportType> includedReports = new ArrayList<>();

}
