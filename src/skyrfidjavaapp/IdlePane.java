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
