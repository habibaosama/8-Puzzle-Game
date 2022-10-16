package com.example._8puzzlegame.puzzle;
import com.example._8puzzlegame.SearchAgent.AStar;
import com.example._8puzzlegame.SearchAgent.Agent;
import com.example._8puzzlegame.SearchAgent.BFS;
import com.example._8puzzlegame.SearchAgent.DFS;
import com.example._8puzzlegame.StateNode.Node;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.nio.file.AccessDeniedException;
import java.util.LinkedList;
import java.util.function.BiFunction;


public class puzzleGame {

    public static LinkedList<Node> puzz = new LinkedList<>();//fiha el result
    public final int[] p={0,0,0,0,0,0,0,0,0};//7yt5zn fyha el input array bt3t el user
    private final GridPane gridPane;
    private final DropShadow shadow;
    public static final Background SKY_BLUE = new Background(new BackgroundFill(Color.BURLYWOOD, null, null));
    public static final Background White = new Background(new BackgroundFill(Color.WHITE, null, null));

    public int ctr=puzz.size()-1;

    Agent b = new BFS();
    Agent d = new DFS();
    Agent a = new AStar();
    public static BiFunction<Point, Point, Double> euclideanDistance =
            (Point p1, Point p2) -> Math.hypot(p1.x - p2.x, p1.y - p2.y);
    public static BiFunction<Point, Point, Double> manhattanDistance =
            (Point p1, Point p2) -> Double.valueOf(Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y));

    private puzzleBoard board;
    public puzzleGame() {
        this.gridPane = new GridPane();
        this.shadow = new DropShadow();
        this.board = new puzzleBoard();
    }

    public void startPlaying(Stage stage) {
        drawMainWindow(stage);
    }
    private void drawMainWindow(Stage stage) {
        AnchorPane pane = new AnchorPane();
        int h = 700;
        int w = 1000;


        drawGridPane();

         //this one
        Label label = new Label("Enter Your  initial Puzzle that form 1253406789 ");
        label.setLocation(700,90);


        //text
        TextField text=new TextField("Enter puzzle");
        text.setLayoutX(700);
        text.setLayoutY(100);

        //button of puzzle entrance
        Button puzzleEnter = new Button("Enter Puzzle :)");
        puzzleEnter.setLayoutX(700);
        puzzleEnter.setLayoutY(130);
        puzzleEnter.setOnMouseClicked(e -> solving(text.getText()));

        //dropdown list
        String[] algo={ "BFS", "DFS", "A* using Euclidean","A* using Manhattan"};

        ComboBox combo_box = new ComboBox(FXCollections.observableArrayList(algo));
        combo_box.setLayoutX(700);
        combo_box.setLayoutY(300);
        //button of puzzle start solving
        Button start = new Button("Solve :)");
        start.setLayoutX(700);
        start.setLayoutY(400);
        start.setOnMouseClicked(e -> solveMethod(text.getText(),p, (String) combo_box.getValue()));

        //button of puzzle entrance
        Button next = new Button("Next");
        next.setLayoutX(400);
        next.setLayoutY(600);
        next.setOnMouseClicked(e -> updateCtr(-1));

        //button of puzzle entrance
        Button prev = new Button("Prev");
        prev.setLayoutX(100);
        prev.setLayoutY(600);
        prev.setOnMouseClicked(e ->updateCtr(1));


        pane.getChildren().addAll(gridPane,text,puzzleEnter,start,combo_box,next,prev);
        pane.setBackground(SKY_BLUE);
        Scene scene = new Scene(pane, w, h);
        stage.setScene(scene);
        stage.show();
    }

    private void startNew() {
        drawGridPane();
        board = new puzzleBoard();
    }

    public void updateCtr(int go){
        if(go==-1&&ctr>=1){
            ctr--;
            System.out.println(puzz.size());
            next(puzz.get(ctr));
        }else if(go==1&&ctr<=puzz.size()-2){
            ctr++;
            next(puzz.get(ctr));
        }
    }
    public void next(Node n){

        drawGridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (n.puzzle[j + 3 * i] != 0) {
                    updateUX(i, j, n.puzzle[j + 3 * i]);
                }
            }
        }
    }
    public void solving(String arr){
        int[] arr1=new int[9];
        for(int i=0;i<arr.length();i++){
            arr1[i] = (arr.charAt(i)-'0');
        }
        drawGridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (arr1[j + 3 * i] != 0) {
                    updateUX(i, j, arr1[j + 3 * i]);
                }
                p[j+3*i]=arr1[j+3*i];
            }
        }
    }

    public void solveMethod(String arr1,int[] arr, String method){
        solving(arr1);
        System.out.println(method);
        switch (method) {
            case "BFS" :
                b.solve(arr);
                puzz=b.res;
                System.out.println("BFS");
                ctr=b.res.size()-1;
                break;
            case "DFS" :
                d.solve(arr);
                System.out.println("DFS");
                puzz=d.res;
                ctr=d.res.size()-1;
                break;
            case "A* using Euclidean" :
                System.out.println("eu");
                a.setHeuristicFunction(euclideanDistance);
                a.solve(arr);
                puzz=a.res;
                ctr=a.res.size()-1;
                break;
            case "A* using Manhattan" :
                System.out.println("man");
                a.setHeuristicFunction(manhattanDistance);
                a.solve(arr);
                puzz=a.res;
                ctr=a.res.size()-1;
                break;

        }
    }



    private void drawGridPane() {//btfady w trsm l grid mn awl w gded
        // Clear all
        gridPane.getChildren().clear();
        gridPane.setDisable(false);
        gridPane.setLayoutY(60);
        // Properties for the GridPane
        gridPane.setPadding(new Insets(40,40,40,70));
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setBackground(new Background(new BackgroundFill(Color.rgb(0,0,0), new CornerRadii(1), new Insets(30,30,30,60))));


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // For the Background Colors
                StackPane field = new StackPane();
                field.setMinWidth(120);
                field.setMinHeight(120);
                field.setBackground(White);
                gridPane.add(field, i, j);
            }
        }



    }

    public void solvePuzzle(Node res) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (res.puzzle[j + 3 * i] != 0) {
                    updateUX(i, j, res.puzzle[j + 3 * i]);
                }
            }
        }
    }
    private void updateUX(int i, int j,int index) {
        ImageView imageView = getSpirit(index);
        gridPane.add(imageView, j, i);
    }
    private ImageView getSpirit(int i) {

        String location = String.format("file:src/main/resources/" + "%s.png", i);
        Image image = new Image(location);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(120);
        imageView.setFitHeight(100);
        return imageView;
    }
}