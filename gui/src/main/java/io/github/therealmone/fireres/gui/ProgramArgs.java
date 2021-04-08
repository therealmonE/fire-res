package io.github.therealmone.fireres.gui;

import com.beust.jcommander.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramArgs {

    @Parameter(names = {"-c", "--config"}, description = "Config path")
    private String config;

}
