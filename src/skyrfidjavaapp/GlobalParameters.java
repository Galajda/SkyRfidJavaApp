/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyrfidjavaapp;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


/**
 *
 * @author MichalG
 */
public class GlobalParameters 
{
    public static final char READ_SINGLE_CARD = 0x36;
    public static final char CARD_TYPE_ISO15693 = 0x31;
    
    public static final int USB_PORT = 100;
    
    
    private AntiTheftEnum antiTheftAction;
    private ProgramModeEnum pgmMode;
    private boolean multiRead;
    private BorderPane rootPane;
    
    //constructor
    GlobalParameters(BorderPane bPane)
    {
     antiTheftAction = AntiTheftEnum.NO_ACTION;
     pgmMode = ProgramModeEnum.IDLE_MODE;
     multiRead = false;
     rootPane = bPane;
    }

    public boolean isMultiRead() {
        return multiRead;
    }

    public void setMultiRead(boolean multiRead) {
        this.multiRead = multiRead;
    }

    //accessors
    public AntiTheftEnum getAntiTheftAction() {
        return antiTheftAction;
    }

    public void setAntiTheftAction(AntiTheftEnum antiTheftAction) {
        this.antiTheftAction = antiTheftAction;
    }

    public ProgramModeEnum getPgmMode() {
        return pgmMode;
    }

    public void setPgmMode(ProgramModeEnum pgmMode) {
        this.pgmMode = pgmMode;
    }
    
    public void setCtrPane(Pane p)
    {
        this.rootPane.setCenter(p);
    }
    
}
