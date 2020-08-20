package com.gluonapplication;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class messageBar {

	public  messageBar(String message,int duration , AnchorPane pane) {
		System.out.print("BARRR  !!!! ");
		JFXSnackbar bar = new JFXSnackbar(pane);
		final double MAX_FONT_SIZE = 20.0; // define max font size
		Labeled label = new Label();
		label.setFont(new Font(MAX_FONT_SIZE));
		label.setText(message);
		label.setStyle("-fx-background-color :#00000099;-fx-text-fill : #ffffff;-fx-background-radius : 0em;");
		bar.enqueue(new SnackbarEvent(label,Duration.seconds(duration),null));
	}

}
