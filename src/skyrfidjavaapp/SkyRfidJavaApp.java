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
import javafx.scene.layout.Pane;
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
    private static BorderPane rootPane;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    @Override public void start(Stage primaryStage) 
    {        
        rootPane = new BorderPane();
        //return app state to default before loading panes        
        AppState state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        state.resetAppState(); 
        // add menu bar and multi read status to rootPane.
        MenuBarPane pgmMenu = new MenuBarPane();
        rootPane.setTop(pgmMenu.getPane());
        
        SkyRfidJavaApp.loadRootPane();
//        SkyRfidJavaApp.loadRootPane();
//        ChooseSingleMultiPane singleMultiPane = new ChooseSingleMultiPane();
//        rootPane.setLeft(singleMultiPane.getPane());
//        IdlePane pane = new IdlePane(); //get app state default, choose ctr pane
//        rootPane.setCenter(pane.getPane());
//        AntiTheftPane antiTheft = new AntiTheftPane();
//        rootPane.setRight(antiTheft.getPane());
                
        Scene scene = new Scene(rootPane, 700, 250);
        
//        primaryStage.setTitle(state.getReadWriteMode().toString());    //does not change with center pane
        primaryStage.setTitle("RFID program");
        primaryStage.getIcons().add(new Image("skyrfidjavaapp/javarhino.jpg"));                
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    public static void loadRootPane() {        
        ChooseSingleMultiPane singleMultiPane = new ChooseSingleMultiPane();
        rootPane.setLeft(singleMultiPane.getPane());
        AntiTheftPane antiTheft = new AntiTheftPane();
        rootPane.setRight(antiTheft.getPane());
        
        //need app state to choose center pane
        AppState state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        ReadWriteModeEnum rw_state = state.getReadWriteMode();        
        switch (rw_state) {            
            case READ_MODE:
                ReadPane rp = new ReadPane();
                rootPane.setCenter(rp.getPane());
                break;
            case WRITE_MODE:
                WritePane wp = new WritePane();
                rootPane.setCenter(wp.getPane());
                break;
            case IDLE_MODE:
                //fall through to default
            default:                
                IdlePane ip = new IdlePane();
                rootPane.setCenter(ip.getPane());            
        }
        
    }
}
