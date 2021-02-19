package io.github.therealmone.fireres.gui;

import com.google.inject.Guice;
import javafx.stage.Stage;
import lombok.val;

import java.util.concurrent.ExecutorService;

public class Application extends javafx.application.Application {

    private ExecutorService executorService;

    @Override
    public void start(Stage stage) {
        val injector = Guice.createInjector(new GuiModule(this));
        val gui = injector.getInstance(GraphicalInterface.class);
        this.executorService = injector.getInstance(ExecutorService.class);

        gui.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        executorService.shutdownNow();
    }
}
