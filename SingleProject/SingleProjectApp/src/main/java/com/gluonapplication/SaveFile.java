package com.gluonapplication;

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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SaveFile {
	
	// pour ecrire des object dans un file (sérealizer l'objet) sérealisation IOS lecture OOS ecriture  
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
    
	public SaveFile() throws IOException {
//		System.out.println("Chemin absolu du fichier : " + f.getAbsolutePath());
//		System.out.println("Nom du fichier : " + f.getName());
//		System.out.println("Est-ce qu'il existe ? " + f.exists());
//		System.out.println("Est-ce un répertoire ? " + f.isDirectory());
//		System.out.println("Est-ce un fichier ? " + f.isFile());

	}
	
	public void ecriture(ObservableList<String> allTxt) throws IOException {
		File f = new File("./src/main/resources/ressource/IOFile.kl");
		fw = new FileWriter(f);
		String str ="";
		for(int i = 0 ; i < allTxt.size();i++) {
			str += allTxt.get(i)+"\n"; 
		}
	
		String temp = "" ;
		for(int i = 0 ; i < str.length();i++) {
			temp += cryptage(str.charAt(i));
		}
		System.out.print(temp);
		fw.write(temp);
		fw.close();
	}
	
	public void ecritureFile2(ObservableList<String> allTxt) throws IOException {
		File f = new File("./src/main/resources/ressource/IOFile2.kl");
		fw = new FileWriter(f);
		String str ="";
		for(int i = 0 ; i < allTxt.size();i++) {
			str += allTxt.get(i)+"\n"; 
		}
	
		String temp = "" ;
		for(int i = 0 ; i < str.length();i++) {
			temp += cryptage(str.charAt(i));
		}
		System.out.print(temp);
		fw.write(temp);
		fw.close();
	}
	
	
	public ObservableList<String> lecture() throws IOException {
		ObservableList<String> file = FXCollections.observableArrayList(); 
		File f = new File("./src/main/resources/ressource/IOFile.kl");
		fr = new FileReader(f);
		String str = "" ;
		int n = 0 ;
		
		while((n = fr.read())!=-1) {
			str+=decryptage((char)n);
		}
		fr.close();
		
		String all[] = str.split("\n");
		for(int i = 0 ; i < all.length; i++) {
			file.add(all[i].trim());
		}
		return file ;
	}
	
	public ObservableList<String> lectureFile2() throws IOException {
		ObservableList<String> file = FXCollections.observableArrayList(); 
		File f = new File("./src/main/resources/ressource/IOFile2.kl");
		fr = new FileReader(f);
		String str = "" ;
		int n = 0 ;
		
		while((n = fr.read())!=-1) {
			str+=decryptage((char)n);
		}
		fr.close();
		
		String all[] = str.split("\n");
		for(int i = 0 ; i < all.length; i++) {
			file.add(all[i].trim());
		}
		return file ;
	}
	
	private char cryptage(char c) {
		return (char)(((byte)c)+4);
		//return c;
	}
	
	private char decryptage(char c) {
		return (char)(((byte)c)-4);
		//return c; 
	}
	
}
