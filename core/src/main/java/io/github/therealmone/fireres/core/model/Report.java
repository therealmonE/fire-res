package io.github.therealmone.fireres.core.model;

import io.github.therealmone.fireres.core.config.ReportProperties;

import java.util.UUID;

public interface Report<P extends ReportProperties> {

    UUID getId();

    Sample getSample();

    P getProperties();

}
