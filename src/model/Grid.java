package model;

import java.io.Serializable;

import controller.GridController;
/** 
 * This class is the grid class of the minesweeper game.
 * 
 * @author Yanxu Wu, Zhenyi Qian
 */
public class Grid  implements Serializable {
	private static final long serialVersionUID = 1L;
    public int neighborMines;
    //private double ratio = 0.12;
    private boolean mine;
    private boolean open;
    private boolean flag;
	/**
	 * constructor
	 * @param GridController gc
	 */
    public Grid(GridController gc){
    	mine = false;
        open = false;
        flag = false;
        //putMine();
    }
    public void setMine(boolean tf) {
    	mine=tf;
    }
	/**
	 * put mines on grid cells.
	 *
	 */
//    private void putMine(){
//        if(Math.random() < ratio) {
//            mine = true;
//        }
//    }
	/**
	 * is the cell contains mine
	 * @return boolean
	 */
    public boolean isMine(){
        return mine;
    }
	/**
	 * get neighbor mines on grid cells.
	 * @return int
	 */ 
    public int getNeighborMines() {
        return neighborMines;
    }
	/**
	 * is the cell open
	 * @return boolean
	 */
    public boolean isOpen(){
    	return open;
    }
	/**
	 * set the cell to open.
	 * 
	 */
    public void setOpen(boolean open){
        this.open =open;
    }
	/**
	 * is the cell contains flag
	 * @return boolean
	 */
    public boolean isFlag(){
    	return flag;
    }
	/**
	 * set flag on cell. Undo if flagged.
	 * 
	 */
    public void setFlag(){
        flag = !flag;
    }
    
}
