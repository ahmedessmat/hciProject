import java.io.IOException;

import com.leapmotion.leap.Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class Interface extends Application{

	GridPane grid = new GridPane();
	int i;
	boolean click;
	
	public void start (Stage primaryStage) {
		click = false;
		int numRows = 4;
        int numColumns = 3;
        for (int row = 0 ; row < numRows ; row++ ){
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);
            rc.setVgrow(Priority.ALWAYS);
            grid.getRowConstraints().add(rc);
        }
        for (int col = 0 ; col < numColumns; col++ ) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(cc);
        }
		i = -1;
	    while(i<11){
	    	addButton();
	    }
        Scene scene = new Scene(grid, 1400, 700);
        scene.setCursor(Cursor.CROSSHAIR);
        primaryStage.setTitle("Interface");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
	}
	
	public void addButton() {
        i++;
        ImageView glasses;
        if (i==4 || i==5)
        	glasses = new ImageView(new Image(getClass().getResourceAsStream(i+".jpg")));
        else 
        	glasses = new ImageView(new Image(getClass().getResourceAsStream(i+".png")));
        glasses.setFitHeight(150.0);
        glasses.setFitHeight(150.0);
        glasses.setPreserveRatio(true); 
        final Button temp = new Button(" Price: "+(100+(i/4)+(i%4))+"$");
        temp.setGraphic(glasses);
        temp.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        temp.setId("" + i);
        temp.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	if (click == false){
            		click = true;
            		try {
                		String path = "D:\\Uni\\Semester 9\\Human Computer Interaction CSEN909\\project\\Windows_MirrorReality" +temp.getId()+ "\\MirrorReality.exe";
    					Process process = new ProcessBuilder(path).start();
    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
                    System.out.println("id = " + temp.getId());
            	}
            }
        });
        grid.add(temp, i%3, i/3);
    }
	
	public static void main(String[] args) {
        launch(args);
        LeapListener listener = new LeapListener();
		Controller controller = new Controller();
		
		controller.addListener(listener);
		
		System.out.println("Press Enter to quit");
		
		try {
			System.in.read();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		controller.removeListener(listener);
    }
}
