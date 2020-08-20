package com.gluonapplication;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

public class Base_view_contenu extends RecursiveTreeObject<Base_view_contenu> {
	public int gkey;
	public ObjectProperty<JFXTextField> commentaire ;
	public ObjectProperty<JFXCheckBox> status;
	public StringProperty dateModification;
	public IntegerProperty gkey_fiche_verification;
	public IntegerProperty gkey_equipement;
	public IntegerProperty gkey_modificateur;
	public ObjectProperty<JFXCheckBox> isClosed;
	public ObjectProperty<JFXCheckBox> reparer;
	public ObjectProperty<JFXTextField> commentaireRP ;
	
	
	//POUR L AFFICHAGE NORMALEMENT SA DEVRAIENT PAS ETRE LA MAIS C POUR EVITER DE TROP FAIRE APPEL AUX SERVEUR BD 
	public StringProperty date_fiche;
	public StringProperty libelle_equipement;
	public StringProperty libelle_engin;
	public StringProperty nom_modificateur;
	public StringProperty nom_operateur;



 
	
	public Base_view_contenu() {
		
		JFXCheckBox checkBox = new JFXCheckBox();
		checkBox.setSelected(true);
		
		
		JFXTextField field = new JFXTextField();
		field.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		field.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore2));
		field.setStyle("-fx-text-fill : "+GlobalColor.coloreText+";"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				+ "-fx-prompt-text-fill: #868895  ;"
				);
		field.setPromptText("Commentaire");
		field.setEditable(true);
		
		this.commentaire = new SimpleObjectProperty<JFXTextField>(field); 
		this.status = new SimpleObjectProperty<JFXCheckBox>(checkBox) ; 
		this.dateModification = new SimpleStringProperty() ; 
	}
	
	public Base_view_contenu(
			 int gkey,
			 String commentaire ,
			 boolean status,
			 String dateModification,
			 int gkey_fiche_verification,
			 int gkey_equipement,
			 int gkey_modificateur,
			 String commentaireRP
			) {
		
		JFXCheckBox checkBox = new JFXCheckBox();
		checkBox.setSelected(status);
		checkBox.setUnCheckedColor(Color.valueOf("#ff0000"));

		
		
		JFXTextField field = new JFXTextField();
		field.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		field.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore2));
		field.setStyle("-fx-text-fill : "+GlobalColor.coloreText+";"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				+ "-fx-prompt-text-fill: #868895  ;"
				);
		field.setText(commentaire);
		field.setEditable(false);
		
		JFXTextField field2 = new JFXTextField();
		field2.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		field2.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore2));
		field2.setStyle("-fx-text-fill : "+GlobalColor.coloreText+";"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				+ "-fx-prompt-text-fill: #868895  ;"
				);
		field2.setText(commentaireRP);
		if(CONFIG.user.type.getValue().equals("TECHNICIEN")) {
			field2.setEditable(true);
		}
		else if(CONFIG.user.type.getValue().equals("TECHNICIEN")) {
			field2.setEditable(false);
			checkBox.setDisable(true);
		}
		else
			field2.setEditable(false);
		
		this.gkey = gkey ; 
		this.commentaire = new SimpleObjectProperty<JFXTextField>(field); 
		this.status = new SimpleObjectProperty<JFXCheckBox>(checkBox) ; 
		this.dateModification = new SimpleStringProperty(dateModification) ; 
		this.gkey_fiche_verification = new SimpleIntegerProperty(gkey_fiche_verification) ; 
		this.gkey_equipement = new SimpleIntegerProperty(gkey_equipement) ; 
		this.gkey_modificateur = new SimpleIntegerProperty(gkey_modificateur);
		this.commentaireRP = new SimpleObjectProperty<JFXTextField>(field2); 
	}
	
	public Base_view_contenu(
			 int gkey,
			 String commentaire ,
			 boolean status,
			 String dateModification,
			 int gkey_fiche_verification,
			 int gkey_equipement,
			 int gkey_modificateur	,
			 String date_fiche,
			 String libelle_equipement,
			 String libelle_engin,
			 String nom_modificateur,
			 boolean isCLosed,
			 boolean reparer,
			 String commentaireRP ,
			 String nom_operateur
			) {
		
		JFXCheckBox checkBoxStatus = new JFXCheckBox();
		checkBoxStatus.setSelected(status);
		checkBoxStatus.setUnCheckedColor(Color.valueOf("#ff0000"));

		
		JFXCheckBox checkBoxIsClosed = new JFXCheckBox();
		checkBoxIsClosed.setSelected(isCLosed);
		checkBoxIsClosed.setUnCheckedColor(Color.valueOf("#ff0000"));

		JFXCheckBox checkBoxReparer = new JFXCheckBox();
		checkBoxReparer.setSelected(reparer);
		checkBoxReparer.setUnCheckedColor(Color.valueOf("#ff0000"));

		JFXTextField field = new JFXTextField();
		field.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		field.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore2));
		field.setStyle("-fx-text-fill : "+GlobalColor.coloreText+";"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				+ "-fx-prompt-text-fill: "+GlobalColor.coloreText+"  ;"
				);
		field.setText(commentaire);
		field.setEditable(false);
		
		JFXTextField field2 = new JFXTextField();
		field2.setFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore1));
		field2.setUnFocusColor(javafx.scene.paint.Paint.valueOf(GlobalColor.colore2));
		field2.setStyle("-fx-text-fill : "+GlobalColor.coloreText+";"
				+ "-fx-font-size: 15px ;" 
				+ "-fx-font-family:  'Bell MT';"
				+ "-fx-prompt-text-fill: "+GlobalColor.coloreText+"  ;"
				);
		field2.setText(commentaireRP);
		if(CONFIG.user.type.getValue().equals("TECHNICIEN")) {
			field2.setEditable(true);
		}
		else if(CONFIG.user.type.getValue().equals("MANAGEUR")) {
			field2.setEditable(false);
			checkBoxIsClosed.setDisable(true);
			checkBoxReparer.setDisable(true);
		}
		else if(CONFIG.user.type.getValue().equals("TECHNICIEN_CR")) {
			field2.setEditable(false);
			checkBoxIsClosed.setDisable(false);
			checkBoxReparer.setDisable(false);
		}
		
		else
			field2.setEditable(false);
		
		this.gkey = gkey ; 
		this.commentaire = new SimpleObjectProperty<JFXTextField>(field); 
		this.status = new SimpleObjectProperty<JFXCheckBox>(checkBoxStatus) ; 
		this.dateModification = new SimpleStringProperty(dateModification) ; 
		this.gkey_fiche_verification = new SimpleIntegerProperty(gkey_fiche_verification) ; 
		this.gkey_equipement = new SimpleIntegerProperty(gkey_equipement) ; 
		this.gkey_modificateur = new SimpleIntegerProperty(gkey_modificateur);
		this.date_fiche = new SimpleStringProperty(date_fiche) ; 
		this.libelle_engin = new SimpleStringProperty(libelle_engin) ; 
		this.libelle_equipement = new SimpleStringProperty(libelle_equipement) ; 
		this.nom_modificateur = new SimpleStringProperty(nom_modificateur) ; 
		this.nom_operateur = new SimpleStringProperty(nom_operateur) ; 

		this.isClosed = new SimpleObjectProperty<JFXCheckBox>(checkBoxIsClosed) ; 
		this.reparer = new SimpleObjectProperty<JFXCheckBox>(checkBoxReparer) ; 
		
		this.commentaireRP = new SimpleObjectProperty<JFXTextField>(field2); 

	}
}
