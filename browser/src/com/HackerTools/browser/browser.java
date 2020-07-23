package com.HackerTools.browser;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.swing.*;

public class browser extends Application {
    private static int WIDTH;
    private static int HEIGHT;
    private static final String urlStart = "http://";

    public void start(Stage primaryStage) {

    }
    
    public JInternalFrame browse(String url) {
        /**
         * @param args
         */
        WIDTH = 1200;
        HEIGHT = 1200;
        JInternalFrame ths = new JInternalFrame("WebToolsManger - Browser");
        final JFXPanel webBrowser = new JFXPanel();
        ths.setLayout(new BorderLayout());
        ths.add(webBrowser, BorderLayout.CENTER);
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        webBrowser.setScene(scene);

        Double widthDouble = new Integer(WIDTH).doubleValue();
        Double heightDouble = new Integer(HEIGHT).doubleValue();

        //VBox box = new VBox(10);
        //HBox urlBox = new HBox(10);
        //final TextField urlTextField = new TextField();
        //urlTextField.setText(url);
        //Button go = new Button("Go");
        //urlTextField.setPrefWidth(WIDTH - 100);
        //urlBox.getChildren().addAll(urlTextField, go);

        WebView view = new WebView();
        view.setMinSize(widthDouble, heightDouble);
        view.setPrefSize(widthDouble, heightDouble);
        final WebEngine eng = view.getEngine();
        eng.load(url);
        ths.add(webBrowser);

        //box.getChildren().add(urlBox);
        //box.getChildren().add(view);
        //root.getChildren().add(box);

                /*
                go.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!urlTextField.getText().startsWith(urlStart)) {
                            eng.load(urlStart + urlTextField.getText());
                        } else {
                            eng.load(urlTextField.getText());
                        }
                    }
                });
                */


        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        ths.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ths.setSize(WIDTH, HEIGHT);
        ths.setLocation((screenWidth - WIDTH) / 2, (screenHeight - HEIGHT) / 2);
        return ths;
    }

    public static void main(String[] args) {

    }
}
