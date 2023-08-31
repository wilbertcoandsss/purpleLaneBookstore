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
import model.Book;
import model.Promo;

public class AdminPage implements EventHandler<ActionEvent>{
	
	Scene scene;
	BorderPane borderContainer, borderContainer2;
	TableView<Book> tableBooks;
	ArrayList<Book> bookList;
	Label bookIDLbl, bookNameLbl, bookStockLbl, bookAuthorLbl, bookPriceLbl;
	Label bookID;
	VBox vb;
	HBox hb;
	TextField bookNameTF, bookStockTF, bookAuthorTF, bookPriceTF;
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
		grid = new GridPane();
		vb = new VBox();
		hb = new HBox();
		bookList = new ArrayList<>();
		tableBooks = new TableView<>();
		bf = new BackgroundFill(Color.rgb(210, 188, 236,  1), null, null);
		borderContainer.setBackground(new Background(bf));
		borderContainer2.setBackground(new Background(bf));
		
		bookIDLbl = new Label("Book ID: ");
		bookNameLbl = new Label("Book Name: ");
		bookStockLbl = new Label("Book Stock: ");
		bookAuthorLbl = new Label("Book Author: ");
		bookPriceLbl = new Label("Book Price: ");
		bookID = new Label();
		
		bookNameTF = new TextField();
		bookStockTF = new TextField();
		bookAuthorTF = new TextField();
		bookPriceTF = new TextField();
		
		grid = new GridPane();
		addBtn = new Button("Add");
		
		updateBtn = new Button("Update");
		
		deleteBtn = new Button("Delete");
		
