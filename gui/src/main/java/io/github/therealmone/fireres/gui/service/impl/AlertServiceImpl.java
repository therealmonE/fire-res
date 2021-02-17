package io.github.therealmone.fireres.gui.service.impl;

import io.github.therealmone.fireres.gui.service.AlertService;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import lombok.val;

public class AlertServiceImpl implements AlertService {

    public static final ButtonType OK = new ButtonType("ОК", ButtonBar.ButtonData.OK_DONE);

    @Override
    public void showError(String text) {
        val alert = new Alert(Alert.AlertType.ERROR, text, OK);

        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.getDialogPane().getStylesheets().add("/css/style.css");
        alert.getDialogPane().getStyleClass().add("alert");

        alert.showAndWait();
    }

}
