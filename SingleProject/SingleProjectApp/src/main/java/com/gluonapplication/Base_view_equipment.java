package com.gluonapplication;

import java.sql.SQLException;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

public class Base_view_equipment extends RecursiveTreeObject<Base_view_equipment>{
	public int gkey;
	public ObjectProperty<JFXTextField> libelle ; 
	public ObjectProperty<JFXCheckBox> critique ; 
	public String type ; 
	public StringProperty dateCreation ; 
	public StringProperty dateModification ; 
	public int createur ; 
	public int modificateur ; 
	public int gkey_engin;
	public int gkey_responsable;

	public ObjectProperty<JFXComboBox<String>> responsable ; 
	
	public String nomResponsable ; 

	public boolean isNew;
	public boolean isEdited;

	public Base_view_contenu tempContenu ; 
	private  ObservableList<Base_view_user> listeUser;
	
	public Base_view_equipment(
			int gkey,
			String libelle ,
			boolean critique,
			String type,
			String dateCreation,
			String dateModification ,
			int createur , 
			int modificateur , 
			int gkey_engin ,
			int gkey_responsable,
			String nomResponsable
			) {
		JFXCheckBox checkBox = new JFXCheckBox();
		checkBox.setSelected(critique);
		checkBox.setDisable(true);

		
		
		JFXTextField field = new JFXTextField();
		field.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		field.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore2));
		field.setStyle("-fx-text-fill : "+GlobalColor.coloreText+" ;"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		field.setText(libelle);
		field.setEditable(false);
		
		this.gkey = gkey ;
		this.libelle = new SimpleObjectProperty<JFXTextField>(field);
		this.critique =  new SimpleObjectProperty<JFXCheckBox>(checkBox);
		this.type = type;
		this.dateCreation = new SimpleStringProperty(dateCreation);
		this.dateModification = new SimpleStringProperty(dateModification);
		this.createur = createur ; 
		this.modificateur = modificateur ; 
		this.gkey_engin = gkey_engin ; 
		this.gkey_responsable = gkey_responsable;
		this.isNew = false; 
		this.nomResponsable = nomResponsable ; 
		
		tempContenu = new Base_view_contenu();
		tempContenu.gkey_equipement = new  SimpleIntegerProperty(this.gkey);
		initComboBox(this.gkey_responsable);
		
	}
	
	public Base_view_equipment() {
		JFXTextField field = new JFXTextField();
		JFXCheckBox checkBox = new JFXCheckBox();
		field.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		field.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore2));
		field.setStyle("-fx-text-fill : "+GlobalColor.coloreText+";"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		field.setText("Libelle");
		
		this.libelle = new SimpleObjectProperty<JFXTextField>(field);
		this.critique =  new SimpleObjectProperty<JFXCheckBox>(checkBox);
		this.isNew = true ; 
		tempContenu = new Base_view_contenu();
		this.isNew = true ;
		initComboBox();

	}
	
	public Base_view_equipment(int gkey, int gkey_engin,String type) {
		JFXTextField field = new JFXTextField();
		JFXCheckBox checkBox = new JFXCheckBox();
		field.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		field.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore2));
		field.setStyle("-fx-text-fill : "+GlobalColor.coloreText+" ;"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		field.setPromptText("Libelle");
		libelle = new SimpleObjectProperty<JFXTextField>(field);
		critique =  new SimpleObjectProperty<JFXCheckBox>(checkBox);
		
		this.gkey_engin = gkey_engin;
		this.gkey = gkey ; 
		this.type = type;
		this.isNew = true; 
		
		tempContenu = new Base_view_contenu();
		tempContenu.gkey_equipement = new SimpleIntegerProperty(this.gkey);
		initComboBox();
	
	}
	
	public void initComboBox(int gkey_responsable) {
		
		ObservableList<String> liste = FXCollections.observableArrayList();

		liste.add(nomResponsable);

		JFXComboBox<String> comboBox = new JFXComboBox<String>();
		comboBox.setItems(liste);
		
		comboBox.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		comboBox.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore3));
		comboBox.setStyle("-fx-text-fill : "+GlobalColor.coloreText+";"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		comboBox.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
		comboBox.setValue(nomResponsable);
		comboBox.setEditable(false);
		responsable = new SimpleObjectProperty<JFXComboBox<String>>(comboBox);

	}
	
	public void initComboBox() {
		listeUser =FXCollections.observableArrayList();
		ObservableList<String> liste = FXCollections.observableArrayList();

		SQLConnection connection;
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeUser = connection.select_user();
			connection.close();

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		for(int i = 0 ; i < listeUser.size() ; i++) {
			liste.add(listeUser.get(i).nom.getValue());
		}

		JFXComboBox<String> comboBox = new JFXComboBox<String>();
		comboBox.setItems(liste);
		
		comboBox.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		comboBox.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore3));
		comboBox.setStyle("-fx-text-fill : "+GlobalColor.coloreText+";"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		
		responsable = new SimpleObjectProperty<JFXComboBox<String>>(comboBox);
		responsable.getValue().setValue(listeUser.get(0).nom.getValue());
	
	}
	
	public boolean isCritique() {
		return this.critique.getValue().isSelected();
	}
	
	public String getLibelle() {
		return this.libelle.getValue().getText();
	}
	
	public int getIdResponsable() {
		return listeUser.get(responsable.getValue().getSelectionModel().getSelectedIndex()).gkey;
	}
	
	public void unlock() {
		ObservableList<String> liste = FXCollections.observableArrayList();

		this.critique.getValue().setDisable(false);
		this.libelle.getValue().setEditable(true);
	
		listeUser =FXCollections.observableArrayList();
		SQLConnection connection;
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeUser = connection.select_user();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		for(int i = 0 ; i < listeUser.size() ; i++) {
			liste.add(listeUser.get(i).nom.getValue());
		}
		
		this.responsable.getValue().setItems(liste);
		this.isEdited = true; 
	}
	
}
