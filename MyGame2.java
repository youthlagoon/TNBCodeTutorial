import java.awt.*;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

//take my bin folder git hub
public class MyGame2 extends JFrame {
	public static void main(String[] args){
		MyGame2 b = new MyGame2();
		b.run();
	}
	private Sprite sprite;
	private Animation a;
	private ScreenManager s;
	private Image bg;
	private static final DisplayMode modes1[] = {
		new DisplayMode(800,600,32,0),
		new DisplayMode(800,600,24,0),
		new DisplayMode(800,600,16,0),
		new DisplayMode(640,480,32,0),
		new DisplayMode(640,480,24,0),
		new DisplayMode(640,480,16,0),
	};
	
	//load Images and add scenes
	public void loadImages(){
		bg = new ImageIcon("Images\\background.jpg").getImage();
		Image face1 = new ImageIcon("Images\\face1.png").getImage();
		Image face2 = new ImageIcon("Images\\face2.png").getImage();
		
		a = new Animation();
		a.addScene(face1, 250);
		a.addScene(face2, 250);
		
		sprite = new Sprite(a);
		sprite.setVelocityX(0.5f);
		sprite.setVelocityY(0.5f);
	}
	
	//main method called from main
	public void run(){
		s = new ScreenManager();
		try{
			DisplayMode dm = s.findFirstCompatableMode(modes1);
			s.setFullScreen(dm);
			loadImages();
			movieLoop();
		}finally{
			s.restoreScreen();
		}
	}
	
	//play movie
	public void movieLoop(){
		long startingTime = System.currentTimeMillis();
		long cumTime = startingTime;
		while(cumTime - startingTime < 10000){
			long timePassed = System.currentTimeMillis() - cumTime;
			cumTime += timePassed;
			update(timePassed);
			
			//draw and update screen
			Graphics2D g = s.getGraphics();
			draw(g);
			g.dispose();
			s.update();
			
			try{
				Thread.sleep(20);
			}catch(Exception ex){}
		}
	}
	
	
	
	//draws graphics
	public void draw(Graphics g){
		g.drawImage(bg, 0, 0, null);
		g.drawImage(sprite.getImage(), Math.round(sprite.getX()), Math.round(sprite.getY()), null);	
	}
	
	
	//updates sprites positioning
	public void update(long timePassed){
		if(sprite.getX() < 0){
			sprite.setVelocityX(Math.abs(sprite.getVelocityX()));
		}else if(sprite.getX() + sprite.getWidth() > s.getWidth()){
			sprite.setVelocityX(-Math.abs(sprite.getVelocityX()));
		}else if(sprite.getY() < 0){
			sprite.setVelocityY(Math.abs(sprite.getVelocityY()));
		}else if(sprite.getY() + sprite.getHeight() > s.getHeight()){
			sprite.setVelocityY(-Math.abs(sprite.getVelocityY()));
		}
		
		
		sprite.update(timePassed);
		
	}
}