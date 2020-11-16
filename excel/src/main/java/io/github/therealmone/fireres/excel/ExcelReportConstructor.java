package io.github.therealmone.fireres.excel;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.common.config.GenerationProperties;
import io.github.therealmone.fireres.core.common.report.FullReport;
import io.github.therealmone.fireres.excel.sheet.ExcelSheet;
import io.github.therealmone.fireres.excel.sheet.ExcessPressureSheet;
import io.github.therealmone.fireres.excel.sheet.FireModeSheet;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

@Slf4j
public class ExcelReportConstructor implements ReportConstructor {

    public static final String TIMES_NEW_ROMAN = "Times New Roman";

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private FullReport report;

    @Override
    @SneakyThrows
    public void construct(File outputFile) {
        log.info("Writing excel report to: {}", outputFile.getAbsolutePath());

        try (val excel = generateExcel(report);
             val outputStream = new FileOutputStream(outputFile)) {
            excel.write(outputStream);
        }
    }

    private Workbook generateExcel(FullReport report) {
        val workbook = new XSSFWorkbook();

        val fireModeSheet = new FireModeSheet(
                report.getFireMode(),
                report.getTime(),
                report.getEnvironmentTemperature());

        val excessPressureSheet = new ExcessPressureSheet(
                report.getExcessPressure(),
                report.getTime(),
                generationProperties.getGeneral().getExcessPressure().getBasePressure());

        createSheets(workbook,
                fireModeSheet,
                excessPressureSheet);

        return workbook;
    }

    private void createSheets(XSSFWorkbook workbook, ExcelSheet... sheets) {
        for (ExcelSheet sheet : sheets) {
            sheet.create(workbook);
        }
    }
}
