package view;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.StringConverter;
import jfxtras.labs.scene.control.window.Window;
import model.TransactionDetail;
import model.TransactionHeader;

public class ManagerPage implements EventHandler<ActionEvent>{

	Scene scene;
	BorderPane borderContainer;
	TableView<TransactionHeader> trHeaderTable;
	TableView<TransactionDetail> trDetailTable;
	ArrayList<TransactionHeader> th;
	ArrayList<TransactionDetail> td;
	DatePicker dpfrom, dpto;
	ScrollPane sp, sp1;
	VBox vb;
	HBox hb;
	Window window;
	Menu menu1, menu2;
	MenuBar menuBar;
	MenuItem menuItem1, menuItem2, menuItem3;
	Button goBtn, refreshBtn;
	Label trDate;
	Text trDateTxt;
	BackgroundFill bf;
	private Connect connect = Connect.getInstance();


	public void init() {
		menuItem1 = new MenuItem("Transaction History");
		menuItem2 = new MenuItem("Hire New Staff");
		menuItem3 = new MenuItem("Logout");
		menuBar = new MenuBar();
		menu1 = new Menu("Transaction");
		menu2 = new Menu("Account");
		dpfrom = new DatePicker();
		trDate = new Label();
		trDateTxt = new Text();
		trDateTxt.setText("Input the transaction date!");
		trDateTxt.setFont(new Font(15));

		dpto = new DatePicker();
		goBtn = new Button("Go!");
		refreshBtn = new Button("Refresh!");
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
		hb = new HBox();
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

		vb.getChildren().addAll(trHeaderTable, trDetailTable, trDateTxt);
		trDateTxt.setTextAlignment(TextAlignment.CENTER);
		vb.setSpacing(15);
		vb.setAlignment(Pos.CENTER);

		dpfrom.setPromptText("yyyy-mm-dd");
		dpto.setPromptText("yyyy-mm-dd");

		String pattern = "yyyy-MM-dd";

		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = 
					DateTimeFormatter.ofPattern(pattern);
			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}
			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		};        

		dpfrom.setConverter(converter);
		dpto.setConverter(converter);

		hb.getChildren().addAll(dpfrom, dpto, goBtn, refreshBtn);
		hb.setSpacing(15);
		hb.setAlignment(Pos.CENTER);
		hb.setPadding(new Insets(5, 5, 30, 5));

		borderContainer.setTop(menuBar);
		borderContainer.setCenter(vb);
		borderContainer.setBottom(hb);

		refreshTable();
	}

	private void getAllTrHeader() {
		th.clear();

		String query = "SELECT * FROM trheader th JOIN users us ON th.userID = us.ID JOIN promo pm ON th.PromoCode = pm.PromoID ";

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

	private void getSpecificTrHeader(String from, String to) {
		th.clear();

		String query = "SELECT * FROM trheader th JOIN users us ON th.userID = us.ID JOIN promo pm ON th.PromoCode = pm.PromoID WHERE th.trDate BETWEEN '"+from+"' AND '"+to+"'";

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
		getAllTrHeader();
		ObservableList<TransactionHeader> regObsNew = FXCollections.observableArrayList(th);
		trHeaderTable.setItems(regObsNew);
	}

	public void setEventHandler() {
		menuItem1.setOnAction(e->{
			ManagerPage mp = new ManagerPage();
			Main.gotoPage(mp.scene);
		});

		menuItem2.setOnAction(e->{
			// hire new staff
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

		goBtn.setOnAction(e->{
			Alert a = new Alert(AlertType.ERROR);

			if(dpfrom.getValue() == null) {
				a.setContentText("Choose starting date!");
				a.showAndWait();
			}
			else if(dpto.getValue() == null) {
				a.setContentText("Choose end date!");
				a.showAndWait();
			}
			else {
				String dateTo = dpto.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				String dateFrom = dpfrom.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				getSpecificTrHeader(dateFrom, dateTo);
				ObservableList<TransactionHeader> regObs1 = FXCollections.observableArrayList(th);
				trHeaderTable.setItems(regObs1);				
			}
		});

		refreshBtn.setOnAction(e->{
			refreshTable();
		});
	}

	public ManagerPage() {
		init();
		set();
		setEventHandler();
		Main.gotoPage(scene);
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub



	}

}
