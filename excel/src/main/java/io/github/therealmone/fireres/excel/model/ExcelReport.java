package io.github.therealmone.fireres.excel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcelReport {

    private Integer time;
    private List<Column> columns = new ArrayList<>();

}
