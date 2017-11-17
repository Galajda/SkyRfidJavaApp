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
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author MichalG
 */
public class ReadPane 
{
    private final VBox pane;
    private final Label lblWelcome;
    private final Button btnReadData;  
    private final Label lblDecodedData;
    
    //constructor
    ReadPane() {
//        System.out.println("read pane constructor running");        
        pane = new VBox();
        pane.setMinWidth(300);
        lblWelcome = new Label("Welcome to the RFID reader.\nThe read mode is under construction.");
        pane.getChildren().add(lblWelcome);        
        
        btnReadData = new Button("Read data");
        btnReadData.setOnAction(e -> btnReadData_Click());
        pane.getChildren().add(btnReadData);
        
        lblDecodedData = new Label("your number here");        
        lblDecodedData.setStyle("-fx-border-color: black; -fx-border-width: 2;"
                + "-fx-font-size:16px; -fx-alignment: center;" 
                + "-fx-min-width: 70; -fx-max-width:200; -fx-min-height: 30;");
        pane.getChildren().add(lblDecodedData);
        
        
        AppState state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        System.out.println("read pane constructor sees multi read " + state.isMultiRead());
        System.out.println("read pane constructor sees anti theft action " + state.getAntiTheftAction());
        
        
            lblDecodedData.setStyle("-fx-text-fill:#dc143c");
            lblDecodedData.setText("Error connecting to device. Try resetting or restarting.");
        
//        System.out.println("read pane constructor finished.");
        
    }
    public VBox getPane()
    {
        return this.pane;
    }


    private void btnReadData_Click() {
        System.out.println("got click to read data");
        TagReader reader = new TagReader();
        String cardData = reader.readCards()[0];
        lblDecodedData.setText(cardData);
        
    }
}
