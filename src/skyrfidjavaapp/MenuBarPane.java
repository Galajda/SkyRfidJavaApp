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
import javafx.scene.input.MouseEvent;


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
    private final MenuItem exitMenu;
    
    private final Menu readWriteMenu;
    private final MenuItem readMenu;
    private final MenuItem writeMenu;
    private final MenuItem idleMenu;
    
    MenuBarPane(GlobalParameters parms)
    {
        pane = new HBox();
        menu = new MenuBar();
        
        fileMenu = new Menu("_File");
        resetMenu = new MenuItem("Rese_t");
        resetMenu.setOnAction(e-> FileMenuItem_Click(e));
        smallWinMenu = new MenuItem("_Small window");
        smallWinMenu.setOnAction(e-> FileMenuItem_Click(e));
        largeWinMenu = new MenuItem("_Large window");
        largeWinMenu.setOnAction(e-> FileMenuItem_Click(e));
        exitMenu = new MenuItem("E_xit");
        exitMenu.setOnAction(e-> FileMenuItem_Click(e));
        fileMenu.getItems().addAll(resetMenu, new SeparatorMenuItem(), smallWinMenu, largeWinMenu,
                new SeparatorMenuItem(), exitMenu);
           
        readWriteMenu = new Menu ("_Read/Write");
        readMenu = new MenuItem("R_ead tags");
        readMenu.setOnAction(e->ModeSelectMenu_Click(e, parms));
        writeMenu = new MenuItem("_Write tags");
        writeMenu.setOnAction(e->ModeSelectMenu_Click(e, parms));
        idleMenu = new MenuItem("_Idle");
        idleMenu.setOnAction(e->ModeSelectMenu_Click(e, parms));

        
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
        if (e.getSource() == resetMenu)
        {
            FxMsgBox.show("pretend to reset", "Menu action");
            //change global params, reload window
            
        }
        if (e.getSource() == smallWinMenu)
        {
            FxMsgBox.show("pretend you see a small window", "Menu action");
        }
        if (e.getSource() == largeWinMenu)
        {
            FxMsgBox.show("pretend you see a large window", "Menu action");
        }
        if (e.getSource() == exitMenu)
        {
            FxMsgBox.show("exit", "Menu action");
            System.exit(0);
        }
        // why can't i use switch case?
        MenuItem eventSource = (MenuItem)e.getSource();
        
        FxMsgBox.show("event source is " + eventSource.getText(), "source debugging");
        FxMsgBox.show("event type is " +e.getEventType().toString(), "source debugging");
        
    }
    private void ModeSelectMenu_Click (ActionEvent e, GlobalParameters parms)
    {
        MenuItem eventSource = (MenuItem)e.getSource();
        switch (eventSource.getText())
        {
            case "R_ead tags":
                parms.setPgmMode(ProgramModeEnum.READ_MODE);
                //FxMsgBox.show("Change mode to read", "Mode select event");
                break;
            case "_Write tags":
                parms.setPgmMode(ProgramModeEnum.WRITE_MODE);
                //FxMsgBox.show("Change mode to write", "Mode select event");
                break;
            case "_Idle":
                parms.setPgmMode(ProgramModeEnum.IDLE_MODE);
                //FxMsgBox.show("Change mode to idle", "Mode select event");
                break;
        }
        switch (parms.getPgmMode())
        {
            //eliminate pgm mode field from parms? 
            //window title does not change
            case IDLE_MODE:
            {
                IdlePane ctrPane = new IdlePane();
                parms.setCtrPane(ctrPane.getPane());
                break;
            }
            case READ_MODE:
            {
                ReadPane ctrPane = new ReadPane();
                parms.setCtrPane(ctrPane.getPane());
                break;
            }
            case WRITE_MODE:
            {
                WritePane ctrPane = new WritePane();
                parms.setCtrPane(ctrPane.getPane());
                break;
            }
                
        }
        
        
    }
}
