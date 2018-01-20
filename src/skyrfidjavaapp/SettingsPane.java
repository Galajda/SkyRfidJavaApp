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
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
//import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.input.KeyEvent;
//import javafx.scene.input.KeyCombination;
//import javafx.scene.input.KeyCodeCombination;
import javafx.event.ActionEvent;
//import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Priority;

import cogimag.javafx.FxComboBox;
import cogimag.javafx.FxMessageBox;
import cogimag.javafx.FxConfirmDialog;

/**
 *Configurations can be defined and modified through the settings pane. While certain
 * parameters--single/multi, read/write, theft bit--may be changed during normal operation, 
 * this pane provides more options.
 * 
 * 
 * @author Michal G. <Michal.G at cogitatummagnumtelae.com>
 */
public class SettingsPane {        
    private final VBox mainPane; //main container
    private static final String PANE_ID_MAIN = "main pane";
    private final GridPane configNamePane; //holds the config name cbo box
    private static final String PANE_ID_CONFIG_NAME = "config name pane";
    private final GridPane controlsPane; //holds the rest of the controls so they can be hidden as a group
    private static final String PANE_ID_CONTROLS = "controls pane";
    private final HBox closeBtnPane;
    private static final String PANE_ID_CLOSE_BTN = "close button pane";
    private AppState state;
    
    //heading
    private final Label lblGreeting;
    private final Label lblConfigSelector;
    private static final String CONFIG_SELECTOR_PROMPT = "Choose a configuration";
//    private final ComboBox<String> cboConfigName;
    private final FxComboBox cboConfigName;
    private static final String COMBO_BOX_ID_CONFIG_NAME = "config name combo box";
    private final Label lblConfigName;
    private final TextField txtConfigName;
    private static final String TXT_FLD_ID_CONFIG_NAME = "config name text field";
    
    //single/multi
    private final Label lblMultiRead;
    private final CheckBox chkMultiRead;
    
    //read/write parameters
    private final Label lblReadWriteMode;    
//    private final ComboBox<String> cboReadWriteMode;  
    private final FxComboBox cboReadWriteMode;  
    private static final String COMBO_BOX_ID_READ_WRITE = "read write combo box";
    private final Label lblReadFreq;
    private final TextField txtReadFreq;    
    private static final String TXT_FLD_ID_READ_FREQ = "read freq";
    private final Label lblXtraKeys;
    private final TextField txtXtraKeys;
    private static final String TXT_FLD_ID_XTRA_KEYS = "extra keys";
    
    //anti-theft parameters
    private final Label lblTheftAction;
//    private final ComboBox<String> cboTheftAction;
    private final FxComboBox cboTheftAction;
    private static final String COMBO_BOX_ID_THEFT_ACTION = "theft action combo box";
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
    private final Region spacerCloseBtn;
    
