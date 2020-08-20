package com.gluonapplication;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.crypto.AEADBadTagException;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginControler implements Initializable {

    @FXML
    private AnchorPane paneLogin;

    @FXML
    private JFXButton bLogin;

    @FXML
    private JFXPasswordField passField;

    @FXML
    private JFXTextField userField;

    private SQLConnection connection ; 
	
    private static BooleanProperty passDesactiver;
    public static boolean etat;
    
    static {
    	passDesactiver = new SimpleBooleanProperty();
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	bLogin.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
    		// SUPER ADMIN POUR LA CONFIGURATION HORS RESEAUX 
    		CONFIG.isExitVisible.set(true);
    		
        	if(userField.getText().trim().equals("SuperAdmin"))
        	{
    			try {	
    				CONFIG.isSuperAdmin.set(true);
    				
    				Region reg1 = FXMLLoader.load(getClass().getResource("/ParametreApp.fxml"));
    				AnchorPane pane = (AnchorPane) paneLogin.getParent();
    				
    				pane.getChildren().clear();
    				pane.getChildren().add(reg1);
    				
    				reg1.prefWidthProperty().bind(paneLogin.widthProperty());
    				reg1.prefHeightProperty().bind(paneLogin.heightProperty());
    			} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    			}
        	}
        	else {
   			 verification();

        	}
        	
		});
    	
    	try {
			SaveFile f = new SaveFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	if(CONFIG.load.get(2).equals("1")) {
			passDesactiver.set(true);
		}
    	else {
    		passDesactiver.set(false);
    	}
    	
    	this.passField.disableProperty().bind(passDesactiver);
    	
    	passField.setPromptText(Langage.loginPass());
    	userField.setPromptText(Langage.loginUser());
    	bLogin.setText(Langage.loginOK());
    	
	}
    
  
    public void verification() {
    	
		SQLConnection connection;
    	Base_view_user user = null;
    	boolean echec = false ; 
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			System.out.println(CONFIG.LOCAL_ADDRESS);
	    	if(passDesactiver.getValue()) {
	    		user = connection.select_user(userField.getText().trim());
	    	}
	    	else {
	        	user = connection.select_user(userField.getText().trim(),passField.getText().trim());
	    	}
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			echec = true ; 
			if(e.getMessage().contains("Échec de la connexion")) {
				new messageBar(Langage.errorConnection(), 4, paneLogin);
			}
			else {
				new messageBar(e.getMessage(), 4, paneLogin);
			}
			e.printStackTrace();
		}

             
		
		if((user == null )&&(echec== false)) {
			if(!passDesactiver.getValue())
				messageBar(Langage.erroLogin(), 8);
			else
				messageBar(Langage.erroLogin2(), 8);

		}
		else {
			CONFIG.user =  user ;
			CONFIG.affichageUser.set(user.id.getValue());
			CONFIG.checkUser();
			try {	
				Region reg1 = FXMLLoader.load(getClass().getResource(selectFiche()));
				AnchorPane pane = (AnchorPane) paneLogin.getParent();
				
				pane.getChildren().clear();
				pane.getChildren().add(reg1);
				
				reg1.prefWidthProperty()
				.bind(pane.widthProperty());
				reg1.prefHeightProperty().bind(pane.heightProperty());
			} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			}
	    	HeadControler.isDrawerDisable.set(false);
		}
    }
    
    private String selectFiche() {
    	switch(CONFIG.user.type.getValue()){
    		case "ADMIN":
    			return "/TCRListePanne.fxml" ; 
    		case "TECHNICIEN_ADMIN":
    			return "/TCRListePanne.fxml" ; 
			case "MANAGEUR":
				return "/TCRListePanne.fxml" ; 
			case "OPERATEUR":
				return "/OPFicheVerification.fxml" ; 
			case "TECHNICIEN":
				return "/TCRListePanne.fxml" ; 
			case "TECHNICIEN_CR":
				return "/TCRListePanne.fxml" ; 
		}
    	return "/Login.fxml";
    }
    
	public void messageBar(String message,int duration ) {
		JFXSnackbar bar = new JFXSnackbar(paneLogin);
		final double MAX_FONT_SIZE = 20.0; // define max font size
		Labeled label = new Label();
		label.setFont(new Font(MAX_FONT_SIZE));
		label.setText(message);
		label.setStyle("-fx-background-color :#00000099;-fx-text-fill : #ffffff;-fx-background-radius : 0em;");
		bar.enqueue(new SnackbarEvent(label,Duration.seconds(duration),null));
	}
	
	public boolean alerte(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Warning message");
		alert.setHeaderText("Warning ! ");
		alert.setContentText(message);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    return true ;
		} else {
		    return false;
		}
	}
}
