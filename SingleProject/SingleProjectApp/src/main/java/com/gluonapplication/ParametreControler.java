package com.gluonapplication;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.FieldPosition;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
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

public class ParametreControler  implements Initializable {

    @FXML
    private JFXComboBox<String> comboEngin;
    
    @FXML
    private JFXComboBox<String> comboLangue;

    @FXML
    private Label labelEngin;
   
    @FXML
    private Label labellLangue;
    
    @FXML
    private JFXButton bsubmit;

    
    @FXML
    private AnchorPane pane;
    
    @FXML
    private JFXCheckBox checkpass;
    
    private SQLConnection connection ; 
    

    @FXML
    private JFXButton bParam2;

    
    private ObservableList<Base_view_engin> listEngin ;
    
    private ObservableList<String> listComboEngin;
    
    ObservableList<String> saveFile ; 
    ObservableList<String> loadFile ; 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		bParam2.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			config2();
		});
		
		bsubmit.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			if(alerteMessage(Langage.confirmationUpdate())) {
				ok();
			}

		});
		
		if(CONFIG.user.type.getValue().equals("ADMIN")) {
			bParam2.setVisible(true);
		}
		else {
			bParam2.setVisible(false);
		}
		testCheck();
		initAll();
	}
	
	public void testCheck() {
		if(LoginControler.etat) {
			checkpass.setSelected(true);
		}
	}
	public void config2() {
		AnchorPane p = (AnchorPane) pane.getParent();
		Region reg;
		try {
			reg = FXMLLoader.load(getClass().getResource("/ParametreApp.fxml"));
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
	
	public void cancel() {	
		AnchorPane p = (AnchorPane) pane.getParent();
		Region reg;
		try {
			reg = FXMLLoader.load(getClass().getResource("/Login.fxml"));
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
			loadFile = sf.lecture();
			
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
		saveFile.add(comboEngin.getSelectionModel().getSelectedItem());
		saveFile.add(comboLangue.getSelectionModel().getSelectedItem());
		if(checkpass.isSelected()) {
			saveFile.add("1");
		}
		else {
			saveFile.add("0");
		}
		
		try {
			SaveFile sf = new SaveFile();
			sf.ecriture(saveFile);
			new messageBar(Langage.configEnregistrer(), 8, pane);
		} catch (IOException e) {
			new messageBar(e.getMessage(), 4, pane);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CONFIG.init();
	}
		
	private void initEngin() {
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listEngin = connection.select_engin();
		connection.close();
		listComboEngin = FXCollections.observableArrayList();
		for(int i = 0 ; i < listEngin.size();i++) {
			listComboEngin.add(listEngin.get(i).libelle.getValue().getText());
		}
		comboEngin.setItems(listComboEngin);
		
		if(!CONFIG.loadFailed)
			comboEngin.setValue(loadFile.get(0));
	}
	
	
	private void initLangue() {
		ObservableList<String> liste = FXCollections.observableArrayList();
		liste.add("Francais");
		liste.add("English");
		liste.add("Malagasy");
		comboLangue.setItems(liste);
		if(!CONFIG.loadFailed) {
			comboLangue.setValue(loadFile.get(1));
			System.out.println("/n COMMENT ? "+loadFile.get(1));
		}			
		else {
			comboLangue.setValue(CONFIG.load.get(1));
		}
		
		//SuperAdminMictsl2020!
	}
	
	private void initCheckBox() {
		if(!CONFIG.loadFailed)
			if(loadFile.get(2).equals("1")) {
				checkpass.setSelected(true);
			}
			else {
				checkpass.setSelected(false);
			}
		else 	
			if(CONFIG.load.get(2).equals("1")) {
				checkpass.setSelected(true);
			}
			else {
				checkpass.setSelected(false);
			}
	}
	
	private void initAll() {
		load();
		if(CONFIG.isSuperAdmin.getValue()) {
			this.comboEngin.setDisable(true);
		}
		else
			initEngin();
		
		initLangue();
		initCheckBox();
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
