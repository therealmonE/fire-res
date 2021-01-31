package io.github.therealmone.fireres.gui.controller;

import io.github.therealmone.fireres.gui.annotation.ParentController;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BottomButtonBarController extends AbstractController {

    @ParentController
    private MainSceneController mainSceneController;

}
