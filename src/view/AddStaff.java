package view;


import java.sql.SQLException;
import java.util.ArrayList;

import connect.Connect;
import controller.AuthController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Checkout;
import model.Promo;
import model.User;

public class AddStaff implements EventHandler<ActionEvent>{

	Scene scene;
	BorderPane borderContainer, borderContainer2;
	ScrollPane sp;
	TableView<User> staffTable;
	ArrayList<User> staffList;
	Label staffIDLbl, staffID, staffNameLbl, staffRoleLbl;
	VBox vb;
	HBox hb;
	TextField staffNameTF;
	GridPane grid;
	Button addBtn, updateBtn, deleteBtn;
	Integer tempID = null;
	MenuBar menuBar;
	Menu menu1, menu2;
	MenuItem menuItem1, menuItem2, menuItem3;
	ComboBox cbRole;
	Connect connect = Connect.getInstance();
	BackgroundFill bf;
	
	public void init() {
		menuItem1 = new MenuItem("Transaction History");
		menuItem2 = new MenuItem("Hire New Staff");
		menuItem3 = new MenuItem("Logout");
		menuBar = new MenuBar();
		menu1 = new Menu("Transaction");
		menu2 = new Menu("Account");
		sp = new ScrollPane();
		
		borderContainer = new BorderPane();
		borderContainer2 = new BorderPane();
		bf = new BackgroundFill(Color.rgb(210, 188, 236,  1), null, null);
		borderContainer.setBackground(new Background(bf));
		borderContainer2.setBackground(new Background(bf));
		grid = new GridPane();
		vb = new VBox();
		hb = new HBox();
		cbRole = new ComboBox();
		staffList = new ArrayList<>();
		staffTable = new TableView<>();
		
		staffIDLbl = new Label("Staff ID: ");
		staffNameLbl = new Label("Staff Name: ");
		staffRoleLbl = new Label("Staff Role: ");
		staffID = new Label();
		
		staffNameTF = new TextField();		
		
		grid = new GridPane();
		addBtn = new Button("Hire New Staff");
		
		updateBtn = new Button("Update Staff");
		
		deleteBtn = new Button("Fire Staff");
		
		scene = new Scene(borderContainer, 500, 500);
	}
	
	public void set() {

		menu1.getItems().addAll(menuItem1, menuItem2);
		menu2.getItems().add(menuItem3);
		menuBar.getMenus().addAll(menu1, menu2);
		
		grid.add(staffIDLbl, 0, 0);
		grid.add(staffID, 1, 0);
		
		grid.add(staffNameLbl, 0, 1);
		grid.add(staffNameTF, 1, 1);
		
		grid.add(staffRoleLbl, 0, 2);
		grid.add(cbRole, 1, 2);
	
		grid.setAlignment(Pos.CENTER);
		
		vb.getChildren().add(grid);
		
		hb.getChildren().addAll(addBtn, updateBtn, deleteBtn);
		
		hb.setAlignment(Pos.CENTER);
		vb.setAlignment(Pos.CENTER);
		
		HBox.setMargin(addBtn, new Insets(10, 10, 10, 10));
		
		HBox.setMargin(updateBtn, new Insets(10, 10, 10, 10));
		
		HBox.setMargin(deleteBtn, new Insets(10, 10, 10, 10));
		
		hb.setPadding(new Insets(15));
		grid.setPadding(new Insets(20));
		grid.setVgap(10);
		grid.setHgap(10);
		
		vb.setPadding(new Insets(15));
		
		sp.setContent(staffTable);

		borderContainer.setTop(menuBar);
		borderContainer.setCenter(borderContainer2);
		
		borderContainer2.setTop(staffTable);
		borderContainer2.setCenter(vb);
		borderContainer2.setBottom(hb);
		
		BorderPane.setAlignment(borderContainer, Pos.CENTER);
		
		TableColumn<User, Integer> idUserCol = new TableColumn<>("Staff ID");
		idUserCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

		TableColumn<User, String> userNameCol = new TableColumn<>("Staff Name");
		userNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<User, String> userRoleCol = new TableColumn<>("Staff Role");
		userRoleCol.setCellValueFactory(new PropertyValueFactory<>("role"));


		staffTable.getColumns().addAll(idUserCol, userNameCol, userRoleCol);
		staffTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		staffTable.setOnMouseClicked(tableMouseEvent());
		refreshTable();
		
		cbRole.getItems().add("Select Role!");
		cbRole.getItems().add("admin");
		cbRole.getItems().add("promotion");
		cbRole.getSelectionModel().selectFirst();
	

	}
	
