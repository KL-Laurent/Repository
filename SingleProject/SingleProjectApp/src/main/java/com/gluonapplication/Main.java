package com.gluonapplication;
	
import java.io.IOException;

import javafx.application.Application;
import com.gluonhq.charm.down.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;


public class Main extends Application {

    @Override
    public void init() throws Exception {
        super.init();
    } 
    
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/Head.fxml"));
		Scene scene = new Scene(root);
		
		 //primaryStage.initStyle(StageStyle.DECORATED.UNDECORATED);
		primaryStage.setScene(scene);
		setScreenSize(primaryStage);
		primaryStage.show();
	}
	
    @Override
    public void stop() throws Exception {
        super.stop();
    } 
    
    void setScreenSize(Stage primaryStage){
        if(Platform.isAndroid() || Platform.isIOS()){
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            primaryStage.setX(primaryScreenBounds.getMinX());
            primaryStage.setY(primaryScreenBounds.getMinY());
            primaryStage.setWidth(primaryScreenBounds.getWidth());
            primaryStage.setHeight(primaryScreenBounds.getHeight());
        }
        else{
            primaryStage.setWidth(800);
            primaryStage.setHeight(600);
        }
    }
    
	public static void main(String[] args) {
		launch(args);
	}
	
}
