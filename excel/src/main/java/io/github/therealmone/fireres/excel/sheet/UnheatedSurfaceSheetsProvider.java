package io.github.therealmone.fireres.excel.sheet;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.annotation.Time;
import io.github.therealmone.fireres.excel.annotation.UnheatedSurface;
import io.github.therealmone.fireres.excel.report.ExcelReport;
import lombok.val;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UnheatedSurfaceSheetsProvider implements ExcelSheetsProvider {

    private static final String SHEET_NAME = "Необогреваемая пов-сть, Обр. %s";

    @Inject
    @Time
    private Integer time;

    @Inject
    @UnheatedSurface
    private Map<Integer, List<ExcelReport>> groupedReports;

    @Override
    public List<ExcelSheet> get() {
        return groupedReports.entrySet().stream()
                .map(entry -> {
                    val index = entry.getKey();
                    val reports = entry.getValue();

                    return new AbstractExcelSheet() {
                        @Override
                        protected Integer getTime() {
                            return time;
                        }

                        @Override
                        protected List<ExcelReport> getReports() {
                            return reports;
                        }

                        @Override
                        protected String getSheetName() {
                            return String.format(SHEET_NAME, index + 1);
                        }
                    };
                })
                .collect(Collectors.toList());
    }
}