		scene = new Scene(borderContainer, 500, 500);
		
	}
	
	public void set() {
		
		menu1.getItems().add(menuItem1);
		menuBar.getMenus().addAll(menu1);
		
		grid.add(bookIDLbl, 0, 0);
		grid.add(bookID, 1, 0);
		
		grid.add(bookNameLbl, 0, 1);
		grid.add(bookNameTF, 1, 1);
		
		grid.add(bookStockLbl, 0, 2);
		grid.add(bookStockTF, 1, 2);
		
		grid.add(bookAuthorLbl, 0, 3);
		grid.add(bookAuthorTF, 1, 3);
		
		grid.add(bookPriceLbl, 0, 4);
		grid.add(bookPriceTF, 1, 4);
		
		vb.getChildren().add(grid);
		
		hb.getChildren().addAll(addBtn, updateBtn, deleteBtn);
		hb.setMargin(addBtn, new Insets(10, 10, 10, 10));
		
		hb.setMargin(updateBtn, new Insets(10, 10, 10, 10));
		
		hb.setMargin(deleteBtn, new Insets(10, 10, 10, 10));
		
		borderContainer.setTop(menuBar);
		borderContainer.setCenter(borderContainer2);

		borderContainer2.setTop(tableBooks);
		borderContainer2.setCenter(vb);
		borderContainer2.setBottom(hb);
		
		TableColumn<Book, Integer> idCol = new TableColumn<>("Book ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("BookID"));
		
		TableColumn<Book, String> bookNameCol = new TableColumn<>("Book Name");
		bookNameCol.setCellValueFactory(new PropertyValueFactory<>("BookName"));
		
		TableColumn<Book, Integer> bookStockLbl = new TableColumn<>("Book Stock");
		bookStockLbl.setCellValueFactory(new PropertyValueFactory<>("BookStock"));

		TableColumn<Book, String> bookAuthorLbl = new TableColumn<>("Book Author");
		bookAuthorLbl.setCellValueFactory(new PropertyValueFactory<>("BookAuthor"));

		TableColumn<Book, Integer> bookPriceLbl = new TableColumn<>("Book Price");
		bookPriceLbl.setCellValueFactory(new PropertyValueFactory<>("BookPrice"));
		
		tableBooks.getColumns().addAll(idCol, bookNameCol, bookStockLbl, bookAuthorLbl, bookPriceLbl);
		tableBooks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableBooks.setOnMouseClicked(tableMouseEvent());
		
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

			if(bookNameTF.getText().isEmpty()) {
				a.setContentText("Book Name can't be empty!");
				a.showAndWait();
			}
			else if(mustBeUnique(bookNameTF.getText()) == false) {
				System.out.println(bookNameTF.getText());
				a.setContentText("Book Name must be unique!");
				a.showAndWait();
			}
			else if(bookStockTF.getText().isEmpty()) {
				a.setContentText("Book Stock can't be empty!");
			}
			else if (!bookStockTF.getText().matches("[0-9]+")) {
				a.setContentText("Book Stock must be numeric!");
				a.showAndWait();
			}
			else if (Integer.valueOf(bookStockTF.getText()) <= 0) {
				a.setContentText("Book Stock must be more than 0");
				a.showAndWait();
			}
			else if (Integer.valueOf(bookStockTF.getText()) < 35) {
				a.setContentText("Book Stock must be more than 35");
				a.showAndWait();
			}
			else if (bookAuthorTF.getText().isEmpty()) {
				a.setContentText("Book Author can't be empty!");
				a.showAndWait();
			}
			else if(bookPriceTF.getText().isEmpty()) {
				a.setContentText("Book Price can't be empty!");
			}
			else if (!bookPriceTF.getText().matches("[0-9]+")) {
				a.setContentText("Book Price must be numeric!");
				a.showAndWait();
			}
			else if (Integer.valueOf(bookPriceTF.getText()) <= 0) {
				a.setContentText("Book Price must be more than 0");
				a.showAndWait();
			}
			else if (Integer.valueOf(bookPriceTF.getText()) < 20000) {
				a.setContentText("Book Price must be more than 20000");
				a.showAndWait();
			}
			else {
				
				String bookName = bookNameTF.getText();
				Integer bookStock = Integer.valueOf(bookStockTF.getText());
				String bookAuthor = bookAuthorTF.getText();
				Integer bookPrice = Integer.valueOf(bookPriceTF.getText());
				
				String query = "INSERT INTO `book`(`BookName`, `BookStock`, `BookAuthor`, `BookPrice`) VALUES ('"+bookName+"', '"+bookStock+"', '"+bookAuthor+"', '"+bookPrice+"')";
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

			if(bookNameTF.getText().isEmpty()) {
				a.setContentText("Book Name can't be empty!");
				a.showAndWait();
			}
			else if(mustBeUnique(bookNameTF.getText()) == false) {
				System.out.println(bookNameTF.getText());
				a.setContentText("Book Name must be unique!");
				a.showAndWait();
			}
			else if(bookStockTF.getText().isEmpty()) {
				a.setContentText("Book Stock can't be empty!");
			}
			else if (!bookStockTF.getText().matches("[0-9]+")) {
				a.setContentText("Book Stock must be numeric!");
				a.showAndWait();
			}
			else if (Integer.valueOf(bookStockTF.getText()) <= 0) {
				a.setContentText("Book Stock must be more than 0");
				a.showAndWait();
			}
			else if (Integer.valueOf(bookStockTF.getText()) < 35) {
				a.setContentText("Book Stock must be more than 35");
				a.showAndWait();
			}
			else if (bookAuthorTF.getText().isEmpty()) {
				a.setContentText("Book Author can't be empty!");
				a.showAndWait();
			}
			else if(bookPriceTF.getText().isEmpty()) {
				a.setContentText("Book Price can't be empty!");
			}
			else if (!bookPriceTF.getText().matches("[0-9]+")) {
				a.setContentText("Book Price must be numeric!");
				a.showAndWait();
			}
			else if (Integer.valueOf(bookPriceTF.getText()) <= 0) {
				a.setContentText("Book Price must be more than 0");
				a.showAndWait();
			}
			else if (Integer.valueOf(bookPriceTF.getText()) < 20000) {
				a.setContentText("Book Price must be more than 20000");
				a.showAndWait();
			}
			else {			
				String bookName = bookNameTF.getText();
				Integer bookStock = Integer.valueOf(bookStockTF.getText());
				String bookAuthor = bookAuthorTF.getText();
				Integer bookPrice = Integer.valueOf(bookPriceTF.getText());
				
				String query = "UPDATE `book` SET `BookName`='"+bookName+"',`BookStock`='"+bookStock+"',`BookAuthor`='"+bookAuthor+"', `BookPrice`='"+bookPrice+"' WHERE BookID = '"+tempID+"'";
				connect.execUpdate(query);
				
				n.setContentText("Update success!");
				n.showAndWait();
				refreshTable();
				refreshAllValue();
			}
		});
		
		deleteBtn.setOnAction(e->{
			Alert n = new Alert(AlertType.INFORMATION);
			String query = "DELETE FROM book WHERE BookID = '"+tempID+"'";
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
	
	private void getBooks() {
		bookList.clear();
		
		String query = "SELECT * from book";
		connect.rs = connect.execQuery(query);
		
		try {
			while(connect.rs.next()) {
				Book b1 = new Book();
				b1.setBookID(connect.rs.getInt("BookID"));
				b1.setBookName(connect.rs.getString("BookName"));
				b1.setBookStock(connect.rs.getInt("BookStock"));
				b1.setBookAuthor(connect.rs.getString("BookAuthor"));
				b1.setBookPrice(connect.rs.getInt("BookPrice"));
				bookList.add(b1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void refreshTable() {
		getBooks();
		ObservableList<Book> regObs = FXCollections.observableArrayList(bookList);
		tableBooks.setItems(regObs);
	}
	
	private EventHandler<MouseEvent> tableMouseEvent(){
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				TableSelectionModel<Book> tableSelectionModel = tableBooks.getSelectionModel();
				tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
				Book book = tableSelectionModel.getSelectedItem();
				
				bookID.setText(book.getBookID().toString());
				bookNameTF.setText(book.getBookName());
				bookStockTF.setText(book.getBookStock().toString());
				bookAuthorTF.setText(book.getBookAuthor());
				bookPriceTF.setText(book.getBookPrice().toString());

				tempID = book.getBookID();
			}
		};
	}
	
	
	public AdminPage() {
		init();
		set();
		setBtnAction();
		Main.gotoPage(scene);
	}

	public boolean mustBeUnique(String bookName) {
		String query = "SELECT * FROM book WHERE BookName = '"+bookName+"'";
		connect.execQuery(query);
		
		try {
			while(connect.rs.next()) {
				if(connect.rs.getString("BookName").equals(bookName)) {
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
		bookID.setText("");
		bookNameTF.setText("");
		bookStockTF.setText("");
		bookAuthorTF.setText("");
		bookPriceTF.setText("");
	}
	

}
