package com.gluonapplication;

import java.io.IOException;
import java.sql.SQLException;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CONFIG {
	//LOCAL SERVEUR
	public static  String LOCAL_ADDRESS ; 
	public static  String LOCAL_BD ;
	public static  String LOCAL_USER  ;
	public static  String LOCAL_PASS ; 
	public static  String LOCAL_PORT ; 
	
	public static String EMAIL; 
	public static String EMAIL_PASS; 
	

	// A CAUSE DU DISABLE BINDING DANS DrawerAccuiel J AI DU METTRE L INVERSE POUR EVITER DE TOUT COMPLIQUER CAD EXEMPLE  SI IL EST MANAGER C FALSE 
	public static BooleanProperty disableFicheVerification ; 
	public static BooleanProperty disablePanne  ; 
	public static BooleanProperty disableTypeEngin ;
	public static BooleanProperty disableEngin ;
	public static BooleanProperty disableUser ; 
	public static BooleanProperty disableConfiguration ; 

	public static BooleanProperty isSuperAdmin ;
	public static StringProperty affichageUser; 
	
	public static Base_view_user user ;
	
	public static ObservableList<String> load ;
	public static ObservableList<String> load2 ;
	
	public static boolean loadFailed ;
	public static BooleanProperty disableExitButton  = new SimpleBooleanProperty(true); 
	static {
		init();
	}
	
	public static void init() {
		new Langage();
		load = FXCollections.observableArrayList();
		load2 = FXCollections.observableArrayList();
		try {
			SaveFile sf = new SaveFile();
			
			load = sf.lecture();
		} catch (IOException e) {
			load = FXCollections.observableArrayList();
			load.add("PM1");
			load.add("Francais");
			load.add("1");
			loadFailed = true ;
			e.printStackTrace();
		}
		try {
			SaveFile sf2 = new SaveFile();
			load2 = sf2.lectureFile2();
		} catch (Exception e) {
			load2 = FXCollections.observableArrayList();
			load2.add("127.0.0.1");
			load2.add("49711");
			load2.add("KL");
			load2.add("lk141294");
			load2.add("@gmail");
			load2.add("141294");
		}
		
		try {
			LOCAL_ADDRESS = load2.get(0);
			LOCAL_PORT = load2.get(1);
			LOCAL_BD = "Claims";
			LOCAL_USER = load2.get(2);
			LOCAL_PASS = load2.get(3);  
			EMAIL = load2.get(4);
			EMAIL_PASS = load2.get(5);
			
		} catch (Exception e) {
			load2 = FXCollections.observableArrayList();
			load2.add("127.0.0.1");
			load2.add("49711");
			load2.add("KL");
			load2.add("lk141294");
			load2.add("@gmail");
			load2.add("141294");
			loadFailed = true ;
		}

		
		System.out.println("STATIC  !!!! ");
		
		switch (load.get(1)) {
		case "Francais":
			System.out.println("fr");
			Langage.ln = "fr";
			break;
		case "English":
			System.out.println("eng");
			Langage.ln = "eng";
			break;
		case "Malagasy":
			System.out.println("mlg");
			Langage.ln = "mlg";
			break;
		default:
			System.out.println("what ? "+load.get(2));
			Langage.ln = "fr";
			break;
		}
	}
	public static Base_view_engin engin  ;

	public static BooleanProperty isExitVisible  = new SimpleBooleanProperty(true) ; 

	public CONFIG() {
		
		user = new Base_view_user();
		affichageUser = user.id;
		
		disableFicheVerification = new SimpleBooleanProperty(true);
		disablePanne = new SimpleBooleanProperty(true);
		disableTypeEngin = new SimpleBooleanProperty(true);
		disableEngin = new SimpleBooleanProperty(true);
		disableUser = new SimpleBooleanProperty(true);
		disableConfiguration = new SimpleBooleanProperty(true);
		
		isSuperAdmin = new SimpleBooleanProperty(false);

		System.out.println("CONFIG !!!! " + load.get(0));
		try {
			SQLConnection connection  = new SQLConnection(CONFIG.LOCAL_ADDRESS,CONFIG.LOCAL_PORT,CONFIG.LOCAL_BD,CONFIG.LOCAL_USER,CONFIG.LOCAL_PASS);
			engin = connection.select_engin(load.get(0));
			connection.close();	
		}
		catch(Exception e) {
			System.out.println("MESSAGE " + e.toString());
		}
	}
	
	private void loadConfig() {
		
	}

	public static void disableAll() {
		disableFicheVerification.set(true);
		disablePanne.set(true);
		disableTypeEngin.set(true);
		disableEngin.set(true);
		disableUser.set(true);
		disableConfiguration.set(true);
	}
	
	public static void checkUser() {
		switch (user.type.getValue()) {
		case "ADMIN":
			disableFicheVerification.set(false);
			disablePanne.set(false);
			disableTypeEngin.set(false);
			disableEngin.set(false);
			disableUser.set(false);
			disableConfiguration.set(false);
			isExitVisible.set(true);
			break;
		case "TECHNICIEN_ADMIN":
			disableFicheVerification.set(false);
			disablePanne.set(false);
			disableTypeEngin.set(false);
			disableEngin.set(false);
			disableUser.set(false);
			disableConfiguration.set(false);
			isExitVisible.set(true);
			break;
		case "MANAGEUR":
			disablePanne.set(false);
			isExitVisible.set(true);break;
			
		case "TECHNICIEN_CR":
			disablePanne.set(false);
			disableTypeEngin.set(false);
			disableEngin.set(false);
			isExitVisible.set(true);
			break;
			
		case "TECHNICIEN":
			disablePanne.set(false);
			isExitVisible.set(true);
			break;
			
		case "OPERATEUR":
			disableFicheVerification.set(false);
			isExitVisible.set(false);
			break;
		
		default:
			disableFicheVerification.set(true);
			disablePanne.set(true);
			disableTypeEngin.set(true);
			disableEngin.set(true);
			disableUser.set(true);
			disableConfiguration.set(true);
			isExitVisible.set(true);
			break;
		}
	}
}