	private EventHandler<MouseEvent> tableMouseEvent(){
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				TableSelectionModel<User> tableSelectionModel = staffTable.getSelectionModel();
				tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
				User user = tableSelectionModel.getSelectedItem();
				
				staffID.setText(user.getID().toString());
				staffNameTF.setText(user.getName());
				cbRole.setValue(user.getRole());

				tempID = user.getID();
			}
		};
	}
	
	private void getUser() {
		staffList.clear();
		
		String query = "SELECT * FROM `users` WHERE role NOT LIKE \"customer\"";
		connect.rs = connect.execQuery(query);
		
		try {
			while(connect.rs.next()) {
				User u1 = new User();
				u1.setID(connect.rs.getInt("ID"));
				u1.setName(connect.rs.getString("name"));
				u1.setRole(connect.rs.getString("role"));
				staffList.add(u1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setBtnAction() {
		addBtn.setOnAction(e->{
			Alert a = new Alert(AlertType.ERROR);
			Alert n = new Alert(AlertType.INFORMATION);
			
			if(staffNameTF.getText().isEmpty()) {
				a.setContentText("Staff name can't be empty!");
				a.showAndWait();
			}
			else if(staffNameTF.getText().length() < 5) {
				a.setContentText("Staff Name must be atleast 5");
				a.showAndWait();
			}
			else if(!staffNameTF.getText().matches("^[a-zA-Z]+")) {
				a.setContentText("Staff Name must be alphabetic");
				a.showAndWait();
			}
			else if(cbRole.getValue().equals("Select Role!")) {
				a.setContentText("Please select the role!");
				a.showAndWait();
			}
			else {
				String staffName = staffNameTF.getText();
				String staffRole = cbRole.getValue().toString();
				String defaultEmail = staffName.toLowerCase() + "@gmail.com";
				String defaultPassword = staffName.toLowerCase() + "123";
						
				String query = "INSERT INTO `users` (`name`, `role`, `email`, `password`) VALUES ('"+staffName+"', '"+staffRole+"', '"+defaultEmail+"', '"+defaultPassword+"')";
				
				connect.execUpdate(query);
				
				n.setContentText("Insert Success!");
				n.showAndWait();
				
				refreshTable();
				refreshAllValue();
			}
		});
		
		updateBtn.setOnAction(e->{
			Alert a = new Alert(AlertType.ERROR);
			Alert n = new Alert(AlertType.INFORMATION);
			
			if(staffNameTF.getText().isEmpty()) {
				a.setContentText("Staff name can't be empty!");
				a.showAndWait();
			}
			else if(staffNameTF.getText().length() < 5) {
				a.setContentText("Staff Name must be atleast 5");
				a.showAndWait();
			}
			else if(!staffNameTF.getText().matches("^[a-zA-Z]+")) {
				a.setContentText("Staff Name must be alphabetic");
				a.showAndWait();
			}
			else if(cbRole.getValue().equals("Select Role!")) {
				a.setContentText("Please select the role!");
				a.showAndWait();
			}
			else {
				String staffName = staffNameTF.getText();
				String staffRole = cbRole.getValue().toString();
				String updatedEmail = staffName.toLowerCase() + "@gmail.com";
				String updatedPassword = staffName.toLowerCase() + "123";
				
				String query = "UPDATE `users` SET `name`='"+staffName+"',`role`='"+staffRole+"', `email`='"+updatedEmail+"', `password`='"+updatedPassword+"' WHERE ID = '"+tempID+"'";
				connect.execUpdate(query);	
				
				n.setContentText("Update Success!");
				n.showAndWait();
				
				refreshTable();
				refreshAllValue();
			}
		});
		
		deleteBtn.setOnAction(e->{
			Alert n = new Alert(AlertType.INFORMATION);
			String query = "DELETE FROM users WHERE ID = '"+tempID+"'";
			connect.execUpdate(query);
			n.setContentText("Delete success!");
			n.showAndWait();
			refreshTable();
			refreshAllValue();
		});
		
		menuItem1.setOnAction(e->{
			ManagerPage mp = new ManagerPage();
			Main.gotoPage(mp.scene);
		});
		
		menuItem2.setOnAction(e->{
			AddStaff as = new AddStaff();
			Main.gotoPage(as.scene);
		});
		
		menuItem3.setOnAction(e->{
			LaunchPage lp = new LaunchPage();
			AuthController ac = new AuthController();
			ac.tempUserID = null;
			ac.tempUserRole = null;

			Main.gotoPage(lp.scene);
		});
	}

	private void refreshTable() {
		getUser();
		ObservableList<User> regObsNew = FXCollections.observableArrayList(staffList);
		staffTable.setItems(regObsNew);
	}
	
	public AddStaff() {
		init();
		set();
		setBtnAction();
		Main.gotoPage(scene);
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void refreshAllValue() {
		staffID.setText("");
		staffNameTF.setText("");
		cbRole.getSelectionModel().selectFirst();
	}
	
}
