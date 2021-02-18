package io.github.therealmone.fireres.gui.controller;

import javafx.scene.control.Spinner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public abstract class AbstractController implements Controller {

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("Initializing: {}", this.getClass().getSimpleName());
        initialize();
    }

    protected void initialize() {
        //do nothing by default
    }

    /**
     * c&p from Spinner
     * https://stackoverflow.com/questions/32340476/manually-typing-in-text-in-javafx-spinner-is-not-updating-the-value-unless-user/32349847
     */
    protected <T> void commitSpinner(Spinner<T> spinner) {
        log.info("Commit {} changes", spinner.getId());
        if (!spinner.isEditable()) {
            return;
        }

        val text = spinner.getEditor().getText();
        val valueFactory = spinner.getValueFactory();

        if (valueFactory != null) {
            val converter = valueFactory.getConverter();

            if (converter != null) {
                val value = converter.fromString(text);
                valueFactory.setValue(value);
            }
        }
    }

    protected void handleSpinnerLostFocus(Boolean focusValue, Spinner<?> spinner, Runnable action) {
        if (!focusValue) {
            log.info("Spinner {} lost focus, new value: {}", spinner.getId(), spinner.getValue());

            commitSpinner(spinner);
            action.run();
        }
    }

}
