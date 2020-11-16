package io.github.therealmone.fireres.cli;

import com.beust.jcommander.JCommander;
import com.google.inject.AbstractModule;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;
import io.github.therealmone.fireres.core.common.config.GenerationProperties;
import io.github.therealmone.fireres.excel.ExcelModule;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;

@Slf4j
public class CliModule extends AbstractModule {

    private final ProgramArguments programArguments;
    private final GenerationProperties generationProperties;

    public CliModule(String[] args) {
        this.programArguments = loadProgramArguments(args);
        this.generationProperties = loadGenerationProperties(programArguments.getConfig());
    }

    @Override
    protected void configure() {
        install(new ExcelModule(generationProperties));

        bind(ProgramArguments.class).toInstance(programArguments);
        bind(GenerationProperties.class).toInstance(generationProperties);
        bind(Application.class);
    }

    private GenerationProperties loadGenerationProperties(String configPath) {
        log.info("Loading generation properties from file: {}", configPath);
        val config = ConfigFactory.parseFile(new File(configPath));
        val generationProperties = ConfigBeanFactory.create(config, GenerationProperties.class);

        log.info("Generation properties: {}", generationProperties);
        return generationProperties;
    }

    private ProgramArguments loadProgramArguments(String[] args) {
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
