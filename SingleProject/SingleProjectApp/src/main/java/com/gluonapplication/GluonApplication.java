package com.gluonapplication;

import java.io.IOException;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GluonApplication extends MobileApplication {

    @Override
    public void init() throws IOException {
        addViewFactory(HOME_VIEW, BasicView::new);
    }

    
    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);
        ((Stage) scene.getWindow()).getIcons().add(new Image(GluonApplication.class.getResourceAsStream("/icon.png")));
    }
}
