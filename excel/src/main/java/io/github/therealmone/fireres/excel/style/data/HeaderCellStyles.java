package io.github.therealmone.fireres.excel.style.data;

import lombok.Getter;
import lombok.val;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import static io.github.therealmone.fireres.excel.ExcelReportConstructor.TIMES_NEW_ROMAN;

@Getter
public class HeaderCellStyles {

    private final CellStyle commonCellStyle;
    private final CellStyle lastCellStyle;

    public HeaderCellStyles(Workbook workbook) {
        this.commonCellStyle = createCommonCellStyle(workbook);
        this.lastCellStyle = createLastCellStyle(workbook);
    }

    private CellStyle createCommonCellStyle(Workbook workbook) {
        val style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(createFont(workbook));
        style.setWrapText(true);

        return style;
    }

    private CellStyle createLastCellStyle(Workbook workbook) {
        val style = createCommonCellStyle(workbook);
        style.setBorderRight(BorderStyle.MEDIUM);

        return style;
    }

    private Font createFont(Workbook workbook) {
        val font = workbook.createFont();
        font.setFontName(TIMES_NEW_ROMAN);
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);

        return font;
    }

}
