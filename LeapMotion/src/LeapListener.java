import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
import com.leapmotion.leap.Gesture.Type;

class LeapListener extends Listener {
	
	public Robot robot;
	
	public void onInit(Controller controller){
		System.out.println("Initialized");
	}
	
	public void onConnect(Controller controller){
		System.out.println("Connected to Motion Sensor");
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	}
	
	public void onDisconnect(Controller controller){
		System.out.println("Motion Sensor Disconnected");
	}
	
	public void onExit(Controller controller){
		System.out.println("Exited");
	}
	
	public void onFrame(Controller controller){
		try {
			robot = new Robot();
		}
		catch(Exception e){}
		Frame frame = controller.frame();
		
		InteractionBox box = frame.interactionBox();
		for (Finger f : frame.fingers()){
			if (f.type() == Finger.Type.TYPE_INDEX){
				Vector fingerPos = f.stabilizedTipPosition();
				Vector boxFingerPos = box.normalizePoint(fingerPos);
				Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
				robot.mouseMove((int)(screen.width * boxFingerPos.getX()), (int)(screen.height - boxFingerPos.getY() * screen.height));
			}
		}
		/*System.out.println("Frame id: "+frame.id()
				+", Timestamp: "+frame.timestamp()
				+", Hands: "+ frame.hands().count()
				+", Fingers: "+ frame.fingers().count()
				+", Tools: "+ frame.tools().count()
				+", Gestures: "+ frame.gestures().count());*/
		
		GestureList gestures = frame.gestures();
		for (int i=0; i<gestures.count();i++){
			Gesture g = gestures.get(i);
			if (g.type()==Type.TYPE_CIRCLE){
				CircleGesture circle = new CircleGesture(g);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
			}
		}
	}
}



