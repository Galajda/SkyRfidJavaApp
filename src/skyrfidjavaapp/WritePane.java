/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyrfidjavaapp;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author MichalG
 */
public class WritePane 
{
    private VBox pane;
    private final Label lblWelcome; 
    
    WritePane()
    {
        lblWelcome = new Label("Welcome to the RFID reader.\nThe write mode is under construction.");
        pane = new VBox();
        pane.setMinWidth(300);
        pane.getChildren().add(lblWelcome);
    }
    public VBox getPane()
    {
        return this.pane;
    }
}
