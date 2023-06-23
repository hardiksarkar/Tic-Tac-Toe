package com.example.tictactoe;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToe extends Application {

    private Button buttons[][]=new Button[3][3];
    private Label playerXScoreLabel, playerOScoreLabel;
    private int playerXScore=0,playerOScore=0;
    private boolean playerXTurn=true;
    private int checkTie=0;
    private boolean isWinnerFound=false;
    private BorderPane createContent(){
        BorderPane root=new BorderPane();       // Creating board layout

        // Title

        Label titleLabel =new Label("Tic-Tac-Toe"); // label at the top of game
        titleLabel.setStyle("-fx-font-size:35pt; -fx-font-weight:bold;"); // styling the label
        root.setTop(titleLabel); // Label position
        BorderPane.setAlignment(titleLabel,Pos.CENTER);

        // Game Board

        GridPane gridPane = new GridPane();  // creating a grid pane to display buttons
        gridPane.setHgap(10);   // horizontal grid pane gap
        gridPane.setVgap(10);   // vertical grid pane gap
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();   // initializing buttons
                button.setPrefSize(100,100); // button size
                button.setStyle("-fx-font-size:30pt; -fx-font-weight:bold;");  // button style
                button.setOnAction(actionEvent -> buttonClicked(button));  // calling buttonClicked func. when button is clicked
                buttons[i][j]=button;
                gridPane.add(button,j,i);   // adding buttons to grid pane
            }
        }
        root.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        Insets paddingRoot = new Insets(10);
        Insets paddingGridPane = new Insets(20);
        root.setPadding(paddingRoot);
        gridPane.setPadding(paddingGridPane);


        // Score

        HBox scoreBoard=new HBox(20);   // creating a horizontal layout for scores
        playerXScoreLabel =new Label("Player X : 0");
        playerXScoreLabel.setStyle("-fx-font-size:16pt; -fx-font-weight:bold;");
        playerOScoreLabel =new Label("Player O : 0");
        playerOScoreLabel.setStyle("-fx-font-size:16pt; -fx-font-weight:bold;");
        scoreBoard.getChildren().addAll(playerXScoreLabel,playerOScoreLabel);   // adding scores of both player to the layout
        root.setBottom(scoreBoard);     // adding the layout to our scoreboard at bottom position
        scoreBoard.setAlignment(Pos.CENTER);
        return root;
    }
    // Players click the cell and inputs X or Y
    private void buttonClicked(Button button){
        if(!isWinnerFound) {
            if (button.getText().equals("")) {
                if (playerXTurn) {
                    button.setText("X");
                } else {
                    button.setText("O");
                }
                playerXTurn = !playerXTurn;
                checkTie++;
                checkWinner();
            }
        }
    }

    // checking if player wins
    private void checkWinner(){
        for (int i = 0; i < 3; i++) {
            // check rows
            if(buttons[i][0].getText().equals(buttons[i][1].getText()) && buttons[i][1].getText().equals(buttons[i][2].getText())
            && !buttons[i][0].getText().isEmpty()){
                showInnerDialog(buttons[i][0].getText());
                scoreUpdate(buttons[i][0].getText());
                return;
            }
            // check columns
            if(buttons[0][i].getText().equals(buttons[1][i].getText()) && buttons[1][i].getText().equals(buttons[2][i].getText())
                    && !buttons[0][i].getText().isEmpty()){
                showInnerDialog(buttons[0][i].getText());
                scoreUpdate(buttons[0][i].getText());
                return;
            }
            // check diagonals
            if(((buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][2].getText()))
            || (buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][0].getText())))
                    && !buttons[1][1].getText().isEmpty()){
                showInnerDialog(buttons[1][1].getText());
                scoreUpdate(buttons[1][1].getText());
                return;
            }
        }
        if(checkTie>8){
            showInnerDialog("");
            resetBoard();
        }
    }

    private void showInnerDialog(String winner){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        if(winner.equals("")){
            alert.setTitle("Tie");
            alert.setContentText("Its a Tie!");     // Display if tie
        }else{
            alert.setTitle("Winner");
            alert.setContentText("Congratulation "+winner+"! You are the Winner.");     // Display if winner found
        }
        checkTie=0;
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void scoreUpdate(String winner){        // Update score of winner
        if(winner.equals("X")){
            playerXScore++;
            playerXScoreLabel.setText("Player X : "+playerXScore);  // X score update
        }else{
            playerOScore++;
            playerOScoreLabel.setText("Player O : "+playerOScore);  // O score update
        }
        resetBoard();
    }

    // reset board
    private void resetBoard(){
        isWinnerFound=false;
        for (Button[] row :buttons){
            for (Button col:row){
                col.setText("");
            }
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Tic-Tac-Toe");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}