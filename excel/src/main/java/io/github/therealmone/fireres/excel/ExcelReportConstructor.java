package io.github.therealmone.fireres.excel;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.config.GeneralProperties;
import io.github.therealmone.fireres.core.model.Sample;
import io.github.therealmone.fireres.excel.sheet.ExcelSheetsBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@SuppressWarnings("rawtypes")
@Slf4j
public class ExcelReportConstructor implements ReportConstructor {

    public static final String TIMES_NEW_ROMAN = "Times New Roman";

    @Inject
    private List<ExcelSheetsBuilder> sheetsBuilders;

    @Override
    @SneakyThrows
    public void construct(GeneralProperties generalProperties, List<Sample> samples, File outputFile) {
        log.info("Writing excel report to: {}", outputFile.getAbsolutePath());

        try (val excel = generateExcel(generalProperties, samples);
             val outputStream = new FileOutputStream(outputFile)) {
            excel.write(outputStream);
        }
    }

    private Workbook generateExcel(GeneralProperties generalProperties, List<Sample> samples) {
        val workbook = new XSSFWorkbook();

        sheetsBuilders.forEach(builder -> {
            val sheets = builder.build(generalProperties, samples);

            sheets.forEach(sheet -> sheet.create(workbook));
        });

        return workbook;
    }

}
