package view;

import java.sql.SQLException;

import java.util.ArrayList;

import connect.Connect;
import controller.AuthController;
import controller.TransactionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jfxtras.labs.scene.control.window.CloseIcon;
import jfxtras.labs.scene.control.window.Window;
import model.Checkout;
import model.TransactionDetail;
import model.TransactionHeader;

public class TransactionHistory implements EventHandler<ActionEvent> {

	public Scene scene;
	BorderPane borderContainer;
	TableView<TransactionHeader> trHeaderTable;
	TableView<TransactionDetail> trDetailTable;
	ArrayList<TransactionHeader> th;
	ArrayList<TransactionDetail> td;
	ScrollPane sp, sp1;
	VBox vb;
	Window window;
	Menu menu1, menu2;
	MenuBar menuBar;
	MenuItem menuItem1, menuItem2, menuItem3;
	BackgroundFill bf;
	
	private Connect connect = Connect.getInstance();

	public void init() {
		menuItem1 = new MenuItem("Buy Books");
		menuItem2 = new MenuItem("Transaction History");
		menuItem3 = new MenuItem("Logout");
		menuBar = new MenuBar();
		menu1 = new Menu("Transaction");
		menu2 = new Menu("Account");

		borderContainer = new BorderPane();
		bf = new BackgroundFill(Color.rgb(210, 188, 236,  1), null, null);
		borderContainer.setBackground(new Background(bf));
		trHeaderTable = new TableView<>();
		trDetailTable = new TableView<>();
		th = new ArrayList<>();
		td = new ArrayList<>();
		sp = new ScrollPane();
		sp1 = new ScrollPane();
		vb = new VBox();
		scene = new Scene(borderContainer, 1500, 1500);
	}

