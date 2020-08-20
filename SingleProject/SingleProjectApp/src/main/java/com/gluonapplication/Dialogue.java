package com.gluonapplication;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Dialogue extends Application{

	

	
	public Dialogue() {

	    JFXDialogLayout content= new JFXDialogLayout();
	    content.setHeading(new Text("Error, No selection"));
	    content.setBody(new Text("No student selected"));
	    StackPane stackpane = new StackPane();
	    JFXDialog dialog =new JFXDialog(stackpane, content, JFXDialog.DialogTransition.CENTER);
	    JFXButton button=new JFXButton("Okay");
	    button.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
            notify();
	    });

	    content.setActions(button);

	    Scene scene = new Scene(stackpane, 300, 250);
		Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
