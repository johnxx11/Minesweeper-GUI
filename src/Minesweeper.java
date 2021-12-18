import controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** 
 * This class is the main class of the minesweeper game.
 * 
 * @author Yanxu Wu
 */
public class Minesweeper extends Application {
    private int N = 16;
    private int M = 16;
	/**
	 * start class will set the GUI view for this game
	 */
    @Override
    public void start(Stage stage){
        MainController mainController = new MainController(N,M);
        //Set title and scene.
        stage.setTitle("Minesweeper");
        stage.setScene(new Scene(mainController.mainView, 800, 610));
        stage.show();
        //quit game
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);});
    }
	/**
	 * main function
	 * @param args arg
	 */
    public static void main(String[] args) {
        launch(args);
    }
}


