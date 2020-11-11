package io.github.therealmone.fireres.core.common.report;


import io.github.therealmone.fireres.core.common.config.GenerationProperties;

public interface ReportBuilder<T extends Report> {

    T build(GenerationProperties properties);

}
