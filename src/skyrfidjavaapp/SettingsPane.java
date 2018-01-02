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

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;


/**
 *
 * The key pressed event triggers validation for Enter as well as Tab. When the user presses Tab,
 * the source of key released is the next control in the tab order, too late to validate the previous control.
 * 
 * 
 * @author Michal G. <Michal.G at cogitatummagnumtelae.com>
 */
public class SettingsPane {
    //use vbox for main pane, grid pane for name, grid pane for rest of controls
    private final GridPane mainPane; //holds the config name cbo box and the sub pane
    private final GridPane subPane; //holds the rest of the controls so they can be hidden as a group.
    private AppState state;
    //heading
    private final Label lblGreeting;
    private final Label lblConfigSelector;
    private static final String CONFIG_SELECTOR_PROMPT = "Choose a configuration";
    private final ComboBox<String> cboConfigName;
    private static final String COMBO_BOX_CONFIG_NAME = "config name combo box";
    private final Label lblConfigName;
    private final TextField txtConfigName;
    private static final String TXT_FLD_ID_CONFIG_NAME = "config name text field";
    //single/multi
    private final Label lblMultiRead;
    private final CheckBox chkMultiRead;
    //read/write parameters
    private final Label lblReadWriteMode;    
    private final ComboBox<String> cboReadWriteMode;  
    private static final String COMBO_BOX_READ_WRITE = "read write combo box";
    private final Label lblReadFreq;
    private final TextField txtReadFreq;    
    private static final String TXT_FLD_ID_READ_FREQ = "read freq";
    private final Label lblXtraKeys;
    private final TextField txtXtraKeys;
    private static final String TXT_FLD_ID_XTRA_KEYS = "extra keys";
    
    //anti-theft parameters
    private final Label lblTheftAction;
    private final ComboBox<String> cboTheftAction;
    private static final String COMBO_BOX_THEFT_ACTION = "theft action combo box";
    private final Label lblTheftOn;
    private final TextField txtTheftOn;
    private static final String TXT_FLD_ID_THEFT_ON = "theft on";
    private final Label lblTheftOff;
    private final TextField txtTheftOff;    
    private static final String TXT_FLD_ID_THEFT_OFF = "theft off";
    
    //buttons
    private final Button btnSaveConfig;
    private final Button btnDeleteConfig;
    private final Button btnUseConfig;
    private final Button btnCloseSettingsPane;
    
