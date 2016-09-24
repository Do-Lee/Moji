package crawler;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class capture {
	public void capture() {
		int i;
		for(i=0; i<16; i++){
			try {
				Robot robot = new Robot();
				
				int x = 100;
				int y = 100;
				int width = 200;
				int height = 200;
				
				
				Rectangle area = new Rectangle(800, 1300);
				BufferedImage bufferedImage = robot.createScreenCapture(area);
				
				
				
				
				// 전체 화면 Capture
				//BufferedImage screencapture = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	
				// JPEG 저장.
				String name = "screencapture";
				File file = new File("C:/Users/박정현/Desktop/캡스톤/capture/" + name + i + ".jpg");
				ImageIO.write(bufferedImage, "jpg", file);
			} catch (HeadlessException e) {
				e.printStackTrace();
			} catch (AWTException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
