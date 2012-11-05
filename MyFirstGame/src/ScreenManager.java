import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;

public class ScreenManager {

	private GraphicsDevice vc;
	
	//Give vc access to monitor and screen
	public ScreenManager(){
		
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		vc = e.getDefaultScreenDevice();
		
	}
	
	//Get all compatable display modes
	public DisplayMode[] getCompatableDisplayModes(){
		return vc.getDisplayModes();
	}
	
	
	//compares display modes passed in to vc displaymodes and see if they match
	public DisplayMode findFirstCompatableMode(DisplayMode modes[]){
		
		DisplayMode goodModes[] = vc.getDisplayModes();
		for(int x=0; x < modes.length; x++){
			for(int y=0; y<goodModes.length; y++){
				if(displayModesMatch(modes[x],goodModes[y])){
					return modes[x];
				}
			}
		}
		System.out.println("No Compatable Display Modes");
		return null;
		
	}
	
	
	//get current DM
	public DisplayMode getCurrentDisplayMode(){
		return vc.getDisplayMode();
	}
	
	//checks if modes match
	public boolean displayModesMatch(DisplayMode m1, DisplayMode m2){
		if(m1.getWidth() != m2.getWidth() || m1.getHeight() != m2.getHeight()){
			return false;
		}
		if(m1.getBitDepth() !=  DisplayMode.BIT_DEPTH_MULTI && m2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m1.getBitDepth() != m2.getBitDepth()){
			return false;
			
		}
		if(m1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m1.getRefreshRate() != m2.getRefreshRate()){
			return false;
		}
		
		return true;
		
	}
	
	//make frame fullscreen
	
	public void setFullScreen(DisplayMode dm){
		JFrame f = new JFrame();
		f.setUndecorated(true);
		f.setIgnoreRepaint(true);
		f.setResizable(false);
		vc.setFullScreenWindow(f);
		
		if(dm != null && vc.isDisplayChangeSupported()){
			try{
			vc.setDisplayMode(dm);
			}catch(Exception ex){}
		}
		
		f.createBufferStrategy(2);
	
	}
	
	
	//set graphics object = this
	public Graphics2D getGraphics(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			BufferStrategy s = w.getBufferStrategy();
			return (Graphics2D)s.getDrawGraphics();
		}else{
			return null;
		}
	}
	
	// updates display
	public void update(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			BufferStrategy s = w.getBufferStrategy();
			if(!s.contentsLost()){
				s.show();
			}
		}
	}
	
	
	//return full screen window
	public Window getFullScreenWindow(){
		return vc.getFullScreenWindow();
	}
	
	
	public int getWidth(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			return w.getWidth();
		} else {
			return 0;
		}
	}
	
	public int getHeight(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			return w.getHeight();
		} else {
			return 0;
		}
	}
	
	//exit fullscreen
	public void restoreScreen(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			w.dispose();
		}
		vc.setFullScreenWindow(null);
	}
	
	//creates image compatable with monitor
	public BufferedImage createCompatableImage(int w, int h, int t){
		Window win = vc.getFullScreenWindow();
		if(win != null){
			GraphicsConfiguration gc = win.getGraphicsConfiguration();
			return gc.createCompatibleImage(w, h, t);
		}
		return null;
	}
	
	
	
}
