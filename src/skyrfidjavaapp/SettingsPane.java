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

import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
/**
 *
 * @author Michal G. <Michal.G at cogitatummagnumtelae.com>
 */
public class SettingsPane {
    
    private final GridPane pane;
    private final AppState state;
    private final Label lblGreeting;
    private final Label lblConfigSelector;
    private final ComboBox<String> configSelector;
    private final Label lblConfigName;
    private final TextField txtConfigName;
    private final Label lblTheftAction;
    private final ComboBox<String> theftActionSelector;
    private final Label lblReadWriteMode;
    private final ComboBox<String> readWriteModeSelector;
    private final Label lblMultiRead;
    private final ComboBox<String> multiReadSelector; //make this a radio
    private final Label lblReadFreq;
    private final TextField txtReadFreq;
    private final Label lblXtraKeys;
    private final TextField txtXtraKeys;
    private final Label lblTheftOn;
    private final TextField txtTheftOn;
    private final Label lblTheftOff;
    private final TextField txtTheftOff;    
    private final Button btnSaveConfig;
    private final Button btnDeleteConfig;
    private final Button btnUseConfig;
    //constructor
    public SettingsPane() {
//        pane = new VBox();
        pane = new GridPane();
        lblGreeting = new Label("the settings pane is under construction");
//        pane.getChildren().add(lblGreeting);
        pane.add(lblGreeting, 0, 0, 2, 1);
        
        lblConfigSelector = new Label("Choose a configuration");
        pane.add(lblConfigSelector, 0, 1);        
        state = new AppState(AppConstants.SETTINGS_CURRENT);
        configSelector = new ComboBox<>();
        configSelector.setPromptText("Choose a configuration");
        configSelector.getItems().addAll(state.getConfigNames());
        configSelector.getItems().add("new");
        configSelector.setOnAction(e -> configSelector_Selected(e));
        pane.add(configSelector, 1, 1);
        
        lblConfigName = new Label("Name");
        pane.add(lblConfigName, 2, 1);        
        txtConfigName = new TextField();
        txtConfigName.setMaxWidth(200);
        pane.add(txtConfigName, 3, 1);
        
        lblTheftAction = new Label("Default theft bit action");
        pane.add(lblTheftAction, 0, 2);
        theftActionSelector = new ComboBox<>();
        theftActionSelector.getItems().addAll(AntiTheftEnum.NO_ACTION.name(),
                AntiTheftEnum.TURN_ON.name(), AntiTheftEnum.TURN_OFF.name());
        pane.add(theftActionSelector, 1, 2);
        
        lblReadWriteMode = new Label("Default read/write");
        pane.add(lblReadWriteMode, 0, 3);
        readWriteModeSelector = new ComboBox<>();
        readWriteModeSelector.getItems().addAll(ReadWriteModeEnum.IDLE_MODE.name(), 
                ReadWriteModeEnum.READ_MODE.name(), ReadWriteModeEnum.WRITE_MODE.name());
        pane.add(readWriteModeSelector, 1, 3);
        
        lblMultiRead = new Label("Multi read");
        pane.add(lblMultiRead, 0, 4);
        multiReadSelector = new ComboBox();
        multiReadSelector.getItems().addAll("true", "false");
        pane.add(multiReadSelector, 1, 4);
        
        lblReadFreq = new Label("Milliseconds between readings");
        pane.add(lblReadFreq, 0, 5);
        txtReadFreq = new TextField();
        pane.add(txtReadFreq, 1, 5);
        
        lblXtraKeys = new Label("Extra keystrokes");
        pane.add(lblXtraKeys, 0, 6);
        txtXtraKeys = new TextField();
        pane.add(txtXtraKeys, 1, 6);
        
        lblTheftOn = new Label("Value of theft on");
        pane.add(lblTheftOn, 0, 7);
        txtTheftOn = new TextField();
        pane.add(txtTheftOn, 1, 7);
        
        lblTheftOff = new Label("Value of theft off");
        pane.add(lblTheftOff, 2, 7);
        txtTheftOff = new TextField();
        pane.add(txtTheftOff, 3, 7);
        
        btnSaveConfig = new Button("Save");
        btnSaveConfig.setOnAction(e -> btnSaveConfig_Click(e));
        pane.add(btnSaveConfig, 0, 8);
        
        btnDeleteConfig = new Button("Delete this config");
        btnDeleteConfig.setOnAction(null);
        pane.add(btnDeleteConfig, 1, 8);
        
        btnUseConfig = new Button("Use this config");
        btnUseConfig.setOnAction(null);
        pane.add(btnUseConfig, 2, 8);
    }

    public GridPane getPane() {
        return this.pane;
    }
    private void testFcn() {
//        AppSettingsEnum.class.
    }

    private void configSelector_Selected(ActionEvent e) {
        System.out.println("you selected config " + configSelector.getValue());
        
    }

    private void btnSaveConfig_Click(ActionEvent e) {
        System.out.println("click to save config");
    }
}
