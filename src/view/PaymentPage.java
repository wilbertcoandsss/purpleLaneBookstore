package view;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;

import connect.Connect;
import controller.AuthController;
import controller.CartController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Book;
import model.Cart;
import model.Checkout;
import model.Promo;
import model.User;

public class PaymentPage implements EventHandler<ActionEvent>{

	Scene scene;
	BorderPane borderContainer;
	ComboBox cbPayment, cbPromo;
	TableView<Checkout> checkoutTable;
	ArrayList<Promo> promo = new ArrayList<>();
	ScrollPane sp;
	Label totalPriceLbl, totalPrice, cardNumberLbl, discount, totalAfterDisc;
	TextField cardNumber;
	VBox vb;
	HBox hb, hb2;
	Button payButton;
	HomePage hp = new HomePage();
	Connect connect;
	Integer tempDisc = 0;
	Integer total = 0;
	BackgroundFill bf;
	
	public void init() {
		borderContainer = new BorderPane();
		bf = new BackgroundFill(Color.rgb(210, 188, 236,  1), null, null);
		borderContainer.setBackground(new Background(bf));
		cbPayment = new ComboBox();
		cbPromo = new ComboBox();
		checkoutTable = new TableView<>();
		sp = new ScrollPane();
		totalPriceLbl = new Label("Total Price: ");
		vb = new VBox(15);
		hb = new HBox(15);
		hb2 = new HBox(15);
		totalPrice = new Label();
		payButton = new Button("Pay Now!");
		cardNumber = new TextField();
		cardNumberLbl = new Label("Card Number: ");
		discount = new Label();
		totalAfterDisc = new Label();


		scene = new Scene(borderContainer, 2000, 2000);
	}

	public void set() {

		sp.setContent(checkoutTable);

		borderContainer.setTop(checkoutTable);
		BorderPane.setAlignment(borderContainer, Pos.CENTER);

		TableColumn<Checkout, Integer> idColCheckout = new TableColumn<>("Book ID");
		idColCheckout.setCellValueFactory(new PropertyValueFactory<>("BookID"));

		TableColumn<Checkout, String> nameColCheckout = new TableColumn<>("Book Name");
		nameColCheckout.setCellValueFactory(new PropertyValueFactory<>("BookName"));

		TableColumn<Checkout, String> authorColCheckout = new TableColumn<>("Book Author");
		authorColCheckout.setCellValueFactory(new PropertyValueFactory<>("BookAuthor"));

		TableColumn<Checkout, String> qtyColCheckout = new TableColumn<>("Book Qty");
		qtyColCheckout.setCellValueFactory(new PropertyValueFactory<>("BookQty"));

		TableColumn<Checkout, Integer> priceColCheckout = new TableColumn<>("Book Price");
		priceColCheckout.setCellValueFactory(new PropertyValueFactory<>("BookPrice"));

		TableColumn<Checkout, Integer> subTotalCheckout = new TableColumn<>("Subtotal");
		subTotalCheckout.setCellValueFactory(new PropertyValueFactory<>("SubTotal"));

		checkoutTable.getColumns().addAll(idColCheckout, nameColCheckout, authorColCheckout, priceColCheckout, qtyColCheckout, subTotalCheckout);
		checkoutTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		refreshTable();

		totalPrice.setText(getTotal().toString());

		hb.getChildren().addAll(totalPriceLbl, totalPrice);
		hb.setAlignment(Pos.CENTER);

		hb2.getChildren().addAll(cardNumberLbl, cardNumber);
		hb2.setAlignment(Pos.CENTER);

		cbPayment.getItems().add("Select Card!");
		cbPayment.getItems().add("Credit");
		cbPayment.getItems().add("Debit");
		cbPayment.getSelectionModel().selectFirst();
		discount.setVisible(false);
		hb2.setVisible(false);

		cbPromo.getItems().add("Select Promo!");
		cbPromo.getSelectionModel().selectFirst();
		for (int i = 0; i < promo.size(); i++) {
			cbPromo.getItems().add(promo.get(i).getPromoCode());
		}

		total = getTotal() - tempDisc;

		totalAfterDisc.setText("Your final total will be " + total.toString());

		payButton.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(hb, cbPayment, hb2, cbPromo, discount, totalAfterDisc, payButton);

		vb.setAlignment(Pos.CENTER);

		borderContainer.setCenter(vb);
	}

