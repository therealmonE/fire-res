package io.github.therealmone.fireres.gui;

import com.google.inject.Guice;
import javafx.stage.Stage;
import lombok.val;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        val injector = Guice.createInjector(new GuiModule(this));
        val gui = injector.getInstance(GraphicalInterface.class);

        gui.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
