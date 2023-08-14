package com.example.algvisualizerjava;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloController {
    @FXML
    public Text algorithmName;
    public Text algorithmExplanation;
    public HBox rectangleContainer;

    private int NUMBER_OF_RECTANGLE = 50;
    int RECTANGLE_WIDTH;

    Thread sortingThread;


    private int[] integerArray = new int[NUMBER_OF_RECTANGLE];


    private Random random = new Random();
    private Scene scene;
    private String sortType = "";



    public void setScene(Scene sc){
        scene = sc;
        sc.setOnKeyPressed(keyEvent -> {
            sortType = keyEvent.getText();
            System.out.println(sortType);
        });

    }

    public void resetRectangleContainer(){
//        if (sortingThread!=null){
//            sortingThread.interrupt();
//
//        }
        rectangleContainer.getChildren().clear();
        RECTANGLE_WIDTH = (int)Math.round(rectangleContainer.getWidth()/ NUMBER_OF_RECTANGLE);
        int lowerBound = 100;
        int upperBound = 250;
        int range = upperBound - lowerBound + 1;

        for(int i = 0; i< NUMBER_OF_RECTANGLE; i++){
            int randomNumber = random.nextInt(range) + lowerBound;
            integerArray[i] = randomNumber;
            Rectangle rect = new Rectangle(RECTANGLE_WIDTH, randomNumber );
            rect.setFill(Color.LIGHTBLUE);
            rectangleContainer.getChildren().add(rect);

        }



    }
    private void redrawRectangles(int[] integerArray, int current, int compareIndex) {
        Platform.runLater(() -> {
            rectangleContainer.getChildren().clear();
            for (int i = 0; i < NUMBER_OF_RECTANGLE; i++) {
                Rectangle rect = new Rectangle(RECTANGLE_WIDTH, integerArray[i]);
                if (i == current) {
                    rect.setFill(Color.RED);
                } else if (i == compareIndex) {
                    rect.setFill(Color.LIGHTGREEN);
                } else {
                    rect.setFill(Color.LIGHTBLUE);
                }
                rectangleContainer.getChildren().add(rect);
            }
        });

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void insertionSort(int[] array) {
        int n = array.length;
        for (int i = 1; i < n; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;

                redrawRectangles(array, i, j);

            }
            array[j + 1] = key;
        }
    }


    public void startSorting() {
        sortingThread = new Thread(() -> {
            switch (sortType){
                case "i":
                    insertionSort(integerArray);
                    break;

                default:
                    algorithmExplanation.setText("No algorithm type selected");
                    break;
            }

        });

        sortingThread.start();



    }


}