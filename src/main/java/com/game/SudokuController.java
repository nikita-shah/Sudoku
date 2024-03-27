package com.game;

import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class SudokuController implements Initializable{
    @FXML
    private Label welcomeText;
    int player_selected_row;
    int player_selected_col;
    int mouse_x;
    int mouse_y;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML // The FXML loader is going to inject from the layout
    Button bt_one; // remember our fx:id's for our buttons? name should be the same in order for the FXMLLoader to find it.
    @FXML Button bt_two;
    @FXML Button bt_three;
    @FXML Button bt_four;
    @FXML Button bt_five;
    @FXML Button bt_six;
    @FXML Button bt_seven;
    @FXML Button bt_eight;
    @FXML Button bt_nine;
    @FXML Button bt_reset;
    @FXML Button bt_submit;
    @FXML Canvas canvas;
    // Make a new GameBoard declaration
    @FXML
    private ChoiceBox<String> difficultyChoiceBox;
    GameBoard gameboard;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //Create an instance of our gameboard
        gameboard = new GameBoard();
        difficultyChoiceBox.setValue("Easy"); // Set the initial value
        //Get graphics context from canvas
        GraphicsContext context = canvas.getGraphicsContext2D();
        canvasMouseClicked(context);
        addButtonClickedEventHandlers();
        drawOnCanvas(context);
        setCanvasFocus();

    }

    private void addButtonClickedEventHandlers() {
        bt_one.setOnMouseClicked(event->numberButtonPressed(1));
        bt_two.setOnMouseClicked(event->numberButtonPressed(2));
        bt_three.setOnMouseClicked(event->numberButtonPressed(3));
        bt_four.setOnMouseClicked(event->numberButtonPressed(4));
        bt_five.setOnMouseClicked(event->numberButtonPressed(5));
        bt_six.setOnMouseClicked(event->numberButtonPressed(6));
        bt_seven.setOnMouseClicked(event->numberButtonPressed(7));
        bt_eight.setOnMouseClicked(event->numberButtonPressed(8));
        bt_nine.setOnMouseClicked(event->numberButtonPressed(9));
        bt_reset.setOnMouseClicked(event->resetButtonPressed());
        bt_submit.setOnMouseClicked(event->{processSumbitButtonPress();});
    }

    private void resetButtonPressed() {
        //reset the player input numbers to all zeros
        int[][] player = gameboard.getPlayer();
        for(int i=0;i<player.length;i++){
            Arrays.fill(player[i],0);
        }

        drawOnCanvas(canvas.getGraphicsContext2D());
        setCanvasFocus();
    }

    private void processSumbitButtonPress(){
        // when the gameboard returns true with its checkForSuccess
        // method, that means it has found no mistakes
        // checkForSuccess CAN BE SUBSTITUTED WITH checkForSuccessGeneral
        GraphicsContext context = canvas.getGraphicsContext2D();
        if(gameboard.checkForSuccessGeneral() == true) {

            // clear the canvas
            context.clearRect(0, 0, 450, 450);
            // set the fill color to green
            context.setFill(Color.GREEN);
            // set the font to 36pt
            context.setFont(new Font(36));
            // display SUCCESS text on the screen
            context.fillText("SUCCESS!", 150, 250);
        }
        else {
            // clear the canvas
            context.clearRect(0, 0, 450, 450);
            // set the fill color to green
            context.setFill(Color.DARKRED);
            // set the font to 36pt
            context.setFont(new Font(32));
            // display SUCCESS text on the screen

            context.fillText("Invalid Sudoku,Please try again!", 0,250 );
        }
    }

    public void drawOnCanvas(GraphicsContext context) {
        context.clearRect(0, 0, 450, 450);

        for(int row = 0; row<9; row++) {
            for(int col = 0; col<9; col++) {
                // finds the y position of the cell, by multiplying the row number by 50, which is the height of a row 					// in pixels
                // then adds 2, to add some offset
                int position_y = row * 50 + 1;

                // finds the x position of the cell, by multiplying the column number by 50, which is the width of a 					// column in pixels
                // then add 2, to add some offset
                int position_x = col * 50 + 1;

                // defines the width of the square as 46 instead of 50, to account for the 4px total of blank space 					// caused by the offset
                // as we are drawing squares, the height is going to be the same
                int width = 48;

                // set the fill color to white (you could set it to whatever you want)
                context.setFill(Color.WHITE);

                // draw a rounded rectangle with the calculated position and width. The last two arguments specify the 					// rounded corner arcs width and height.
                // Play around with those if you want.

                context.fillRect(position_x, position_y, width, width);


            }
        }

        highlightAllCubes(context);

        highlightSelectedCellRowColumn(context);

        // draw the initial numbers from our GameBoard instance
        int[][] initial = gameboard.getInitial();
        for(int row = 0; row<9; row++) {
            for(int col = 0; col<9; col++) {

                // finds the y position of the cell, by multiplying the row number by 50, which
                // is the height of a row in pixels then adds 2, to add some offset
                int position_y = row * 50 + 30;

                // finds the x position of the cell, by multiplying the column number by 50,
                // which is the width of a column in pixels then add 2, to add some offset
                int position_x = col * 50 + 20;

                // set the fill color to white (you could set it to whatever you want)
                context.setFill(Color.BLACK);

                // set the font, from a new font, constructed from the system one, with size 20
                context.setFont(new Font(20));

                // check if value of coressponding initial array position is not 0, remember that
                // we treat zeroes as squares with no values.
                if(initial[row][col]!=0) {

                    // draw the number using the fillText method
                    context.fillText(initial[row][col] + "", position_x, position_y);
                }
            }
        }
        // draw the players numbers from our GameBoard instance
        int[][] player = gameboard.getPlayer();
        for(int row = 0; row<9; row++) {
            for(int col = 0; col<9; col++) {
                // finds the y position of the cell, by multiplying the row
                // number by 50, which is the height of a row in pixels
                // then adds 2, to add some offset
                int position_y = row * 50 + 30;
                // finds the x position of the cell, by multiplying the column
                // number by 50, which is the width of a column in pixels
                // then add 2, to add some offset
                int position_x = col * 50 + 20;
                // set the fill color to purple (you could set it to whatever you want)
                context.setFill(Color.PURPLE);
                // set the font, from a new font, constructed from the system one, with size 20
                context.setFont(new Font(20));
                // check if value of coressponding array position is not 0
                if(player[row][col]!=0) {
                    // draw the number
                    context.fillText(player[row][col] + "", position_x, position_y);
                }
            }
        }
    }
