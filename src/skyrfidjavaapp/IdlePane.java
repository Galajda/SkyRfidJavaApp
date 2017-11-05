/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyrfidjavaapp;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;


/**
 *
 * @author MichalG
 */
public class IdlePane 
{
    private VBox pane;
    private final Label lblWelcome;
    
    
    IdlePane()
    {
        lblWelcome = new Label("Welcome to the RFID reader.\nThe program is in idle mode.");
        pane = new VBox();
        pane.setMinWidth(300);
        pane.getChildren().add(lblWelcome);
               
    }
    VBox getPane()
    {
        return this.pane;
    }
}
