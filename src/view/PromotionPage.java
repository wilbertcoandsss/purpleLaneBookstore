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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
import model.Promo;

public class PromotionPage implements EventHandler<ActionEvent> {
	
	Scene scene;
	BorderPane borderContainer, borderContainer2;
	TableView<Promo> tablePromo;
	ArrayList<Promo> promoList;
	Label promoIDLbl, promoID, promoCode, promoDiscount, promoNotes;
	VBox vb;
	HBox hb;
	TextField promoCodeTF, promoDiscountTF, promoNotesTF;
	GridPane grid;
	Button addBtn, updateBtn, deleteBtn;
	Integer tempID = null;
	MenuBar menuBar;
	Menu menu1;
	MenuItem menuItem1;
	Connect connect = Connect.getInstance();
	BackgroundFill bf;
	
	public void init() {
		menuItem1 = new MenuItem("Logout");
		menu1 = new Menu("Account");
		menuBar = new MenuBar();
		
		borderContainer = new BorderPane();
		borderContainer2 = new BorderPane();
		bf = new BackgroundFill(Color.rgb(210, 188, 236,  1), null, null);
		borderContainer.setBackground(new Background(bf));
		borderContainer2.setBackground(new Background(bf));
		grid = new GridPane();
		vb = new VBox();
		hb = new HBox();
		promoList = new ArrayList<>();
		tablePromo = new TableView<>();
		promoCode = new Label("Promo Code: ");
		promoDiscount = new Label("Promo Discount: ");
		promoNotes = new Label("Promo Notes: ");
		promoIDLbl = new Label("Promo ID: ");
		promoID = new Label();
		promoCodeTF = new TextField();
		promoDiscountTF = new TextField();
		promoNotesTF = new TextField();
		
		grid = new GridPane();
		addBtn = new Button("Add");
		
		updateBtn = new Button("Update");
		
		deleteBtn = new Button("Delete");
		
		scene = new Scene(borderContainer, 500, 500);
		
	}
	
