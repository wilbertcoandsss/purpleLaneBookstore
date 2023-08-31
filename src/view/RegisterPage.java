package view;

import controller.AuthController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.User;

public class RegisterPage implements EventHandler<ActionEvent>{
	
	Scene scene;
	
	BorderPane borderContainer;
	GridPane gridContainer;
	
	Label titleLbl, nameLbl, usernameLbl, passwordLbl, emailLbl, confirmPasswordLbl;
	TextField nameTF, usernameTF, emailTF;
	PasswordField passwordPF, confirmPasswordPF;
	
	Button backButton, regisButton;
	HBox hb;
	BackgroundFill bf;

	public void init() {
		borderContainer = new BorderPane();
		gridContainer = new GridPane();
		
		titleLbl = new Label("Please Register!");
		nameLbl = new Label("Name");
		emailLbl = new Label("Email");
		usernameLbl = new Label("Username");
		passwordLbl = new Label("Password");
		confirmPasswordLbl = new Label("Confirm Password: ");
		bf = new BackgroundFill(Color.rgb(210, 188, 236,  1), null, null);
		borderContainer.setBackground(new Background(bf));
		nameTF = new TextField("");
		emailTF = new TextField("");
		usernameTF = new TextField("");
		nameTF.setPromptText("Input your name[more than 5]");
		emailTF.setPromptText("Input your email");
		usernameTF.setPromptText("Input your username");
		passwordPF = new PasswordField();
		confirmPasswordPF = new PasswordField();
		passwordPF.setPromptText("Must be alphanumeric(capital,normal and numbers)");
		
		regisButton = new Button("Register");
		backButton = new Button("Back");
		hb = new HBox(15);
		scene = new Scene(borderContainer, 600, 350);	
	}
	
	public void addComponent() {
		
		hb.getChildren().addAll(backButton, regisButton);
		hb.setAlignment(Pos.CENTER);
		
		borderContainer.setTop(titleLbl);
		borderContainer.setCenter(gridContainer);
		borderContainer.setBottom(hb);
		
		gridContainer.add(nameLbl, 0, 0);
		gridContainer.add(emailLbl, 0, 1);
		gridContainer.add(usernameLbl, 0, 2);
		gridContainer.add(passwordLbl, 0, 3);
		gridContainer.add(confirmPasswordLbl, 0, 4);
		
		gridContainer.add(nameTF, 1, 0);
		gridContainer.add(emailTF, 1, 1);
		gridContainer.add(usernameTF, 1, 2);
		gridContainer.add(passwordPF, 1, 3);
		gridContainer.add(confirmPasswordPF, 1, 4);
		
	}
	
	public void arrangeComponent() {
		BorderPane.setAlignment(titleLbl, Pos.CENTER);
		BorderPane.setAlignment(gridContainer, Pos.CENTER);
		BorderPane.setAlignment(regisButton, Pos.CENTER);
		
		borderContainer.setPadding(new Insets(10));
		
		BorderPane.setMargin(titleLbl, new Insets(10));
		BorderPane.setAlignment(borderContainer, Pos.CENTER);
		
		gridContainer.setAlignment(Pos.CENTER);
		gridContainer.setVgap(20);
		gridContainer.setHgap(20);
		GridPane.setMargin(borderContainer, new Insets(10, 10, 10, 10));
		
		usernameTF.setMaxWidth(320);
		passwordPF.setMaxWidth(320);
		
		nameLbl.setMinWidth(100);
		usernameLbl.setMinWidth(100);
		passwordLbl.setMinWidth(100);
		emailTF.setMinWidth(100);
		
	}
	
	public void setEventHandler() {
		regisButton.setOnAction(e -> {
			String name = nameTF.getText();
			String email = emailTF.getText();
			String username = usernameTF.getText();
			String password = passwordPF.getText();
			String confpw = confirmPasswordPF.getText();
			String matches = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
			Alert a = new Alert(AlertType.ERROR);
			Alert n = new Alert(AlertType.INFORMATION);
			if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
				a.setContentText("Must be filled!");
				a.showAndWait();
			}
			else if (name.length() < 5) {
				a.setContentText("Name must be at least 5");
				a.showAndWait();
			}
			else if (!name.matches("^[a-zA-Z]+")){
				a.setContentText("Name must be numeric!");
				a.showAndWait();
			}
			else if(!email.matches(matches)) {
				a.setContentText("Email must be in email format");
				a.showAndWait();
			}
			else if(username.length() < 5) {
				a.setContentText("Username must be at least 5");
				a.showAndWait();
			}
			else if(!password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,}$")) {
				a.setContentText("Password must consists of capital, digits and atleast 6!");
				a.showAndWait();
			}
			else if(!confpw.equals(password)) {
				a.setContentText("Password didn't match!");
				a.showAndWait();
			}
			else {
				AuthController authController = new AuthController();
				authController.insertUser(name, email, username, password);

				n.setContentText("Your account succesfully registered");
				n.showAndWait();
				LaunchPage lp = new LaunchPage();
				Main.gotoPage(lp.scene);
			}
		});
		
		backButton.setOnAction(e -> {
			Main.gotoPage(new LaunchPage().scene);
		});
	}
	
	public RegisterPage() {
		init();
		addComponent();
		arrangeComponent();
		setEventHandler();
		Main.gotoPage(scene);
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