    //constructor
    public SettingsPane() {
        mainPane = new GridPane();
        //row 0 of main
        //heading         
        lblGreeting = new Label("the settings pane is under construction");
        mainPane.add(lblGreeting, 0, 0, 2, 1);
        
        subPane = new GridPane();
        //add these controls to sub pane, add sub pane at end.
        //row 1
        //config name
        lblConfigSelector = new Label(SettingsPane.CONFIG_SELECTOR_PROMPT);
        mainPane.add(lblConfigSelector, 0, 1);        
        state = new AppState(AppConstants.SETTINGS_CURRENT);
        cboConfigName = new ComboBox<>();
        cboConfigName.setId(SettingsPane.COMBO_BOX_CONFIG_NAME);
        cboConfigName.setPromptText("Choose a configuration");
        cboConfigName.getItems().addAll(state.getConfigNames());
        cboConfigName.getItems().add("new");
        cboConfigName.setOnAction(e -> configSelector_Selected(e));
        mainPane.add(cboConfigName, 1, 1);        
        lblConfigName = new Label("Name");
        lblConfigName.setVisible(false);
        mainPane.add(lblConfigName, 2, 1);        
        txtConfigName = new TextField();        
        txtConfigName.setId(SettingsPane.TXT_FLD_ID_CONFIG_NAME);
        txtConfigName.setMaxWidth(200);
        txtConfigName.setOnKeyPressed(e -> this.txtFldKeyPress(e));
        txtConfigName.setOnMouseExited(e -> this.txtFldMouseExit(e));
        txtConfigName.setVisible(false);
        mainPane.add(txtConfigName, 3, 1);        
        
        //row 2     
        //single/multi        
        lblMultiRead = new Label("Multi read t/f");
        mainPane.add(lblMultiRead, 0, 2);
        chkMultiRead = new CheckBox();
        mainPane.add(chkMultiRead, 1, 2);
        
        //row 3, 4, 5
        //read/write
        lblReadWriteMode = new Label("read/write/idle");
        mainPane.add(lblReadWriteMode, 0, 3);
        cboReadWriteMode = new ComboBox<>();
        cboReadWriteMode.setId(SettingsPane.COMBO_BOX_READ_WRITE);
        cboReadWriteMode.getItems().addAll(ReadWriteModeEnum.IDLE_MODE.name(), 
                ReadWriteModeEnum.READ_MODE.name(), ReadWriteModeEnum.WRITE_MODE.name());
        cboReadWriteMode.setOnKeyPressed(e -> comboBoxKeyboardShortcut(e, cboReadWriteMode));
        mainPane.add(cboReadWriteMode, 1, 3);
                
        lblReadFreq = new Label("Msec between readings");
        mainPane.add(lblReadFreq, 0, 4);
        txtReadFreq = new TextField();
        txtReadFreq.setId(SettingsPane.TXT_FLD_ID_READ_FREQ);
        txtReadFreq.setOnKeyPressed(e -> this.txtFldKeyPress(e));
        txtReadFreq.setOnMouseExited(e -> this.txtFldMouseExit(e));
        mainPane.add(txtReadFreq, 1, 4);
        
        lblXtraKeys = new Label("Extra keystrokes");
        mainPane.add(lblXtraKeys, 2, 4);
        txtXtraKeys = new TextField();
        txtXtraKeys.setId(SettingsPane.TXT_FLD_ID_XTRA_KEYS);
        txtXtraKeys.setOnKeyPressed(e -> this.txtFldKeyPress(e));
        txtXtraKeys.setOnMouseExited(e -> txtFldMouseExit(e));
            //could send this directly to input validator
        mainPane.add(txtXtraKeys, 3, 4);
        
        //row 5, 6
        //anti-theft
        lblTheftAction = new Label("Theft bit action");
        mainPane.add(lblTheftAction, 0, 5);
        cboTheftAction = new ComboBox<>();
        cboTheftAction.setId(SettingsPane.COMBO_BOX_THEFT_ACTION);
        cboTheftAction.getItems().addAll(AntiTheftEnum.NO_ACTION.name(),
                AntiTheftEnum.TURN_ON.name(), AntiTheftEnum.TURN_OFF.name());
        cboTheftAction.setOnKeyPressed(e -> comboBoxKeyboardShortcut(e, cboTheftAction));
        mainPane.add(cboTheftAction, 1, 5);
        
        lblTheftOn = new Label("Value of theft on");
        mainPane.add(lblTheftOn, 0, 6);
        txtTheftOn = new TextField();
        txtTheftOn.setId(TXT_FLD_ID_THEFT_ON);
        txtTheftOn.setOnKeyPressed(e -> this.txtFldKeyPress(e));
        txtTheftOn.setOnMouseExited(e -> this.txtFldMouseExit(e));
        mainPane.add(txtTheftOn, 1, 6);
        
        lblTheftOff = new Label("Value of theft off");
        mainPane.add(lblTheftOff, 2, 6);
        txtTheftOff = new TextField();
        txtTheftOff.setId(SettingsPane.TXT_FLD_ID_THEFT_OFF);
        txtTheftOff.setOnKeyPressed(e -> this.txtFldKeyPress(e));
        txtTheftOff.setOnMouseExited(e -> this.txtFldMouseExit(e));
        mainPane.add(txtTheftOff, 3, 6);
        
        //row 7
        //buttons
        btnSaveConfig = new Button("Save");
        btnSaveConfig.setOnAction(e -> btnSaveConfig_Click(e));
        mainPane.add(btnSaveConfig, 0, 7);
        
        btnDeleteConfig = new Button("Delete this config");
        btnDeleteConfig.setOnAction(e -> btnDeleteConfig_Click(e));
        mainPane.add(btnDeleteConfig, 1, 7);
        
        btnUseConfig = new Button("Use this config");
        btnUseConfig.setOnAction(e -> btnUseConfig_Click(e));
        mainPane.add(btnUseConfig, 2, 7);
        
        btnCloseSettingsPane = new Button("Close");
        btnCloseSettingsPane.setOnAction(e -> btnCloseSettingsPane_Click(e));
        mainPane.add(btnCloseSettingsPane, 3, 7);
    }

