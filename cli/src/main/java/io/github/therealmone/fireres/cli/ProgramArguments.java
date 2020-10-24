package io.github.therealmone.fireres.cli;

import com.beust.jcommander.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramArguments {

    @Parameter(names = {"-c", "--config"}, description = "Config path")
    private String config;

    @Parameter(names = {"-o", "--output"}, description = "Output file")
    private String outputFile;

}
