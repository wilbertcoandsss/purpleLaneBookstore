package view;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import connect.Connect;
import controller.AuthController;
import controller.CartController;
import javafx.application.Application;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Book;
import model.Cart;
import model.Checkout;


public class HomePage implements EventHandler<ActionEvent>{
	public Scene scene;
	BorderPane borderContainer, layoutContainer, leftContainer;
	TableView bookTable, cartTable;

	GridPane manageBookForm;

	Menu menu1, menu2;
	MenuBar menuBar;
	MenuItem menuItem1, menuItem2, menuItem3;
	ScrollPane scPane, scPaneCart;
	ArrayList<Book> books;
	ArrayList<Cart> carts;
	Spinner<Integer> spinner;
	public static ArrayList<Checkout> test = new ArrayList<>();
	Label BookIDLbl, BookNameLbl, BookStockLbl, BookAuthorLbl, BookPriceLbl;
	Label BookID, BookName, BookStock, BookAuthor, BookPrice;

	Button addToCart, removeFromCart, checkOut;
	BackgroundFill bf;
	private Connect connect = Connect.getInstance();
	//	Label 


	public void init() {
		borderContainer = new BorderPane();
		layoutContainer = new BorderPane();
		leftContainer = new BorderPane();

		manageBookForm = new GridPane();

		bookTable = new TableView<>();
		cartTable = new TableView<>();
		
		bf = new BackgroundFill(Color.rgb(210, 188, 236,  1), null, null);
		borderContainer.setBackground(new Background(bf));
		
		scPane = new ScrollPane();
		scPane.setContent(borderContainer);
		scPane.setFitToWidth(true);

		scPaneCart = new ScrollPane();
		scPaneCart.setContent(cartTable);
		scPaneCart.setFitToWidth(true);

		BookIDLbl = new Label("Book ID: ");
		BookNameLbl = new Label("Book Name: ");
		BookStockLbl = new Label("Book Stock: ");
		BookAuthorLbl = new Label("Book Author: ");
		BookPriceLbl = new Label("Book Price: ");

		BookID = new Label();
		BookName = new Label();
		BookStock = new Label();
		BookAuthor = new Label();
		BookPrice = new Label();

		addToCart = new Button("Add To Cart");
		removeFromCart = new Button("Remove From Cart");
		checkOut = new Button("Check Out");

		spinner = new Spinner<>(0, Integer.MAX_VALUE, 0);
		spinner.setEditable(true);
		
		menuItem1 = new MenuItem("Buy Books");
		menuItem2 = new MenuItem("Transaction History");
		menuItem3 = new MenuItem("Logout");
		
		menuBar = new MenuBar();
		menu1 = new Menu("Transaction");
		menu2 = new Menu("Account");


		scene = new Scene(borderContainer, 500, 500);
	}

