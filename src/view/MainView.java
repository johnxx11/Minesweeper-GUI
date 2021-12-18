package view;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.io.Serializable;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import network.networking;
/** 
 * This class is the main view class of the minesweeper game.
 * 
 * @author Yanxu Wu ;  Qi Zhang
 */

public class MainView extends BorderPane  implements Serializable {
    //Title Pane
    private AnchorPane titlePane = new AnchorPane();
    private Label title = new Label("Minesweeper");
    // info & buttons Pane
    private VBox info = new VBox();
    private VBox buttons = new VBox();
    //grid Pane
    public GridPane gridPane = new GridPane();
    //time pane
    private AnchorPane timePane = new AnchorPane();
    public Label timeLabel = new Label(); 
    private Label timeSpentLabel = new Label("Time Spent");
    //cells status pane
    private AnchorPane cellsPane = new AnchorPane();
    public Label openedCells = new Label();
    private Label cellsLabel = new Label("Opened/Total Cells");
    //remaining mines status pane
    private AnchorPane minePane = new AnchorPane();
    public Label remainingMines = new Label();
    private Label minesLabel = new Label("Unflagged/Total Mines");
    //restart
    public Button restart = new Button("New Game");
    public Button exit = new Button("Exit");
    
    public Button rank = new Button("Score Board");
    public Button save = new Button("Save Game");
    public Button resume = new Button("Resume");
    
    public Button netpaly = new Button("Network play");
	private networking network;

    
	/**
	 * This start class will set the GUI view for this game
	 * 
	 * @param MainController mainController
	 */
    public MainView(MainController mainController){
        //set Pane Position
        this.setTop(titlePane);
        this.setRight(info);
        this.setCenter(gridPane);
        this.setPadding(new Insets(20,30,20,30));
        //set Font
        Font ft = new Font("Arial", 25);
        //set TitlePane
        title.setFont(ft);
        //set gridPane
        gridPane.setPadding(new Insets(15,0,0,0));
        //set statusPane
        info.getChildren().addAll(timePane,minePane,cellsPane,buttons);
        info.setSpacing(3);
        
        //set timePane
        timeSpentLabel.setLayoutX(50);
        timeSpentLabel.setLayoutY(20);
        timeLabel.setFont(ft);
        timeLabel.setLayoutX(55);
        timeLabel.setLayoutY(35);
        timePane.getChildren().addAll(timeLabel,timeSpentLabel);
        
        //set minePane
        minesLabel.setLayoutX(50);
        minesLabel.setLayoutY(20);
        remainingMines.setFont(ft);
        remainingMines.setLayoutX(50);
        remainingMines.setLayoutY(35);
        minePane.getChildren().addAll(minesLabel,remainingMines);
        
        //set openedCellsPane
        cellsLabel.setLayoutX(50);
        cellsLabel.setLayoutY(20);
        openedCells.setFont(ft);
        openedCells.setLayoutX(50);
        openedCells.setLayoutY(35);
        cellsPane.getChildren().addAll(openedCells,cellsLabel);

        //set btnPane
        buttons.getChildren().addAll(restart,exit,netpaly,rank,save,resume);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(160,0,0,0));
        
        exit.setPrefSize(150,35);
        exit.setStyle("-fx-font-weight: bold;");
        exit.setTextFill(Color.BLACK);
        
        restart.setPrefSize(150,35);
        restart.setStyle("-fx-font-weight: bold;");
        restart.setTextFill(Color.BLACK);
        
        netpaly.setPrefSize(150,35);
        netpaly.setStyle("-fx-font-weight: bold;");
        netpaly.setTextFill(Color.BLACK);
        
        rank.setPrefSize(150,35);
        rank.setStyle("-fx-font-weight: bold;");
        rank.setTextFill(Color.BLACK);
        
        save.setPrefSize(150, 35);
        save.setStyle("-fx-font-weight: bold;");
        save.setTextFill(Color.BLACK);

        resume.setPrefSize(150, 35);
        resume.setStyle("-fx-font-weight: bold;");
        resume.setTextFill(Color.BLACK);
    }
    

}