    //constructor
    public SettingsPane() {
        this.mainPane = new VBox(5);
        this.mainPane.setId(PANE_ID_MAIN);
//        mainPane.setMinHeight(300);
        configNamePane = new GridPane();
        configNamePane.setId(SettingsPane.PANE_ID_CONFIG_NAME);
        configNamePane.setVgap(5);
        //row 0 of name pane
        //heading         
        lblGreeting = new Label("the settings pane has been released from the intensive care unit and moved to recovery");
        configNamePane.add(lblGreeting, 0, 0, 2, 1);
        
        //row 1 of name pane
        //config name
        lblConfigSelector = new Label(SettingsPane.CONFIG_SELECTOR_PROMPT);
        configNamePane.add(lblConfigSelector, 0, 1);        
        state = new AppState(AppConstants.SETTINGS_CURRENT);
//        cboConfigName = new ComboBox<>();
//        cboConfigName = new FxComboBox(SettingsPane.COMBO_BOX_ID_CONFIG_NAME, state.getConfigNames());
        cboConfigName = new FxComboBox();
        cboConfigName.setId(SettingsPane.COMBO_BOX_ID_CONFIG_NAME);
        cboConfigName.setPromptText("Choose a configuration");
        cboConfigName.getItems().addAll(state.getConfigNames());
        cboConfigName.getItems().add("new");
//        cboConfigName.setOnKeyPressed(e -> comboBoxKeyboardShortcut(e, cboConfigName));
        cboConfigName.setOnAction(e -> configSelector_Selected(e));
        configNamePane.add(cboConfigName, 1, 1);        
        lblConfigName = new Label("Name");
        lblConfigName.setVisible(false);
        configNamePane.add(lblConfigName, 2, 1);        
        txtConfigName = new TextField();        
        txtConfigName.setId(SettingsPane.TXT_FLD_ID_CONFIG_NAME);
        txtConfigName.setMaxWidth(200);
        txtConfigName.setOnKeyPressed(e -> this.txtFldKeyPress(e));
        txtConfigName.setOnMouseExited(e -> this.txtFldMouseExit(e));
        txtConfigName.setVisible(false);
        configNamePane.add(txtConfigName, 3, 1);        
        
        mainPane.getChildren().add(configNamePane);
        
        controlsPane = new GridPane();
        controlsPane.setId(SettingsPane.PANE_ID_CONTROLS);
        controlsPane.setVgap(5);
        //add these controls to sub pane, add sub pane at end.
        //row 0 of controls pane 
        //single/multi        
        lblMultiRead = new Label("Multi read t/f");
        controlsPane.add(lblMultiRead, 0, 0);
        chkMultiRead = new CheckBox();
        controlsPane.add(chkMultiRead, 1, 0);
        
        //row 1, 2 of controls pane 
        //read/write
        lblReadWriteMode = new Label("read/write/idle");
        controlsPane.add(lblReadWriteMode, 0, 1);
        cboReadWriteMode = new FxComboBox();
        cboReadWriteMode.setId(SettingsPane.COMBO_BOX_ID_READ_WRITE);
        cboReadWriteMode.getItems().addAll(ReadWriteModeEnum.IDLE_MODE.name(), 
                ReadWriteModeEnum.READ_MODE.name(), ReadWriteModeEnum.WRITE_MODE.name());
//        cboReadWriteMode.setOnKeyPressed(e -> comboBoxKeyboardShortcut(e, cboReadWriteMode));
        controlsPane.add(cboReadWriteMode, 1, 1);
                
        lblReadFreq = new Label("Msec between readings");
        controlsPane.add(lblReadFreq, 0, 2);
        txtReadFreq = new TextField();
        txtReadFreq.setId(SettingsPane.TXT_FLD_ID_READ_FREQ);
        txtReadFreq.setOnKeyPressed(e -> this.txtFldKeyPress(e));
        txtReadFreq.setOnMouseExited(e -> this.txtFldMouseExit(e));
        controlsPane.add(txtReadFreq, 1, 2);
        
        lblXtraKeys = new Label("Extra keystrokes");
        controlsPane.add(lblXtraKeys, 2, 2);
        txtXtraKeys = new TextField();
        txtXtraKeys.setId(SettingsPane.TXT_FLD_ID_XTRA_KEYS);
        txtXtraKeys.setOnKeyPressed(e -> this.txtFldKeyPress(e));
        txtXtraKeys.setOnMouseExited(e -> txtFldMouseExit(e));
            //could send this directly to input validator
        controlsPane.add(txtXtraKeys, 3, 2);
        
        //row 3, 4 of controls pane 
        //anti-theft
        lblTheftAction = new Label("Theft bit action");
        controlsPane.add(lblTheftAction, 0, 3);
        cboTheftAction = new FxComboBox();
        cboTheftAction.setId(SettingsPane.COMBO_BOX_ID_THEFT_ACTION);
        cboTheftAction.getItems().addAll(AntiTheftEnum.NO_ACTION.name(),
                AntiTheftEnum.TURN_ON.name(), AntiTheftEnum.TURN_OFF.name());
//        cboTheftAction.setOnKeyPressed(e -> comboBoxKeyboardShortcut(e, cboTheftAction));
        controlsPane.add(cboTheftAction, 1, 3);
        
        lblTheftOn = new Label("Value of theft on");
        controlsPane.add(lblTheftOn, 0, 4);
        txtTheftOn = new TextField();
        txtTheftOn.setId(TXT_FLD_ID_THEFT_ON);
        txtTheftOn.setOnKeyPressed(e -> this.txtFldKeyPress(e));
        txtTheftOn.setOnMouseExited(e -> this.txtFldMouseExit(e));
        controlsPane.add(txtTheftOn, 1, 4);
        
        lblTheftOff = new Label("Value of theft off");
        controlsPane.add(lblTheftOff, 2, 4);
        txtTheftOff = new TextField();
        txtTheftOff.setId(SettingsPane.TXT_FLD_ID_THEFT_OFF);
        txtTheftOff.setOnKeyPressed(e -> this.txtFldKeyPress(e));
        txtTheftOff.setOnMouseExited(e -> this.txtFldMouseExit(e));
        controlsPane.add(txtTheftOff, 3, 4);
        
        //row 5 of controls pane 
        //buttons
        btnSaveConfig = new Button("Save");
        //error: scene is not available when pane is initialized
//        btnSaveConfig.getScene().getAccelerators().put(
//                new KeyCodeCombination(KeyCode.S,KeyCombination.SHORTCUT_DOWN),
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        btnSaveConfig.fire();
//                    }                    
//                }
//        );
        btnSaveConfig.setOnAction(e -> btnSaveConfig_Click(e));
        controlsPane.add(btnSaveConfig, 0, 5);
        
        btnDeleteConfig = new Button("Delete this config");
        btnDeleteConfig.setOnAction(e -> btnDeleteConfig_Click(e));
        controlsPane.add(btnDeleteConfig, 1, 5);
        
        btnUseConfig = new Button("Use this config");
        btnUseConfig.setOnAction(e -> btnUseConfig_Click(e));
        controlsPane.add(btnUseConfig, 2, 5);
        
        controlsPane.setVisible(false);
        this.mainPane.getChildren().add(this.controlsPane);
        
        closeBtnPane = new HBox();
        closeBtnPane.setId(SettingsPane.PANE_ID_CLOSE_BTN);
        btnCloseSettingsPane = new Button("Close");        
        HBox.setMargin(this.btnCloseSettingsPane, new Insets(0,20,0,0));
//        VBox.setMargin(this.btnCloseSettingsPane, new Insets(0,0,0,500));
        //changes alignment of text within the button
        btnCloseSettingsPane.setOnAction(e -> btnCloseSettingsPane_Click(e));
//        controlsPane.add(btnCloseSettingsPane, 3, 5);
        //technique from D Lowe JavaFX for Dummies
        spacerCloseBtn = new Region();
        HBox.setHgrow(this.spacerCloseBtn, Priority.ALWAYS);
        closeBtnPane.getChildren().addAll(this.spacerCloseBtn,this.btnCloseSettingsPane);
        
        
//        this.mainPane.getChildren().add(this.btnCloseSettingsPane);
        this.mainPane.getChildren().add(this.closeBtnPane);
//        System.out.println("setting left margin to main pane width " + mainPane.getWidth() +
//                " minus button width " + btnCloseSettingsPane.getWidth());
        //both zero
        
        
    }

