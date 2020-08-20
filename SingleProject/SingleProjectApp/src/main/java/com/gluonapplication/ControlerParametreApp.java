package com.gluonapplication;

import java.awt.peer.LabelPeer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

public class ControlerParametreApp implements Initializable{

	 @FXML
	    private Label labelUserServeur;

	    @FXML
	    private Label labellEmail;

	    @FXML
	    private JFXTextField fieldEmail;

	    @FXML
	    private Label labelEmailPass;

	    @FXML
	    private JFXPasswordField fieldEmailPass;

	    @FXML
	    private Label labelServeurAddress;

	    @FXML
	    private JFXTextField fieldAddress;

	    @FXML
	    private Label labelServeurPort;

	    @FXML
	    private JFXTextField fieldPort;

	    @FXML
	    private JFXTextField fieldUser;

	    @FXML
	    private Label labelUSerPass;

	    @FXML
	    private JFXPasswordField fieldPass;

	    @FXML
	    private JFXButton bsubmit;
	    
	    @FXML
	    private AnchorPane pane;
	    
	    private SQLConnection connection ; 
	    
	    private ObservableList<Base_view_engin> listEngin ;
	    
	    private ObservableList<String> listComboEngin;
	    
	    ObservableList<String> saveFile ; 
	    ObservableList<String> loadFile ; 
	    
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
		
			
			bsubmit.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
				if(alerteMessage(Langage.confirmationUpdate())) {
					ok();
				}
		
			});
			initAll();
			
			fieldUser.visibleProperty().bind(CONFIG.isSuperAdmin.not());
			fieldEmail.visibleProperty().bind(CONFIG.isSuperAdmin.not());
			fieldEmailPass.visibleProperty().bind(CONFIG.isSuperAdmin.not());
			fieldUser.visibleProperty().bind(CONFIG.isSuperAdmin.not());
			labelUserServeur.visibleProperty().bind(CONFIG.isSuperAdmin.not());
			fieldPass.visibleProperty().bind(CONFIG.isSuperAdmin.not());
			labellEmail.visibleProperty().bind(CONFIG.isSuperAdmin.not());
			labelUSerPass.visibleProperty().bind(CONFIG.isSuperAdmin.not());
			labelEmailPass.visibleProperty().bind(CONFIG.isSuperAdmin.not());
			
		}
		
		
		public void cancel() {	
			AnchorPane p = (AnchorPane) pane.getParent();
			Region reg;
			try {
				reg = FXMLLoader.load(getClass().getResource("Login.fxml"));
				p.getChildren().clear();
				p.getChildren().add(reg);
				p.setVisible(true);
				reg.prefWidthProperty().bind(p.widthProperty());
				reg.prefHeightProperty().bind(p.heightProperty());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void ok() {
		
			/*AnchorPane p = (AnchorPane) pane.getParent();
			Region reg;
			try {
				reg = FXMLLoader.load(getClass().getResource("Login.fxml"));
				p.getChildren().clear();
				p.getChildren().add(reg);
				p.setVisible(true);
				reg.prefWidthProperty().bind(p.widthProperty());
				reg.prefHeightProperty().bind(p.heightProperty());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			save();
		}
		
		private void load() {
			try {
				SaveFile sf = new SaveFile();
				loadFile = sf.lectureFile2();
				
				for(int i = 0 ; i < loadFile.size() ; i++) {
					System.out.print("\n " + loadFile.get(i));
				}
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void save() {
			saveFile = FXCollections.observableArrayList(); 
			saveFile.add(fieldAddress.getText());
			saveFile.add(fieldPort.getText());
			saveFile.add(fieldUser.getText());
			saveFile.add(fieldPass.getText());
			saveFile.add(fieldEmail.getText());
			saveFile.add(fieldEmailPass.getText());
			
			try {
				SaveFile sf = new SaveFile();
				sf.ecritureFile2(saveFile);
				new messageBar(Langage.configEnregistrer(), 8, pane);
			} catch (IOException e) {
				new messageBar(e.getMessage(), 4, pane);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			CONFIG.init();
		}
			

		
		private void initAll() {
			load();
			if(!CONFIG.loadFailed) {
				fieldAddress.setText(CONFIG.load2.get(0));
				fieldPort.setText(CONFIG.load2.get(1));
				fieldUser.setText(CONFIG.load2.get(2));
				fieldPass.setText(CONFIG.load2.get(3));
				fieldEmail.setText(CONFIG.load2.get(4));
				fieldEmailPass.setText(CONFIG.load2.get(5));
			}
			else {
				fieldAddress.setText(loadFile.get(0));
				fieldPort.setText(loadFile.get(1));
				fieldUser.setText(loadFile.get(2));
				fieldPass.setText(loadFile.get(3));
				fieldEmail.setText(loadFile.get(4));
				fieldEmailPass.setText(loadFile.get(5));
			}
		}
		
		public boolean alerteMessage(String message) {
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
