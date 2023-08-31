package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class LaunchPage implements EventHandler<ActionEvent>{
	Scene scene;
	
	Text text;
	BorderPane borderContainer;
	Button signUpBtn, signInBtn;
	HBox hb;
	BackgroundFill bf;
	
	public void init() {
		text = new Text();
		hb = new HBox();
		borderContainer = new BorderPane();
		bf = new BackgroundFill(Color.rgb(210, 188, 236,  1), null, null);
		signUpBtn = new Button("Sign Up");
		signInBtn = new Button("Sign In");
		borderContainer.setBackground(new Background(bf));
		scene = new Scene(borderContainer, 600, 350);
	}
	
	public void set() {
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFont(new Font(20));
		text.setText("Welcome to Purple Lane Bookstore");
		
		hb.getChildren().addAll(signUpBtn, signInBtn);
		hb.setSpacing(15);
		
		borderContainer.setTop(text);
		borderContainer.setCenter(hb);
		
		BorderPane.setAlignment(text, Pos.CENTER);
		BorderPane.setAlignment(hb, Pos.CENTER);
		hb.setAlignment(Pos.CENTER);
		
		BorderPane.setAlignment(borderContainer, Pos.CENTER);
		
	}
	
	public void setEventHandler() {
		signInBtn.setOnAction(this);
		signUpBtn.setOnAction(this);
	}
	
	public LaunchPage() {
		init();
		set();
		setEventHandler();
		Main.gotoPage(scene);
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		signUpBtn.setOnAction(e -> {
			RegisterPage rp = new RegisterPage();
			Main.gotoPage(rp.scene);
		});
		
		signInBtn.setOnAction(e -> {
			LoginPage lp = new LoginPage();
			Main.gotoPage(lp.scene);
		});
	}
}
