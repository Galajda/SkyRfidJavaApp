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

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;


/**
 *
 * @author MichalG
 */
public class WritePane 
{
    private final VBox pane;
    private final Label lblWelcome; 
    private final TextField txtBarcodeInput;
    private final String STYLE_WRITE_SUCCESS = "-fx-border-color: black; -fx-border-width: 2;"
            + "-fx-border-radius:5; -fx-background-color: #adff2f;"; //green
    private final String STYLE_WRITE_FAIL = "-fx-border-color: black; -fx-border-width: 2;"
            + "-fx-border-radius:5; -fx-background-color: #ff1493;"; //red
    private final String STYLE_WRITE_NEUTRAL = "-fx-border-color: black; -fx-border-width: 2;"
            + "-fx-border-radius:5; -fx-background-color: #ffffff;"; //white
    
    private final Label lblInputInstructions;
    private static TagWriter writer;
    
    WritePane()
    {        
        pane = new VBox();
        pane.setMinWidth(300);
        
        lblWelcome = new Label("Welcome to the RFID reader.\nThe write mode is under construction.");
        pane.getChildren().add(lblWelcome);
        
        txtBarcodeInput = new TextField();
        resetTextField();
        txtBarcodeInput.setPrefWidth(200);
        txtBarcodeInput.setMaxWidth(200);
        txtBarcodeInput.setPrefHeight(30);
        txtBarcodeInput.setOnKeyReleased(e -> txtBarcodeInput_KeyUp(e));
        pane.getChildren().add(txtBarcodeInput);
        
        lblInputInstructions = new Label("Press enter on keyboard to submit");
        pane.getChildren().add(lblInputInstructions);
        
        
    }
    public VBox getPane()
    {
        return this.pane;
    }
    
    private void txtBarcodeInput_KeyUp(KeyEvent e) {
//        System.out.println("key release " + e.getCode());
        if (e.getCode().equals(KeyCode.ENTER)) {
            writer = new TagWriter();
            System.out.println("you entered barcode " + txtBarcodeInput.getText());
            boolean writeSuccess = writer.WriteOneTag(txtBarcodeInput.getText());
            writer.closePort();
            System.out.println("write result " + writeSuccess);
            if (writeSuccess) {
                txtBarcodeInput.setStyle(this.STYLE_WRITE_SUCCESS);
                //change theft bit
            }
            else {
                txtBarcodeInput.setStyle(this.STYLE_WRITE_FAIL);
            }
            //wait, reset text field 
            Timer tmr = new Timer();
            tmr.schedule(new DelayedResetTextField(), 1000);
            //SO 24104313
//            System.out.println("changed style. start wait " + System.currentTimeMillis());
//            try {Thread.sleep(1000);}
//            catch (InterruptedException interruptEx) {Thread.currentThread().interrupt();}
//            System.out.println("end wait " + System.currentTimeMillis());
//            txtBarcodeInput.clear();
//            txtBarcodeInput.setStyle(this.STYLE_WRITE_NEUTRAL);
        }
    }
    class DelayedResetTextField extends TimerTask {
        @Override
        public void run() {
            Platform.runLater(new Runnable () {
                @Override
                public void run() {
                    resetTextField();
                }            
            });            
        }        
    }
    private void resetTextField() {
        txtBarcodeInput.setStyle(this.STYLE_WRITE_NEUTRAL);        
        txtBarcodeInput.clear();
        txtBarcodeInput.setPromptText("enter barcode");
        //prompt text is erased when cursor enters field
    }
}
