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
//import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Michal G. <Michal.G at cogitatummagnumtelae.com>
 */
public class SkyRfidJavaApp extends Application {
//    private Scene scene;
    private static Stage appStage;
    private static BorderPane rootPane;   
//    private BorderPane rootPane;   
    private static MenuBarPane pgmMenu;
    private static ToggleSingleMultiPane singleMultiPane;
    private static ReadPane readPane;
    private static WritePane writePane;
    private static IdlePane idlePane;
    private static AntiTheftPane antiTheftPane;
    private static SettingsPane settingsPane;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    @Override 
    public void start(Stage primaryStage) 
    {                        
        //return app state to default before loading panes        
//        AppState state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        AppState state = new AppState(AppConstants.SETTINGS_CURRENT);
        state.resetAppState(); 
        SkyRfidJavaApp.initializePanes();
//        SkyRfidJavaApp.initializeRootPane();
                
        Scene scene = new Scene(rootPane, 700, 250);
//        primaryStage.setHeight(600);
//        scene = new Scene(rootPane, 700, 250);
        
//        primaryStage.setTitle(state.getReadWriteMode().toString());    //does not change with center pane
//        primaryStage.setTitle(AppConstants.APP_TITLE);        
//        primaryStage.getIcons().add(new Image("skyrfidjavaapp/images/javarhino.jpg"));  
//        primaryStage.setMinWidth(700);
//        primaryStage.setMinHeight(250);
//        primaryStage.setMaxWidth(900);
//        primaryStage.setMaxHeight(600);
//        primaryStage.setScene(scene);
//        primaryStage.show();
        
        appStage = primaryStage;
        appStage.setTitle(AppConstants.APP_TITLE);        
        appStage.getIcons().add(new Image("skyrfidjavaapp/images/javarhino.jpg"));  
//        appStage.setMinWidth(700);
//        appStage.setMinHeight(250);
//        appStage.setMaxWidth(900);
//        appStage.setMaxHeight(600);        
        System.out.println("app start asks to reset panes");
        SkyRfidJavaApp.resetWorkingPanes();
        appStage.setScene(scene);
        appStage.show();
        
        
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("application stop");
        System.exit(0);
        //Platform.exit() does not stop timer
    }
    /**
     * Creates instances for all of the panes used in the application. One instance of each
     * is used throughout operation.
     */
    private static void initializePanes() {
        rootPane = new BorderPane();
        pgmMenu = new MenuBarPane();
        singleMultiPane = new ToggleSingleMultiPane();
        readPane = new ReadPane();    
        writePane = new WritePane();
        idlePane = new IdlePane();
        antiTheftPane = new AntiTheftPane();
        settingsPane = new SettingsPane();
    }
    /**
     * @deprecated 
     * Used when application launches. Loads the working panes.
     * Superseded by resetWorkingPanes().
     */
    private static void initializeRootPane() {
        
//        rootPane.setTop(pgmMenu.getPane());
//        rootPane.setLeft(singleMultiPane.getPane());
//        rootPane.setRight(antiTheftPane.getPane());
        //center pane is set in resetWorkingPanes, as it varies with app state.
        
        
    }
    /**
     * Used when application launches and when state changes. Also used to return to normal operation after
     * viewing the settings pane. The working panes are the panes that display operational features, 
     * such as displaying the data from a tag and providing quick access to common parameters. The 
     * working panes are contrasted with the settings pane, where more advanced changes may 
     * be made to the configuration.
     * Because this method changes the dimensions of the stage, the stage must be initialized before 
     * calling this method.
     */
    public static void resetWorkingPanes() {   
//        ObservableList<Node> currentNodes = rootPane.getChildren();
//        System.out.println("there are " + currentNodes.size() + " nodes in the root pane");
//        rootPane.getChildren().clear();
        System.out.println("app class reset panes start");
        
        rootPane.setTop(pgmMenu.getPane());
        rootPane.setLeft(singleMultiPane.getPane());
        rootPane.setRight(antiTheftPane.getPane());
        
        readPane.stopTimer();
        singleMultiPane.setLblAndButtonTxt();
        antiTheftPane.setButtons();
        //need app state to choose center pane
//        AppState state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        AppState state = new AppState(AppConstants.SETTINGS_CURRENT);
        ReadWriteModeEnum rw_state = state.getReadWriteMode();        
        switch (rw_state) {            
            case READ_MODE:                     
                rootPane.setCenter(readPane.getPane());
                //adjust read params
                readPane.setReadFreq(state.getReadFreq());
//                readPane.setTheftOffValue(state.getAntiTheftOff());
//                readPane.setTheftOnValue(state.getAntiTheftOn());
                readPane.setXtraKeys(state.getExtraKeys());
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
        SkyRfidJavaApp.setStageSize(StageSizeEnum.RW_LARGE);
    }
    /**
     * Replaces the working panes with a settings pane
     */
    public static void openSettingsPane() {
//        Stage s = new Stage();
//        s.setHeight(500);
//        appStage.setHeight(600);
        rootPane.getChildren().clear();
        
        rootPane.setTop(pgmMenu.getPane());
        settingsPane.resetForm();
        rootPane.setCenter(settingsPane.getPane());
//        SkyRfidJavaApp.setStageSize(StageSizeEnum.CONFIG);
    }
    public static void setStageSize(StageSizeEnum size) {
        switch (size) {
            case RW_SMALL:
                appStage.setHeight(AppConstants.APP_SIZE_RW_SMALL_HEIGHT);
                appStage.setWidth(AppConstants.APP_SIZE_RW_SMALL_WIDTH);
                break;
            case CONFIG:                
                appStage.setHeight(AppConstants.APP_SIZE_CONFIG_HEIGHT);
                appStage.setWidth(AppConstants.APP_SIZE_CONFIG_WIDTH);
                break;                
            case RW_LARGE:
            default:                
                appStage.setHeight(AppConstants.APP_SIZE_RW_LARGE_HEIGHT);
                appStage.setWidth(AppConstants.APP_SIZE_RW_LARGE_WIDTH);                        
        }
    }
    public static void showStageSize() {
        System.out.println("stage width " + appStage.getWidth() + " height " + appStage.getHeight());
    }
   
}
