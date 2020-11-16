package io.github.therealmone.fireres.excel.sheet;

import io.github.therealmone.fireres.core.unheated.report.UnheatedSurfaceReport;
import io.github.therealmone.fireres.excel.model.ExcelReport;
import io.github.therealmone.fireres.excel.model.unheated.UnheatedSurfaceExcelReport;
import lombok.val;

import java.util.List;

public class UnheatedSurfaceSheet extends AbstractExcelSheet<UnheatedSurfaceReport> {

    private static final String SHEET_NAME = "Обр.%d - Необогреваемая поверхность";

    private final Integer time;
    private final Integer sampleIndex;

    public UnheatedSurfaceSheet(Integer time, UnheatedSurfaceReport report, Integer sampleIndex) {
        super(String.format(SHEET_NAME, sampleIndex + 1), time, report);
        this.sampleIndex = sampleIndex;
        this.time = time;
    }

    @Override
    protected List<ExcelReport> createExcelReports(UnheatedSurfaceReport report) {
        val sample = report.getSamples().get(sampleIndex);

        return List.of(
                new UnheatedSurfaceExcelReport(time, sample.getFirstGroup(), 0),

                new UnheatedSurfaceExcelReport(time, sample.getSecondGroup(),
                        sample.getFirstGroup().getThermocoupleTemperatures().size()),

                new UnheatedSurfaceExcelReport(time, sample.getThirdGroup(),
                        sample.getFirstGroup().getThermocoupleTemperatures().size() +
                        sample.getSecondGroup().getThermocoupleTemperatures().size())
        );
    }

}
