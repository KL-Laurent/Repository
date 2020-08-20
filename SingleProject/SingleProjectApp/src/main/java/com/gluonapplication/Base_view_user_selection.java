package com.gluonapplication;

import java.sql.SQLException;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


public class Base_view_user_selection extends RecursiveTreeObject<Base_view_user_selection> {

	int gkey ; 
	public ObjectProperty<JFXComboBox<String>> comboNom ; 
	int fkey ; 
	public  ObservableList<Base_view_user> listeUser;
	
	public Base_view_user_selection() {
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

		ObservableList<String> liste = FXCollections.observableArrayList();

		
		for(int i = 0 ; i < listeUser.size() ; i++) {
			liste.add(listeUser.get(i).nom.getValue());
		}
		
		JFXComboBox<String> comboBox = new JFXComboBox<String>();
		comboBox.setItems(liste);
		comboNom = new SimpleObjectProperty<JFXComboBox<String>>(comboBox);
		comboNom.getValue().setValue(listeUser.get(0).nom.getValue());
	}    
	
	
	public Base_view_user_selection(int gkey,int fkey) {
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

		ObservableList<String> liste = FXCollections.observableArrayList();

		
		for(int i = 0 ; i < listeUser.size() ; i++) {
			liste.add(listeUser.get(i).nom.getValue());
		}
		
		JFXComboBox<String> comboBox = new JFXComboBox<String>();
		comboBox.setItems(liste);
		comboNom = new SimpleObjectProperty<JFXComboBox<String>>(comboBox);
		comboNom.getValue().setValue(listeUser.get(0).nom.getValue());
		this.gkey = gkey ; 
		this.fkey = fkey;
	}
	

	
	public Base_view_user_selection(int gkey,Base_view_user user,int fkey) {
		listeUser =FXCollections.observableArrayList();
		ObservableList<String> liste = FXCollections.observableArrayList();

		ObservableList<Base_view_user> u ;
		SQLConnection connection;
		try {
			connection = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			u  = connection.select_user() ; 
			connection.close();

			listeUser.add(user);
			for(int i = 0 ; i < u.size() ; i++) {
				listeUser.add(u.get(i));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		for(int i = 0 ; i < listeUser.size() ; i++) {
			liste.add(listeUser.get(i).nom.getValue());
		}
		
		JFXComboBox<String> comboBox = new JFXComboBox<String>();
		comboBox.setItems(liste);

		comboNom = new SimpleObjectProperty<JFXComboBox<String>>(comboBox);
		comboNom.getValue().setValue("SELECTED ! ");

		this.gkey = gkey ; 
		this.fkey = fkey;
	}
	
	public int selectedGkey() {
		return listeUser.get(comboNom.getValue().getSelectionModel().getSelectedIndex()).gkey;
	}
	
	public String getMailUser() {
		System.out.println(listeUser.get(comboNom.getValue().getSelectionModel().getSelectedIndex()).mail.getValue());
		return listeUser.get(comboNom.getValue().getSelectionModel().getSelectedIndex()).mail.getValue();
	}
}
