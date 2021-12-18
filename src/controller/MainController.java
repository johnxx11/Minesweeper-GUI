package controller;

import model.MainModel;
import model.Score;
import network.networking;
import view.MainView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import network.networking;

/** 
 * This class is the main controller class of the minesweeper game.
 * 
 * @author Yanxu Wu, Zhenyi Qian,  Qi Zhang, Jingyu Xin
 */

public class MainController implements Serializable {
    private static final long serialVersionUID = 1L;
    public int N, M;
    public MainView mainView;
    public MainModel mainModel;
    public int setmine=0;
    public boolean firstclick;
    public networkController networkcontrol;
    public int netminesnum = 0;
    public String resultofsend = "";
    /**
	 * Constructor
	 * @param int N, M
	 */
    public MainController(int N, int M) {
    	networkcontrol = new networkController(mainModel);
    	mainModel = new MainModel(this);
    	mainView = new MainView(this);
    	firstclick=true;
        setTimer();
        this.N = N;
        this.M = M;
        init(N, M);
        mainView.restart.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            init(N, M);setfirsrtclick(true);});
        
        mainView.exit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Platform.exit();
            System.exit(0);});
   
    	mainView.netpaly.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
    		
    		networking network = new networking(mainModel);
        	Stage secondaryStage = new Stage();
        	network.start(secondaryStage);
    	    });
    	
    	mainView.resume.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                FileInputStream gameInput = new FileInputStream("Game_data.dat");
                ObjectInputStream gameObj = new ObjectInputStream(gameInput);
                MainModel tmp = (MainModel) gameObj.readObject();
                gameObj.close();
                mainModel.grid = tmp.grid;
                for (int i = 0; i < N; i++)
                    for (int j = 0; j < M; j++) {
                        mainModel.grid[i][j].init();
                        mainView.gridPane.add(mainModel.grid[i][j].gridView, i, j, 1, 1);
                    }

                this.mainModel.spentTime = tmp.spentTime;
                this.mainModel.openedCells = tmp.openedCells;
                this.mainModel.totalMines = tmp.totalMines;
                this.mainModel.unflaggedMines = tmp.unflaggedMines;

                mainView.openedCells.setText(mainModel.openedCells + "/" + N * M);
                mainView.remainingMines.setText(mainModel.unflaggedMines + "/" + mainModel.totalMines);
                mainView.timeLabel.setTextFill(Color.BLACK);
                addEventHandler();
            } catch (FileNotFoundException fileNotFound) {
            	Alert alert = new Alert(Alert.AlertType.WARNING);
          	  	((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("OK");
          	  	alert.setTitle("No Game data in local!");
          	  	alert.setContentText("Waiting for save");
          	  	alert.showAndWait();
            } catch (IOException io) {
                io.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });
    	
    	mainView.save.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            File gameData = new File("Game_data.dat");
            FileOutputStream gameFile;
            try {
                gameFile = new FileOutputStream(gameData);
                ObjectOutputStream gameOut = new ObjectOutputStream(gameFile);
                gameOut.writeObject(this.mainModel);

                gameOut.close();
            } catch (IOException io) {
                io.printStackTrace();
            }
        });
        mainView.rank.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("Game_Score_History.dat"));
                List<Score> scoreList = new ArrayList<>();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] scoreArr = line.split(" ");
                    scoreList.add(new Score(scoreArr[0], Integer.parseInt(scoreArr[1])));
                }
                scoreList.sort((o1, o2) -> {
                    return o1.getSpentTime() - o2.getSpentTime();
                });
                VBox rankList = new VBox();

                rankList.setSpacing(3);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < scoreList.size(); i++) {
                    Score score = scoreList.get(i);
                    rankList.getChildren().add(new Label((i + 1) + "       " + score.getUsername() + " " + score.getSpentTime() + "s"));
                }
                Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                dialog.setTitle("Score Rank");
                dialog.setHeaderText("");
                dialog.getDialogPane().setContent(rankList);
                ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                Optional<ButtonType> result = dialog.showAndWait();
                // The Java 8 way to get the response value (with lambda expression).
            } catch (FileNotFoundException fileNotFoundException) {
            	Alert alert = new Alert(AlertType.INFORMATION);
            	alert.titleProperty().set("No Score Yet!");
            	alert.headerTextProperty().set("Please try to win the game first");
            	alert.showAndWait();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
    
    /**
     * check this click is first time or not
     * @return boolean
     */
    public boolean getfirsrtclick() {
    	return firstclick;
    }
    
    /**
     * change the status of first click or not.
     * @param first boolean
     */
    public void setfirsrtclick(boolean first) {
    	firstclick=first;
    }
    
    /**
	 * init main controller
	 * @param int N, M
	 */
    public void init(int N, int M) {
    	firstclick=true;
    	mainModel.grid = new GridController[N][M];
        //create cells grid.
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
            	mainModel.grid[i][j] = new GridController();
                mainView.gridPane.add(mainModel.grid[i][j].gridView, i, j, 1, 1);
            }
        
        int count=40;
    	int x,y;
    	Random random=new Random();
    	while(count>0) {
    		x= random.nextInt(16);
    		y=random.nextInt(16);
    		if(!mainModel.grid[x][y].cell.isMine()) {
    			mainModel.grid[x][y].cell.setMine(true);
    			count--;
    		}
    	}
    	
        mainModel.init();
        mainView.openedCells.setText(mainModel.openedCells +"/"+ N*M);
        mainView.remainingMines.setText(mainModel.unflaggedMines+"/"+mainModel.totalMines);
        mainView.timeLabel.setTextFill(Color.BLACK);
    }
    public void init_netserver(int N, int M) {
    	
    	mainModel.grid = new GridController[N][M];
        //create cells grid.
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
            	mainModel.grid[i][j] = new GridController();
                mainView.gridPane.add(mainModel.grid[i][j].gridView, i, j, 1, 1);
                mainModel.grid[i][j].cell.setMine(false);

            }

        mainModel.init();
        mainModel.isNetwork = true;
        mainModel.isClient = true;        
    	mainView.openedCells.setText(mainModel.openedCells +"/"+ N*M);
        mainView.remainingMines.setText(mainModel.unflaggedMines+"/"+mainModel.totalMines);
        mainView.timeLabel.setTextFill(Color.BLACK);
        
        
    }
    /**
	 * Set timer for the game
	 * 
	 */
    public void setTimer() {
    	try {
        	mainModel.timer = new Timer();
        	mainModel.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                    	mainView.timeLabel.setText(mainModel.spentTime + "s");
                    	mainModel.spentTime++;
                    }
                });
            }
            }, 0, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
	 * add EventHandler
	 */
    public void addEventHandler() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int a = i;
                int b = j;
                mainModel.grid[i][j].gridView.x = b;
                mainModel.grid[i][j].gridView.y = a;
                mainModel.grid[i][j].gridView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                	@Override
                    public void handle(MouseEvent mouseEvent) {
                		if(mainModel.isNetwork) {
                			if(mainModel.isClient) {
                				if (mouseEvent.getButton() == MouseButton.PRIMARY) {	
                					if ((!mainModel.grid[a][b].cell.isMine())&&netminesnum <40) {
                						updateGridb(a, b);
                						netminesnum ++;
                						resultofsend += String.valueOf(b);
                						resultofsend += ";";
                						resultofsend += String.valueOf(a)+";";
                						if (netminesnum == 40) { 
                			
                							networkcontrol.datasend(resultofsend);
                							mainModel.finishset();
                						}
                					}
                					
                				}
                			}
                			else {
                				if (mouseEvent.getButton() == MouseButton.PRIMARY) {	
    	                			updateGrid(a, b, true);
    	                        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
    	                            updateGrid(a, b, false);
    	                        }
                			}
                		}
                		else {
	                		if (mouseEvent.getButton() == MouseButton.PRIMARY) {	
	                			updateGrid(a, b, true);
	                        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
	                            updateGrid(a, b, false);
	                        }
                		}
                	}
                });
            }
        }
    }

    /**
	 * If a cell is clicked, the model will be updated
	 * 
	 * @param int i, int j, boolean left
	 */
    public void updateGrid(int i, int j, boolean left) {
        if (left) {
        	if(firstclick) {
        		if (mainModel.grid[i][j].cell.isMine()) {
        			init(N, M);
        			updateGrid( i,  j, left);
        		}
        		else
        			setfirsrtclick(false);
        	}
        	if(!mainModel.grid[i][j].cell.isFlag()) {
	            if(!mainModel.grid[i][j].cell.isOpen()) {
	                openCell(i, j);
	            }
	            if (mainModel.grid[i][j].cell.isMine()) {
	            	mainModel.lose(mainModel.spentTime,mainModel.openedCells);
	            }
        	}

        } else {
            if(mainModel.grid[i][j].cell.isFlag()){
            	mainModel.unflaggedMines++;
            } else {
            	mainModel.unflaggedMines--;
            }
            mainModel.grid[i][j].cell.setFlag();
            mainView.remainingMines.setText(mainModel.unflaggedMines+"/"+mainModel.totalMines);
            mainView.openedCells.setText(mainModel.openedCells +"/"+ N*M);
        }
        mainModel.win(mainModel.spentTime);
        mainModel.grid[i][j].init();
    }
    
    /**
	 *update for net for servert
	 * 
	 * @param int i, int j, boolean left
	 */
    public void updateGridb(int i, int j) {
    	mainModel.grid[i][j].cell.setMine(true);
        mainView.remainingMines.setText(this.netminesnum+1+"/"+40);
        mainModel.grid[i][j].showmines();
    }
    
    /**
	 * check if a cell position is in the grid
	 * 
	 * @param int i, int j
	 */
    public boolean isValid(int i, int j){
        if(i >= 0 && i < N && j >= 0 && j < M)
            return true;
        return false;
    }

    /**
 	 * open a cell 
 	 * 
 	 * @param int i, int j
 	 */
    public void openCell(int i, int j) {
    	mainModel.grid[i][j].cell.setOpen(true);
    	mainModel.openedCells ++;
        mainView.openedCells.setText(mainModel.openedCells +"/"+ N*M);

        if (mainModel.grid[i][j].cell.getNeighborMines() == 0) {
            for (int r = i - 1; r <= i + 1; r++) {
                for (int c = j - 1; c <= j + 1; c++) {
                    if (isValid(r, c) && !mainModel.grid[r][c].cell.isOpen() && !mainModel.grid[r][c].cell.isMine()) {
                        openCell(r, c);
                        mainModel.grid[r][c].init();
                    }
                }
            }
        }
    }

    /**
     * store history
     *
     * @param name      username
     * @param spentTime spentTime of win
     */
    public void addScoreHistory(Score score) {
        try {
            FileWriter history = new FileWriter("Game_Score_History.dat", true);
            PrintWriter writer = new PrintWriter(history);
            writer.println(score.getUsername() + " " + score.getSpentTime());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