	private void refreshPromo() {
		if(!cbPromo.getValue().equals("Select Promo!")) {

			for (int i = 0; i < promo.size(); i++) {
				if (promo.get(i).getPromoCode().equals(cbPromo.getValue())) {
					tempDisc = promo.get(i).getPromoDiscount();
				}
			}
			total = getTotal() - tempDisc;
			if (total < 0) {
				total = 0;
			}
			totalAfterDisc.setText("Your final total will be " + total.toString());
			discount.setText("Youve got " + tempDisc + "discount");
			discount.setVisible(true);
		}
		else {
			tempDisc = 0;
			total = getTotal() - tempDisc;
			totalAfterDisc.setText("Your final total will be " + total.toString());
			discount.setVisible(false);
		}
	}

	private void refreshPayment() {
		if(!cbPayment.getValue().equals("Select Card!")) {
			hb2.setVisible(true);
		}

		else {
			hb2.setVisible(false);			
		}
	}

	private void refreshTable() {
		getPromo();
		ObservableList<Checkout> regObsNew = FXCollections.observableArrayList(hp.test);
		checkoutTable.setItems(regObsNew);
	}

	private Integer getTotal() {
		Integer totalPrice = 0;
		for (int i = 0; i < hp.test.size(); i++) {
			totalPrice += (hp.test.get(i).getBookPrice() * hp.test.get(i).getBookQty());
		}
		return totalPrice;
	}
	public void setEventHandler() {
		cbPayment.setOnAction(e -> {
			refreshPayment();
		});

		cbPromo.setOnAction(e -> {
			refreshPromo();
		});

		payButton.setOnAction(e -> {

			Alert a = new Alert(AlertType.ERROR);
			Alert n = new Alert(AlertType.INFORMATION);

			if (cbPayment.getValue().equals("Select Card!")) {
				a.setContentText("Please select payment card!");
				a.showAndWait();
			}
			else if(cardNumber.getText().length() != 16) {
				a.setContentText("Card Number must be consists of 16 number!");
				a.showAndWait();
			}
			else if(!cardNumber.getText().matches("[0-9]+")) {
				a.setContentText("Card Number must be numeric!");
				a.showAndWait();
			}
			else if(cbPromo.getValue().equals("Select Promo!")) {
				a.setContentText("Promo are available!");
				a.showAndWait();
			}
			else {

				AuthController ac = new AuthController();
				Integer totalItem = hp.test.size();
				String promoCode = cbPromo.getValue().toString();

				Integer user = ac.tempUserID;
				String paymentType = cbPayment.getValue().toString();
				String cardNum = cardNumber.getText().toString();

				String queryPromo = "SELECT * FROM promo WHERE PromoCode = '"+promoCode+"'";
				Integer tempPromoID = null;

				connect.execQuery(queryPromo);

				try {
					while(connect.rs.next()) {
						tempPromoID = connect.rs.getInt("PromoID");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


				String query = "INSERT INTO trheader (`userID`, `totalItem`, `trDate`, `PaymentType`, `CardNumber`, `PromoCode`, `GrandTotal`)"
						+ "VALUES ('"+user+"', '"+totalItem+"', now(), '"+paymentType+"', '"+cardNum+"', '"+tempPromoID+"', '"+total+"')";

				connect.execUpdate(query);

				String queryTr = "SELECT thID FROM trheader ORDER BY thID DESC";
				Integer lastthID = null;
				//				
				connect.execQuery(queryTr);
				try {
					while(connect.rs.next()) {
						lastthID = connect.rs.getInt("thID");
						break;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				for (int i = 0; i < hp.test.size(); i++) {
					String queryDetail = "INSERT INTO trdetail (`thID`, `bookID`, `qty`) VALUES ('"+lastthID+"', '"+hp.test.get(i).getBookID()+"', '"+hp.test.get(i).getBookQty()+"')";
					connect.execUpdate(queryDetail);
				}

				for (int i = 0; i < hp.test.size(); i++) {
					String queryDetail = "UPDATE `book` SET BookStock = BookStock - '"+hp.test.get(i).getBookQty()+"' WHERE BookID = '"+hp.test.get(i).getBookID()+"'";
					connect.execUpdate(queryDetail);
				}

				n.setContentText("Book checkout succesfully!");
				n.showAndWait();

				hp.test.clear();
				Main.gotoPage(hp.scene);
			}
		});
	}
	private void getPromo() {
		promo.clear();
		connect = connect.getInstance();
		// SELECT FROM DB
		String query = "SELECT * FROM promo";
		connect.rs = connect.execQuery(query);

		try {
			while(connect.rs.next()){
				Integer promoID = connect.rs.getInt("PromoID");
				String promoCode = connect.rs.getString("PromoCode");
				Integer promoDiscount = connect.rs.getInt("PromoDiscount");
				String promoNotes = connect.rs.getString("PromoNotes");
				promo.add(new Promo(promoID, promoCode, promoDiscount, promoNotes));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public PaymentPage() {
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
