package com.gluonapplication;

import java.sql.SQLException;

import org.omg.CORBA.RepositoryIdHelper;

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

public class Base_view_engin extends RecursiveTreeObject<Base_view_engin>{
	public int gkey; 
	public ObjectProperty<JFXTextField> libelle ;
	public StringProperty dateCreation ;
	public StringProperty dateModification ;
	public int  createur ;
	public int modificateur ;
	public int gkey_type_engin ;
	public int gkey_responsable ;
	
	//Juste pour l'affichage et la selection de type d'engin 
	public ObjectProperty<JFXComboBox<String>> type_Engin ; 
	public ObservableList<Base_view_type_engin> listeTypeEngin;

	//pour l'affichage responsable
	public ObjectProperty<JFXComboBox<String>> responsable ; 
	public ObservableList<Base_view_user> listeResponsable;
	
	public boolean isNews;
	public boolean isEdited;
	
	private String nomEngin ;
	private String nomResponsable;

	public Base_view_engin(
			int gkey ,
			String libelle ,
			String dateCreation,
			String dateModification , 
			int createur , 
			int modificatieur ,
			int gkey_type_engin,
			int gkey_responsable
			) {
		
		JFXTextField field = new JFXTextField();
		field.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		field.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore2));
		field.setStyle("-fx-text-fill :"+GlobalColor.coloreText+" ;"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		field.setText(libelle);
		field.setEditable(false);
		
		/******************************/
		listeTypeEngin =FXCollections.observableArrayList();
		SQLConnection connection;
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeTypeEngin = connection.select_type_engin_by_gkey(gkey_type_engin);
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		ObservableList<String> liste = FXCollections.observableArrayList();

		
		for(int i = 0 ; i < listeTypeEngin.size() ; i++) {
			liste.add(listeTypeEngin.get(i).libelle.getValue().getText());
		}
		
		JFXComboBox<String> comboBox = new JFXComboBox<String>();
		comboBox.setItems(liste);
		comboBox.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		comboBox.setUnFocusColor(javafx.scene.paint.Paint.valueOf("#ffffff"));
		comboBox.setStyle("-fx-text-fill :"+GlobalColor.coloreText+" ;"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		type_Engin = new SimpleObjectProperty<JFXComboBox<String>>(comboBox);
		type_Engin.getValue().setValue(listeTypeEngin.get(0).libelle.getValue().getText());
		type_Engin.getValue().setEditable(false);
		type_Engin.getValue().setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

		/******************************/
		
		/******************************/
		listeTypeEngin =FXCollections.observableArrayList();
		try {
			connection  = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeResponsable = connection.select_user_by_gkey(gkey_responsable);
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		ObservableList<String> liste2 = FXCollections.observableArrayList();

		
		for(int i = 0 ; i < listeResponsable.size() ; i++) {
			liste2.add(listeResponsable.get(i).nom.getValue());
		}
		
		JFXComboBox<String> comboBox2 = new JFXComboBox<String>();
		comboBox2.setItems(liste2);
		comboBox2.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		comboBox2.setUnFocusColor(javafx.scene.paint.Paint.valueOf("#ffffff"));
		comboBox2.setStyle("-fx-text-fill :"+GlobalColor.coloreText+" ;"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		
		responsable = new SimpleObjectProperty<JFXComboBox<String>>(comboBox2);
		responsable.getValue().setValue(listeResponsable.get(0).nom.getValue());

		responsable.getValue().setEditable(false);
		responsable.getValue().setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
		/******************************/
		this.gkey =gkey;
		this.libelle = new SimpleObjectProperty<JFXTextField>(field);
		this.dateCreation = new SimpleStringProperty(dateCreation);
		this.dateModification = new SimpleStringProperty(dateModification);
		this.createur = createur ;
		this.modificateur = modificatieur;
		this.gkey_type_engin = gkey_type_engin;
		this.gkey_responsable = gkey_responsable ; 
		isNews = false;
		
		this.nomEngin = this.type_Engin.getValue().getSelectionModel().getSelectedItem();
		this.nomResponsable = this.responsable.getValue().getSelectionModel().getSelectedItem();
		

	}
	
	public Base_view_engin(
			int gkey ,
			String libelle ,
			String dateCreation,
			int createur , 
			int gkey_type_engin,
			int gkey_responsable 
			) {
		JFXTextField field = new JFXTextField();
		field.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		field.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore2));
		field.setStyle("-fx-text-fill : "+GlobalColor.coloreText+";"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		field.setText(libelle);
		field.setEditable(true);
		
		/******************************/
		listeResponsable =FXCollections.observableArrayList();
		SQLConnection connection;
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeTypeEngin = connection.select_type_engin_by_gkey(gkey_type_engin);
			connection.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ObservableList<String> liste = FXCollections.observableArrayList();

		
		for(int i = 0 ; i < listeTypeEngin.size() ; i++) {
			liste.add(listeTypeEngin.get(i).libelle.getValue().getText());
		}
		
		JFXComboBox<String> comboBox = new JFXComboBox<String>();
		comboBox.setItems(liste);
		comboBox.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		comboBox.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore3));
		comboBox.setStyle("-fx-text-fill : #868895;"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		
		comboBox.setValue(getlibelleTypeEnginByGkey(gkey_type_engin));
		type_Engin = new SimpleObjectProperty<JFXComboBox<String>>(comboBox);
		if(!listeTypeEngin.isEmpty())
			type_Engin.getValue().setValue(listeTypeEngin.get(0).libelle.getValue().getText());
		type_Engin.getValue().setEditable(false);
		/******************************/
		
		
		/******************************/
		listeResponsable =FXCollections.observableArrayList();
		try {
			connection  = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeResponsable = connection.select_user();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		ObservableList<String> liste2 = FXCollections.observableArrayList();

		
		for(int i = 0 ; i < listeResponsable.size() ; i++) {
			liste2.add(listeResponsable.get(i).nom.getValue());
		}
		
		JFXComboBox<String> comboBox2 = new JFXComboBox<String>();
		comboBox2.setItems(liste2);
		comboBox2.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		comboBox2.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore3));
		comboBox2.setStyle("-fx-text-fill : #868895;"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		
		comboBox2.setValue(getNomResponsableByGkey(gkey_responsable));
		responsable = new SimpleObjectProperty<JFXComboBox<String>>(comboBox2);
		//responsable.getValue().setValue(listeResponsable.get(0).libelle.getValue().getText());
		responsable.getValue().setEditable(false);
		/******************************/
		
		this.gkey = gkey ; 
		this.libelle = new SimpleObjectProperty<JFXTextField>(field);
		this.dateCreation = new SimpleStringProperty(dateCreation);
		this.createur = createur ; 
		this.gkey_type_engin = gkey_type_engin;
		this.gkey_responsable = gkey_responsable ;
		
		this.nomEngin = this.type_Engin.getValue().getSelectionModel().getSelectedItem();
		this.nomResponsable = this.responsable.getValue().getSelectionModel().getSelectedItem();
		
		
		isNews = false ; 
	}
	
	public Base_view_engin() {
		JFXTextField field = new JFXTextField();
		field.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		field.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore2));
		field.setPromptText("libelle");
		field.setStyle("-fx-text-fill : "+GlobalColor.coloreText+";"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		field.setEditable(true);
		
		/******************************/
		listeTypeEngin =FXCollections.observableArrayList();
		SQLConnection connection;
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeTypeEngin = connection.select_type_engin();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		ObservableList<String> liste = FXCollections.observableArrayList();

		
		for(int i = 0 ; i < listeTypeEngin.size() ; i++) {
			liste.add(listeTypeEngin.get(i).libelle.getValue().getText());
		}
		
		JFXComboBox<String> comboBox = new JFXComboBox<String>();
		comboBox.setItems(liste);
		comboBox.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		comboBox.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore3));
		comboBox.setStyle("-fx-text-fill : #868895;"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		
		type_Engin = new SimpleObjectProperty<JFXComboBox<String>>(comboBox);
		type_Engin.getValue().setEditable(true);
		/******************************/
		
		
		/******************************/
		listeResponsable =FXCollections.observableArrayList();
		try {
			connection  = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeResponsable = connection.select_user();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		ObservableList<String> liste2 = FXCollections.observableArrayList();

		
		for(int i = 0 ; i < listeResponsable.size() ; i++) {
			liste2.add(listeResponsable.get(i).nom.getValue());
		}
		
		JFXComboBox<String> comboBox2 = new JFXComboBox<String>();
		comboBox2.setItems(liste2);
		comboBox2.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		comboBox2.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore3));
		comboBox2.setStyle("-fx-text-fill : #868895;"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		 
		//comboBox2.setValue(listeResponsable.get(0).nom.getValue());
		responsable = new SimpleObjectProperty<JFXComboBox<String>>(comboBox2);
		/******************************/
		
		this.libelle = new SimpleObjectProperty<JFXTextField>(field);
		
		isNews = true;
	}
	
	private  String getNomResponsableByGkey(int gkey) {
		for(int i = 0; i < listeResponsable.size(); i++) {
			if(listeResponsable.get(i).gkey == gkey) {
				return listeResponsable.get(i).nom.getValue();
			}
		}
		return "";
	}
	
	private  String getlibelleTypeEnginByGkey(int gkey) {
		for(int i = 0; i < listeTypeEngin.size(); i++) {
			if(listeTypeEngin.get(i).gkey == gkey) {
				return listeTypeEngin.get(i).libelle.getValue().getText();
			}
		}
		return "";
	}
	
	public int getGkeyResponsable() {
		return listeResponsable.get(responsable.getValue().getSelectionModel().getSelectedIndex()).gkey;
	}
	
	
	public int getGkeyTypeEngin() {
		return listeTypeEngin.get(type_Engin.getValue().getSelectionModel().getSelectedIndex()).gkey;
	}	
	
	
	public void unlockField() {
		this.libelle.getValue().setEditable(true);
		
		SQLConnection connection;
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeTypeEngin = connection.select_type_engin();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		ObservableList<String> liste = FXCollections.observableArrayList();
		for(int i = 0 ; i < listeTypeEngin.size() ; i++) {
			liste.add(listeTypeEngin.get(i).libelle.getValue().getText());
		}
		
		this.type_Engin.getValue().setItems(liste);
		this.type_Engin.getValue().setValue(this.nomEngin);
		this.type_Engin.getValue().setEditable(true);
		
		System.out.println(this.nomEngin);
		System.out.println(this.type_Engin.getValue().getSelectionModel().getSelectedItem());
		
		listeResponsable =FXCollections.observableArrayList();
		try {
			connection  = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			listeResponsable = connection.select_user();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		ObservableList<String> liste2 = FXCollections.observableArrayList();

		
		for(int i = 0 ; i < listeResponsable.size() ; i++) {
			liste2.add(listeResponsable.get(i).nom.getValue());
		}
		
		this.responsable.getValue().setItems(liste2);
		this.responsable.getValue().setValue(this.nomResponsable);
		this.responsable.getValue().setEditable(true);
		
		this.isEdited = true;
	}
	
	public void lockField() {
		this.libelle.getValue().setEditable(false);
		ObservableList<String> liste = FXCollections.observableArrayList();
		String engin = this.type_Engin.getValue().getSelectionModel().getSelectedItem();
		
		liste.setAll();
		liste.add(engin);
		this.type_Engin.getValue().setItems(liste);
		this.type_Engin.getValue().setPromptText(engin);
		type_Engin.getValue().setValue(engin);
		this.type_Engin.getValue().setEditable(false);
		
		ObservableList<String> liste2 = FXCollections.observableArrayList();
		String nomResponsable = this.responsable.getValue().getSelectionModel().getSelectedItem();
		liste2.setAll();
		liste2.add(nomResponsable);
		
		this.responsable.getValue().setItems(liste2);
		this.responsable.getValue().setPromptText(nomResponsable);
		responsable.getValue().setValue(nomResponsable);
		this.responsable.getValue().getSelectionModel().select(0);
		this.responsable.getValue().setEditable(false);
	}
	
}
