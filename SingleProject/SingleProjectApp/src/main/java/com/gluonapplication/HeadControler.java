package com.gluonapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

public class HeadControler  implements Initializable{


    @FXML
    private AnchorPane pane;
  
    @FXML
    private JFXButton bexit;
    

    @FXML
    private JFXButton bdrawer;

    @FXML
    private JFXDrawer drawer;
    

    @FXML
    private Label labelTitle;
    

    @FXML
    private JFXButton bMaximize;

    @FXML
    private FontAwesomeIconView fontMaximze;
    
    public static BooleanProperty isDrawerDisable ;
    static {
    	System.out.println("HEAD  STATIC!!!");
    	new CONFIG();
    }

	@Override
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
    	System.out.println("HEAD INIT  !!!");

    	//bexit.visibleProperty().bind(CONFIG.isExitVisible);
    	//bMaximize.visibleProperty().bind(CONFIG.isExitVisible);
    	
    	bexit.setVisible(false);
    	bMaximize.setVisible(false);
		isDrawerDisable = new SimpleBooleanProperty(true);
		Region reg;
		bdrawer.toFront();
		bdrawer.visibleProperty().bind(isDrawerDisable.not());
		bexit.toFront();

		try {
//			Login
//			TCRListePanne
//			TCRTypeEngin
//			OPFicheVerification
			reg = FXMLLoader.load(getClass().getResource("/Login.fxml"));
//			labelTitle.setText("Type engin");
			pane.getChildren().clear();
			pane.getChildren().add(reg);
			pane.setVisible(true);
			reg.prefWidthProperty().bind(pane.widthProperty());
			reg.prefHeightProperty().bind(pane.heightProperty());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		initDrawer();
		drawer.toBack();
		
		bexit.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			exit();
		});
		
		bdrawer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
	    	drawer.toFront();
			drawer.open();
		});
	}

	public void initDrawer() {
		AnchorPane pane = null ;
		try {
			pane = FXMLLoader.load(getClass().getResource("/DrawerAccueil.fxml"));
			pane.prefHeightProperty().bind(pane.heightProperty());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		drawer.setSidePane(pane);
		for(Node node : pane.getChildren()) {
			if(node.getAccessibleText()!= null) {
				node.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
					switch (node.getAccessibleText()){
					case "B_FICHE" : 
						changePane("/OPFicheVerification.fxml");
						labelTitle.setText(Langage.drawerAccueilBficheVerification());
						drawer.close();
					break;
					
					case "B_PANNE" : 
						changePane("/TCRListePanne.fxml");
						labelTitle.setText(Langage.drawerAccueilBPannes());
						drawer.close();
					break;
					
					case "B_TYPE_ENGIN" : 
						changePane("/TCRTypeEngin.fxml");
						labelTitle.setText(Langage.drawerAccueiBTypeEngin());
						drawer.close();
					break;
					
					case "B_USER" :
						changePane("/User.fxml");
						labelTitle.setText(Langage.drawerAccueiBUser());
						drawer.close();
					break;
					
					case "B_CONFIG" : 
						labelTitle.setText(Langage.drawerAccueilConfiguration());
						changePane("/Parametre.fxml");
						drawer.close();
					break;
					
					case "B_EXIT" : 
						changePane("/Login.fxml");
						labelTitle.setText("");
						drawer.close();
						
						isDrawerDisable.set(true);
						CONFIG.disableAll();
					break;
					case "B_ENGIN" : 
						changePane("/TCREngin.fxml");
						labelTitle.setText(Langage.drawerAccueiBEngin());
						drawer.close();
					break;

					}
				});
			}
		}
	}
	public void exit() {
		Platform.exit();
		System.exit(0);
	}
	
    @FXML
    void onCllosed() {
    	drawer.toBack();
    }

	public void changePane(String fxml) {
		Region reg;
		try {
			reg = FXMLLoader.load(getClass().getResource(fxml));
			pane.getChildren().clear();
			pane.getChildren().add(reg);
			pane.setVisible(true);
			reg.prefWidthProperty().bind(pane.widthProperty());
			reg.prefHeightProperty().bind(pane.heightProperty());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @FXML
    void onOpened() {

    }
}

