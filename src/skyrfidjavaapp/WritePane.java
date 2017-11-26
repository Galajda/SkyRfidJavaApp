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


/**
 *
 * @author MichalG
 */
public class WritePane 
{
    private VBox pane;
    private final Label lblWelcome; 
    private TextField txtBarcodeInput;
    
    WritePane()
    {        
        pane = new VBox();
        pane.setMinWidth(300);
        
        lblWelcome = new Label("Welcome to the RFID reader.\nThe write mode is under construction.");
        pane.getChildren().add(lblWelcome);
        
        txtBarcodeInput = new TextField();
        txtBarcodeInput.setPromptText("enter barcode");
        txtBarcodeInput.setPrefWidth(50);
        txtBarcodeInput.setPrefHeight(30);
        txtBarcodeInput.setOnKeyReleased(e -> txtBarcodeInput_KeyUp(e));
        pane.getChildren().add(txtBarcodeInput);
    }
    public VBox getPane()
    {
        return this.pane;
    }
    
    private void txtBarcodeInput_KeyUp(KeyEvent e) {
        System.out.println("key release " + e.getCode());
        if (e.getCode().equals(KeyCode.ENTER)) {
            System.out.println("you entered barcode " + txtBarcodeInput.getText());
            txtBarcodeInput.clear();
        }
    }
}
