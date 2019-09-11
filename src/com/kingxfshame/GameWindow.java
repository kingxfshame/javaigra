package com.kingxfshame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameWindow extends JFrame {
    private static GameWindow game_window;
    private static long last_frame_time;
	private static Image bg;
	private static Image go;
	private static Image drop;
	private static float drop_left = 200;
	private static float drop_top = -100;
	private static float drop_v = 200; // скорость падения капли


    public static void main(String[] args) throws IOException {
    	bg = ImageIO.read(GameWindow.class.getResourceAsStream("bg.jpg"));
		go = ImageIO.read(GameWindow.class.getResourceAsStream("gameover.png"))
				.getScaledInstance(300,200,Image.SCALE_DEFAULT);
		drop = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"))
				.getScaledInstance(30,60,Image.SCALE_DEFAULT);


	game_window = new GameWindow(); // создали объект
	game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // при закрытии программы будет завершаться код
	game_window.setLocation(200,100);
	game_window.setSize(906,478);
	game_window.setResizable(false);

	last_frame_time =System.nanoTime();
	GameField game_field = new GameField();


	game_window.add(game_field);
	game_window.setVisible(true);
    }
    private static void onRepaint(Graphics g){
    	long current_time =System.nanoTime();
    	float delta_time = (current_time - last_frame_time) * 0.000000001f;
    	last_frame_time = current_time;
    	drop_top = drop_top + drop_v * delta_time;
		drop_left = drop_top + drop_v * delta_time;
		g.drawImage(bg,0,0,null);
		g.drawImage(drop,(int)drop_left,(int)drop_top,null);
		//g.drawImage(go,280,120,null);
	}
	private static class GameField extends JPanel{
    	@Override
		protected void paintComponent(Graphics g){
    		super.paintComponent(g);
    		onRepaint(g);
    		repaint();
		}
	}
}