    public GridPane getPane() {
        return this.mainPane;
    }
    /**
     * Called when a change is made to the config name combo box.
     * When the "new" config is selected, a text field is displayed
     * where the user can enter a name for the new config. If an existing
     * config is selected, its values are loaded into the form.
     * @param e 
     */
    private void configSelector_Selected(ActionEvent e) {
        String selectedConfig = (cboConfigName.getValue() == null)? "" : cboConfigName.getValue();   
        //after reset, selector value is null, which complicates following steps.
        //use empty string instead.
        System.out.println("you selected config " + selectedConfig);
        Boolean isNewConfig = selectedConfig.equals("new");        
        lblConfigName.setVisible(isNewConfig);
        txtConfigName.setVisible(isNewConfig);        
        //if existing config, load these values
        if (!isNewConfig && !selectedConfig.equals("")) {
            state = new AppState(selectedConfig); 
        //AppState loads default config in case of empty string, which should not happen
//            System.out.println("its anti theft value is " + state.getAntiTheftAction().name());
//            System.out.println("its r/w value is " + state.getReadWriteMode().name());
            this.cboConfigName.getSelectionModel().select(selectedConfig);
            this.chkMultiRead.setSelected(state.isMultiRead());
            this.cboReadWriteMode.getSelectionModel().select(state.getReadWriteMode().name());
            this.txtReadFreq.setText(Integer.toString(state.getReadFreq()));
            this.txtXtraKeys.setText(state.getExtraKeys());
            this.cboTheftAction.getSelectionModel().select(state.getAntiTheftAction().name());
            this.txtTheftOn.setText(state.getAntiTheftOn());
            this.txtTheftOff.setText(state.getAntiTheftOff());            
        } 
    }
    /**
     * Matches key press with a value from the combo box. Java FX does not do this
     * automatically. The algorithm tries to match the key pressed with the first letter
     * of one of the choices. In order to handle repeated first letters among the 
     * choices, the algorithm first searches for one match, then searches for a second match
     * further down the list. If a second match is found, this is selected. If none
     * is found, the search resumes at the top of the list. If the depressed key
     * does not match any of the choices, nothing happens. This routine does not
     * interrupt the action of the arrow keys.
     * Unlike text field events, combo box events raise compiler warnings
     * due to possible type mismatch when deriving the source from the event. In order
     * to prevent these warnings, the combo box is passed directly to the handler, so that
     * the compiler recognizes its type.    
     * @param e the key press event
     * @param combo_box the variable representing the combo box
     */
    private void comboBoxKeyboardShortcut(KeyEvent e, ComboBox<String> combo_box) {
        //idea from SO 13362607 is to check the type of each element.
        //since I have the calling object, I think it is easier to pass this
        //to the handler        
        
        System.out.println("keyboard shortcut on combo box " + combo_box.getId());        
        System.out.println("key press in r/w selector " + e.getCode());
//        System.out.println("event get text " + e.getText());
        System.out.println("selected item index " + combo_box.getSelectionModel().getSelectedIndex());
        //index = 0 is first item in list. if none selected, index = -1
        int firstMatchLocation = (combo_box.getSelectionModel().getSelectedIndex() < 0) ? 0 : combo_box.getSelectionModel().getSelectedIndex();
        Boolean foundSecondMatch = false;
        //start searching for a new match after the first match
        //if no first match is found, this loop is skipped, foundSecondMatch remains false
        for (int i=firstMatchLocation+1; i<combo_box.getItems().size(); i++) {
            if (e.getText().equalsIgnoreCase(combo_box.getItems().get(i).substring(0,1))) {
                combo_box.getSelectionModel().select(i);
                foundSecondMatch = true;
                System.out.println("matched " + e.getText() + " with " + combo_box.getItems().get(i));
                break;
            }
        }
        //if no second match is found, start from the 0 index
        if (!foundSecondMatch) {
            for (String item : combo_box.getItems() ) {
                System.out.println("\tcycling through options. item " + item);            
                if (e.getText().equalsIgnoreCase(item.substring(0, 1))) {
                    combo_box.getSelectionModel().select(item);
                    break;
                }
            }
        }        
    }
    /**
     * Triggers validation for text field entries. This event covers the case where
     * the user exits the text field by Enter or Tab keys. 
     * @param e 
     */
    private void txtFldKeyPress(KeyEvent e) {        
//        System.out.println("event type " + e.getEventType());
        
        if (e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.TAB)) {
            System.out.println("enter or tab key pressed");            
//            System.out.println("event source " + e.getSource());     
            if (e.getSource().getClass().equals(TextField.class)) {
                //it should always be a text field. check to be sure
                TextField src = (TextField)e.getSource();            
                System.out.println("key press text field id:" + src.getId() + ":");            
                this.txtFldInputValidator(src);                     
            }
        }
    }
    /**
     * Also triggers validation for text field entries. This event covers the case
     * where the user exits the text field by clicking out.
     * @param e Mouse event
     */
    private void txtFldMouseExit(MouseEvent e) {
        //mouse event is triggered when the pointer crosses the text field,
        //even if no edit was intended. validate only if text was entered in box
//        System.out.println("mouse exit source " + e.getSource());
        if (e.getSource().getClass().equals(TextField.class)) {
            //it should always be a text field. check to be sure
            TextField src = (TextField)e.getSource();
//            System.out.println("mouse exit text field id " + src.getId() + " is content \"\"? "+src.getText().equals(""));            
            if (!src.getText().equals("")) {
                this.txtFldInputValidator(src);    
            }            
        }        
    }
    /**
     * One routine checks all text fields. A unique error message is shown 
     * for each text field. The message explains causes and solutions. All text 
     * fields share a second error indication, the changing background color.
     * @param tf The text field to be validated.
     */
    private void txtFldInputValidator(TextField tf) {
        System.out.println("validating text field");
        System.out.println("testing input:" + tf.getText() + ":");
        Boolean validInput;
        switch (tf.getId()) {
            case SettingsPane.TXT_FLD_ID_CONFIG_NAME:
                validInput = DataValidation.isValidConfigName(tf.getText());
                if (!validInput) {
                    FxMsgBox.show(InputErrorMsg.ERR_CONFIG_NAME, InputErrorMsg.ERR_INPUT_TITLE);
                }
                break;
            case SettingsPane.TXT_FLD_ID_READ_FREQ:
                validInput = DataValidation.isValidReadFreq(tf.getText());
                if (!validInput) {
                    FxMsgBox.show(InputErrorMsg.ERR_READ_FREQ, InputErrorMsg.ERR_INPUT_TITLE);
                }
                break;
            case SettingsPane.TXT_FLD_ID_THEFT_OFF:
                validInput = DataValidation.isValidTheftValue(tf.getText());
                if (!validInput) {
                    FxMsgBox.show(InputErrorMsg.ERR_THEFT_VALUE, InputErrorMsg.ERR_INPUT_TITLE);
                }
                break;
            case SettingsPane.TXT_FLD_ID_THEFT_ON:
                validInput = DataValidation.isValidTheftValue(tf.getText());
                if (!validInput) {
                    FxMsgBox.show(InputErrorMsg.ERR_THEFT_VALUE, InputErrorMsg.ERR_INPUT_TITLE);
                }
                break;
            case SettingsPane.TXT_FLD_ID_XTRA_KEYS:
                validInput = DataValidation.isValidXtraKeys(tf.getText());
                if (!validInput) {
                    FxMsgBox.show(InputErrorMsg.ERR_XTRA_KEYS, InputErrorMsg.ERR_INPUT_TITLE);
                }
                break;                          
            default:
                validInput = false;
        }
        System.out.println("data validator says " + validInput);
        if (validInput) {
            tf.setStyle(AppConstants.STYLE_TEXT_FLD_OK);
        }
        else {
            tf.setStyle(AppConstants.STYLE_TEXT_FLD_FAIL);                
        }
    }
    /**
     * Works for saving new configs and for saving changes to existing configs.
     * @param e Button click event
     */    
    private void btnSaveConfig_Click(ActionEvent e) {
        System.out.println("click to save config");
        //validate all controls
        
        if (isValidConfig()) {
            //convert combo box and text field values to parameters for app state functions
            
            if (cboConfigName.getValue().equals("new")) {
            //save or create?        
            //if new, create config
            state.createConfig(txtConfigName.getText(), AppState.theftStringToEnum(cboTheftAction.getValue()),
                    AppState.readWriteStringToEnum(cboReadWriteMode.getValue()), chkMultiRead.isSelected(),
                    Integer.parseInt(txtReadFreq.getText()), txtXtraKeys.getText(), 
                    txtTheftOn.getText(), txtTheftOff.getText());
            }
            else {
            //if existing, set new values
            //fast way: delete and recreate? it created a new config with empty name
                System.out.println("saving new values to " + cboConfigName.getValue());
                state = new AppState(cboConfigName.getValue());
                state.setAntiTheftAction(AppState.theftStringToEnum(cboTheftAction.getValue()));
                state.setAntiTheftOff(txtTheftOff.getText());
                state.setAntiTheftOn(txtTheftOn.getText());
                state.setExtraKeys(txtXtraKeys.getText());
                state.setMultiRead(chkMultiRead.isSelected());
                state.setReadFreq(Integer.parseInt(txtReadFreq.getText()));
                state.setReadWriteMode(AppState.readWriteStringToEnum(cboReadWriteMode.getValue()));
            }
            this.resetForm();
            //reset form only if config was valid and was saved successfully
        }
        else {
            FxMsgBox.show(InputErrorMsg.ERR_INVALID_PAGE_MSG, InputErrorMsg.ERR_CANNOT_SAVE_TITLE);
        }
        
    }
    /**
     * 
     * @param e 
     */
    private void btnDeleteConfig_Click(ActionEvent e) {
        System.out.println("click to delete config");
        if (this.isValidConfig()) {
            //ensures that config name is not null
            String selectedConfig = cboConfigName.getValue(); //might be "new", in which case delete will fail
            //TODO: add ok/cancel msg box. proceed if ok
            FxMsgBox confirmationDialog = new FxMsgBox();
            Boolean okDelete = confirmationDialog.confirm("are you sure you want to delete?", "confirm delete");
            System.out.println("response to confirmation " + okDelete);
            if (okDelete) {
                if (state.deleteConfiguration(selectedConfig)) {
                    state = new AppState(AppConstants.SETTINGS_DEFAULT); //in case previous state was the deleted one?
                    this.resetForm();
                }
                else {
                    FxMsgBox.show(InputErrorMsg.ERR_CANNOT_DELETE_MSG, InputErrorMsg.ERR_CANNOT_DELETE_TITLE);
                }
            //do not allow delete default or current        
            }
            else {
                FxMsgBox.show(InputErrorMsg.CANCEL_DELETE_MSG, InputErrorMsg.CANCEL_DELETE_TITLE);
            }
        }
        else {
            FxMsgBox.show(InputErrorMsg.ERR_CANNOT_DELETE_MSG, InputErrorMsg.ERR_CANNOT_DELETE_TITLE);
            //use different message?
        }        
    }
    
    private void btnUseConfig_Click(ActionEvent e) {
        System.out.println("click to use config");
        //validate all controls        
        if (isValidConfig()) {
            //save these values to current
            state = new AppState(AppConstants.SETTINGS_CURRENT);            
            state.setAntiTheftAction(AppState.theftStringToEnum(this.cboTheftAction.getValue()));            
            state.setAntiTheftOff(this.txtTheftOff.getText());                        
            state.setAntiTheftOn(this.txtTheftOn.getText());                       
            state.setExtraKeys(this.txtXtraKeys.getText());            
            state.setMultiRead(this.chkMultiRead.isSelected());            
            state.setReadFreq(Integer.parseInt(this.txtReadFreq.getText()));            
            state.setReadWriteMode(AppState.readWriteStringToEnum(this.cboReadWriteMode.getValue()));        
        }
        else {
            FxMsgBox.show(InputErrorMsg.ERR_INVALID_PAGE_MSG, InputErrorMsg.ERR_CANNOT_USE_TITLE);
        }
    }
    private void btnCloseSettingsPane_Click(ActionEvent e) {
        System.out.println("btn click close pane");
        this.resetForm(); //so that it is blank when next used
        //show single/multi, r/w, theft panes with current values    
        SkyRfidJavaApp.resetWorkingPanes();
    }
    private void resetForm() {
        //TODO: reload config names to add any new configs or remove deleted configs
        for (Node n : mainPane.getChildren()) {
            //cannot use switch case because TextField.class is not considered a constant            
            if (n.getClass() == TextField.class) {
//                System.out.println("found a text field node " + n.getId());
                TextField tf = (TextField)n;
//                n.setStyle("");
                tf.setStyle("");
                tf.clear();
            }
            if (n.getClass().equals(ComboBox.class)) {
                ComboBox cbo = (ComboBox)n;                
//                System.out.println("cbo value property class " + cbo.valueProperty().getClass().getName());
                //SO 12142518 how to clear a combo box offers more complex solutions. this seems to work.
                cbo.getSelectionModel().clearSelection();
//                System.out.println("after clear selection value is " + cbo.getValue()); //null
            }
            if (n.getClass().equals(CheckBox.class)) {
                CheckBox chk = (CheckBox)n;
                chk.setSelected(false);
            }
            //ignore labels and buttons
        }
        state = new AppState(AppConstants.SETTINGS_DEFAULT);
        //must reinstantiate app state so the changed xml doc is loaded
        cboConfigName.getItems().clear();
        cboConfigName.getItems().addAll(state.getConfigNames());
        cboConfigName.getItems().add("new");
    }
    
    private Boolean isValidConfig() {
        //config name
        Boolean configNameOk = false;
        String config = cboConfigName.getValue(); 
        if (config == null) { configNameOk = false; }
        else {
            if (config.equals("new")) {
            //if new config, check name
             configNameOk = DataValidation.isValidConfigName(txtConfigName.getText());
            }
            else { configNameOk = true; }
        }    
//            else {
//                //if not new config, check not prompt text
//                configNameOk = ! config.equals(SettingsPane.CONFIG_SELECTOR_PROMPT);
//            }            
        System.out.println("validation results");
        System.out.println("\tconfig name " + configNameOk);
        //theft bit action not empty
        Boolean theftActionOk = cboTheftAction.getValue() != null;
        System.out.println("\ttheft action " + theftActionOk);
        //read write not empty
        Boolean rWModeOk = cboReadWriteMode.getValue() != null;
        System.out.println("\tr/w mode " + rWModeOk);
        //read freq valid range
        Boolean readFreqOk = DataValidation.isValidReadFreq(txtReadFreq.getText());
        System.out.println("\tread freq " + readFreqOk);
        //theft on valid value
        Boolean theftOnOk = DataValidation.isValidTheftValue(txtTheftOn.getText());
        System.out.println("\ttheft on " + theftOnOk);
        //theft off valid value      
        Boolean theftOffOk = DataValidation.isValidTheftValue(txtTheftOff.getText());
        System.out.println("\ttheft off " + theftOffOk);

        return configNameOk && theftActionOk && rWModeOk && readFreqOk && theftOnOk & theftOffOk;
    }
    
}
