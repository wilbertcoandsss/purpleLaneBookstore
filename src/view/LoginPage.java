package view;


import controller.AuthController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class LoginPage implements EventHandler<ActionEvent>{
	Scene scene;

	BorderPane borderContainer, borderContainer2, borderContainer3;
	GridPane gridContainer;

	Label passwordLbl, emailLbl;
	TextField  emailTF;
	PasswordField passwordPF;

	Button signInButton, backButton;
	HBox hb;
	BackgroundFill bf;

	
	public void init() {
		borderContainer = new BorderPane();
		borderContainer2 = new BorderPane();
		gridContainer = new GridPane();

		emailLbl = new Label("Email");
		passwordLbl = new Label("Password");
		
		emailTF = new TextField("Email");
		passwordPF = new PasswordField();

		signInButton = new Button("Login");
		backButton = new Button("Back");
		bf = new BackgroundFill(Color.rgb(210, 188, 236,  1), null, null);
		borderContainer.setBackground(new Background(bf));
		hb = new HBox(15);
		
		scene = new Scene(borderContainer, 500, 250);	
	}

	public void addComponent() {
		hb.getChildren().addAll(backButton, signInButton);
		
		borderContainer2.setCenter(gridContainer);
		borderContainer2.setBottom(hb);
		
		borderContainer.setCenter(borderContainer2);

		gridContainer.add(emailLbl, 0, 0);
		gridContainer.add(passwordLbl, 0, 1);

		gridContainer.add(emailTF, 1, 0);
		gridContainer.add(passwordPF, 1, 1);


	}

	public void arrangeComponent() {
		BorderPane.setAlignment(gridContainer, Pos.CENTER);
		
		hb.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(borderContainer, Pos.CENTER);
		
		gridContainer.setAlignment(Pos.CENTER);
		gridContainer.setVgap(20);
		gridContainer.setHgap(20);

		passwordPF.setMaxWidth(320);

		passwordLbl.setMinWidth(100);
		emailTF.setMinWidth(100);

	}
	
	public void setEventHandler() {
		signInButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String email = emailTF.getText();
				String password = passwordPF.getText();
				AuthController ac = new AuthController();
				if (ac.login(email, password) == false) {
					Alert a = new Alert(AlertType.ERROR);
					a.setContentText("Failed");
					a.showAndWait();
				}
				else {
					Alert a = new Alert(AlertType.INFORMATION);
					a.setContentText("Succesfully login!");
					a.showAndWait();
					ac.tempUserID = ac.authUser.getID();
					ac.tempUserRole = ac.authUser.getRole();
	
					if(ac.tempUserRole.equalsIgnoreCase("customer")) {
						HomePage hp = new HomePage();
						Main.gotoPage(hp.scene);					
					}
					else if(ac.tempUserRole.equalsIgnoreCase("promotion")) {
						PromotionPage pp = new PromotionPage();
						Main.gotoPage(pp.scene);
					}
					else if(ac.tempUserRole.equalsIgnoreCase("admin")) {
						AdminPage ap = new AdminPage();
						Main.gotoPage(ap.scene);
					}
					else if(ac.tempUserRole.equalsIgnoreCase("manager")) {
						ManagerPage mp = new ManagerPage();
						Main.gotoPage(mp.scene);
					}
					
				}
			}
		});
		
		backButton.setOnAction(e -> {
			LaunchPage lp = new LaunchPage();
			Main.gotoPage(lp.scene);
		});
	}
	
	public LoginPage() {
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
