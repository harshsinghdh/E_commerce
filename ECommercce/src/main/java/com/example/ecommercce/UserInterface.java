package com.example.ecommercce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserInterface {
     GridPane loginPage;
     HBox headerBar;
     HBox footerBar;
     Button signInButton;

     Label welcomeLabel;
     VBox body;

     Customer loggedInCustomer;

     ProductList productList = new ProductList();

     VBox productPage;

     Button placeOrderButton =  new Button("Place Order");

     ObservableList<Product> itemsInCart = FXCollections.observableArrayList();
   public BorderPane createContent(){

        BorderPane root = new BorderPane();
        root.setPrefSize(800,600);
        //root.getChildren().add(loginPage);
      //  root.setCenter(loginPage);
       body = new VBox();

       body.setPadding(new Insets(10));
       body.setAlignment(Pos.CENTER);
       root.setCenter(body);
       productPage = productList.getAllProducts();

       body.getChildren().add(productPage);
        root.setTop(headerBar);
        root.setBottom(footerBar);
        return root;
    }

public UserInterface(){
    createLoginPage();
    createHeaderBar();
    createFooterBar();

}
   private void createLoginPage(){
        Text userNameText = new Text("user Name");
        Text passwordText = new Text("Password");


       TextField userName = new TextField();
       userName.setPromptText("Type Your User name here");
       PasswordField password= new PasswordField();
       password.setPromptText("Type Your password here");
       Label messageLabel = new Label("Hii");

       Button loginButton = new Button("Login");


       loginPage = new GridPane();
      // loginPage.setStyle("  -fx-background-color:green;");
       loginPage.setAlignment(Pos.CENTER);
       loginPage.setHgap(10);
       loginPage.setVgap(10);
       loginPage.add(userNameText, 0,0);
       loginPage.add(userName,1,0);
       loginPage.add(passwordText,0,1);
       loginPage.add(password,1,1);
       loginPage.add(messageLabel,0,2);
       loginPage.add(loginButton,1,2);


       loginButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               String name = userName.getText();
               String pass = password.getText();
               Login login = new Login();
               loggedInCustomer = login.customerLogin(name,pass);
               if(loggedInCustomer != null){
                   messageLabel.setText("Welcome "+ loggedInCustomer.getName());
                   welcomeLabel.setText("Welcome -"+loggedInCustomer.getName());
                   headerBar.getChildren().add(welcomeLabel);
                   body.getChildren().clear();
                   body.getChildren().add(productPage);
               }
               else {
                   messageLabel.setText("Login Failed !! Please give correct user name and password: ");
               }
           }
       });



    }


    private void createHeaderBar(){
       Button homeButton=  new Button();
        Image image =new Image("C:\\Users\\Harsh Singh\\ECommercce\\src\\images (1).png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(80);
        homeButton.setGraphic(imageView);

       TextField searchBar = new TextField();
       searchBar.setPromptText("Search Here");
       searchBar.setPrefWidth(280);

       Button searchButton = new Button("Search");

        signInButton = new Button("Sign In");
        welcomeLabel = new Label();
        Button cartButton = new Button("Cart");

        Button orderButton  = new Button("Order");


       headerBar = new HBox();
      // headerBar.setStyle( "  -fx-background-color:green;");
       headerBar.setPadding(new Insets(10));
       headerBar.setSpacing(10);
       headerBar.setAlignment(Pos.CENTER);
       headerBar.getChildren().addAll(homeButton,searchBar,searchButton,signInButton,cartButton,orderButton);

       signInButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               body.getChildren().clear();
               body.getChildren().add(loginPage);
               headerBar.getChildren().remove(signInButton);
           }
       });
       cartButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               body.getChildren().clear();
               VBox productPage = productList.getProductsInCart(itemsInCart);
               productPage.setAlignment(Pos.CENTER);
               productPage.setSpacing(10);
               productPage.getChildren().add(placeOrderButton);
               body.getChildren().add(productPage);
               footerBar.setVisible(false);

           }
       });


       placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {

               //need list of product and customer.

               if(itemsInCart==null){
                   showDialog("Please add some products in the to go Order");
                   return;
               }
               if(loggedInCustomer==null){
                   showDialog("Please login first to place order");
                   return;
               }
               int count = Order.placeMultipleOrder(loggedInCustomer,itemsInCart);
               if (count!=0){
                   showDialog("Order for "+count+" product placed successfully!!");
               }
               else {
                   showDialog("Order failed!!!");
               }
           }
       });
       homeButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               body.getChildren().clear();
               body.getChildren().add(productPage);
               footerBar.setVisible(true);
           }
       });
    }

    private void createFooterBar(){


        Button buyNowButton = new Button("Buy Now");
        Button addToCartButton = new Button("Add to Cart");

        signInButton = new Button("Sign In");
        welcomeLabel = new Label();

        footerBar = new HBox();
        // headerBar.setStyle( "  -fx-background-color:green;");
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton,addToCartButton);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product= productList.getSelectedProduct();
                if(product==null){
                    showDialog("Please select a product first to go Order");
                    return;
                }
                if(loggedInCustomer==null){
                    showDialog("Please login first to place order");
                    return;
                }
                boolean status = Order.placeOrder(loggedInCustomer,product);
                if (status==true){
                    showDialog("Order placed successfully!!");
                }
                else {
                    showDialog("Order failed");
                }

            }
        });


        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product= productList.getSelectedProduct();
                if(product==null){
                    showDialog("Please select a product first to add it to Cart!");
                    return;
                }
                itemsInCart.add(product);
                showDialog("Selected Item has been added to the cart successfully.");

            }
        });


    }


    private void showDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }
}



