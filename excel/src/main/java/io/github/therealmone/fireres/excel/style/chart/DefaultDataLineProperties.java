package io.github.therealmone.fireres.excel.style.chart;

import lombok.val;
import org.apache.poi.xddf.usermodel.PresetColor;
import org.apache.poi.xddf.usermodel.XDDFColorPreset;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;

import static io.github.therealmone.fireres.core.utils.RandomUtils.generateValueInInterval;

public class DefaultDataLineProperties extends XDDFLineProperties {

    public DefaultDataLineProperties() {
        val fillProperties = new XDDFSolidFillProperties();
        val availableColors = PresetColor.values();
        val color = new XDDFColorPreset(availableColors[generateValueInInterval(0, availableColors.length - 1)]);

        fillProperties.setColor(color);

        setWidth(1.0);
        setFillProperties(fillProperties);
    }

}
