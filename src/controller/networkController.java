/** 
 * This class is the controller for network
 * 
 * @author Qi Zhang; Zhenyi Qian
 */

package controller;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import model.MainModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.ServerSocket;

import java.net.Socket;

public class networkController implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean freezeBoard = false;
    private boolean isClient;
    public DataOutputStream dataOutputStream;
    public DataInputStream dataInputStream;
    public int index = 1;
    public OutputStream outputStream;
    
	public networkController(MainModel model){
		isClient = true;


	}
	public void serverset(MainModel model) {
		 try {

	            ServerSocket serverSocket =new ServerSocket(9999);

	            Socket client = serverSocket.accept();
	            InputStream inputStream = client.getInputStream();
	            DataInputStream dataInputStream =new DataInputStream(inputStream);

		int len = dataInputStream.readInt();
		byte[] data =new byte[len -5];
		 dataInputStream.readFully(data);
		 String str =new String(data);
		 
		 System.out.println("recieve:"+str);
		 
		 String[] temp= str.split(";");
		 for (int i = 0; i < 80; i= i+ 2) {
			 int x = Integer.parseInt(temp[i]);
			 int y = Integer.parseInt(temp[i+1]);
			 model.grid[y][x].cell.setMine(true);

		 }
		 model.init();
		 model.controller.mainView.openedCells.setText(model.openedCells +"/"+ 16*16);
		 model.controller.mainView.remainingMines.setText(model.unflaggedMines+"/"+model.totalMines);
		 model.controller.mainView.timeLabel.setTextFill(Color.BLACK);
	
		 
	
	        }catch (IOException e) {

	            e.printStackTrace();

	 }
	}
	
	public void clientset() {
		
//		try {
//
//            this.socket =new Socket("127.0.0.1",9999);
//            OutputStream outputStream = socket.getOutputStream();
//            this.dataOutputStream = new DataOutputStream(outputStream);
//            this.outputStream = socket.getOutputStream();
            
//int count=40;
//while(count>0) {
//    if (scanner.hasNext()) {
//        count--;
//        String str = scanner.next();
//        int type = 1;
//        byte[] data = str.getBytes();
//        int len = data.length + 5;
//        dataOutputStream.writeByte(type);
//        dataOutputStream.writeInt(len);
//        dataOutputStream.write(data);
//        dataOutputStream.flush();

//        }catch (IOException e) {
//
//            e.printStackTrace();
//		System.out.println("passaa");
//        }
	}
	public void datasend(String result) {
		
		
		try {
			Socket socket =new Socket("127.0.0.1",9999);
            OutputStream outputStream = socket.getOutputStream();
            this.dataOutputStream = new DataOutputStream(outputStream);
			System.out.println("pass"+result);
			byte[] data = result.getBytes();
			int len = data.length + 5;
			dataOutputStream.writeInt(len);
			this.dataOutputStream.write(data);
			this.dataOutputStream.flush();
		 }catch (IOException e) {
			 e.printStackTrace();
		 }
		
	}
	public void getdata(int x, int y) {

	}
	
	
}
