package com.gluonapplication;

import java.net.URL;
import java.sql.SQLException;
import java.util.Observable;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

public class ControlerFiltre implements Initializable{

	@FXML
    private JFXComboBox<String> CEngin;
	
    public static StringProperty tengin = new SimpleStringProperty("");

    @FXML
    private JFXComboBox<String> CEquipement;

    public static StringProperty tequipement = new SimpleStringProperty("");

    
    @FXML
    private JFXComboBox<String> CReparer;

    public static StringProperty treparer = new SimpleStringProperty("");

    @FXML
    private JFXComboBox<String> CFermer;

    public static StringProperty tfermer = new SimpleStringProperty("");

    @FXML
    private JFXComboBox<String> CCreateur;

    public static StringProperty tcreateur = new SimpleStringProperty("");

    @FXML
    private JFXComboBox<String> CModificateur;

    public static StringProperty tmodificateur = new SimpleStringProperty("");

    @FXML
    private JFXDatePicker DateModification;

    @FXML

    public static StringProperty tDateModification = new SimpleStringProperty("");

    @FXML
    private JFXCheckBox checkClosed;
    
    public static BooleanProperty showClosed = new SimpleBooleanProperty(false);

    @FXML
    private JFXButton bCancel;

    @FXML
    private JFXButton bOk;

   
    public static StringProperty tDateCreation = new SimpleStringProperty("");


    @FXML
    private JFXDatePicker DateCreation;

    SQLConnection connection ; 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		bOk.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			set();
		});
		init();
	}
	
	private void init() {
		ObservableList<String> listeEngin = FXCollections.observableArrayList();
		ObservableList<String> listeEquipement = FXCollections.observableArrayList();
		ObservableList<String> listeUser = FXCollections.observableArrayList();

		ObservableList<String> listeCocher = FXCollections.observableArrayList();
		listeCocher.add("true");
		listeCocher.add("false");

		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeEngin = connection.select_distinct_engin();
			listeEquipement = connection.select_distinct_equipement();
			listeUser = connection.select_distinct_user();	
			connection.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CEngin.setItems(listeEngin);
		CEquipement.setItems(listeEquipement);
		CCreateur.setItems(listeUser) ; 
		CModificateur.setItems(listeUser);
		CFermer.setItems(listeCocher);
	CReparer.setItems(listeCocher);
		
		CEngin.setValue("");
		CEquipement.setValue("");
		CCreateur.setValue(""); 
		CModificateur.setValue("");
		CFermer.setValue("");
		CReparer.setValue("");
		
		CEngin.setPromptText(Langage.tCREnginLabelEngin());
		CEquipement.setPromptText(Langage.tableEquipement());
		CReparer.setPromptText(Langage.tableReparer());
		CFermer.setPromptText(Langage.tableFermer());
		CCreateur.setPromptText(Langage.createur());
		CModificateur.setPromptText(Langage.modificateur());
		
		DateModification.setPromptText(Langage.tableDateModification());
		DateCreation.setPromptText(Langage.tableDateCreation());
		
		checkClosed.setText(Langage.tableFermer());
	
	}
	
	public static  void reset() {
	    tengin = new SimpleStringProperty("");
	    tequipement = new SimpleStringProperty("");
	    treparer = new SimpleStringProperty("");
	    tfermer = new SimpleStringProperty("");
	    tcreateur = new SimpleStringProperty("");
	    tmodificateur = new SimpleStringProperty("");
	    tDateModification = new SimpleStringProperty("");
	    tDateCreation = new SimpleStringProperty("");
	    showClosed = new SimpleBooleanProperty(false);
	}
	
	private  void set() {
	    tengin.set(CEngin.getSelectionModel().getSelectedItem());
	    tequipement.set(CEquipement.getSelectionModel().getSelectedItem());;
	    treparer.set(CReparer.getSelectionModel().getSelectedItem());;
	    tfermer.set(CFermer.getSelectionModel().getSelectedItem());;
	    tcreateur.set(CCreateur.getSelectionModel().getSelectedItem());;
	    tmodificateur.set(CModificateur.getSelectionModel().getSelectedItem());
	    if(DateModification.getValue() != null)
	    	tDateModification.set(DateModification.getValue().toString());
	    else 
	    	tDateModification.set("");
	    
	    if(DateCreation.getValue() != null)
	    	tDateCreation.set(DateCreation.getValue().toString());
	    else 
	    	tDateCreation.set("");
	    showClosed.set(checkClosed.isSelected());
	}
	
	
}