	public void set() {
		menu1.getItems().addAll(menuItem1, menuItem2);
		menu2.getItems().add(menuItem3);
		menuBar.getMenus().addAll(menu1, menu2);
		books = new ArrayList<Book>();
		carts = new ArrayList<Cart>();

		borderContainer.setTop(menuBar);
		borderContainer.setCenter(layoutContainer);
		layoutContainer.setTop(bookTable);


		TableColumn<Book, Integer> idCol = new TableColumn<>("Book ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("BookID"));

		TableColumn<Book, String> nameCol = new TableColumn<>("Book Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("BookName"));

		TableColumn<Book, Integer> stockCol = new TableColumn<>("Book Stock");
		stockCol.setCellValueFactory(new PropertyValueFactory<>("BookStock"));

		TableColumn<Book, String> authorCol = new TableColumn<>("Book Author");
		authorCol.setCellValueFactory(new PropertyValueFactory<>("BookAuthor"));

		TableColumn<Book, Integer> priceCol = new TableColumn<>("Book Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("BookPrice"));


		bookTable.getColumns().addAll(idCol, nameCol, stockCol, authorCol, priceCol);
		bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<Cart, Integer> idColCart = new TableColumn<>("Book ID");
		idColCart.setCellValueFactory(new PropertyValueFactory<>("BookID"));

		TableColumn<Cart, String> nameColCart = new TableColumn<>("Book Name");
		nameColCart.setCellValueFactory(new PropertyValueFactory<>("BookName"));

		TableColumn<Cart, String> authorColCart = new TableColumn<>("Book Author");
		authorColCart.setCellValueFactory(new PropertyValueFactory<>("BookAuthor"));

		TableColumn<Cart, String> qtyColCart = new TableColumn<>("Book Qty");
		qtyColCart.setCellValueFactory(new PropertyValueFactory<>("BookQty"));

		TableColumn<Cart, Integer> priceColCart = new TableColumn<>("Book Price");
		priceColCart.setCellValueFactory(new PropertyValueFactory<>("BookPrice"));

		TableColumn<Cart, Integer> subTotalCart = new TableColumn<>("Subtotal");
		subTotalCart.setCellValueFactory(new PropertyValueFactory<>("SubTotal"));

		cartTable.getColumns().addAll(idColCart, nameColCart, authorColCart, priceColCart, qtyColCart, subTotalCart);
		cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		manageBookForm.setAlignment(Pos.CENTER);
		manageBookForm.setHgap(10);
		manageBookForm.setVgap(10);
		manageBookForm.setPadding(new Insets(25, 25, 25, 25));

		manageBookForm.add(BookIDLbl, 0, 0);
		manageBookForm.add(BookID, 1, 0);

		manageBookForm.add(BookNameLbl, 0, 1);
		manageBookForm.add(BookName, 1, 1);

		manageBookForm.add(BookStockLbl, 0, 2);
		manageBookForm.add(BookStock, 1, 2);

		manageBookForm.add(BookAuthorLbl, 0, 3);
		manageBookForm.add(BookAuthor, 1, 3);

		manageBookForm.add(BookPriceLbl, 0, 4);
		manageBookForm.add(BookPrice, 1, 4);

		manageBookForm.add(spinner, 2, 0);
		manageBookForm.add(addToCart, 2, 1);
		manageBookForm.add(removeFromCart, 2, 2);
		manageBookForm.add(checkOut, 2, 3);


		layoutContainer.setCenter(manageBookForm);
		layoutContainer.setRight(cartTable);


		bookTable.setOnMouseClicked(bookTableMouseEvent());
		cartTable.setOnMouseClicked(cartTableMouseEvent());
		refreshTable();
	}


	private EventHandler<MouseEvent> bookTableMouseEvent(){
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				TableSelectionModel<Book> tableSelectionModel = bookTable.getSelectionModel();
				tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
				Book book = tableSelectionModel.getSelectedItem();

				BookID.setText(book.getBookID().toString());
				BookName.setText(book.getBookName());
				BookStock.setText(book.getBookStock().toString());
				BookAuthor.setText(book.getBookAuthor());
				BookPrice.setText((book.getBookPrice()).toString());

				Integer maxStock = book.getBookStock();

				spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0));
			}

		};
	}
	private EventHandler<MouseEvent> cartTableMouseEvent(){
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				TableSelectionModel<Cart> cartSelectionModel = cartTable.getSelectionModel();
				cartSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);
			}

		};
	}

	public void setEventHandler() {
		addToCart.setOnAction(e -> {
			TableSelectionModel<Book> tableSelectionModel = bookTable.getSelectionModel();
			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
			
			Book book = tableSelectionModel.getSelectedItem();
			Alert a = new Alert(AlertType.ERROR);
			Alert b = new Alert(AlertType.INFORMATION);
			if (tableSelectionModel.isEmpty()) {
				a.setContentText("Choose item first!");
				a.showAndWait();
			}
			else if (spinner.getValue() == 0) {
				a.setContentText("Qty cannot be 0!");
				a.showAndWait();
			}
			else if (spinner.getValue() > book.getBookStock()) {
				a.setContentText("Qty cannot be exceed the book stock!");
				a.showAndWait();
			}
			else {

				Integer bookID = book.getBookID();
				String bookName = book.getBookName();
				String bookAuthor = book.getBookAuthor();
				Integer bookQty = spinner.getValue();
				Integer bookPrice = book.getBookPrice();
				Integer subTotal = bookPrice * bookQty;
				AuthController ac = new AuthController();
				Integer userID = ac.tempUserID;
	
				CartController cc = new CartController();

					
				if(cc.addToCart(bookID, bookName, bookAuthor, bookQty, bookPrice, subTotal, userID) == false) {
					a.setContentText("Invalid credentials!");
					a.showAndWait();
				}
				else {
					b.setContentText("Succesfull add to cart!");
					b.showAndWait();
					refreshTable();
				}
			}

		});

		removeFromCart.setOnAction(e -> {
			TableSelectionModel<Cart> cartSelectionModel = cartTable.getSelectionModel();
			cartSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);

			if(cartSelectionModel.isEmpty()) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("Choose item first!");
				a.showAndWait();
			}
			else {
				ObservableList<Cart> selectedItems = cartSelectionModel.getSelectedItems();

				CartController cc = new CartController();
				for (Cart c : selectedItems) {
					cc.removeCart(c.getBookID());
				}

				Alert a = new Alert(AlertType.INFORMATION);
				a.setContentText("Succesfull remove selected cart!");
				a.showAndWait();
				refreshTable();
			}
		});

		checkOut.setOnAction(e -> {
			TableSelectionModel<Cart> cartSelectionModel = cartTable.getSelectionModel();
			cartSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);

			if(cartSelectionModel.isEmpty()) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("Choose item to be checkout first!");
				a.showAndWait();
			}
			else {
				ObservableList<Cart> selectedItems = cartSelectionModel.getSelectedItems();

				CartController cc = new CartController();

				
				for (Cart c : selectedItems) {
					Checkout ch = new Checkout(c.getBookID(), c.getBookName(), c.getBookAuthor(), c.getBookPrice(), c.getBookQty(), c.getSubTotal());
					test.add(ch);
				}
				
				Integer tempID;
				for (Cart c : selectedItems) {
					tempID = c.getBookID();
					for (int i = 0; i < carts.size(); i++) {
						if (carts.get(i).getBookID() == tempID) {
							cc.removeCart(carts.get(i).getBookID());
						}
					}
				}

				PaymentPage pp = new PaymentPage();
				Main.gotoPage(pp.scene);
			}

		});
		
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
			
			String query = "DELETE FROM carts WHERE user_ID = '"+ac.tempUserID+"'";
			connect.execUpdate(query);
			
			ac.tempUserID = null;
			ac.tempUserRole = null;
			Main.gotoPage(lp.scene);
		});
	}

	private void getData() {
		books.clear();

		// SELECT FROM DB
		String query = "SELECT * FROM book";
		connect.rs = connect.execQuery(query);

		try {
			while(connect.rs.next()){
				Integer bookID = connect.rs.getInt("BookID");
				String bookName = connect.rs.getString("BookName");
				Integer bookStock = connect.rs.getInt("BookStock");
				String bookAuthor = connect.rs.getString("BookAuthor");
				Integer bookPrice = connect.rs.getInt("BookPrice");
				books.add(new Book(bookID, bookName, bookStock, bookAuthor, bookPrice));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void getCart() {
		carts.clear();

		// SELECT FROM DB
		AuthController ac = new AuthController();
		Integer tempID = ac.tempUserID;
		String query = "SELECT * FROM carts WHERE user_ID = '"+tempID+"'";
		connect.rs = connect.execQuery(query);

		try {
			while(connect.rs.next()){
				Integer bookID1 = connect.rs.getInt("BookID");
				String bookName1 = connect.rs.getString("BookName");
				Integer bookQty1 = connect.rs.getInt("BookQty");
				String bookAuthor1 = connect.rs.getString("BookAuthor");
				Integer bookPrice1 = connect.rs.getInt("BookPrice");
				Integer subTotal1 = connect.rs.getInt("SubTotal");
				carts.add(new Cart(bookID1, bookName1, bookAuthor1, bookPrice1, bookQty1, subTotal1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void refreshTable() {
		getData();
		getCart();
		ObservableList<Book> regObs = FXCollections.observableArrayList(books);
		ObservableList<Cart> regObs1 = FXCollections.observableArrayList(carts);
		bookTable.setItems(regObs);
		cartTable.setItems(regObs1);
	}

	public HomePage() {
		init();
		set();
		setEventHandler();
		refreshTable();
		Main.gotoPage(scene);
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
