package view;



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{


//	public static StackPane pane;
	public static Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	public static void gotoPage(Scene newScene) {
		stage.setMaximized(true);
		stage.setScene(newScene);
		stage.show();
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
//		pane = new StackPane();
		stage= arg0;
		stage.setMaximized(true);
		new LaunchPage();
	}

}
