package io.github.therealmone.fireres.gui.preset;

import io.github.therealmone.fireres.core.config.ReportProperties;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPreset implements Preset {

    private final Map<Class<? extends ReportProperties>, ReportProperties> propertiesMap = new HashMap<>();

    @Override
    public <T extends ReportProperties> T getProperties(Class<T> propertiesClass) {
        if (propertiesMap.containsKey(propertiesClass)) {
            return (T) propertiesMap.get(propertiesClass);
        } else {
            return getDefaultProperties(propertiesClass);
        }
    }

    @SneakyThrows
    private <T extends ReportProperties> T getDefaultProperties(Class<T> propertiesClass) {
        return propertiesClass.getDeclaredConstructor(new Class[]{}).newInstance();
    }

    protected void registerProperties(ReportProperties properties) {
        if (propertiesMap.containsKey(properties.getClass())) {
            throw new IllegalArgumentException("Properties already defined");
        }

        propertiesMap.put(properties.getClass(), properties);
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
