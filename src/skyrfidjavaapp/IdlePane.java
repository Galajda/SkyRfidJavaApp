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
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
//import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//import javafx.scene.input.KeyCode;
//import cogimag.javafx.input.FxComboBox;

import java.awt.event.KeyEvent;
/**
 * The idle pane allows the program to remain running without taking any action
 * on RFID tags. It may be used during development to test features.
 * @author MichalG
 */
public class IdlePane 
{
    private final VBox pane;
    private final Label lblWelcome;
    private final Label lblTestIntro;
    private final TextField txtCharInput;
    private final Button btnGetCharCode;
//    private final Button btnShowConfirmation;
//    private final Button btnShowLogin;
//    private final Button btnSmallWindow;
//    private final Button btnMediumWindow;
//    private final Button btnLargeWindow;
//    private final Button btnShowCurrentSize;
//    private final FxComboBox myComboBox;
    IdlePane()
    {
        pane = new VBox();
        pane.setMinWidth(180);
        lblWelcome = new Label("Welcome to the RFID reader.\nThe program is in idle mode.");
        pane.getChildren().add(lblWelcome);
        
        lblTestIntro = new Label("test feature: extend send keys to full keyboard");
        pane.getChildren().add(lblTestIntro);
//        btnShowConfirmation = new Button("Show confirmation box");
//        btnShowConfirmation.setOnAction(e -> btnShowConfirmation_Click(e));
//        pane.getChildren().add(btnShowConfirmation);
//        btnShowLogin = new Button("Show login box");
//        btnShowLogin.setOnAction(e -> btnShowLogin_Click(e));
//        pane.getChildren().add(btnShowLogin);
               
//        this.btnSmallWindow = new Button("Small R/W window");
//        btnSmallWindow.setOnAction(e -> btnResize(e, StageSizeEnum.RW_SMALL));
//        this.btnMediumWindow = new Button("Large R/W window");
//        btnMediumWindow.setOnAction(e -> btnResize(e, StageSizeEnum.RW_LARGE));
//        this.btnLargeWindow = new Button("Config window");
//        btnLargeWindow.setOnAction(e -> btnResize(e, StageSizeEnum.CONFIG));
//        btnShowCurrentSize = new Button("Show current size");
//        btnShowCurrentSize.setOnAction(e -> SkyRfidJavaApp.showStageSize());        
//        myComboBox = new FxComboBox();
//        myComboBox.getItems().addAll("one", "two", "three", "ka", "four");        
//        pane.getChildren().add(myComboBox);
//        pane.getChildren().addAll(btnSmallWindow,btnMediumWindow,btnLargeWindow,btnShowCurrentSize);
        
        txtCharInput = new TextField();
        txtCharInput.setMaxWidth(50);
//        txtCharInput.setOnKeyReleased(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                System.out.println("handling key release");
//                System.out.println("\tcode name " + event.getCode().getName());
//                System.out.println("\tchar " + event.getCharacter().toString());
//                System.out.println("\tcode value of " + Enum.valueOf(KeyCode.class, event.getCode().toString()));
//                System.out.println("\tcode hash code " + event.getCode().hashCode());
//                txtCharInput.setText("");
//            }            
//        });
        
        pane.getChildren().add(txtCharInput);
        
        
        btnGetCharCode = new Button("Get the char code");
        btnGetCharCode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                char keystroke = txtCharInput.getText().charAt(0);
                System.out.println("literal value " + txtCharInput.getText());
                System.out.println("\tcode point " + txtCharInput.getText().codePointAt(0));
                System.out.println("\tchar hex value " + String.format("%04x", (int)keystroke));
//                System.out.println("\tkey text " + KeyEvent.getKeyText(keystroke));
                    //mapping of key text to keys is inaccurate
                int extKeyCode = KeyEvent.getExtendedKeyCodeForChar(keystroke);
                System.out.println("\textended keycode " + extKeyCode);
//                System.out.println("\tucase? " + Character.isUpperCase(keystroke)); //@ is not considered ucase
                System.out.println("\tchar type " + Character.getType(keystroke));                
                System.out.println("\tnumeric value " + Character.getNumericValue(keystroke));
                System.out.println("\tis it a letter or digit? " + Character.isLetterOrDigit(keystroke));
                System.out.println("\tstring val of char " + String.valueOf(keystroke));
                System.out.println("\tstring to lcase " + String.valueOf(keystroke).toLowerCase()); 
                System.out.println("\tstring to ucase " + String.valueOf(keystroke).toUpperCase()); 
                    //does not downshift symbols, e.g. ! -> 1
                txtCharInput.clear();
                txtCharInput.requestFocus();
//                System.out.println("\tasterisk sign " + (KeyEvent.VK_ASTERISK));
            }
            
            
        });
        
        pane.getChildren().add(btnGetCharCode);
    }
    VBox getPane()
    {
        
        return this.pane;
    }
    private void processKey(char ch) {
        
    }
//    private void btnShowConfirmation_Click(ActionEvent e) {
//        FxMsgBox confirmDialog = new FxMsgBox();
//        Boolean confirmationResponse = confirmDialog.confirm("click ok or cancel", "confirm the action");
//                //FxMsgBox.confirm("click ok or cancel", "confirm the action");
//        System.out.println("msg box response " + confirmationResponse);
//    }
//    private void btnShowLogin_Click(ActionEvent e) {
//        FxMsgBox loginDialog = new FxMsgBox();
//        Boolean loginSuccess = loginDialog.login("pass");
//        System.out.println("login success " + loginSuccess);
//    }
    
//    private void btnResize(ActionEvent e, StageSizeEnum size) {
//        SkyRfidJavaApp.setStageSize(size);
//    }
}
