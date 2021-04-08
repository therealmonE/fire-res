package io.github.therealmone.fireres.gui;

import com.beust.jcommander.JCommander;
import com.google.inject.Guice;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;
import java.util.concurrent.ExecutorService;

@Slf4j
public class Application extends javafx.application.Application {

    private static ProgramArgs PROGRAM_ARGS;
    private static ApplicationConfig CONFIG;

    private ExecutorService executorService;

    @Override
    public void start(Stage stage) {
        val injector = Guice.createInjector(new GuiModule(this, CONFIG));
        val gui = injector.getInstance(GraphicalInterface.class);
        this.executorService = injector.getInstance(ExecutorService.class);

        gui.start(stage);
    }

    public static void main(String[] args) {
        loadProgramArgs(args);
        loadApplicationConfig();
        launch(args);
    }

    private static void loadProgramArgs(String[] args) {
        log.info("Loading program arguments");

        val programArgs = new ProgramArgs();

        JCommander.newBuilder()
                .addObject(programArgs)
                .build()
                .parse(args);

        log.info("Program arguments: {}", programArgs);

        PROGRAM_ARGS = programArgs;
    }

    private static void loadApplicationConfig() {
        log.info("Loading application config by path: {}", PROGRAM_ARGS.getConfig());

        val config = ConfigFactory.parseFile(new File(PROGRAM_ARGS.getConfig()));
        val applicationConfig = ConfigBeanFactory.create(config, ApplicationConfig.class);

        log.info("Loaded application config: {}", applicationConfig);

        CONFIG = applicationConfig;
    }

    @Override
    public void stop() throws Exception {
        executorService.shutdownNow();
    }
}
