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

import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.Pane;
//import javafx.scene.input.MouseEvent;


/**
 *
 * @author MichalG
 */
public class MenuBarPane 
{
    private final HBox pane;
    private final MenuBar menu;
    
    private final Menu fileMenu;
    private final MenuItem resetMenu;
    private final MenuItem smallWinMenu;
    private final MenuItem largeWinMenu;
    private final MenuItem configMenu;
    private final MenuItem exitMenu;
    
    private final Menu readWriteMenu;
    private final MenuItem readMenu;
    private final MenuItem writeMenu;
    private final MenuItem idleMenu;
    
    private final static String FILE_MENU_RESET = "Rese_t";
    private final static String FILE_MENU_SM_WIN = "_Small window";
    private final static String FILE_MENU_LG_WIN = "_Large window";
    private final static String FILE_MENU_CONFIG = "_Configuration";
    private final static String FILE_MENU_EXIT = "E_xit";
    
    private final static String R_W_MENU_READ = "R_ead tags";
    private final static String R_W_MENU_WRITE = "_Write tags";
    private final static String R_W_MENU_IDLE = "_Idle";
   
    
    MenuBarPane()
    {
//        AppState state;
//        state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        pane = new HBox();
        menu = new MenuBar();
        
        fileMenu = new Menu("_File");        
        resetMenu = new MenuItem(MenuBarPane.FILE_MENU_RESET);
        resetMenu.setOnAction(e-> FileMenuItem_Click(e));
        smallWinMenu = new MenuItem(MenuBarPane.FILE_MENU_SM_WIN);
        smallWinMenu.setOnAction(e-> FileMenuItem_Click(e));
        largeWinMenu = new MenuItem(MenuBarPane.FILE_MENU_LG_WIN);
        largeWinMenu.setOnAction(e-> FileMenuItem_Click(e));
        configMenu = new MenuItem(MenuBarPane.FILE_MENU_CONFIG);
        configMenu.setOnAction(e-> FileMenuItem_Click(e));
        exitMenu = new MenuItem(MenuBarPane.FILE_MENU_EXIT);
        exitMenu.setOnAction(e-> FileMenuItem_Click(e));
        fileMenu.getItems().addAll(resetMenu, new SeparatorMenuItem(), smallWinMenu, largeWinMenu,
                configMenu, new SeparatorMenuItem(), exitMenu);
           
        readWriteMenu = new Menu ("_Read/Write");
        readMenu = new MenuItem(MenuBarPane.R_W_MENU_READ);
        readMenu.setOnAction(e->ModeSelectMenu_Click(e));
        writeMenu = new MenuItem(MenuBarPane.R_W_MENU_WRITE);
        writeMenu.setOnAction(e->ModeSelectMenu_Click(e));
        idleMenu = new MenuItem(MenuBarPane.R_W_MENU_IDLE);
        idleMenu.setOnAction(e->ModeSelectMenu_Click(e));

        
        readWriteMenu.getItems().addAll(readMenu, writeMenu, idleMenu);
        
        menu.getMenus().add(fileMenu);
        menu.getMenus().add(readWriteMenu);
        
        pane.getChildren().add(menu);
        
    }
    public HBox getPane()
    {
        return this.pane;
    }
    //action events
    private void FileMenuItem_Click(ActionEvent e)
    {        
        MenuItem eventSource = (MenuItem)e.getSource();
        switch (eventSource.getText()) {
            case MenuBarPane.FILE_MENU_RESET:                
//                FxMsgBox.show("pretend to reset", "Menu action");
                //reset the current parameters to the defaults
//                AppState state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
                AppState state = new AppState(AppConstants.SETTINGS_CURRENT);
                state.resetAppState(); 
                 //reload stage
                System.out.println("menu bar pane file menu asks to reset panes");
                SkyRfidJavaApp.resetWorkingPanes();
                
                break;
            case MenuBarPane.FILE_MENU_SM_WIN:
                FxMsgBox.show("pretend you see a small window", "Menu action");
                break;
            case MenuBarPane.FILE_MENU_LG_WIN:
                FxMsgBox.show("pretend you see a large window", "Menu action");
                break;
            case MenuBarPane.FILE_MENU_CONFIG:                
//                FxMsgBox.show("config is not yet configged", "Menu action");
                SkyRfidJavaApp.openSettingsPane();
                break;
            case MenuBarPane.FILE_MENU_EXIT:                
//                FxMsgBox.show("exit", "Menu action");
                System.exit(0);
            default:                
                FxMsgBox.show("event source is " + eventSource.getText(), "source debugging");
                FxMsgBox.show("event type is " +e.getEventType().toString(), "source debugging");                
        }
        
    }
    private void ModeSelectMenu_Click (ActionEvent e)
    {
        Pane p;
        MenuItem eventSource = (MenuItem)e.getSource();
//        AppState state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        AppState state = new AppState(AppConstants.SETTINGS_CURRENT);
        switch (eventSource.getText()) {
            case MenuBarPane.R_W_MENU_READ:                
                state.setReadWriteMode(ReadWriteModeEnum.READ_MODE);
                //FxMsgBox.show("Change mode to read", "Mode select event");
//                ReadPane readPane = new ReadPane();
//                p = readPane.getPane();
                break;
            case MenuBarPane.R_W_MENU_WRITE:
                state.setReadWriteMode(ReadWriteModeEnum.WRITE_MODE);
                //FxMsgBox.show("Change mode to write", "Mode select event");
//                WritePane writePane = new WritePane();
//                p = writePane.getPane();
                break;
            case MenuBarPane.R_W_MENU_IDLE:                
                //FxMsgBox.show("Change mode to idle", "Mode select event");
                //fall through to default
            default:
                state.setReadWriteMode(ReadWriteModeEnum.IDLE_MODE);
//                IdlePane idlePane = new IdlePane();
//                p = idlePane.getPane();
        }
        System.out.println("menu bar pane mode menu asks to reset panes");
        SkyRfidJavaApp.resetWorkingPanes();
        
        
    }
}
