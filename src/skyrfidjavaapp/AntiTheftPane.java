/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyrfidjavaapp;


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
    AntiTheftPane(GlobalParameters parms)
    {
        lblTheftState = new Label(); //("Current theft bit state is\n" + parms.getAntiTheftAction().name());
        
        tglTheftButtons = new ToggleGroup();
        rdoTurnOn = new RadioButton("Turn theft bit o_n");
        rdoTurnOn.setToggleGroup(tglTheftButtons);
        rdoTurnOn.setOnAction(e->RadioChange(parms, "on"));
        rdoTurnOff = new RadioButton("Turn theft bit _off");
        rdoTurnOff.setToggleGroup(tglTheftButtons);
        rdoTurnOff.setOnAction(e->RadioChange(parms, "off"));
        rdoNoAction = new RadioButton("Ta_ke no action");
        //rdoNoAction.fire();
        rdoNoAction.setToggleGroup(tglTheftButtons);
        rdoNoAction.setOnAction(e->RadioChange(parms, "neither"));
        
        //borrow event handler to initialize label text and radio button
        RadioChange( parms, "neither");
        
        pane = new VBox();
        pane.setMinWidth(200);
        pane.getChildren().addAll(lblTheftState, rdoTurnOn, rdoTurnOff, rdoNoAction);
    }
    
    public VBox getPane()
    {
        return this.pane;
    }
    //action events. change text of label and button, change enum theft state
    private void RadioChange(GlobalParameters parms, String whichState)
    {
        //FxMsgBox.show("The current selection is " + tglTheftButtons.getSelectedToggle().toString(), "Radio button change");
        
        switch (whichState)
        {
            case "on":
                //FxMsgBox.show("turning on", "in switch");
                parms.setAntiTheftAction(AntiTheftEnum.TURN_ON);
                rdoTurnOn.fire();
                break;
            case "off":
                //FxMsgBox.show("turning off", "in switch");
                parms.setAntiTheftAction(AntiTheftEnum.TURN_OFF);
                rdoTurnOff.fire();
                break;
                
            case "neither":
                //FxMsgBox.show("turning idle", "in switch");
                parms.setAntiTheftAction(AntiTheftEnum.NO_ACTION);
                rdoNoAction.fire();
                break;
                
        }
        lblTheftState.setText("Current state is\n" + parms.getAntiTheftAction().name());
        
    }
}
