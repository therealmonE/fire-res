package io.github.therealmone.fireres.excel.chart.lines;

import lombok.val;
import org.apache.poi.xddf.usermodel.PresetColor;
import org.apache.poi.xddf.usermodel.PresetLineDash;
import org.apache.poi.xddf.usermodel.XDDFColorPreset;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;
import org.apache.poi.xddf.usermodel.XDDFPresetLineDash;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;

public class StandardTemperatureLineProperties extends XDDFLineProperties {

    public StandardTemperatureLineProperties() {
        val fillProperties = new XDDFSolidFillProperties();
        val color = new XDDFColorPreset(PresetColor.LIGHT_GRAY);

        fillProperties.setColor(color);

        setWidth(1.0);
        setFillProperties(fillProperties);
        setPresetDash(new XDDFPresetLineDash(PresetLineDash.DASH_DOT));
    }

}
