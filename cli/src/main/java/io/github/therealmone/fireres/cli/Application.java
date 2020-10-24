package io.github.therealmone.fireres.cli;

import com.beust.jcommander.JCommander;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;
import io.github.therealmone.fireres.core.config.GenerationProperties;
import io.github.therealmone.fireres.excel.ExcelReportConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;

@Slf4j
public class Application {

    public static void main(String[] args) {
        val programArguments = loadProgramArguments(args);
        val generationProperties = loadGenerationProperties(programArguments.getConfig());
        val outputFile = new File(programArguments.getOutputFile());

        val excelReportConstructor = new ExcelReportConstructor(generationProperties);
        excelReportConstructor.construct(outputFile);
    }

    private static GenerationProperties loadGenerationProperties(String configPath) {
        log.info("Loading generation properties from file: {}", configPath);
        val config = ConfigFactory.parseFile(new File(configPath));
        val generationProperties = ConfigBeanFactory.create(config, GenerationProperties.class);

        log.info("Generation properties: {}", generationProperties);
        return generationProperties;
    }

    private static ProgramArguments loadProgramArguments(String[] args) {
        log.info("Loading program arguments");
        val programArguments = new ProgramArguments();

        JCommander.newBuilder()
                .addObject(programArguments)
                .build()
                .parse(args);

        log.info("Program arguments: {}", programArguments);
        return programArguments;
    }

}
