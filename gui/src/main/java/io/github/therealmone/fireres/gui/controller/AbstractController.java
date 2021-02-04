package io.github.therealmone.fireres.gui.controller;

import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ChildControllers;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import javafx.scene.control.Spinner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    protected List<Object> getAllElementsById(String id) {
        val root = lookUpRootController(this);

        return lookUpElements(root, id);
    }

    protected Optional<Object> getFirstElementById(String id) {
        val root = lookUpRootController(this);

        val elements = lookUpElements(root, id);

        if (!elements.isEmpty()) {
            return Optional.of(elements.get(0));
        } else {
            return Optional.empty();
        }
    }

    @SneakyThrows
    private List<Object> lookUpElements(Controller controller, String id) {
        val elements = new ArrayList<>();
        val fields = controller.getClass().getDeclaredFields();

        val elementField = getFieldById(fields, id);

        if (elementField.isPresent()) {
            elementField.get().setAccessible(true);
            elements.add(elementField.get().get(controller));
        }

        elements.addAll(lookUpElementInChildControllers(controller, id));

        return elements;
    }

    private List<Object> lookUpElementInChildControllers(Controller controller, String id) {
        return getChildControllers(controller).stream()
                .flatMap(childController -> lookUpElements(childController, id).stream())
                .collect(Collectors.toList());
    }

    @SneakyThrows
    protected List<Controller> getChildControllers(Controller controller) {
        val fields = controller.getClass().getDeclaredFields();

        return Arrays.stream(fields)
                .filter(this::isControllerField)
                .flatMap(field -> {
                    field.setAccessible(true);

                    if (field.isAnnotationPresent(ChildController.class)) {
                        return Stream.of((Controller) getFieldValue(field, controller));
                    } else {
                        //noinspection unchecked
                        return ((List<Controller>) getFieldValue(field, controller)).stream();
                    }
                }).collect(Collectors.toList());
    }

    private boolean isControllerField(Field field) {
        return field.isAnnotationPresent(ChildController.class)
                || field.isAnnotationPresent(ChildControllers.class);
    }

    private Optional<Field> getFieldById(Field[] fields, String id) {
        for (Field field : fields) {
            if (field.getName().equals(id)) {
                return Optional.of(field);
            }
        }

        return Optional.empty();
    }

    @SneakyThrows
    private Controller lookUpRootController(Controller controller) {
        val fields = controller.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(ParentController.class)) {
                field.setAccessible(true);

                val parent = (Controller) field.get(controller);
                return lookUpRootController(parent);
            }
        }

        return controller;
    }

    @SneakyThrows
    private Object getFieldValue(Field field, Object source) {
        return field.get(source);
    }

    protected void handleSpinnerLostFocus(Boolean focusValue, Spinner<?> spinner, Runnable action) {
        if (!focusValue) {
            log.info("Spinner {} lost focus", spinner.getId());

            commitSpinner(spinner);
            action.run();
        }
    }

}
