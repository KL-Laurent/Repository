package com.gluonapplication;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User  extends RecursiveTreeObject<User> {

	public StringProperty id;
	public StringProperty pass;

	
	public User(String id,String pass) {
		this.id = new SimpleStringProperty(id);
		this.pass = new SimpleStringProperty(pass);
	}
	
}
