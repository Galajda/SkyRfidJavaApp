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

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

/**
 *
 * @author MichalG
 */
public class ToggleSingleMultiPane 
{
    //display current mode in label
    //alter button text for the other choice
    
    private final VBox pane;
    private final Label lblCurrentSingleMulti;
    private final Button btnToggleSingleMulti;
    
    
    //constructor
    ToggleSingleMultiPane()
    {
        
        pane = new VBox();
        pane.setMinWidth(200);
        lblCurrentSingleMulti = new Label();
        btnToggleSingleMulti = new Button();
        btnToggleSingleMulti.setOnAction(e-> btnChooseSingleMulti_Click());
        setLblAndButtonTxt();
        
        pane.getChildren().addAll(lblCurrentSingleMulti,btnToggleSingleMulti);
    }
    public VBox getPane()
    {
        return this.pane;
    }
        //action events. change text of label and button, change bool multi read mode
    private void btnChooseSingleMulti_Click()
    {
        FxMsgBox.show("Multiple card reading\nis under construction.\nThe button changes,\nbut the action is always single.", 
                "Change single/multi read");
        AppState state;
//        state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        state = new AppState(AppConstants.SETTINGS_CURRENT);
        state.setMultiRead(!state.isMultiRead());
        setLblAndButtonTxt();
        System.out.println("single/multi toggle event asks to reset panes");
        SkyRfidJavaApp.resetWorkingPanes();
    }
    
    public final void setLblAndButtonTxt()
    {
        AppState state;
//        state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        state = new AppState(AppConstants.SETTINGS_CURRENT);
        if (state.isMultiRead())
        {
            lblCurrentSingleMulti.setText("Reading multiple tags");
            btnToggleSingleMulti.setText("Switch to _single tag");
        }
        else 
        {
            lblCurrentSingleMulti.setText("Reading single tag");
            btnToggleSingleMulti.setText("Switch to _multiple tags");
        }
    }

}
