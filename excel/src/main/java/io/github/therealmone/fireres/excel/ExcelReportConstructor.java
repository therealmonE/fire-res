package io.github.therealmone.fireres.excel;

import com.google.inject.Inject;
import io.github.therealmone.fireres.excel.annotation.ExcessPressure;
import io.github.therealmone.fireres.excel.annotation.FireMode;
import io.github.therealmone.fireres.excel.annotation.UnheatedSurface;
import io.github.therealmone.fireres.excel.sheet.ExcelSheet;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Slf4j
public class ExcelReportConstructor implements ReportConstructor {

    public static final String TIMES_NEW_ROMAN = "Times New Roman";

    @Inject
    @FireMode
    private List<ExcelSheet> fireModeSheets;

    @Inject
    @ExcessPressure
    private List<ExcelSheet> excessPressureSheets;

    @Inject
    @UnheatedSurface
    private List<ExcelSheet> unheatedSurfaceSheets;

    @Override
    @SneakyThrows
    public void construct(File outputFile) {
        log.info("Writing excel report to: {}", outputFile.getAbsolutePath());

        try (val excel = generateExcel();
             val outputStream = new FileOutputStream(outputFile)) {
            excel.write(outputStream);
        }
    }

    private Workbook generateExcel() {
        val workbook = new XSSFWorkbook();

        fireModeSheets.forEach(sheet -> sheet.create(workbook));
        excessPressureSheets.forEach(sheet -> sheet.create(workbook));
        unheatedSurfaceSheets.forEach(sheet -> sheet.create(workbook));

        return workbook;
    }
}
