package io.github.therealmone.fireres.excel.report;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.annotation.Time;
import io.github.therealmone.fireres.excel.chart.UnheatedSurfaceChart;
import io.github.therealmone.fireres.excel.column.Column;
import io.github.therealmone.fireres.excel.column.TimeColumn;
import io.github.therealmone.fireres.excel.column.unheated.surface.UnheatedSurfaceMeanBoundColumn;
import io.github.therealmone.fireres.excel.column.unheated.surface.UnheatedSurfaceMeanColumn;
import io.github.therealmone.fireres.excel.column.unheated.surface.UnheatedSurfaceThermocoupleBoundColumn;
import io.github.therealmone.fireres.excel.column.unheated.surface.UnheatedSurfaceThermocoupleColumn;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceGroup;
import io.github.therealmone.fireres.unheated.surface.model.UnheatedSurfaceSample;
import io.github.therealmone.fireres.unheated.surface.report.UnheatedSurfaceReport;
import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnheatedSurfaceExcelReportsProvider implements GroupedExcelReportsProvider {

    @Inject
    private UnheatedSurfaceReport report;

    @Inject
    @Time
    private Integer time;

    @Override
    public Map<Integer, List<ExcelReport>> get() {
        val groupedReports = new HashMap<Integer, List<ExcelReport>>();

        for (int i = 0; i < report.getSamples().size(); i++) {
            val sample = report.getSamples().get(i);
            val reports = createReports(sample);

            groupedReports.put(i, reports);
        }

        return groupedReports;
    }

    private List<ExcelReport> createReports(UnheatedSurfaceSample sample) {
        return List.of(
                createReportForGroup(1, sample.getFirstGroup(), 0),

                createReportForGroup(2, sample.getSecondGroup(),
                        sample.getFirstGroup().getThermocoupleTemperatures().size()),

                createReportForGroup(3, sample.getThirdGroup(),
                        sample.getFirstGroup().getThermocoupleTemperatures().size() +
                        sample.getSecondGroup().getThermocoupleTemperatures().size())
        );
    }

    private ExcelReport createReportForGroup(Integer groupIndex, UnheatedSurfaceGroup group,
                                             Integer thermocoupleIndexShift) {
        val columns = new ArrayList<Column>();

        columns.add(new TimeColumn(time));

        for (int i = 0; i < group.getThermocoupleTemperatures().size(); i++) {
            val thermocouple = group.getThermocoupleTemperatures().get(i);
            columns.add(new UnheatedSurfaceThermocoupleColumn(thermocoupleIndexShift + i, thermocouple));
        }

        columns.add(new UnheatedSurfaceMeanColumn(group.getMeanTemperature()));

        if (group.getMeanBound() != null) {
            columns.add(new UnheatedSurfaceMeanBoundColumn(group.getMeanBound()));
        }

        columns.add(new UnheatedSurfaceThermocoupleBoundColumn(group.getThermocoupleBound()));

        return ExcelReport.builder()
                .data(columns)
                .chart(new UnheatedSurfaceChart(time, columns, groupIndex))
                .build();
    }
}
