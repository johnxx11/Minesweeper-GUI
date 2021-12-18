package network;
/** 
 * This class is the controller for network
 * 
 * @author Qi Zhang; Zhenyi Qian
 */

import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.IOException;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;

import java.net.Socket;


import controller.MainController;
import controller.networkController;
//import ReversiController.ReversiController;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.MainModel;


public class networking extends Stage {
	
	private Rectangle2D screen;
    private MainModel model;
    private networkController netcontroller;

	
	public networking(MainModel mainmodel) {
		this.model = mainmodel;
		this.netcontroller = new networkController(mainmodel);
	}
	public void start(Stage secondaryStage) {
		this.screen = Screen.getPrimary().getVisualBounds(); // Screen bounds
		
		secondaryStage.initModality(Modality.APPLICATION_MODAL);
		Pane window = new Pane();
		// Create HBox
		HBox cbox = new HBox();
		
		Label create = new Label("Create:  ");
		create.setFont(new Font(15));
		create.setAlignment(Pos.CENTER);
		
		ToggleGroup createToggle = new ToggleGroup();
		
		RadioButton server = new RadioButton("Server  ");
		server.setSelected(true);
		RadioButton client = new RadioButton("Client  ");
		
		server.setToggleGroup(createToggle);
		client.setToggleGroup(createToggle);
				
		cbox.getChildren().add(create);
		cbox.getChildren().add(server);
		cbox.getChildren().add(client);
		cbox.relocate(15, 20);
		window.getChildren().add(cbox);
		

		// Button HBox
		HBox Bbox = new HBox();
		Button ok = new Button("OK");
		Label space = new Label(" ");
		Button cancel = new Button("Cancel");
		Bbox.getChildren().add(ok);
		
        ok.setOnAction((event) -> {
        	
        	String createOrJoin = ((RadioButton)createToggle.getSelectedToggle()).getText().trim();// to stroe "server" or "client"
        	
        	try {
//        		int port = Integer.parseInt(psTX.getText());
        		// Starts a network game
        		model.isNetwork = true;
        		System.out.println("server:"+createOrJoin);
        		if( !createOrJoin.equals("Server")) {
        			model.isClient = true;
        			model.controller.init_netserver(16,16);
        			netcontroller.clientset();
        
        		}else {
        			model.controller.init_netserver(16,16);
        			model.isClient = false;
        			netcontroller.serverset(model);
        		}

        		secondaryStage.close();
        	
        	} catch (NumberFormatException e) {
        		e.printStackTrace();
        		new Alert(AlertType.ERROR, "Invalid port number format").showAndWait();
        	}
        	
        });
        
        
		Bbox.getChildren().add(space);
		
		Bbox.getChildren().add(cancel);
        cancel.setOnAction((event) -> {
        	secondaryStage.close(); // Close Stage
        });
        
		Bbox.relocate(15, 60);
		window.getChildren().add(Bbox);
		
		window.setStyle("-fx-background-color: white");
		secondaryStage.setScene(new Scene(window, 450, 100));
        secondaryStage.setTitle("Network Setup");
        secondaryStage.showAndWait();
	}
	
}
