/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyrfidjavaapp;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

/**
 *
 * @author MichalG
 */
public class ChooseSingleMultiPane 
{
    //display current mode in label
    //alter button text for the other choice
    
    private VBox pane;
    private Label lblCurrentSingleMulti;
    private Button btnChooseSingleMulti;
    
    
    //constructor
    ChooseSingleMultiPane(GlobalParameters parms)
    {
        
        pane = new VBox();
        pane.setMinWidth(200);
        lblCurrentSingleMulti = new Label();
        btnChooseSingleMulti = new Button();
        btnChooseSingleMulti.setOnAction(e-> btnChooseSingleMulti_Click(parms));
        setLblAndButtonTxt(parms);
        
        pane.getChildren().addAll(lblCurrentSingleMulti,btnChooseSingleMulti);
    }
    public VBox getPane()
    {
        return this.pane;
    }
    
    private void setLblAndButtonTxt(GlobalParameters parms)
    {
        if (parms.isMultiRead())
        {
            lblCurrentSingleMulti.setText("Reading multiple tags");
            btnChooseSingleMulti.setText("Switch to _single tag");
        }
        else 
        {
            lblCurrentSingleMulti.setText("Reading single tag");
            btnChooseSingleMulti.setText("Switch to _multiple tags");
        }
    }
    //action events. change text of label and button, change bool multi read mode
    private void btnChooseSingleMulti_Click(GlobalParameters parms)
    {
        parms.setMultiRead(!parms.isMultiRead());
        setLblAndButtonTxt(parms);
    }
}
