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
import javafx.event.ActionEvent;
import cogimag.javafx.FxComboBox;
/**
 * The idle pane allows the program to remain running without taking any action
 * on RFID tags. It may be used during development to test features.
 * @author MichalG
 */
public class IdlePane 
{
    private final VBox pane;
    private final Label lblWelcome;
    
//    private final Button btnShowConfirmation;
//    private final Button btnShowLogin;
//    private final Button btnSmallWindow;
//    private final Button btnMediumWindow;
//    private final Button btnLargeWindow;
//    private final Button btnShowCurrentSize;
    private final FxComboBox myComboBox;
    IdlePane()
    {
        lblWelcome = new Label("Welcome to the RFID reader.\nThe program is in idle mode.");
        
        pane = new VBox();
        pane.setMinWidth(180);
        pane.getChildren().add(lblWelcome);
        
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
        
        myComboBox = new FxComboBox();
        myComboBox.getItems().addAll("one", "two", "three", "ka", "four");
        
        pane.getChildren().add(myComboBox);
//        pane.getChildren().addAll(btnSmallWindow,btnMediumWindow,btnLargeWindow,btnShowCurrentSize);
    }
    VBox getPane()
    {
        return this.pane;
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
    
    private void btnResize(ActionEvent e, StageSizeEnum size) {
        SkyRfidJavaApp.setStageSize(size);
    }
}
