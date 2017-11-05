/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    private HBox pane;
    private MenuBar menu;
    
    private Menu fileMenu;
    private MenuItem resetMenu;
    private MenuItem smallWinMenu;
    private MenuItem largeWinMenu;
    private MenuItem exitMenu;
    
    private Menu readWriteMenu;
    private MenuItem readMenu;
    private MenuItem writeMenu;
    private MenuItem idleMenu;
    
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
            FxMsgBox.show("reset", "Menu action");
        }
        if (e.getSource() == smallWinMenu)
        {
            FxMsgBox.show("small window", "Menu action");
        }
        if (e.getSource() == largeWinMenu)
        {
            FxMsgBox.show("large window", "Menu action");
        }
        if (e.getSource() == exitMenu)
        {
            FxMsgBox.show("exit", "Menu action");
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
