package io.github.therealmone.fireres.gui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationConfig {

    private String defaultPresetsPath;
    private String customPresetsPath;
    private Integer threadsCount;

}
