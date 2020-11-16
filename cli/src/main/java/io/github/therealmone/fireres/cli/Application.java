package io.github.therealmone.fireres.cli;

import com.google.inject.Inject;
import io.github.therealmone.fireres.core.common.config.GenerationProperties;
import io.github.therealmone.fireres.excel.ReportConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.nio.file.Path;

@Slf4j
public class Application {

    @Inject
    private ProgramArguments programArguments;

    @Inject
    private GenerationProperties generationProperties;

    @Inject
    private ReportConstructor reportConstructor;

    public void run() {
        val outputFile = Path.of(
                programArguments.getPath(),
                generationProperties.getGeneral().getFileName() + ".xlsx"
        ).toFile();

        reportConstructor.construct(outputFile);
    }
}