	public void set() {
		
		menu1.getItems().add(menuItem1);
		menuBar.getMenus().addAll(menu1);
		
		grid.add(promoIDLbl, 0, 0);
		grid.add(promoID, 1, 0);
		
		grid.add(promoCode, 0, 1);
		grid.add(promoCodeTF, 1, 1);
		
		grid.add(promoDiscount, 0, 2);
		grid.add(promoDiscountTF, 1, 2);
		
		grid.add(promoNotes, 0, 3);
		grid.add(promoNotesTF, 1, 3);
		
		vb.getChildren().add(grid);
		
		hb.getChildren().addAll(addBtn, updateBtn, deleteBtn);
		hb.setMargin(addBtn, new Insets(10, 10, 10, 10));
		
		hb.setMargin(updateBtn, new Insets(10, 10, 10, 10));
		
		hb.setMargin(deleteBtn, new Insets(10, 10, 10, 10));
		
		borderContainer.setTop(menuBar);
		borderContainer.setCenter(borderContainer2);

		borderContainer2.setTop(tablePromo);
		borderContainer2.setCenter(vb);
		borderContainer2.setBottom(hb);
		
		BorderPane.setAlignment(borderContainer, Pos.CENTER);
		
		TableColumn<Promo, Integer> idCol = new TableColumn<>("Promo ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("PromoID"));
		
		TableColumn<Promo, String> promoCodeCol = new TableColumn<>("Promo Code");
		promoCodeCol.setCellValueFactory(new PropertyValueFactory<>("PromoCode"));
		
		TableColumn<Promo, Integer> promoDiscCol = new TableColumn<>("Promo Discount");
		promoDiscCol.setCellValueFactory(new PropertyValueFactory<>("PromoDiscount"));

		TableColumn<Promo, String> promoNotesCol = new TableColumn<>("Promo Notes");
		promoNotesCol.setCellValueFactory(new PropertyValueFactory<>("PromoNotes"));

		tablePromo.getColumns().addAll(idCol, promoCodeCol, promoDiscCol, promoNotesCol);
		tablePromo.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		tablePromo.setOnMouseClicked(tableMouseEvent());
		
		refreshTable();
		
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setPadding(new Insets(20));
		grid.setVgap(10);
		grid.setHgap(10);
		
		hb.setAlignment(Pos.CENTER);
	}
	
	private void setBtnAction() {
		
		addBtn.setOnAction(e -> {
			
			Alert a = new Alert(AlertType.ERROR);
			Alert n = new Alert(AlertType.INFORMATION);

			if(promoCodeTF.getText().isEmpty()) {
				a.setContentText("Promo Code can't be empty!");
				a.showAndWait();
			}
			else if (mustBeUnique(promoCodeTF.getText())==false) {
				a.setContentText("Promo Code must be unique!");
				a.showAndWait();
			}
			else if (promoDiscountTF.getText().isEmpty()) {
				a.setContentText("Promo Discount can't be empty!");
				a.showAndWait();
			}
			else if (!promoDiscountTF.getText().matches("[0-9]+")) {
				a.setContentText("Promo Discount must be numeric!");
				a.showAndWait();
			}
			else if (Integer.valueOf(promoDiscountTF.getText()) < 15000) {
				a.setContentText("Promo Discount must be atleast 15000!");
				a.showAndWait();
			}
			else if(promoNotesTF.getText().isEmpty()) {
				a.setContentText("Promo Notes can't be empty!");
				a.showAndWait();
			}
			else {
				Integer promoDisc = Integer.valueOf(promoDiscountTF.getText());
				String promoCode = promoCodeTF.getText();
				String promoNotes = promoNotesTF.getText();
				
				String query = "INSERT INTO `promo`(`PromoCode`, `PromoDiscount`, `PromoNotes`) VALUES ('"+promoCode+"', '"+promoDisc+"', '"+promoNotes+"')";
				connect.execUpdate(query);
				
				n.setContentText("Insert success!");
				n.showAndWait();
				
				refreshTable();
				refreshAllValue();				
			}
		});
		
		updateBtn.setOnAction(e -> {
			
			Alert a = new Alert(AlertType.ERROR);
			Alert n = new Alert(AlertType.INFORMATION);

			if(promoCodeTF.getText().isEmpty()) {
				a.setContentText("Promo Code can't be empty!");
				a.showAndWait();
			}
			else if (promoDiscountTF.getText().isEmpty()) {
				a.setContentText("Promo Discount can't be empty!");
				a.showAndWait();
			}
			else if (!promoDiscountTF.getText().matches("[0-9]+")) {
				a.setContentText("Promo Discount must be numeric!");
				a.showAndWait();
			}
			else if (Integer.valueOf(promoDiscountTF.getText()) < 15000) {
				a.setContentText("Promo Discount must be atleast 15000!");
				a.showAndWait();
			}
			else if(promoNotesTF.getText().isEmpty()) {
				a.setContentText("Promo Notes can't be empty!");
				a.showAndWait();
			}
			else {			
				String promoCode = promoCodeTF.getText();
				Integer promoDisc = Integer.valueOf(promoDiscountTF.getText());
				String promoNotes = promoNotesTF.getText();
				
				String query = "UPDATE `promo` SET `PromoCode`='"+promoCode+"',`PromoDiscount`='"+promoDisc+"',`PromoNotes`='"+promoNotes+"' WHERE PromoID = '"+tempID+"'";
				connect.execUpdate(query);
				
				n.setContentText("Update success!");
				n.showAndWait();
				refreshTable();
				refreshAllValue();
			}
		});
		
		deleteBtn.setOnAction(e->{
			Alert n = new Alert(AlertType.INFORMATION);
			String query = "DELETE FROM promo WHERE PromoID = '"+tempID+"'";
			connect.execUpdate(query);
			n.setContentText("Delete success!");
			n.showAndWait();
			refreshTable();
			refreshAllValue();
		});
		
		menuItem1.setOnAction(e->{
			LaunchPage lp = new LaunchPage();
			AuthController ac = new AuthController();
			ac.tempUserID = null;
			ac.tempUserRole = null;
			Main.gotoPage(lp.scene);
		});
	}
	
	private void getPromo() {
		promoList.clear();
		
		String query = "SELECT * from promo";
		connect.rs = connect.execQuery(query);
		
		try {
			while(connect.rs.next()) {
				Promo p1 = new Promo();
				p1.setPromoID(connect.rs.getInt("PromoID"));
				p1.setPromoCode(connect.rs.getString("PromoCode"));
				p1.setPromoDiscount(connect.rs.getInt("PromoDiscount"));
				p1.setPromoNotes(connect.rs.getString("PromoNotes"));
				promoList.add(p1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void refreshTable() {
		getPromo();
		ObservableList<Promo> regObs = FXCollections.observableArrayList(promoList);
		tablePromo.setItems(regObs);
	}
	
	private EventHandler<MouseEvent> tableMouseEvent(){
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				TableSelectionModel<Promo> tableSelectionModel = tablePromo.getSelectionModel();
				tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
				Promo promo = tableSelectionModel.getSelectedItem();
				
				promoIDLbl.setText(promo.getPromoID().toString());
				promoCodeTF.setText(promo.getPromoCode());
				promoDiscountTF.setText(promo.getPromoDiscount().toString());
				promoNotesTF.setText(promo.getPromoNotes().toString());

				tempID = promo.getPromoID();
			}
		};
	}
	
	public PromotionPage() {
		init();
		set();
		setBtnAction();
		Main.gotoPage(scene);
	}
	
	public boolean mustBeUnique(String promoCode) {
		String query = "SELECT * FROM promo WHERE PromoCode = '"+promoCode+"'";
		connect.execQuery(query);
		
		try {
			while(connect.rs.next()) {
				if(connect.rs.getString("PromoCode").equals(promoCode)) {
					return false;
				}
			}
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	private void refreshAllValue() {
		promoCodeTF.setText("");
		promoDiscountTF.setText("");
		promoIDLbl.setText("");
		promoID.setText("");
		promoNotesTF.setText("");
	}
}
