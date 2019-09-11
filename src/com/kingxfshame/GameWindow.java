package com.kingxfshame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameWindow extends JFrame {
    private static GameWindow game_window;
	private static Image bg;
	private static Image go;
	private static Image drop;


    public static void main(String[] args) throws IOException {
    	bg = ImageIO.read(GameWindow.class.getResourceAsStream("bg.jpg"));
	game_window = new GameWindow(); // создали объект
	game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // при закрытии программы будет завершаться код
	game_window.setLocation(200,100);
	game_window.setSize(906,478);
	game_window.setResizable(false);
	GameField game_field = new GameField();
	game_window.add(game_field);
	game_window.setVisible(true);
    }
    private static void onRepaint(Graphics g){
    		
	}
	private static class GameField extends JPanel{
    	@Override
		protected void paintComponent(Graphics g){
    		super.paintComponent(g);
    		onRepaint(g);
		}
	}
}
