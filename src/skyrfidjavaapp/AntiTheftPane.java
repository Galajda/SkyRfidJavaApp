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


//import java.util.HashSet;
//import java.util.Set;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 *
 * @author MichalG
 */
public class AntiTheftPane {
    
    
    Label lblTheftState;
    RadioButton rdoTurnOn;
    RadioButton rdoTurnOff;
    RadioButton rdoNoAction;
    ToggleGroup tglTheftButtons;
    VBox pane;
    
    //constructor
//    AntiTheftPane(AppConstants parms)
    AntiTheftPane()
    {
        lblTheftState = new Label(); 
        
        tglTheftButtons = new ToggleGroup();
        rdoTurnOn = new RadioButton("Turn theft bit o_n");
        rdoTurnOn.setToggleGroup(tglTheftButtons);
        rdoTurnOn.setOnAction(e->RadioChange(AntiTheftEnum.TURN_ON));
        rdoTurnOff = new RadioButton("Turn theft bit _off");
        rdoTurnOff.setToggleGroup(tglTheftButtons);
        rdoTurnOff.setOnAction(e->RadioChange(AntiTheftEnum.TURN_OFF));
        rdoNoAction = new RadioButton("Ta_ke no action");     
        rdoNoAction.setToggleGroup(tglTheftButtons);
        rdoNoAction.setOnAction(e->RadioChange(AntiTheftEnum.NO_ACTION));
        
        //borrow some parts of event handler to initialize radio button and label text
        //skip the state set part
        AppState state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        AntiTheftEnum currentAction = state.getAntiTheftAction();
        switch (currentAction) {
            case TURN_ON:
//                rdoTurnOn.fire();
                rdoTurnOn.setSelected(true);
                break;
            case TURN_OFF:
//                rdoTurnOff.fire();
                rdoTurnOff.setSelected(true);
                break;                
            case NO_ACTION:
//                rdoNoAction.fire();
                rdoNoAction.setSelected(true);
                break;                
        }
        lblTheftState.setText("Current theft bit action is\n" + currentAction.name());
        
        pane = new VBox();
        pane.setMinWidth(200);
        pane.getChildren().addAll(lblTheftState, rdoTurnOn, rdoTurnOff, rdoNoAction);
        
        
    }
    
    public VBox getPane()
    {
        return this.pane;
    }
    //action events. change text of label and button, change enum theft state
    private void RadioChange(AntiTheftEnum whichState)
    {
//        System.out.println("radio change event");
        //FxMsgBox.show("The current selection is " + tglTheftButtons.getSelectedToggle().toString(), "Radio button change");
        AppState state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        state.setAntiTheftAction(whichState);
        switch (whichState)
        {
            case TURN_ON:
                //FxMsgBox.show("turning on", "in switch");                
//                state.setAntiTheftAction(AntiTheftEnum.TURN_ON);                
//                rdoTurnOn.fire();
                rdoTurnOn.setSelected(true);
                break;
            case TURN_OFF:
                //FxMsgBox.show("turning off", "in switch");
//                parms.setAntiTheftAction(AntiTheftEnum.TURN_OFF);
//                state.setAntiTheftAction(AntiTheftEnum.TURN_OFF);
//                rdoTurnOff.fire();
                rdoTurnOff.setSelected(true);
                break;
                
            case NO_ACTION:
                //FxMsgBox.show("turning idle", "in switch");
//                parms.setAntiTheftAction(AntiTheftEnum.NO_ACTION);
//                state.setAntiTheftAction(AntiTheftEnum.NO_ACTION);
//                rdoNoAction.fire();
                rdoNoAction.setSelected(true);
                break;
                
        }
        lblTheftState.setText("Current theft bit action is\n" + state.getAntiTheftAction().name());
        
    }
}
