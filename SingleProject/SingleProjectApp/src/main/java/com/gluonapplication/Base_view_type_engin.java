package com.gluonapplication;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Paint;

public class Base_view_type_engin extends RecursiveTreeObject<Base_view_type_engin>{
	public int gkey;
	public ObjectProperty<JFXTextField> libelle ; 
	public String dateCreation ;
	public String dateModification;
	public int createur ; 
	public int modificateur;
	public boolean isNew;
	public boolean isEdit;
	
	public Base_view_type_engin() {
		JFXTextField field = new JFXTextField();
		field.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		field.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore2));
		field.setStyle("-fx-text-fill : "+GlobalColor.coloreText+";" //#6a6a6f
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				+ "-fx-prompt-text-fill:"+GlobalColor.coloreText+" ;"
				);
		field.setPromptText("libelle engin");
		libelle = new SimpleObjectProperty<JFXTextField>(field);
		this.isNew = true; 
	}
	
	public Base_view_type_engin(int gkey ) {
		JFXTextField field = new JFXTextField();
		field.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		field.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore2));
		field.setStyle("-fx-text-fill : "+GlobalColor.coloreText+";"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		field.setPromptText("libelle engin");
		//field.setEditable(false);

		libelle = new SimpleObjectProperty<JFXTextField>(field);
		this.gkey = gkey ; 
		this.isNew = true;
	}
	
	public Base_view_type_engin(
			int gkey,
			String libelle ,
			String dateCreation,
			String dateModification,
			int createur,
			int modificateur ) {
		JFXTextField field = new JFXTextField();
		field.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		field.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore2));
		field.setStyle("-fx-text-fill : "+GlobalColor.coloreText+";"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				);
		field.setText(libelle);
		field.setEditable(false);
		this.libelle = new SimpleObjectProperty<JFXTextField>(field);
		this.gkey = gkey ; 
		this.dateCreation = dateCreation;
		this.dateModification = dateModification ; 
		this.createur = createur ; 
		this.modificateur = modificateur;
	}
	public void unlock() {
		this.libelle.getValue().setEditable(true);
		this.isEdit  = true ;
	}
	
	public void lock() {
		this.libelle.getValue().setEditable(false);;
	}
}
