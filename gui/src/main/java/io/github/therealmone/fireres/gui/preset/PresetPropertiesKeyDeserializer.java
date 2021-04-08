package io.github.therealmone.fireres.gui.preset;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import io.github.therealmone.fireres.core.config.ReportProperties;
import lombok.SneakyThrows;
import lombok.val;

import java.io.IOException;

public class PresetPropertiesKeyDeserializer extends KeyDeserializer {

    @SneakyThrows
    @Override
    public Object deserializeKey(String s, DeserializationContext deserializationContext) throws IOException {
        val clazz = Class.forName(s);

        if (ReportProperties.class.isAssignableFrom(clazz)) {
            return clazz;
        } else {
            throw new IllegalStateException("Invalid property class: " + s);
        }
    }

}
