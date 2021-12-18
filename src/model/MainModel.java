//package model;
//
//import controller.GridController;
//import controller.MainController;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.TextInputDialog;
//
//import java.io.Serializable;
//import java.util.Random;
//import java.util.Timer;
///** 
// * This class is the main model class of the minesweeper game.
// * 
// * @author Yanxu Wu, Zhenyi Qian,  Qi Zhang, Jingyu Xin
// */
//public class MainModel implements Serializable {
//    private static final long serialVersionUID = 1L;
//    public GridController[][] grid;
//    public MainController controller;
//    public Timer timer;
//    public int spentTime = 0, openedCells = 0, totalMines = 0, unflaggedMines = 0;
//    public boolean isNetwork;
//    public boolean isClient;
//
//    /**
//	 * Constructor
//	 */
//    public MainModel(MainController controller){
//        this.controller = controller;
//    }
//    
//    
//    /**
//	 * init the game.
//	 */
//    public void init(){
//    	
//    	isNetwork = false;
//    	isClient = false;
//    	
//    	
//        spentTime = 0;
//        openedCells = 0;
//        putNeighborMines();
//        controller.addEventHandler();
//        totalMines = 0;
//        for (int i = 0; i < controller.N; i++) {
//            for (int j = 0; j < controller.M; j++) {
//                if (grid[i][j].cell.isMine()) {
//                	totalMines++;
//                }
//            }
//        }
//        controller.setTimer();
//        unflaggedMines = totalMines;
//    }
//    
//    /**
//	 * put neighbor mines on cell.
//	 */
//    public void putNeighborMines() {
//        for (int i = 0; i < controller.N; i++) {
//            for (int j = 0; j < controller.M; j++) {
//                if (grid[i][j].cell.isMine()) {
//                	grid[i][j].cell.neighborMines = -1;}
//                grid[i][j].cell.neighborMines = 0;
//                for (int r = i - 1; r <= i + 1; r++) {
//                    for (int c = j - 1; c <= j + 1; c++) {
//                        if (controller.isValid(r, c) && grid[r][c].cell.isMine()) {
//                        	grid[i][j].cell.neighborMines++;}}}    
//            }
//        }
//    }
//    
//    /**
//	 * check if the game is won.
//	 * @return boolean
//	 */  
//    public boolean win(int spentTime) {
//    	if ((openedCells + totalMines) == controller.N * controller.M) {
//            timer.cancel();
//            TextInputDialog dialog = new TextInputDialog("");
////            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            // Traditional way to get the response value.
//
////            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("New Game");
//            dialog.setTitle("YOU WON");
//            dialog.setHeaderText("Congrats!\n\nTime spent in game: " + spentTime + "s");
//            dialog.setContentText("Please enter you name:");
//            // The Java 8 way to get the response value (with lambda expression).
//            dialog.showAndWait().ifPresent(name -> {
//                controller.addScoreHistory(new Score(name,spentTime));
//            });
//            return true;
//        }
//        return false;
//    }
//    
//    /**
//	 * check if the game is lost.
//	 * @return boolean
//	 */  
//    public void lose(int spentTime,int openedCellsNumber) {
//    	timer.cancel();
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("New Game");
//        alert.setTitle("YOU LOST");
//        alert.setContentText("Boom!\n\nTime spent in game: " + spentTime + "s "
//                + "\nNumber of cells you opened: " + openedCellsNumber);
//        //open all cells
//        for (int i = 0; i < controller.N; i++) {
//            for (int j = 0; j < controller.M; j++) {
//                grid[i][j].cell.setOpen(true);
//                grid[i][j].init();
//            }
//        }
//        //start new game.
//        alert.showAndWait().ifPresent(response -> {
//            if (response == ButtonType.OK) {
//                controller.setTimer();
//                controller.init(controller.N, controller.M);
//            }
//        });
//    }
//    
//    public void finishset() {
//    	Alert alert = new Alert(Alert.AlertType.WARNING);
//        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("OK");
//        alert.setTitle("You are done");
//        alert.setContentText("Waiting for user play");
//        alert.showAndWait();
//    }
//}
//
package model;

import controller.GridController;
import controller.MainController;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.io.Serializable;
import java.util.Optional;
import java.util.Timer;

/**
 * This class is the main model class of the minesweeper game.
 *
 * @author Yanxu Wu
 */
public class MainModel implements Serializable {
    private static final long serialVersionUID = 1L;
    public GridController[][] grid;
    public transient MainController controller;
    public transient Timer timer;
    public int spentTime = 0, openedCells = 0, totalMines = 0, unflaggedMines = 0;
    public boolean isNetwork;
    public boolean isClient;

    /**
     * Constructor
     */
    public MainModel(MainController controller) {
        this.controller = controller;
    }

    /**
     * init the game.
     */
    public void init() {
    	isNetwork = false;
    	isClient = false;
        spentTime = 0;
        openedCells = 0;
        putNeighborMines();
        controller.addEventHandler();
        totalMines = 0;
        for (int i = 0; i < controller.N; i++) {
            for (int j = 0; j < controller.M; j++) {
                if (grid[i][j].cell.isMine()) {
                    totalMines++;
                }
            }
        }
        unflaggedMines = totalMines;
    }

    /**
     * put neighbor mines on cell.
     */
    public void putNeighborMines() {
        for (int i = 0; i < controller.N; i++) {
            for (int j = 0; j < controller.M; j++) {
                if (grid[i][j].cell.isMine()) {
                    grid[i][j].cell.neighborMines = -1;
                }
                grid[i][j].cell.neighborMines = 0;
                for (int r = i - 1; r <= i + 1; r++) {
                    for (int c = j - 1; c <= j + 1; c++) {
                        if (controller.isValid(r, c) && grid[r][c].cell.isMine()) {
                            grid[i][j].cell.neighborMines++;
                        }
                    }
                }
            }
        }
    }

    /**
     * check if the game is won.
     *
     * @return boolean
     */
    public boolean win(int spentTime) {
        if ((openedCells + totalMines) == controller.N * controller.M) {
            timer.cancel();
            TextInputDialog dialog = new TextInputDialog("");
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            // Traditional way to get the response value.

//            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("New Game");
            dialog.setTitle("YOU WON");
            dialog.setHeaderText("Congrats!\n\nTime spent in game: " + spentTime + "s");
            dialog.setContentText("Please enter you name:");
            // The Java 8 way to get the response value (with lambda expression).
            dialog.showAndWait().ifPresent(name -> {
                controller.addScoreHistory(new Score(name,spentTime));
            });
            return true;
        }
        return false;
    }
    
  public void finishset() {
	  Alert alert = new Alert(Alert.AlertType.WARNING);
	  ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("OK");
	  alert.setTitle("You are done");
	  alert.setContentText("Waiting for user play");
	  alert.showAndWait();
}

    /**
     * check if the game is lost.
     *
     * @return boolean
     */
    public void lose(int spentTime, int openedCellsNumber) {
        timer.cancel();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("New Game");
        alert.setTitle("YOU LOST");
        alert.setContentText("Boom!\n\nTime spent in game: " + spentTime + "s "
                + "\nNumber of cells you opened: " + openedCellsNumber);
        //open all cells
        for (int i = 0; i < controller.N; i++) {
            for (int j = 0; j < controller.M; j++) {
                grid[i][j].cell.setOpen(true);
                grid[i][j].init();
            }
        }
        //start new game.
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                controller.setTimer();
                controller.init(controller.N, controller.M);
            }
        });
    }

}
