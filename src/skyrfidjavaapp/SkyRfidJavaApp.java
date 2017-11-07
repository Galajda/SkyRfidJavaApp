/*
 * Copyright (C) 2017 Michal G. <Michal.G at cogitatummagnumtelae.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package skyrfidjavaapp;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Michal G. <Michal.G at cogitatummagnumtelae.com>
 */
public class SkyRfidJavaApp extends Application {
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    //how to make these accessible to anti theft, multi read and menu?
    //define a class for params?
    
    
  
    @Override public void start(Stage primaryStage) 
    {
//        System.out.println("app start. java libraries " + System.getProperty("java.library.path"));
//        System.out.println("app start. jna libraries " + System.getProperty("jna.library.path"));
//        System.setProperty("java.library.path", "c:\\windows\\system32;");
//        System.out.println("after set property. java libraries " + System.getProperty("jna.library.path"));
        BorderPane rootPane = new BorderPane();
        GlobalParameters globalParams = new GlobalParameters(rootPane); 
        // sets default parameters: anti theft--no action, program mode--idle, multi read--false
        
        // add menu bar and multi read status to rootPane.
        MenuBarPane pgmMenu = new MenuBarPane(globalParams);
        rootPane.setTop(pgmMenu.getPane());
        ChooseSingleMultiPane singleMultiStatus = new ChooseSingleMultiPane(globalParams);
        rootPane.setLeft(singleMultiStatus.getPane());
        IdlePane pane = new IdlePane();
        rootPane.setCenter(pane.getPane());
        AntiTheftPane antiTheft = new AntiTheftPane(globalParams);
        rootPane.setRight(antiTheft.getPane());
        
        
        Scene scene = new Scene(rootPane, 700, 250);
        
        primaryStage.setTitle(globalParams.getPgmMode().toString());    //does not change with center pane
        primaryStage.setTitle("RFID program");
        primaryStage.getIcons().add(new Image("skyrfidjavaapp/javarhino.jpg"));
                //add("javarhino.jpg");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
