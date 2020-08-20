package com.gluonapplication;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

public class Conteneur extends RecursiveTreeObject<Conteneur>  {
	public ObjectProperty<JFXComboBox<String>> claimsType;
	public ObjectProperty<JFXTextArea> description;
	public StringProperty cancel;
	 
	public  Conteneur(){
		JFXComboBox<String> cl  = new JFXComboBox<String>();
        cl.setMinSize(500, 50);
        cl.autosize();
        cl.setFocusColor(Color.DARKORANGE);
        cl.setItems(claimstype());
        cl.setAccessibleText("Break");
		claimsType = new SimpleObjectProperty<JFXComboBox<String>>(cl);
		description = new SimpleObjectProperty<JFXTextArea>(new JFXTextArea());
	}
	
	public ObservableList<String> claimstype(){
		 ObservableList<String> item  = FXCollections.observableArrayList(); 
		 item.add("Break");
		 item.add("Missing");
		 item.add("Replaced");
		 item.add("Other");
		return item;
	}
	
	public  ObjectProperty<JFXComboBox<String>> getClaimsType() {
		return claimsType;
	}
	
	public  String getDescription() {
		return description.toString();
	}
	
	
	public  String getCancel() {
		return cancel.toString();
	}
	
	public  void setCancel(String txt) {
		this.cancel = new SimpleStringProperty(txt); 
	}
}
