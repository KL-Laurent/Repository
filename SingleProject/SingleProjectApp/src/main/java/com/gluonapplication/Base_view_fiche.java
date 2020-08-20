package com.gluonapplication;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Base_view_fiche {
	public int gkey ;
	public StringProperty observation ; 
	public StringProperty heuredebut ; 
	public StringProperty heurefin ; 
	public StringProperty date; 
	public int gkey_operateur ; 
	public int gkey_engin ; 
	public boolean shift_jour ;
	
	public Base_view_fiche(
			 int gkey ,
			 String observation ,
			 String heuredebut ,
			 String heurefin ,
			 String date,
			 int gkey_operateur ,
			 int gkey_engin,
			 boolean shift_jour
			) {
		this.gkey = gkey ; 
		this.observation = new SimpleStringProperty(observation);
		this.heuredebut = new SimpleStringProperty(heuredebut);
		this.heurefin = new SimpleStringProperty(heurefin);
		this.date = new SimpleStringProperty(date);
		this.gkey_operateur = gkey_operateur;
		this.gkey_engin = gkey_engin;
		this.shift_jour = shift_jour;
		
	}
}
