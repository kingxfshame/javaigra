package com.kingxfshame;


import java.sql.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Console;
import java.io.IOException;

public class GameWindow extends JFrame {
    private static GameWindow game_window;
    private static long last_frame_time;
	private static Image bg;
	private static Image go;
	private static Image drop;
	private static Image restart;
	private static float drop_left = 200;
	private static float drop_top = -100;
	private static float restart_left = 280;
	private static float restart_top = 300;
	private static float drop_v = 200; // скорость падения капли
	private static int score = 0; // счёт
	private static boolean end = false;
	private static int drop_width = 100 ;
	private static int drop_height = 152;
	private static boolean pause = false;
	private static float drop_v2 = 0;
	private static boolean mousebool = false;
	private static double mousepositionX = 0;
	private static double mousepositionY = 0;
	private static int droppos_left = -1;
	private static float delta_time = 0;






    public static void main(String[] args) throws IOException {
    	bg = ImageIO.read(GameWindow.class.getResourceAsStream("bg.jpg"));
		go = ImageIO.read(GameWindow.class.getResourceAsStream("gameover.png"))
				.getScaledInstance(300,200,Image.SCALE_DEFAULT);
		drop = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"))
				.getScaledInstance(drop_width,drop_height,Image.SCALE_DEFAULT);
		restart = ImageIO.read(GameWindow.class.getResourceAsStream("restart.png")).getScaledInstance(50,50,Image.SCALE_DEFAULT);

		//sql
		try {
			databaseConnection();
			databasesql();
		}
		catch (SQLException asdf){

		}
		//game
	game_window = new GameWindow(); // создали объект
	game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // при закрытии программы будет завершаться код
	game_window.setLocation(200,100);
	game_window.setSize(906,478);
	game_window.setResizable(false);

	last_frame_time =System.nanoTime();
	GameField game_field = new GameField();

	game_field.addMouseListener(new MouseAdapter()  {
		@Override
		public void mousePressed(MouseEvent e){
			float drop_right = drop_left + drop.getWidth(null);
			float drop_botton = drop_top + drop.getHeight(null);
			if(e.getButton() == MouseEvent.BUTTON3) {
				gamepause();
			}

			int x = e.getX();
			int y = e.getY();
			if(e.getButton() == MouseEvent.BUTTON3) {

			}
			if(pause == false){
				boolean is_drop = x>= drop_left && x <= drop_right && y>= drop_top && y<= drop_botton;

				if(is_drop){
					if(drop_height >= 25 && drop_width >= 50){
						drop_height = drop_height -10 ;
						drop_width = drop_width -10;
						try {
							drop = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"))
									.getScaledInstance(drop_width,drop_height,Image.SCALE_DEFAULT);
						}
						catch (IOException ioe){

						}
					}
					drop_top = -100;
					drop_left =(int) (Math.random() * (game_field.getWidth() - drop.getWidth(null)));
					drop_v = drop_v + 20;
					score++;


					int random = (int)(Math.random() *2 + 1);
					if(random == 1) droppos_left = 1;
					else droppos_left = 0;

					game_window.setTitle("Score : " + score);
				}
				if(end){
					float restart_right = restart_left + restart.getWidth(null);
					float restart_bottom = restart_top + restart.getHeight(null);
					boolean isRestart = x>= restart_left && x <= restart_right && y>= restart_top && y <= restart_bottom;
					if (isRestart){
						end = false;
						score = 0;
						drop_left =(int) (Math.random() * (game_field.getWidth() - drop.getWidth(null)));
						drop_top= -100;
						drop_v = 200;
						score++;
						game_window.setTitle("Score : " + score);
					}
				}
			}
		}
	}); // Отслеживание нажатие кнопки мыши

	game_window.add(game_field);
	game_window.setVisible(true);
    }
    private static int onDirection(){
    	int rand = (int)(Math.random() *2 +1);
    	if(rand == 2) droppos_left = 1;
    	else droppos_left = -1;

    	return  droppos_left;
	}
    private static void onRepaint(Graphics g){
    	long current_time =System.nanoTime();
    	delta_time = (current_time - last_frame_time) * 0.000000001f;
    	last_frame_time = current_time;
    	drop_top = drop_top + drop_v * delta_time;
		g.drawImage(bg,0,0,null);

		droprandom();


		g.drawImage(drop,(int)drop_left,(int)drop_top,null);

		if(drop_top > game_window.getHeight()){
			g.drawImage(go,280,120,null);
			g.drawImage(restart,(int)restart_left,(int)restart_top,null);}
			end = true;
	}

	private static void gamepause(){
		if(pause == true){
			System.out.println(pause);
			pause = false;
			drop_v = drop_v2;
			drop_v2 = 0;
			System.out.println((int)mousepositionX);
			System.out.println((int)mousepositionY);
			try{
				Robot r = new Robot();

				r.mouseMove((int)mousepositionX,(int)mousepositionY);


			}
			catch (AWTException ee){

			}
		}
		else{
			System.out.println(pause);
			drop_v2 = drop_v;
			drop_v = 0;
			mousepositionY = MouseInfo.getPointerInfo().getLocation().getY();
			mousepositionX = MouseInfo.getPointerInfo().getLocation().getX();
			pause = true;
		}
	}

	private static void droprandom(){

		if(droppos_left == 1){
			drop_left = drop_left + drop_v * delta_time;
		}
		else{
			drop_left = drop_left + -drop_v* delta_time;
		}

		if(drop_left <= 0.0 || drop_left + drop_width >= game_window.getWidth()){
			if(droppos_left == 1) droppos_left = -1;
			else droppos_left = 1;
		}

	}


	private static class GameField extends JPanel{
    	@Override
		protected void paintComponent(Graphics g){
    		super.paintComponent(g);
    		onRepaint(g);
    		repaint();
		}
	}
	private static void databaseConnection()throws SQLException{

		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			System.out.println("Connection succesfull!");
		}
		catch(Exception ex){
			System.out.println("Connection failed...");

			System.out.println(ex);
		}
	}
	private static void databasesql()throws  SQLException{
		String connectionUrl =
				"jdbc:sqlserver://localhost;"
						+ "database=mysql;"
						+ "user=root@localhost;"
						+ "password=;"
						+ "encrypt=true;"
						+ "trustServerCertificate=false;"
						+ "loginTimeout=30;";

		try (Connection connection = DriverManager.getConnection(connectionUrl);) {
			System.out.println("success");
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
