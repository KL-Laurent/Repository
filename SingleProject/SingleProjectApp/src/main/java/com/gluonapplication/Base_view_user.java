package com.gluonapplication;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Base_view_user  extends RecursiveTreeObject<Base_view_user> {
	public int gkey ;
	public StringProperty nom ; 
	public StringProperty mail ; 
	public StringProperty address ; 
	public StringProperty numTel ;
	public StringProperty type ; 
	public StringProperty dateCreation ; 
	public StringProperty dateModification ; 
	public StringProperty id ; 
	public StringProperty pass ; 
	public int createur ; 
	public int modificateur; 
	
	public ObjectProperty<JFXTextField> fieldNom ; 
	public ObjectProperty<JFXTextField> fieldMail ; 
	public ObjectProperty<JFXTextField> fieldAddress; 
	public ObjectProperty<JFXTextField> fieldNumtel ;
	public ObjectProperty<JFXComboBox<String>>  fieldType ; 
	public ObjectProperty<JFXTextField> fieldId ;
	public ObjectProperty<JFXTextField> fieldPass ;
	
	public boolean isNews;
	public boolean isEdited;
	
	private JFXTextField baseField(String txt) {
		JFXTextField field = new JFXTextField();
		field.setFocusColor(javafx.scene.paint.Paint.valueOf("#03A9F4"));
		field.setUnFocusColor(javafx.scene.paint.Paint.valueOf("#ffffff"));
		field.setStyle("-fx-text-fill : #868895 ;"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				+ "-fx-prompt-text-fill: #868895  ;"
				);
		field.setPromptText("");
		field.setText(txt);
		field.setEditable(false);
		return field;
	}
	
	private JFXTextField baseField() {
		JFXTextField field = new JFXTextField();
		field.setFocusColor(javafx.scene.paint.Paint.valueOf("#03A9F4"));
		field.setUnFocusColor(javafx.scene.paint.Paint.valueOf("#ffffff"));
		field.setStyle("-fx-text-fill : #868895 ;"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				+ "-fx-prompt-text-fill: #868895  ;"
				);
		field.setPromptText("Vide");
		return field;
	}
	
	// ATTENTION !  METHODE PAS TRES SAFE. MARCHE SEULMENT APRES QUE LES STRINGPROPRIETE SOIT INITIALISER 
	private void initField() {
		fieldNom = new SimpleObjectProperty<JFXTextField>(baseField(this.nom.getValue()));
		fieldMail = new SimpleObjectProperty<JFXTextField>(baseField(this.mail.getValue()));
		fieldAddress = new SimpleObjectProperty<JFXTextField>(baseField(this.address.getValue()));
		fieldNumtel = new SimpleObjectProperty<JFXTextField>(baseField(this.numTel.getValue()));
		fieldId = new SimpleObjectProperty<JFXTextField>(baseField(this.id.getValue()));
		fieldPass = new SimpleObjectProperty<JFXTextField>(baseField(this.pass.getValue()));
		
		ObservableList<String> liste = FXCollections.observableArrayList();

	
		JFXComboBox<String> comboBox = new JFXComboBox<String>();
		liste.add(this.type.getValue());
		
		comboBox.setItems(liste);
		comboBox.setFocusColor(javafx.scene.paint.Paint.valueOf("#03A9F4"));
		comboBox.setUnFocusColor(javafx.scene.paint.Paint.valueOf("#ffffff"));
		comboBox.setStyle("-fx-text-fill : #868895 ;"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		comboBox.setPromptText(this.type.getValue());
		fieldType = new SimpleObjectProperty<JFXComboBox<String>>(comboBox);
		fieldType.getValue().setValue(this.type.getValue());
		fieldType.getValue().setEditable(false);	
	}
	
	public Base_view_user(
		     int gkey ,
			String nom ,
			String mail , 
			String address , 
			String numTel ,
			String type , 
			String dateCreation ,
			String dateModification,
			int createur,
			int modificateur, 
			String id , 
			String pass 
			) {
		this.gkey = gkey;
		this.nom = new SimpleStringProperty(nom);
		this.mail = new SimpleStringProperty(mail);
		this.address = new SimpleStringProperty(address);
		this.numTel = new SimpleStringProperty(numTel);
		this.type = new SimpleStringProperty(type);
		this.dateCreation = new SimpleStringProperty(dateCreation);
		this.dateModification = new SimpleStringProperty(dateModification);
		this.createur = createur ; 
		this.modificateur = modificateur;
		this.id = new SimpleStringProperty(id);
		this.pass = new SimpleStringProperty(pass);
		initField();
		isNews = false; 
	}
	
	public Base_view_user(
		     int gkey ,
			String nom ,
			String mail , 
			String address , 
			String numTel ,
			String type , 
			String dateCreation ,
			int createur,
			String id , 
			String pass 
			) {
		this.gkey = gkey;
		this.nom = new SimpleStringProperty(nom);
		this.mail = new SimpleStringProperty(mail);
		this.address = new SimpleStringProperty(address);
		this.numTel = new SimpleStringProperty(numTel);
		this.type = new SimpleStringProperty(type);
		this.dateCreation = new SimpleStringProperty(dateCreation);
		this.createur = createur ; 
		this.id = new SimpleStringProperty(id);
		this.pass = new SimpleStringProperty(pass);
		initField();
		isNews = false ; 
	}
	
	public Base_view_user() {
		fieldNom = new SimpleObjectProperty<JFXTextField>(baseField());
		fieldMail = new SimpleObjectProperty<JFXTextField>(baseField());
		fieldAddress = new SimpleObjectProperty<JFXTextField>(baseField());
		fieldNumtel = new SimpleObjectProperty<JFXTextField>(baseField());
		fieldId = new SimpleObjectProperty<JFXTextField>(baseField());
		fieldPass = new SimpleObjectProperty<JFXTextField>(baseField());
		
		this.gkey = 0;
		this.nom = new SimpleStringProperty("");
		this.mail = new SimpleStringProperty("");
		this.address = new SimpleStringProperty("");
		this.numTel = new SimpleStringProperty("");
		this.type = new SimpleStringProperty("");
		this.dateCreation = new SimpleStringProperty("");
		this.createur = 0 ; 
		this.id = new SimpleStringProperty("");
		this.pass = new SimpleStringProperty("");
		
		
		JFXComboBox<String> comboBox = new JFXComboBox<String>();	
		ObservableList<String> liste = FXCollections.observableArrayList();
		liste.add("ADMIN");
		liste.add("MANAGEUR");
		liste.add("TECHNICIEN_CR");
		liste.add("TECHNICIEN");
		liste.add("TECHNICIEN_ADMIN");
		liste.add("OPERATEUR");
		
		comboBox.setItems(liste);
		comboBox.setFocusColor(javafx.scene.paint.Paint.valueOf("#03A9F4"));
		comboBox.setUnFocusColor(javafx.scene.paint.Paint.valueOf("#ffffff"));
		comboBox.setStyle("-fx-text-fill : #868895 ;"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		fieldType = new SimpleObjectProperty<JFXComboBox<String>>(comboBox);
		fieldType.getValue().setEditable(false);
		isNews = true; 
	}
	
	public void unlockField() {
		this.fieldNom.getValue().setEditable(true);
		this.fieldMail.getValue().setEditable(true);
		this.fieldAddress.getValue().setEditable(true);
		this.fieldNumtel.getValue().setEditable(true);
		this.fieldId.getValue().setEditable(true);
		this.fieldPass.getValue().setEditable(true);
		
		ObservableList<String> liste = FXCollections.observableArrayList();
		liste.add("ADMIN");
		liste.add("MANAGEUR");
		liste.add("TECHNICIEN_CR");
		liste.add("TECHNICIEN");
		liste.add("OPERATEUR");
		
		this.fieldType.getValue().setItems(liste);
		this.fieldType.getValue().setPromptText(this.type.getValue());
		//this.fieldType.getValue().getSelectionModel().select(this.type.getValue());
		this.fieldType.getValue().setValue(this.type.getValue());
		this.fieldType.getValue().setEditable(true);
		System.out.println(this.fieldType.getValue().getSelectionModel().getSelectedItem());

		
		this.isEdited = true;
		
	}
	public void lockField() {
		this.fieldNom.getValue().setEditable(false);
		this.fieldMail.getValue().setEditable(false);
		this.fieldAddress.getValue().setEditable(false);
		this.fieldNumtel.getValue().setEditable(false);
		this.fieldId.getValue().setEditable(false);
		this.fieldPass.getValue().setEditable(false);
		System.out.println(this.fieldType.getValue().getSelectionModel().getSelectedItem());

		ObservableList<String> liste = FXCollections.observableArrayList();
		
		String type = fieldType.getValue().getSelectionModel().getSelectedItem();
		liste.setAll();
		liste.add(type);
		this.fieldType.getValue().setItems(liste);
		this.fieldType.getValue().setPromptText(type);
		
		fieldType.getValue().setValue(type);
		this.fieldType.getValue().setEditable(false);
		
		this.isEdited = false;	
	}
	

}
