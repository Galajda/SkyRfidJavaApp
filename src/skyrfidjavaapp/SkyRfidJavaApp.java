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
import javafx.application.Platform;
import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Node;
//import javafx.scene.layout.Pane;
//import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Michal G. <Michal.G at cogitatummagnumtelae.com>
 */
public class SkyRfidJavaApp extends Application {
    
    private static BorderPane rootPane;   
    private static MenuBarPane pgmMenu;
    private static ToggleSingleMultiPane singleMultiPane;
    private static ReadPane readPane;
    private static WritePane writePane;
    private static IdlePane idlePane;
    private static AntiTheftPane antiTheftPane;
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    @Override public void start(Stage primaryStage) 
    {                        
        //return app state to default before loading panes        
        AppState state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        state.resetAppState(); 
        
        SkyRfidJavaApp.initializeRootPane();
        System.out.println("app start asks to reset panes");
        SkyRfidJavaApp.resetPanes();
                
        Scene scene = new Scene(rootPane, 700, 250);
        
//        primaryStage.setTitle(state.getReadWriteMode().toString());    //does not change with center pane
        primaryStage.setTitle("RFID program");        
        primaryStage.getIcons().add(new Image("skyrfidjavaapp/javarhino.jpg"));  
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(250);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("application stop");
        System.exit(0);
        //Platform.exit() does not stop timer
    }
    private static void initializeRootPane() {
        rootPane = new BorderPane();
        pgmMenu = new MenuBarPane();
        singleMultiPane = new ToggleSingleMultiPane();
        readPane = new ReadPane();    
        writePane = new WritePane();
        idlePane = new IdlePane();
        antiTheftPane = new AntiTheftPane();
        rootPane.setTop(pgmMenu.getPane());
        rootPane.setLeft(singleMultiPane.getPane());
        rootPane.setRight(antiTheftPane.getPane());
    }
    public static void resetPanes() {    
//        ObservableList<Node> currentNodes = rootPane.getChildren();
//        System.out.println("there are " + currentNodes.size() + " nodes in the root pane");
//        rootPane.getChildren().clear();
        System.out.println("app class reset panes start");
        readPane.stopTimer();
        singleMultiPane.setLblAndButtonTxt();
        antiTheftPane.setButtons();
        //need app state to choose center pane
        AppState state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        ReadWriteModeEnum rw_state = state.getReadWriteMode();        
        switch (rw_state) {            
            case READ_MODE:                     
                rootPane.setCenter(readPane.getPane());
                readPane.startTimer();
                break;
            case WRITE_MODE:                 
                rootPane.setCenter(writePane.getPane());
                break;
            case IDLE_MODE:
                //fall through to default
            default:                                 
                rootPane.setCenter(idlePane.getPane());                        
        }
        
    }
   
}
