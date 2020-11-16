package io.github.therealmone.fireres.excel;

import io.github.therealmone.fireres.core.common.config.GenerationProperties;
import io.github.therealmone.fireres.core.common.report.FullReport;
import io.github.therealmone.fireres.core.common.report.FullReportBuilder;
import io.github.therealmone.fireres.excel.sheet.ExcessPressureSheet;
import io.github.therealmone.fireres.excel.sheet.FireModeSheet;
import io.github.therealmone.fireres.excel.sheet.UnheatedSurfaceSheet;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

@RequiredArgsConstructor
@Slf4j
public class ExcelReportConstructor implements ReportConstructor {

    public static final String TIMES_NEW_ROMAN = "Times New Roman";

    private final GenerationProperties generationProperties;

    @Override
    @SneakyThrows
    public void construct(File outputFile) {
        log.info("Writing excel report to: {}", outputFile.getAbsolutePath());
        val report = new FullReportBuilder().build(generationProperties);

        try (val excel = generateExcel(report);
             val outputStream = new FileOutputStream(outputFile)) {
            excel.write(outputStream);
        }
    }

    private Workbook generateExcel(FullReport report) {
        val workbook = new XSSFWorkbook();

        createFireModeSheet(workbook, report);
        createExcessPressureSheet(workbook, report);
        createUnheatedSurfaceSheets(workbook, report);

        return workbook;
    }

    private void createFireModeSheet(XSSFWorkbook workbook, FullReport report) {
        val fireModeSheet = new FireModeSheet(
                report.getFireMode(),
                report.getTime(),
                report.getEnvironmentTemperature());

        fireModeSheet.create(workbook);
    }

    private void createExcessPressureSheet(XSSFWorkbook workbook, FullReport report) {
        val excessPressureSheet = new ExcessPressureSheet(
                report.getExcessPressure(),
                report.getTime(),
                generationProperties.getGeneral().getExcessPressure().getBasePressure());

        excessPressureSheet.create(workbook);
    }

    private void createUnheatedSurfaceSheets(XSSFWorkbook workbook, FullReport report) {
        val samples = report.getUnheatedSurface().getSamples();

        for (int i = 0; i < samples.size(); i++) {
            val unheatedSurfaceSheet = new UnheatedSurfaceSheet(report.getTime(), report.getUnheatedSurface(), i);

            unheatedSurfaceSheet.create(workbook);
        }
    }
}
