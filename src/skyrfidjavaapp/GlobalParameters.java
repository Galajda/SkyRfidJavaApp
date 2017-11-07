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
