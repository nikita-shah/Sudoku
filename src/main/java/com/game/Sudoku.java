package com.game;
//initial draft inspired by https://github.com/jcollard/captaincoder/blob/master/Java/sudoku-javafx/README.md
// very useful site: https://openjfx.io/openjfx-docs/#introduction
//packaging and distribution could be improved, need to look at : https://github.com/dlemmermann/JPackageScriptFX

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Sudoku extends Application {
    @Override
    /* modify the method declaration to throw generic Exception (in case any of the steps fail) */
    public void start(Stage primaryStage) throws Exception {

        /* load layout.fxml from file and assign it to a scene root object */
        //System.out.println(getClass().getResource("").getPath());
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));
        /* assign the root to a new scene and define its dimensions */
        Scene scene = new Scene(root, 873, 716);

        /* set the title of the stage (window) */
        primaryStage.setTitle("Sudoku");
        /* set the scene of the stage to our newly created from the layout scene */
        primaryStage.setScene(scene);
        /* show the stage */
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.out.println("Launching Sudoku!");
        launch();
    }
}