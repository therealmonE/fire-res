package io.github.therealmone.fireres.excel.style.chart;

import lombok.val;
import org.apache.poi.xddf.usermodel.PresetColor;
import org.apache.poi.xddf.usermodel.XDDFColorPreset;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;

public class AxisLineProperties extends XDDFLineProperties {

    public AxisLineProperties() {
        val fillProperties = new XDDFSolidFillProperties();
        fillProperties.setColor(new XDDFColorPreset(PresetColor.BLACK));

        setWidth(1.0);
        setFillProperties(fillProperties);
    }

}
