package com.gluonapplication;

import java.awt.Component.BaselineResizeBehavior;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Langage {
	public static String ln ;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    
    // FIS et FOS ameliorer 
    BufferedInputStream bis = null ; 
    BufferedOutputStream bos = null ; 
    
    // FIS Lecture FOS ecriture 
    FileInputStream fis = null ;
    FileOutputStream fos = null ; 
    
    FileWriter fw = null ; 
    FileReader fr = null ;
    
  
	static ObservableList<Base_langue> liste = FXCollections.observableArrayList();
	public Langage() {
		// TODO Auto-generated constructor stub
		try {
			liste.setAll(lecture());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0 ; i < liste.size() ; i++) {
			System.out.println(" fr :" + liste.get(i).fr +"\n mlg  : "+ liste.get(i).mlg + "\n eng :"+ liste.get(i).eng);
		}
	}
	
	
	private  ObservableList<Base_langue> lecture() throws IOException {
		ObservableList<String> file = FXCollections.observableArrayList(); 
		ObservableList<Base_langue> lng = FXCollections.observableArrayList(); 

		File f = new File("./src/main/resources/ressource/langage.kl");
		fr = new FileReader(f);
		String str = "" ;
		int n = 0 ;
		
		while((n = fr.read())!=-1) {
			str+=(char)n;
		}
		fr.close();
		
		String all[] = str.split("\n");
		for(int i = 0 ; i < all.length; i++) {
			String temp[] = all[i].split(";");
			System.out.println(temp[0]);
			lng.add(new Base_langue(temp[0].trim(),temp[2].trim(), temp[1].trim()));
		}
		
		return lng ;
	}
	
	public static String FVLabelCodeEngin() {
		switch(ln) {
			case "fr" : {
				return "";
			}
			case "eng" :{
				return "";
			}
			case "mlg" : {
				return "";
			}
		}
		return "" ;
	}
	
	public static String OPFicheLabelEquipementsCritiques() {
		switch(ln) {
			case "fr" : {
				return liste.get(0).fr;
			}
			case "eng" :{
				return liste.get(0).eng;
			}
			case "mlg" : {
				return liste.get(0).mlg;
			}
		}
		return "" ;
	}
	
	public static String OPFicheLabelEquipementsNonCritiques() {
		switch(ln) {
		case "fr" : {
			return liste.get(1).fr;
		}
		case "eng" :{
			return liste.get(1).eng;
		}
		case "mlg" : {
			return liste.get(1).mlg;
		}
	}
	return "" ;
	}
	
	public static String OPFicheLabelTestFonction() {
		switch(ln) {
		case "fr" : {
			return liste.get(2).fr;
		}
		case "eng" :{
			return liste.get(2).eng;
		}
		case "mlg" : {
			return liste.get(2).mlg;
		}
	}
	return "" ;
	}
	
	public static String OPFicheLabelCodeEngin() {
		switch(ln) {
		case "fr" : {
			return liste.get(3).fr;
		}
		case "eng" :{
			return liste.get(3).eng;
		}
		case "mlg" : {
			return liste.get(3).mlg;
		}
	}
	return "" ;
	}
	
	public static String OPFicheLabeldate() {
		switch(ln) {
		case "fr" : {
			return liste.get(4).fr;
		}
		case "eng" :{
			return liste.get(4).eng;
		}
		case "mlg" : {
			return liste.get(4).mlg;
		}
	}
	return "" ;
	}
	
	public static String OPFicheLabelShift() {
		switch(ln) {
		case "fr" : {
			return liste.get(5).fr;
		}
		case "eng" :{
			return liste.get(5).eng;
		}
		case "mlg" : {
			return liste.get(5).mlg;
		}
	}
	return "" ;
	}
	
	public static  String OPFicheLabelHeureDebut() {
		switch(ln) {
		case "fr" : {
			return liste.get(6).fr;
		}
		case "eng" :{
			return liste.get(6).eng;
		}
		case "mlg" : {
			return liste.get(6).mlg;
		}
	}
	return "" ;
	}
	
	public static String OPFicheLabelHeureFin() {
		switch(ln) {
		case "fr" : {
			return liste.get(7).fr;
		}
		case "eng" :{
			return liste.get(7).eng;
		}
		case "mlg" : {
			return liste.get(7).mlg;
		}
	}
	return "" ;
	}
	
	public static String OPFicheButtonEnvoyer() {
		switch(ln) {
		case "fr" : {
			return liste.get(8).fr;
		}
		case "eng" :{
			return liste.get(8).eng;
		}
		case "mlg" : {
			return liste.get(8).mlg;
		}
	}
	return "" ;
	}

	public static String tableLibelle() {
		switch(ln) {
		case "fr" : {
			return liste.get(9).fr;
		}
		case "eng" :{
			return liste.get(9).eng;
		}
		case "mlg" : {
			return liste.get(9).mlg;
		}
	}
	return "" ;
	}
	
	public static String tableEtat() {
		switch(ln) {
		case "fr" : {
			return liste.get(10).fr;
		}
		case "eng" :{
			return liste.get(10).eng;
		}
		case "mlg" : {
			return liste.get(10).mlg;
		}
	}
	return "" ;
	}
	
	public static String tableCommentaire() {
		switch(ln) {
		case "fr" : {
			return liste.get(11).fr;
		}
		case "eng" :{
			return liste.get(11).eng;
		}
		case "mlg" : {
			return liste.get(11).mlg;
		}
	}
	return "" ;
	}
	
	public static String tableEquipement() {
		switch(ln) {
		case "fr" : {
			return liste.get(12).fr;
		}
		case "eng" :{
			return liste.get(12).eng;
		}
		case "mlg" : {
			return liste.get(12).mlg;
		}
	}
	return "" ;
	}
	
	
	public static String buttonSearch() {
		switch(ln) {
		case "fr" : {
			return liste.get(13).fr;
		}
		case "eng" :{
			return liste.get(13).eng;
		}
		case "mlg" : {
			return liste.get(13).mlg;
		}
	}
	return "" ;
	}
	
	
	public static String tcrListePanneLableListeEquipement() {
		switch(ln) {
		case "fr" : {
			return liste.get(14).fr;
		}
		case "eng" :{
			return liste.get(14).eng;
		}
		case "mlg" : {
			return liste.get(14).mlg;
		}
	}
	return "" ;
	}
	
	
	public static String tableCommentaireOperateur() {
		switch(ln) {
		case "fr" : {
			return liste.get(15).fr;
		}
		case "eng" :{
			return liste.get(15).eng;
		}
		case "mlg" : {
			return liste.get(15).mlg;
		}
	}
	return "" ;
	}
	
	public static String tableReparer(){
		switch(ln) {
		case "fr" : {
			return liste.get(16).fr;
		}
		case "eng" :{
			return liste.get(16).eng;
		}
		case "mlg" : {
			return liste.get(16).mlg;
		}
	}
	return "" ;
	}
	
	public static String tableFermer(){
		switch(ln) {
		case "fr" : {
			return liste.get(17).fr;
		}
		case "eng" :{
			return liste.get(17).eng;
		}
		case "mlg" : {
			return liste.get(17).mlg;
		}
	}
	return "" ;
	}
	
	public static String tableCommentaireReparation(){
		switch(ln) {
		case "fr" : {
			return liste.get(18).fr;
		}
		case "eng" :{
			return liste.get(18).eng;
		}
		case "mlg" : {
			return liste.get(18).mlg;
		}
	}
	return "" ;
	}
	
	public static String tCRTypeEnginLabelTypeEngin(){
		switch(ln) {
		case "fr" : {
			return liste.get(19).fr;
		}
		case "eng" :{
			return liste.get(19).eng;
		}
		case "mlg" : {
			return liste.get(19).mlg;
		}
	}
	return "" ;
	}
	
	public static String tCRTypeEnginLabelEquipement(){
		switch(ln) {
		case "fr" : {
			return liste.get(20).fr;
		}
		case "eng" :{
			return liste.get(20).eng;
		}
		case "mlg" : {
			return liste.get(20).mlg;
		}
	}
	return "" ;
	}
	
	
	public static String tCRTypeEnginTestFonction(){
		switch(ln) {
		case "fr" : {
			return liste.get(21).fr;
		}
		case "eng" :{
			return liste.get(21).eng;
		}
		case "mlg" : {
			return liste.get(21).mlg;
		}
	}
	return "" ;
	}
	
	public static String tableResponsable() {
		switch(ln) {
		case "fr" : {
			return liste.get(22).fr;
		}
		case "eng" :{
			return liste.get(22).eng;
		}
		case "mlg" : {
			return liste.get(22).mlg;
		}
	}
	return "" ;
	}
	
	
	public static String bAjouter() {
		switch(ln) {
		case "fr" : {
			return liste.get(23).fr;
		}
		case "eng" :{
			return liste.get(23).eng;
		}
		case "mlg" : {
			return liste.get(23).mlg;
		}
	}
	return "" ;
	}
	
	public static String bEnregistrer() {
		switch(ln) {
		case "fr" : {
			return liste.get(24).fr;
		}
		case "eng" :{
			return liste.get(24).eng;
		}
		case "mlg" : {
			return liste.get(24).mlg;
		}
	}
	return "" ;
	}
	

	public static String tableCritique() {
		switch(ln) {
		case "fr" : {
			return liste.get(25).fr;
		}
		case "eng" :{
			return liste.get(25).eng;
		}
		case "mlg" : {
			return liste.get(25).mlg;
		}
	}
	return "" ;
	}
	
	public static String tCREnginLabelEngin() {
		switch(ln) {
		case "fr" : {
			return liste.get(26).fr;
		}
		case "eng" :{
			return liste.get(26).eng;
		}
		case "mlg" : {
			return liste.get(26).mlg;
		}
	}
	return "" ;
	}

	public static String tableNom() {
		switch(ln) {
		case "fr" : {
			return liste.get(27).fr;
		}
		case "eng" :{
			return liste.get(27).eng;
		}
		case "mlg" : {
			return liste.get(27).mlg;
		}
	}
	return "" ;
	}

	
	public static String tableAdresse() {
		switch(ln) {
		case "fr" : {
			return liste.get(28).fr;
		}
		case "eng" :{
			return liste.get(28).eng;
		}
		case "mlg" : {
			return liste.get(28).mlg;
		}
	}
	return "" ;
	}
	
	public static String tableMail() {
		switch(ln) {
		case "fr" : {
			return liste.get(29).fr;
		}
		case "eng" :{
			return liste.get(29).eng;
		}
		case "mlg" : {
			return liste.get(29).mlg;
		}
	}
	return "" ;
	}
	
	
	public static String tableTelephone() {
		switch(ln) {
		case "fr" : {
			return liste.get(30).fr;
		}
		case "eng" :{
			return liste.get(30).eng;
		}
		case "mlg" : {
			return liste.get(30).mlg;
		}
	}
	return "" ;
	}
	
	public static String tableType() {
		switch(ln) {
		case "fr" : {
			return liste.get(31).fr;
		}
		case "eng" :{
			return liste.get(31).eng;
		}
		case "mlg" : {
			return liste.get(31).mlg;
		}
	}
	return "" ;
	}

	public static String tableID() {
		switch(ln) {
		case "fr" : {
			return liste.get(32).fr;
		}
		case "eng" :{
			return liste.get(32).eng;
		}
		case "mlg" : {
			return liste.get(32).mlg;
		}
	}
	return "" ;
	}
	
	
	public static String tablePassword() {
		switch(ln) {
		case "fr" : {
			return liste.get(33).fr;
		}
		case "eng" :{
			return liste.get(33).eng;
		}
		case "mlg" : {
			return liste.get(33).mlg;
		}
	}
	return "" ;
	}
	
	public static String user() {
		switch(ln) {
		case "fr" : {
			return liste.get(34).fr;
		}
		case "eng" :{
			return liste.get(34).eng;
		}
		case "mlg" : {
			return liste.get(34).mlg;
		}
	}
	return "" ;
	}
	
	public static String messageSave() {
		switch(ln) {
		case "fr" : {
			return liste.get(35).fr;
		}
		case "eng" :{
			return liste.get(35).eng;
		}
		case "mlg" : {
			return liste.get(35).mlg;
		}
	}
	return "" ;
	}
	
	public static String messageUpdate() {
		switch(ln) {
		case "fr" : {
			return liste.get(36).fr;
		}
		case "eng" :{
			return liste.get(36).eng;
		}
		case "mlg" : {
			return liste.get(36).mlg;
		}
	}
	return "" ;
	}
	
	public static String messageAjouter() {
		switch(ln) {
		case "fr" : {
			return liste.get(37).fr;
		}
		case "eng" :{
			return liste.get(37).eng;
		}
		case "mlg" : {
			return liste.get(37).mlg;
		}
	}
	return "" ;
	}
	
	public static String messageDeverouiller() {
		switch(ln) {
		case "fr" : {
			return liste.get(38).fr;
		}
		case "eng" :{
			return liste.get(38).eng;
		}
		case "mlg" : {
			return liste.get(38).mlg;
		}
	}
	return "" ;
	}
	
	public static String messageErreur() {
		switch(ln) {
		case "fr" : {
			return liste.get(39).fr;
		}
		case "eng" :{
			return liste.get(39).eng;
		}
		case "mlg" : {
			return liste.get(39).mlg;
		}
	}
	return "" ;
	}
	
	public static String confirmationSuppr() {
		switch(ln) {
		case "fr" : {
			return liste.get(40).fr;
		}
		case "eng" :{
			return liste.get(40).eng;
		}
		case "mlg" : {
			return liste.get(40).mlg;
		}
	}
	return "" ;
	}
	
	public static String confirmationUpdate() {
		switch(ln) {
		case "fr" : {
			return liste.get(41).fr;
		}
		case "eng" :{
			return liste.get(41).eng;
		}
		case "mlg" : {
			return liste.get(41).mlg;
		}
	}
	return "" ;
	}
	
	public static String confirmationEdit() {
		switch(ln) {
		case "fr" : {
			return liste.get(42).fr;
		}
		case "eng" :{
			return liste.get(42).eng;
		}
		case "mlg" : {
			return liste.get(42).mlg;
		}
	}
	return "" ;
	}
	
	public static String confirmationEnvoye() {
		switch(ln) {
		case "fr" : {
			return liste.get(43).fr;
		}
		case "eng" :{
			return liste.get(43).eng;
		}
		case "mlg" : {
			return liste.get(43).mlg;
		}
	}
	return "" ;
	}
	
	public static String confirmation() {
		switch(ln) {
		case "fr" : {
			return liste.get(44).fr;
		}
		case "eng" :{
			return liste.get(44).eng;
		}
		case "mlg" : {
			return liste.get(44).mlg;
		}
	}
	return "" ;
	}
	
	public static String drawerAccueilBficheVerification() {
		switch(ln) {
		case "fr" : {
			return liste.get(45).fr;
		}
		case "eng" :{
			return liste.get(45).eng;
		}
		case "mlg" : {
			return liste.get(45).mlg;
		}
	}
	return "" ;
	}
	
	public static String drawerAccueilBPannes() {
		switch(ln) {
		case "fr" : {
			return liste.get(46).fr;
		}
		case "eng" :{
			return liste.get(46).eng;
		}
		case "mlg" : {
			return liste.get(46).mlg;
		}
	}
	return "" ;
	}
	
	public static String drawerAccueiBTypeEngin() {
		switch(ln) {
		case "fr" : {
			return liste.get(47).fr;
		}
		case "eng" :{
			return liste.get(47).eng;
		}
		case "mlg" : {
			return liste.get(47).mlg;
		}
	}
	return "" ;
	}
	
	public static String drawerAccueiBEngin() {
		switch(ln) {
		case "fr" : {
			return liste.get(48).fr;
		}
		case "eng" :{
			return liste.get(48).eng;
		}
		case "mlg" : {
			return liste.get(48).mlg;
		}
	}
	return "" ;
	}
	
	public static String drawerAccueiBUser() {
		switch(ln) {
		case "fr" : {
			return liste.get(49).fr;
		}
		case "eng" :{
			return liste.get(49).eng;
		}
		case "mlg" : {
			return liste.get(49).mlg;
		}
	}
	return "" ;
	}
	
	
	public static String drawerAccueilConfiguration() {
		switch(ln) {
		case "fr" : {
			return liste.get(50).fr;
		}
		case "eng" :{
			return liste.get(50).eng;
		}
		case "mlg" : {
			return liste.get(50).mlg;
		}
	}
	return "" ;
	}
	
	public static String OPFicheObservation() {
		switch(ln) {
		case "fr" : {
			return liste.get(51).fr;
		}
		case "eng" :{
			return liste.get(51).eng;
		}
		case "mlg" : {
			return liste.get(51).mlg;
		}
	}
	return "" ;
	}
	//ici
	public static String erroLogin() {
		switch(ln) {
		case "fr" : {
			return liste.get(52).fr;
		}
		case "eng" :{
			return liste.get(52).eng;
		}
		case "mlg" : {
			return liste.get(52).mlg;
		}
	}
	return "" ;
	}
	public static String erroLogin2() {
		switch(ln) {
		case "fr" : {
			return liste.get(53).fr;
		}
		case "eng" :{
			return liste.get(53).eng;
		}
		case "mlg" : {
			return liste.get(53).mlg;
		}
	}
	return "" ;
	}
	//echec de la connexion"
	
	public static String errorConnection() {
		switch(ln) {
		case "fr" : {
			return liste.get(54).fr;
		}
		case "eng" :{
			return liste.get(54).eng;
		}
		case "mlg" : {
			return liste.get(54).mlg;
		}
	}
	return "" ;
	}
	
	
	public static String fieldEmpty() {
		switch(ln) {
		case "fr" : {
			return liste.get(55).fr;
		}
		case "eng" :{
			return liste.get(55).eng;
		}
		case "mlg" : {
			return liste.get(55).mlg;
		}
	}
	return "" ;
	}
	
	public static String loginUser() {
		switch(ln) {
		case "fr" : {
			return liste.get(56).fr;
		}
		case "eng" :{
			return liste.get(56).eng;
		}
		case "mlg" : {
			return liste.get(56).mlg;
		}
	}
	return "" ;
	}
		
	public static String loginPass() {
		switch(ln) {
		case "fr" : {
			return liste.get(57).fr;
		}
		case "eng" :{
			return liste.get(57).eng;
		}
		case "mlg" : {
			return liste.get(57).mlg;
		}
	}
	return "" ;
	}
	
	public static String loginOK() {
		switch(ln) {
		case "fr" : {
			return liste.get(58).fr;
		}
		case "eng" :{
			return liste.get(58).eng;
		}
		case "mlg" : {
			return liste.get(58).mlg;
		}
	}
	return "" ;
	}
	
	
	public static String configEnregistrer() {
		switch(ln) {
		case "fr" : {
			return liste.get(59).fr;
		}
		case "eng" :{
			return liste.get(59).eng;
		}
		case "mlg" : {
			return liste.get(59).mlg;
		}
	}
	return "" ;
	}
	//setPlaceholder
	
	
	public static String vide() {
		switch(ln) {
		case "fr" : {
			return liste.get(60).fr;
		}
		case "eng" :{
			return liste.get(60).eng;
		}
		case "mlg" : {
			return liste.get(60).mlg;
		}
	}
	return "" ;
	}
	public static String tableDateCreation() {
		switch(ln) {
		case "fr" : {
			return liste.get(61).fr;
		}
		case "eng" :{
			return liste.get(61).eng;
		}
		case "mlg" : {
			return liste.get(61).mlg;
		}
	}
	return "" ;
	}
	
	public static String tableDateModification() {
		switch(ln) {
		case "fr" : {
			return liste.get(62).fr;
		}
		case "eng" :{
			return liste.get(62).eng;
		}
		case "mlg" : {
			return liste.get(62).mlg;
		}
	}
	return "" ;
	}
	
	public static String createur() {
		switch(ln) {
		case "fr" : {
			return liste.get(63).fr;
		}
		case "eng" :{
			return liste.get(63).eng;
		}
		case "mlg" : {
			return liste.get(63).mlg;
		}
	}
	return "" ;
	}
	
	public static String modificateur() {
		switch(ln) {
		case "fr" : {
			return liste.get(64).fr;
		}
		case "eng" :{
			return liste.get(64).eng;
		}
		case "mlg" : {
			return liste.get(64).mlg;
		}
	}
	return "" ;
	}
	
	public static String supprimer() {
		switch(ln) {
		case "fr" : {
			return liste.get(65).fr;
		}
		case "eng" :{
			return liste.get(65).eng;
		}
		case "mlg" : {
			return liste.get(65).mlg;
		}
	}
	return "" ;
	}
}