/*
    public void canvasMouseClicked() {
        // attach a new EventHandler of the MouseEvent type to the canvas
        System.out.println("nikita");

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            // override the MouseEvent to do what we want it to do
            @Override
            public void handle(MouseEvent event) {
                // intercept the mouse position relative to the canvas and cast it to an integer
                int mouse_x = (int) event.getX();
                int mouse_y = (int) event.getY();
                System.out.println(this.getClass());
                // convert the mouseX and mouseY into rows and cols
                // We are going to take advantage of the way integers are treated and we are going to divide
                //by a cell's width.
                // This way any value between 0 and 449 for x and y is going to give us an integer from
                //0 to 8, which is exactly what we are after.
                player_selected_row = (int) (mouse_y / 50); // update player selected row
                player_selected_col = (int) (mouse_x / 50); // update player selected column

                //get the canvas graphics context and redraw
                drawOnCanvas(canvas.getGraphicsContext2D());
            }
        });
    }
*/

    public void canvasMouseClicked(GraphicsContext context) {
        canvas.setOnMouseClicked(event -> {
            mouse_x = (int) event.getX();
            mouse_y = (int) event.getY();
            player_selected_row = (int) (mouse_y / 50);
            player_selected_col = (int) (mouse_x / 50);
            drawOnCanvas(context);
        });
    }


    public void numberButtonPressed(int number){
        gameboard.modifyPlayer(number, player_selected_row, player_selected_col);
        drawOnCanvas(canvas.getGraphicsContext2D());

    }

    private void highlightSelectedCellRowColumn(GraphicsContext context){

        context.setStroke(Color.DARKGREEN);
        context.setLineWidth(6);
        //context.strokeLine(start x, start y, end x, end y)

        //selected cell row
        context.strokeLine(0  ,player_selected_row*50,canvas.getWidth(),player_selected_row*50);
        context.strokeLine(0,player_selected_row*50+46,canvas.getWidth(),player_selected_row*50+46);

       //selected cell column
        context.strokeLine(player_selected_col*50  ,0,player_selected_col*50,canvas.getWidth());
        context.strokeLine(player_selected_col*50+46,0,player_selected_col*50+46,canvas.getWidth());

        context.setLineWidth(13);
        context.setStroke(Color.DARKOLIVEGREEN);
        //initial selected cell
        context.strokeRoundRect(player_selected_col * 50 + 2, player_selected_row * 50 + 2, 46, 46, 10, 10);

    }

    private void highlightAllCubes(GraphicsContext context){
        context.setStroke(Color.ORANGE);
        context.setLineWidth(12);
        //horizontal lines taking 4 pixels above 150-4
        context.strokeLine(0,0,canvas.getWidth(),0);
        context.strokeLine(0,146,canvas.getWidth(),146);
        context.strokeLine(0,296,canvas.getWidth(),296);
        context.strokeLine(0,446,canvas.getWidth(),446);
        //vertical lines
        context.strokeLine(0,0,0,canvas.getWidth());
        context.strokeLine(146,0,146,canvas.getWidth());
        context.strokeLine(296,0,296,canvas.getWidth());
        context.strokeLine(446,0,446,canvas.getWidth());

    }

    private void handleKeyPress(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        switch (keyCode) {
            case UP:
                if (player_selected_row > 0) {
                    player_selected_row--;
                    drawOnCanvas(canvas.getGraphicsContext2D());
                }
                break;
            case DOWN:
                if (player_selected_row < 8) {
                    player_selected_row++;
                    drawOnCanvas(canvas.getGraphicsContext2D());
                }
                break;
            case LEFT:
                if (player_selected_col > 0) {
                    player_selected_col--;
                    drawOnCanvas(canvas.getGraphicsContext2D());
                }
                break;
            case RIGHT:
                if (player_selected_col < 8) {
                    player_selected_col++;
                    drawOnCanvas(canvas.getGraphicsContext2D());
                }
                break;
            case DIGIT1: case DIGIT2: case DIGIT3: case DIGIT4:
            case DIGIT5: case DIGIT6: case DIGIT7: case DIGIT8: case DIGIT9:
                int number = Integer.parseInt(keyCode.getName());
                gameboard.modifyPlayer(number, player_selected_row, player_selected_col);
                drawOnCanvas(canvas.getGraphicsContext2D());
                break;
            default:
                // Ignore other key presses
                break;
        }
    }

    private void setCanvasFocus() {
        // Request focus for canvas to make it receive key events
        canvas.requestFocus();
        // Add key event handler for keyboard input
        canvas.setOnKeyPressed(this::handleKeyPress);
    }

}

