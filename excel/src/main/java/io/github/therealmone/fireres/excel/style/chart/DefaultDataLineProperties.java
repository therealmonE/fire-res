package io.github.therealmone.fireres.excel.style.chart;

import lombok.val;
import org.apache.poi.xddf.usermodel.XDDFColorPreset;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;


public class DefaultDataLineProperties extends XDDFLineProperties {

    public DefaultDataLineProperties() {
        val fillProperties = new XDDFSolidFillProperties();
        val color = new XDDFColorPreset(AvailableColors.get());

        fillProperties.setColor(color);

        setWidth(1.5);
        setFillProperties(fillProperties);
    }

}