    public VBox getPane() {
        return this.mainPane;
    }
    /**
     * Called when a change is made to the config name combo box.
     * When the "new" option is selected, a text field is displayed
     * where the user can enter a name for the new config. If an existing
     * config is selected, its values are loaded into the form.
     * @param e ActionEvent for combo box selection change
     */
    private void configSelector_Selected(ActionEvent e) {
        String selectedConfig = (cboConfigName.getValue() == null)? "" : cboConfigName.getValue();   
        //after reset, selector value is null, which complicates following steps.
        //use empty string instead.
        System.out.println("you selected config " + selectedConfig);
        Boolean isNewConfig = selectedConfig.equals("new");        
        
        if (isNewConfig) {
            System.out.println("found new config. resetting form with false");
//            System.out.println("found new config. taking no action");
            this.resetForm(false);
//            this.cboConfigName.getSelectionModel().select("new");
        }
        else if (!selectedConfig.equals("")) {
            //if existing config, load these values
            state = new AppState(selectedConfig);
            this.chkMultiRead.setSelected(state.isMultiRead());
            this.cboReadWriteMode.getSelectionModel().select(state.getReadWriteMode().name());
            this.txtReadFreq.setText(Integer.toString(state.getReadFreq()));
            this.txtXtraKeys.setText(state.getExtraKeys());
            this.cboTheftAction.getSelectionModel().select(state.getAntiTheftAction().name());
            this.txtTheftOn.setText(state.getAntiTheftOn());
            this.txtTheftOff.setText(state.getAntiTheftOff());   
        }
        
//        if (!isNewConfig && !selectedConfig.equals("")) {            
//            state = new AppState(selectedConfig); 
        //AppState loads default config in case of empty string, which should not happen
//            System.out.println("its anti theft value is " + state.getAntiTheftAction().name());
//            System.out.println("its r/w value is " + state.getReadWriteMode().name());
//            this.cboConfigName.getSelectionModel().select(selectedConfig);
//            this.chkMultiRead.setSelected(state.isMultiRead());
//            this.cboReadWriteMode.getSelectionModel().select(state.getReadWriteMode().name());
//            this.txtReadFreq.setText(Integer.toString(state.getReadFreq()));
//            this.txtXtraKeys.setText(state.getExtraKeys());
//            this.cboTheftAction.getSelectionModel().select(state.getAntiTheftAction().name());
//            this.txtTheftOn.setText(state.getAntiTheftOn());
//            this.txtTheftOff.setText(state.getAntiTheftOff());            
//        } 
        
        //these commands must follow reset, not precede it.
        lblConfigName.setVisible(isNewConfig);
        txtConfigName.setVisible(isNewConfig);   
        this.controlsPane.setVisible(!selectedConfig.equals(""));
        
//        e.consume();
    }
    /**
     * Matches key press with a value from the combo box. Java FX does not do this
     * automatically. The algorithm tries to match the key pressed with the first letter
     * of one of the choices. In order to handle repeated first letters among the 
     * choices, the algorithm first searches for one match, then searches for a second match
     * further down the list. If a second match is found, this is selected. If none
     * is found, the search resumes at the top of the list. If the depressed key
     * does not match any of the choices, nothing happens. This routine does not
     * interrupt the action of the arrow keys.<br/>
     * Unlike text field events, combo box events raise compiler warnings
     * due to possible type mismatch when deriving the source from the event. In order
     * to prevent these warnings, the combo box is passed directly to the handler, so that
     * the compiler recognizes its type.    
     * @param e the key press event
     * @param combo_box the variable representing the combo box
     */
//    private void comboBoxKeyboardShortcut(KeyEvent e, ComboBox<String> combo_box) {
//        //idea from SO 13362607 is to check the type of each element.
//        //since I have the calling object, I think it is easier to pass this
//        //to the handler        
//        
//        System.out.println("keyboard shortcut on combo box " + combo_box.getId() + " handled in settings pane");        
////        System.out.println("key pressed " + e.getCode());
////        System.out.println("event get text " + e.getText());
////        System.out.println("selected item index " + combo_box.getSelectionModel().getSelectedIndex());
//        //index = 0 is first item in list. if none selected, index = -1
//        int firstMatchLocation = (combo_box.getSelectionModel().getSelectedIndex() < 0) ? 0 : combo_box.getSelectionModel().getSelectedIndex();
//        Boolean foundSecondMatch = false;
//        //start searching for a new match after the first match
//        //if no first match is found, this loop is skipped, foundSecondMatch remains false
//        for (int i=firstMatchLocation+1; i<combo_box.getItems().size(); i++) {
//            if (e.getText().equalsIgnoreCase(combo_box.getItems().get(i).substring(0,1))) {
//                combo_box.getSelectionModel().select(i);
//                foundSecondMatch = true;
//                e.consume();
////                System.out.println("matched " + e.getText() + " with " + combo_box.getItems().get(i));
//                break;
//            }
//        }
//        //if no second match is found, start from the 0 index
//        if (!foundSecondMatch) {
//            for (String item : combo_box.getItems() ) {
////                System.out.println("\tcycling through options. item " + item);            
//                if (e.getText().equalsIgnoreCase(item.substring(0, 1))) {
//                    combo_box.getSelectionModel().select(item);
//                    e.consume();
//                    break;
//                }
//            }
//        }
////        e.consume();
//    }
    /**
     * Triggers validation for text field entries. This event covers the case where
     * the user exits the text field by Enter or Tab keys.      
     * The key pressed event is preferred to the key released event. When the user presses Tab,
     * the source of key released is the next control in the tab order, too late to validate the previous control.
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
//            e.consume();
        }
        else {
            
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
     * @param text_field The text field to be validated.
     */
    private void txtFldInputValidator(TextField text_field) {
        System.out.println("validating text field");
        System.out.println("testing input:" + text_field.getText() + ":");
        Boolean validInput;
        switch (text_field.getId()) {
            case SettingsPane.TXT_FLD_ID_CONFIG_NAME:
                validInput = DataValidation.isValidConfigName(text_field.getText());
                if (!validInput) {
//                    FxMsgBox.show(InputErrorMsg.ERR_CONFIG_NAME, InputErrorMsg.ERR_INPUT_TITLE);
                    FxMessageBox.show(InputErrorMsg.ERR_INPUT_TITLE, InputErrorMsg.ERR_CONFIG_NAME);
                }
                break;
            case SettingsPane.TXT_FLD_ID_READ_FREQ:
                validInput = DataValidation.isValidReadFreq(text_field.getText());
                if (!validInput) {
//                    FxMsgBox.show(InputErrorMsg.ERR_READ_FREQ, InputErrorMsg.ERR_INPUT_TITLE);
                    FxMessageBox.show(InputErrorMsg.ERR_INPUT_TITLE, InputErrorMsg.ERR_READ_FREQ);
                }
                break;
            case SettingsPane.TXT_FLD_ID_THEFT_OFF:
                validInput = DataValidation.isValidTheftValue(text_field.getText());
                if (!validInput) {
//                    FxMsgBox.show(InputErrorMsg.ERR_THEFT_VALUE, InputErrorMsg.ERR_INPUT_TITLE);
                    FxMessageBox.show(InputErrorMsg.ERR_INPUT_TITLE, InputErrorMsg.ERR_THEFT_VALUE);
                }
                break;
            case SettingsPane.TXT_FLD_ID_THEFT_ON:
                validInput = DataValidation.isValidTheftValue(text_field.getText());
                if (!validInput) {
//                    FxMsgBox.show(InputErrorMsg.ERR_THEFT_VALUE, InputErrorMsg.ERR_INPUT_TITLE);
                    FxMessageBox.show(InputErrorMsg.ERR_INPUT_TITLE, InputErrorMsg.ERR_THEFT_VALUE);
                }
                break;
            case SettingsPane.TXT_FLD_ID_XTRA_KEYS:
                validInput = DataValidation.isValidXtraKeys(text_field.getText());
                if (!validInput) {
//                    FxMsgBox.show(InputErrorMsg.ERR_XTRA_KEYS, InputErrorMsg.ERR_INPUT_TITLE);
                    FxMessageBox.show(InputErrorMsg.ERR_INPUT_TITLE, InputErrorMsg.ERR_XTRA_KEYS);
                }
                break;                          
            default:
                validInput = false;
        }
        System.out.println("data validator says " + validInput);
        if (validInput) {
            text_field.setStyle(AppConstants.STYLE_TEXT_FLD_OK);
        }
        else {
            text_field.setStyle(AppConstants.STYLE_TEXT_FLD_FAIL);                
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
//            FxMsgBox.show(AppConstants.SAVE_CONFIG_SUCCESS_MSG, AppConstants.SAVE_CONFIG_SUCCESS_TITLE);
            FxMessageBox.show(AppConstants.SAVE_CONFIG_SUCCESS_TITLE, AppConstants.SAVE_CONFIG_SUCCESS_MSG);
            this.resetForm(true);
            //reset form only if config was valid and was saved successfully
        }
        else {
//            FxMsgBox.show(InputErrorMsg.ERR_INVALID_PAGE_MSG, InputErrorMsg.ERR_CANNOT_SAVE_TITLE);
            FxMessageBox.show(InputErrorMsg.ERR_CANNOT_SAVE_TITLE, InputErrorMsg.ERR_INVALID_PAGE_MSG);
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
//            FxMsgBox confirmationDialog = new FxMsgBox();
//            Boolean okDelete = confirmationDialog.confirm("are you sure you want to delete?", "confirm delete");
            FxConfirmDialog confirmationDialog = new FxConfirmDialog();
            Boolean okDelete = confirmationDialog.confirm(AppConstants.DELETE_CONFIG_CONFIRM_TITLE, AppConstants.DELETE_CONFIG_CONFIRM_MSG);
            System.out.println("response to confirmation " + okDelete);
            if (okDelete) {
                if (state.deleteConfiguration(selectedConfig)) {
//                    FxMsgBox.show(AppConstants.DELETE_CONFIG_SUCCESS_MSG, AppConstants.DELETE_CONFIG_SUCCESS_TITLE);
                    FxMessageBox.show(AppConstants.DELETE_CONFIG_SUCCESS_TITLE, AppConstants.DELETE_CONFIG_SUCCESS_MSG);
                    state = new AppState(AppConstants.SETTINGS_DEFAULT); //in case previous state was the deleted one?
                    this.resetForm(true);
                }
                else {
//                    FxMsgBox.show(InputErrorMsg.ERR_CANNOT_DELETE_MSG, InputErrorMsg.ERR_CANNOT_DELETE_TITLE);
                    FxMessageBox.show(InputErrorMsg.ERR_CANNOT_DELETE_TITLE, InputErrorMsg.ERR_CANNOT_DELETE_MSG);
                }
            //do not allow delete default or current        
            }
            else {
//                FxMsgBox.show(InputErrorMsg.CANCEL_DELETE_MSG, InputErrorMsg.CANCEL_DELETE_TITLE);
                FxMessageBox.show(InputErrorMsg.CANCEL_DELETE_TITLE, InputErrorMsg.CANCEL_DELETE_MSG);
            }
        }
        else {
//            FxMsgBox.show(InputErrorMsg.ERR_CANNOT_DELETE_MSG, InputErrorMsg.ERR_CANNOT_DELETE_TITLE);
            FxMessageBox.show(InputErrorMsg.ERR_CANNOT_DELETE_TITLE, InputErrorMsg.ERR_CANNOT_DELETE_MSG);
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
//            FxMsgBox.show(InputErrorMsg.ERR_INVALID_PAGE_MSG, InputErrorMsg.ERR_CANNOT_USE_TITLE);
            FxMessageBox.show(InputErrorMsg.ERR_CANNOT_USE_TITLE, InputErrorMsg.ERR_INVALID_PAGE_MSG);
        }
    }
    private void btnCloseSettingsPane_Click(ActionEvent e) {
        System.out.println("btn click close pane");
        this.resetForm(true); //so that it is blank when next used
        //show single/multi, r/w, theft panes with current values    
        SkyRfidJavaApp.resetWorkingPanes();
    }
    /**
     * The controls are embedded in sub-panes of the main pane. Cycling through the 
     * controls requires two layers of for loops.
     * @param full_reset flag to indicate whether all controls are to be reset, or 
     * only those in the controls pane. Full reset is called when config has changed.
     * New configs are loaded into the config name combo box.
     * Partial reset is called when user selects new config. Since no configs have
     * been altered, the config name combo box does not need to be reset. If you try,
     * you get an infinite recursion overflow from the configSelector_Selected function.
     */
    public void resetForm(Boolean full_reset) {        
        for (Node n : mainPane.getChildren()) {
            if (n.getClass().equals(GridPane.class)) {
                //they should all be grid panes
                GridPane subPane = (GridPane)n;
//                System.out.print("pane id " + subPane.getId());
//                System.out.print(" full reset= " + full_reset);
//                System.out.print(" sub pane id equals config name=" + 
//                        (subPane.getId().equals(SettingsPane.PANE_ID_CONFIG_NAME)));
//                System.out.println(" this||that=" +
//                        (full_reset || !(subPane.getId().equals(SettingsPane.PANE_ID_CONFIG_NAME))));
                if (full_reset || !(subPane.getId().equals(SettingsPane.PANE_ID_CONFIG_NAME))) {     
//                    System.out.println("resetting pane " +subPane.getId());
                    for (Node ctl : subPane.getChildren()) {
//                        System.out.println("ctl class " + ctl.getClass());
                    //cannot use switch case because TextField.class is not considered a constant            
                        if (ctl.getClass() == TextField.class) {
    //                        System.out.println("found a text field node " + ctl.getId());
                            TextField tf = (TextField)ctl;
    //                        n.setStyle("");
                            tf.setStyle("");
                            tf.clear();
                        }
                        if (ctl.getClass().equals(FxComboBox.class)) {
                            
                            FxComboBox cbo = (FxComboBox)ctl;                
//                            System.out.println("found combo box " + cbo.getId());
    //                        System.out.println("cbo value property class " + cbo.valueProperty().getClass().getName());
                            //SO 12142518 how to clear a combo box offers more complex solutions. this seems to work.
                            cbo.getSelectionModel().clearSelection();
    //                        System.out.println("after clear selection value is " + cbo.getValue()); //null
                        }
                        if (ctl.getClass().equals(CheckBox.class)) {
                            CheckBox chk = (CheckBox)ctl;
    //                        System.out.println("found check box " + chk.getId());
                            chk.setSelected(false);
                        }
                        //ignore labels and buttons
                    }
                }
            }
        }
        if (full_reset) {
            //reinstantiate app state so the changed xml doc is loaded
            state = new AppState(AppConstants.SETTINGS_DEFAULT);            
            cboConfigName.getItems().clear();
            cboConfigName.getItems().addAll(state.getConfigNames());
            cboConfigName.getItems().add("new");
            this.controlsPane.setVisible(false);
        }
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
