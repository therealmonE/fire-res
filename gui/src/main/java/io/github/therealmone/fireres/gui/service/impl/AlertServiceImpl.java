package io.github.therealmone.fireres.gui.service.impl;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.model.Logos;
import io.github.therealmone.fireres.gui.service.AlertService;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lombok.val;

public class AlertServiceImpl implements AlertService {

    public static final ButtonType OK = new ButtonType("ОК");

    @Inject
    private Logos logos;

    @Override
    public void showError(String text) {
        val alert = new Alert(Alert.AlertType.ERROR, text, OK);

        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.getDialogPane().getStylesheets().add("/css/style.css");
        alert.getDialogPane().getStyleClass().add("alert");
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(logos.getLogo512());

        alert.showAndWait();
    }

}
