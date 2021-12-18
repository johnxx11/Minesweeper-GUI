package view;

import java.io.Serializable;

import controller.GridController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
/** 
 * This class is the grid view class of the minesweeper game.
 * 
 * @author Yanxu Wu
 */
public class GridView extends StackPane  implements Serializable {
	private static final long serialVersionUID = 1L;
	public GridController gc;
    public int x;
    public int y;
	/**
	 * constructor
	 * 
	 * @param GridController gc
	 */
    public GridView(GridController gc){
        this.gc = gc;
    }
    
	/**
	 * set image view of a cell.
	 * 
	 * @param int wid,int hei, String url
	 * @return ImageView imgView
	 */
    public ImageView drawImg(int wid,int hei, String url){
        Image img = new Image(url);
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(wid);
        imgView.setFitHeight(hei);
        return imgView;
    }
}

