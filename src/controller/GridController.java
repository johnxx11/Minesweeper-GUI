package controller;

import java.io.Serializable;

import model.Grid;
import view.GridView;
/** 
 * This class is the grid controller class of the minesweeper game.
 * 
 * @author author Yanxu Wu, Zhenyi Qian,  Qi Zhang, Jingyu Xin
 */
public class GridController implements Serializable {
	private static final long serialVersionUID = 1L;
    public Grid cell = new Grid(this);
    public GridView gridView = new GridView(this);
    
    /**
	 * Constructor
	 */
    public GridController(){
        init();
    }
    
	/**
	 * draw images on grid cells.
	 * 
	 */
    public void init() {
        if (cell.isOpen()) {
            if (cell.isMine()) {
            	gridView.getChildren().add(gridView.drawImg(32,32,"img/mine.png"));} 
            else {
            	gridView.getChildren().add(gridView.drawImg(32,32,numURL(cell.getNeighborMines())));}} 
        else if (cell.isFlag()) 
        	gridView.getChildren().add(gridView.drawImg(32,32,"img/flag.png"));
        else  
        	gridView.getChildren().add(gridView.drawImg(32,32,"img/cover.png"));
   }
    
   /**
    * this function will show all the mines in the map
    */
   public void showmines() {
	   gridView.getChildren().add(gridView.drawImg(32,32,"img/mine.png"));
   }
    
	/**
	 * get the url for needed images.
	 * @param Stage stage
	 */
   public static String numURL(int num) {
	   return "img/" + num + ".png";
   }
   
}
