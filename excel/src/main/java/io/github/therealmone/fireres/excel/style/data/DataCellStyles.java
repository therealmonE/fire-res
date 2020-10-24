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
public class DataCellStyles {

    private final CellStyle commonCellStyle;
    private final CellStyle highlightedCellStyle;

    public DataCellStyles(Workbook workbook) {
        this.commonCellStyle = createCommonCellStyle(workbook);
        this.highlightedCellStyle = createHighlightedCellStyle(workbook);
    }

    private CellStyle createCommonCellStyle(Workbook workbook) {
        val style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(createFont(workbook, false));
        style.setWrapText(true);

        return style;
    }

    private CellStyle createHighlightedCellStyle(Workbook workbook) {
        val style = createCommonCellStyle(workbook);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setFont(createFont(workbook, true));

        return style;
    }

    private Font createFont(Workbook workbook, boolean bold) {
        val font = workbook.createFont();
        font.setFontName(TIMES_NEW_ROMAN);
        font.setFontHeightInPoints((short) 12);
        font.setBold(bold);

        return font;
    }
}
