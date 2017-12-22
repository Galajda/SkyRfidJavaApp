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
    private AppState state;
    //heading
    private final Label lblGreeting;
    private final Label lblConfigSelector;
    private final ComboBox<String> configSelector;
    private final Label lblConfigName;
    private final TextField txtConfigName;
    public static final String TXT_FLD_ID_CONFIG_NAME = "config name";
    //single/multi
    private final Label lblMultiRead;
    private final ComboBox<String> multiReadSelector; //make this a checkbox    
    //read/write parameters
    private final Label lblReadWriteMode;    
    private final ComboBox<String> readWriteModeSelector;    
    private final Label lblReadFreq;
    private final TextField txtReadFreq;    
    private final Label lblXtraKeys;
    private final TextField txtXtraKeys;
    
    //anti-theft parameters
    private final Label lblTheftAction;
    private final ComboBox<String> theftActionSelector;   
    private final Label lblTheftOn;
    private final TextField txtTheftOn;
    private final Label lblTheftOff;
    private final TextField txtTheftOff;    
    
    //buttons
    private final Button btnSaveConfig;
    private final Button btnDeleteConfig;
    private final Button btnUseConfig;
    
    //constructor
    public SettingsPane() {
        pane = new GridPane();
        //row 0
        //heading         
        lblGreeting = new Label("the settings pane is under construction");
        pane.add(lblGreeting, 0, 0, 2, 1);
        
        //row 1
        //config name
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
        lblConfigName.setVisible(false);
        pane.add(lblConfigName, 2, 1);        
        txtConfigName = new TextField();
        txtConfigName.setId(this.TXT_FLD_ID_CONFIG_NAME);
        txtConfigName.setMaxWidth(200);
        txtConfigName.setOnAction(e -> this.inputValidator(e));
        txtConfigName.setVisible(false);
        pane.add(txtConfigName, 3, 1);        
        
        //row 2     
        //single/multi        
        lblMultiRead = new Label("Multi read t/f");
        pane.add(lblMultiRead, 0, 2);
        multiReadSelector = new ComboBox();
        multiReadSelector.getItems().addAll("true", "false");
        pane.add(multiReadSelector, 1, 2);      
        
        //row 3, 4, 5
        //read/write
        lblReadWriteMode = new Label("read/write/idle");
        pane.add(lblReadWriteMode, 0, 3);
        readWriteModeSelector = new ComboBox<>();
        readWriteModeSelector.getItems().addAll(ReadWriteModeEnum.IDLE_MODE.name(), 
                ReadWriteModeEnum.READ_MODE.name(), ReadWriteModeEnum.WRITE_MODE.name());
        pane.add(readWriteModeSelector, 1, 3);
                
        lblReadFreq = new Label("Msec between readings");
        pane.add(lblReadFreq, 0, 4);
        txtReadFreq = new TextField();
        pane.add(txtReadFreq, 1, 4);
        
        lblXtraKeys = new Label("Extra keystrokes");
        pane.add(lblXtraKeys, 2, 4);
        txtXtraKeys = new TextField();
        pane.add(txtXtraKeys, 3, 4);
        
        //row 5, 6
        //anti-theft
        lblTheftAction = new Label("Theft bit action");
        pane.add(lblTheftAction, 0, 5);
        theftActionSelector = new ComboBox<>();
        theftActionSelector.getItems().addAll(AntiTheftEnum.NO_ACTION.name(),
                AntiTheftEnum.TURN_ON.name(), AntiTheftEnum.TURN_OFF.name());
        pane.add(theftActionSelector, 1, 5);
        
        lblTheftOn = new Label("Value of theft on");
        pane.add(lblTheftOn, 0, 6);
        txtTheftOn = new TextField();
        pane.add(txtTheftOn, 1, 6);
        
        lblTheftOff = new Label("Value of theft off");
        pane.add(lblTheftOff, 2, 6);
        txtTheftOff = new TextField();
        pane.add(txtTheftOff, 3, 6);
        
        //row 7
        //buttons
        btnSaveConfig = new Button("Save");
        btnSaveConfig.setOnAction(e -> btnSaveConfig_Click(e));
        pane.add(btnSaveConfig, 0, 7);
        
        btnDeleteConfig = new Button("Delete this config");
        btnDeleteConfig.setOnAction(e -> btnDeleteConfig_Click(e));
        pane.add(btnDeleteConfig, 1, 7);
        
        btnUseConfig = new Button("Use this config");
        btnUseConfig.setOnAction(e -> btnUseConfig_Click(e));
        pane.add(btnUseConfig, 2, 7);
    }

    public GridPane getPane() {
        return this.pane;
    }

    private void configSelector_Selected(ActionEvent e) {
        String selectedConfig = configSelector.getValue();        
        System.out.println("you selected config " + selectedConfig);
        Boolean isNewConfig = selectedConfig.equals("new");        
        lblConfigName.setVisible(isNewConfig);
        txtConfigName.setVisible(isNewConfig);        
        //load these values
        state = new AppState(selectedConfig);
        System.out.println("its anti theft value is " + state.getAntiTheftAction().name());
        System.out.println("its r/w value is " + state.getReadWriteMode().name());
    }

    private void inputValidator(ActionEvent e) {
        System.out.println("validating field");
        System.out.println("event type " + e.getEventType());
        System.out.println("event source " + e.getSource());
        TextField src = (TextField)e.getSource();
        System.out.println("field id " + src.getId());
    }
    
    
    private void btnSaveConfig_Click(ActionEvent e) {
        System.out.println("click to save config");
        
    }
    
    private void btnDeleteConfig_Click(ActionEvent e) {
        System.out.println("click to delete config");
    }
    
    private void btnUseConfig_Click(ActionEvent e) {
        System.out.println("click to use config");
        //save these values to current
    }
    
}
