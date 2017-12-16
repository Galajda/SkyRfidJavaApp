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
 * @author Michal G. <Michal.G at cogitatummagnumtelae.com>
 */
public class SettingsPane {
    
    private final VBox pane;
    private final Label lblGreeting;
    
    //constructor
    public SettingsPane() {
        pane = new VBox();
        lblGreeting = new Label("the settings pane is under construction");
        pane.getChildren().add(lblGreeting);
        
    }
    public VBox getPane() {
        return this.pane;
    }
    private void testFcn() {
//        AppSettingsEnum.class.
    }
}
