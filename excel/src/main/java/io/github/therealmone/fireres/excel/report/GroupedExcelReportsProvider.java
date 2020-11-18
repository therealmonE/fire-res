package io.github.therealmone.fireres.excel.report;

import com.google.inject.Provider;

import java.util.List;
import java.util.Map;

public interface GroupedExcelReportsProvider extends Provider<Map<Integer, List<ExcelReport>>> {
}
