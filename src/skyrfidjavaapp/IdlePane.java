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

    IdlePane()
    {
        pane = new VBox();
        pane.setMinWidth(180);
        lblWelcome = new Label("Welcome to the RFID reader.\nThe program is in idle mode.");
        pane.getChildren().add(lblWelcome);
        
    }
    VBox getPane()
    {
        
        return this.pane;
    }

}
