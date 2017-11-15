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

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author MichalG
 */
public class FxMsgBox {
    public static void show(String message, String title)
    {
        //displays a message box using FX tools
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(300);
        stage.setMaxWidth(500);
        //set width according to content. 
        
        Label msgArea = new Label(message); //how to wrap message?
        Button btnOk = new Button("OK");
        btnOk.setOnAction(e->stage.close());
        VBox pane = new VBox(40);
        pane.getChildren().addAll(msgArea, btnOk);
        pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
