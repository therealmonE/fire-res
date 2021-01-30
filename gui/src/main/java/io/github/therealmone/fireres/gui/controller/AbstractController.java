package io.github.therealmone.fireres.gui.controller;

import com.google.inject.Inject;
import io.github.therealmone.fireres.gui.service.ElementStorageService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public abstract class AbstractController implements Initializable {

    @Inject
    private ElementStorageService elementStorageService;

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("Initializing: {}", this.getClass().getSimpleName());
        val fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(FXML.class)) {
                field.setAccessible(true);

                val fieldValue = field.get(this);
                val fieldId = field.getName();

                elementStorageService.addById(fieldId, fieldValue);
            }
        }

        initialize();
    }

    protected abstract void initialize();

}
