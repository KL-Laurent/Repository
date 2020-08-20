package com.gluonapplication;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ControlerDrawerAccueil implements Initializable{


    @FXML
    private Label labelUser;
    
    @FXML
    private JFXButton bConfig;

    @FXML
    private JFXButton bTypeEngin;

    @FXML
    private JFXButton bFicheVerification;

    @FXML
    private JFXButton bUser;

    @FXML
    private JFXButton bPanne;

    @FXML
    private JFXButton bEngin;
    
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
		labelUser.setText(CONFIG.affichageUser.getValue());
		
		labelUser.textProperty().bind(CONFIG.affichageUser);
		
		bConfig.disableProperty().bind(CONFIG.disableConfiguration);

		bTypeEngin.disableProperty().bind(CONFIG.disableTypeEngin);
		
		bFicheVerification.disableProperty().bind(CONFIG.disableFicheVerification);
		
		bUser.disableProperty().bind(CONFIG.disableUser);
		
		bPanne.disableProperty().bind(CONFIG.disablePanne);
		
		bEngin.disableProperty().bind(CONFIG.disableEngin);
		
		bConfig.setText(Langage.drawerAccueilConfiguration());

		bTypeEngin.setText(Langage.drawerAccueiBTypeEngin());
		
		bFicheVerification.setText(Langage.drawerAccueilBficheVerification());
		
		bUser.setText(Langage.drawerAccueiBUser());
		
		bPanne.setText(Langage.drawerAccueilBPannes());;
		
		bEngin.setText(Langage.drawerAccueiBEngin());
		
	}

}