	public void set() {
		menu1.getItems().addAll(menuItem1, menuItem2);
		menu2.getItems().add(menuItem3);
		menuBar.getMenus().addAll(menu1, menu2);
		
		borderContainer.setTop(menuBar);
		
		sp.setContent(trHeaderTable);
		sp1.setContent(trDetailTable);

		BorderPane.setAlignment(borderContainer, Pos.CENTER);

		TableColumn<TransactionHeader, Integer> thIDCol = new TableColumn<>("Transaction ID");
		thIDCol.setCellValueFactory(new PropertyValueFactory<>("thID"));

		TableColumn<TransactionHeader, Integer> userIDCol = new TableColumn<>("User ID");
		userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

		TableColumn<TransactionHeader, String> userNameCol = new TableColumn<>("User Name");
		userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
		
		TableColumn<TransactionHeader, String> trDateCol = new TableColumn<>("Transaction Date");
		trDateCol.setCellValueFactory(new PropertyValueFactory<>("trDate"));

		TableColumn<TransactionHeader, Integer> totalItemCol = new TableColumn<>("Total Item");
		totalItemCol.setCellValueFactory(new PropertyValueFactory<>("totalItem"));

		TableColumn<TransactionHeader, String> paymentTypeCol = new TableColumn<>("Payment Type");
		paymentTypeCol.setCellValueFactory(new PropertyValueFactory<>("PaymentType"));

		TableColumn<TransactionHeader, String> cardNumberCol = new TableColumn<>("Card Number");
		cardNumberCol.setCellValueFactory(new PropertyValueFactory<>("CardNumber"));

		TableColumn<TransactionHeader, Integer> promoCodeCol = new TableColumn<>("Promo Code");
		promoCodeCol.setCellValueFactory(new PropertyValueFactory<>("PromoCode"));
		
		TableColumn<TransactionHeader, Integer> beforeDiscCol = new TableColumn<>("Before Discount");
		beforeDiscCol.setCellValueFactory(new PropertyValueFactory<>("BeforeDiscount"));

		TableColumn<TransactionHeader, Integer> promoDiscCol = new TableColumn<>("Promo Discount");
		promoDiscCol.setCellValueFactory(new PropertyValueFactory<>("PromoDiscount"));
		
		TableColumn<TransactionHeader, Integer> grandTotalCol = new TableColumn<>("Grand Total");
		grandTotalCol.setCellValueFactory(new PropertyValueFactory<>("grandTotal"));

		
		TableColumn<TransactionDetail, Integer> tdIDCol = new TableColumn<>("Transaction ID");
		tdIDCol.setCellValueFactory(new PropertyValueFactory<>("thID"));

		TableColumn<TransactionDetail, Integer> bookIDCol = new TableColumn<>("Book ID");
		bookIDCol.setCellValueFactory(new PropertyValueFactory<>("bookID"));

		TableColumn<TransactionDetail, String> bookNameCol = new TableColumn<>("Book Name");
		bookNameCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
		
		TableColumn<TransactionDetail, Integer> bookPriceCol = new TableColumn<>("Book Price");
		bookPriceCol.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
		
		TableColumn<TransactionDetail, Integer> qtyCol = new TableColumn<>("Quantity");
		qtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));

		TableColumn<TransactionDetail, Integer> subTotalCol = new TableColumn<>("Subtotal");
		subTotalCol.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
		
		
		trHeaderTable.getColumns().addAll(thIDCol, userIDCol, userNameCol, trDateCol, totalItemCol, paymentTypeCol, cardNumberCol, promoCodeCol, beforeDiscCol, promoDiscCol, grandTotalCol);
		trDetailTable.getColumns().addAll(tdIDCol, bookIDCol, bookNameCol, bookPriceCol, qtyCol, subTotalCol);
		trHeaderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		trDetailTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		TableViewSelectionModel<TransactionHeader> selectionModel = trHeaderTable.getSelectionModel();
		
		selectionModel.setSelectionMode(SelectionMode.SINGLE);
		
		selectionModel.selectedItemProperty().addListener((obs, oldSelection, newSelection)->{
			getTrDetail(newSelection.getThID());
			ObservableList<TransactionDetail> regObs1 = FXCollections.observableArrayList(td);
			trDetailTable.setItems(regObs1);
		});
		
		vb.getChildren().addAll(trHeaderTable, trDetailTable);
		vb.setSpacing(15);
		
		borderContainer.setTop(menuBar);
		borderContainer.setCenter(vb);
		
		refreshTable();
	}


	private void getTrHeaderUser() {
		th.clear();
		AuthController ac = new AuthController();
		Integer userID = ac.tempUserID;

		String query = "SELECT * FROM trheader th JOIN users us ON th.userID = us.ID JOIN promo pm ON th.PromoCode = pm.PromoID WHERE us.ID = '"+userID+"'";
		
		connect.rs = connect.execQuery(query);

		try {
			while(connect.rs.next()) {
				TransactionHeader th1 = new TransactionHeader();
				th1.setThID(connect.rs.getInt("thID"));
				th1.setUserID(connect.rs.getInt("userID"));
				th1.setUserName(connect.rs.getString("name"));
				th1.setTrDate(connect.rs.getString("trDate"));
				th1.setTotalItem(connect.rs.getInt("totalItem"));
				th1.setPaymentType(connect.rs.getString("PaymentType"));
				th1.setCardNumber(connect.rs.getString("CardNumber"));
				th1.setPromoCode(connect.rs.getString("pm.PromoCode"));
				
				Integer beforeDisc = connect.rs.getInt("GrandTotal") + connect.rs.getInt("PromoDiscount");
				th1.setBeforeDiscount(beforeDisc);
				th1.setPromoDiscount(connect.rs.getInt("PromoDiscount"));
				th1.setGrandTotal(connect.rs.getInt("GrandTotal"));
				th.add(th1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	private void getTrDetail(Integer thID) {
		td.clear();
		
		String query = "SELECT * FROM trdetail td JOIN trheader th ON th.thID = td.thID JOIN book b ON b.BookID = td.bookID WHERE th.thID = '"+thID+"'";
		connect.rs = connect.execQuery(query);
		
		try {
			while(connect.rs.next()) {
				TransactionDetail td1 = new TransactionDetail();
				td1.setThID(connect.rs.getInt("thID"));
				td1.setBookID(connect.rs.getInt("bookID"));
				td1.setBookName(connect.rs.getString("b.BookName"));
				td1.setBookPrice(connect.rs.getInt("b.BookPrice"));
				td1.setQty(connect.rs.getInt("qty"));
				Integer subTotal = td1.getBookPrice() * td1.getQty();
				td1.setSubTotal(subTotal);
				td.add(td1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void refreshTable() {
		getTrHeaderUser();
		ObservableList<TransactionHeader> regObsNew = FXCollections.observableArrayList(th);
		trHeaderTable.setItems(regObsNew);
	}

	public TransactionHistory(){
		init();
		set();
		setEventHandler();
		Main.gotoPage(scene);
	}

	public void setEventHandler() {
		menuItem1.setOnAction(e->{
			HomePage hp = new HomePage();
			Main.gotoPage(hp.scene);
		});
		
		menuItem2.setOnAction(e->{
			TransactionHistory th = new TransactionHistory();
			Main.gotoPage(th.scene);
		});
		
		menuItem3.setOnAction(e->{
			LaunchPage lp = new LaunchPage();
			AuthController ac = new AuthController();
			ac.tempUserID = null;
			ac.tempUserRole = null;
			Main.gotoPage(lp.scene);
		});
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
