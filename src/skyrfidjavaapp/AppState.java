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
 *
 * XML read/write technique copied from Rogers Cadenhead, "Sams Teach Yourself
 * Java in 21 Days." DTD follows w3schools.com example.
 */
package skyrfidjavaapp;

import java.io.File;
import java.io.FileOutputStream;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Text;
import nu.xom.Serializer;
import java.util.ArrayList;
import javafx.collections.ObservableList;
//import java.util.ob

/**
 *
 * @author Michal G. <Michal.G at cogitatummagnumtelae.com>
 */
public class AppState {
        
//    private AppSettingsEnum configurationName;
    private String configurationName;
    private final String settingsPath;
//    private File settingsFile;
    private Document settingsDoc;    
    private Element docRoot;
    private Elements configurationsCollection; //Elements class is not iterable
    private Element configurationSection;
    

//    public AppState(AppSettingsEnum configName) {
public AppState(String configName) {
//        System.out.println("initialize app state");
//        System.out.println("xml parser " + org.xml.sax.driver);
        configurationName = configName;
        String basePath = new File("").getAbsolutePath();
        settingsPath = basePath.concat("/src/skyrfidjavaapp/app_data/settings.xml");
        
        try {
//            File settingsFile = new File("skyrfidjavaapp.app_data/settings.xml");                
//            System.out.println("abs docRoot path " + basePath);                 
            File settingsFile = new File(settingsPath);                  
            Builder builder = new Builder(true); //true arg validates xml.                 
            settingsDoc = builder.build(settingsFile);
//            settingsDoc = (new Builder(true)).build(settingsFile); is this better?
            docRoot = settingsDoc.getRootElement();
            configurationsCollection = docRoot.getChildElements();            
        }
        catch (nu.xom.ValidityException ex) {
            System.out.println("error app state constructor. invalid xml " + ex.getMessage());
            FxMsgBox.show("The settings file has been corrupted.\nPlease reinstall the application.", 
                    "Program error");
            System.exit(1);
        }
        catch (Exception ex) {
            System.out.println("error app state constructor " + ex.getMessage());
            ex.printStackTrace();
            FxMsgBox.show("Problem with settings file\nPlease take a screen shot and notify author\n" 
                    + ex.getMessage(), "Program error");
            System.exit(1);
        }        
    }
    
    public AntiTheftEnum getAntiTheftAction() {
//        System.out.println("app state getAntiTheftAction");
        //read file
        String value = getSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_THEFT_BIT);
//        System.out.println("anti theft text value" + value);
        switch (value) {            
            case AppConstants.THEFT_MODE_OFF:
                return AntiTheftEnum.TURN_OFF;                
            case AppConstants.THEFT_MODE_ON:
                return AntiTheftEnum.TURN_ON;                
            case AppConstants.THEFT_MODE_NONE:
                //fall through to default    
            default:
                return AntiTheftEnum.NO_ACTION;    
        }        
    }
        
    public void setAntiTheftAction(AntiTheftEnum antiTheftAction) {
//        System.out.println("app state setAntiTheftAction");
        //write file
        String newValue = antiTheftAction.name();
//        switch (antiTheftAction) {            
//            case TURN_ON:
//                newValue = AppConstants.THEFT_MODE_ON;
//                break;
//            case TURN_OFF:
//                newValue = AppConstants.THEFT_MODE_OFF;
//                break;
//            case NO_ACTION:
//                //fall through to default
//            default:                
//                newValue = AppConstants.THEFT_MODE_NONE;                
//        }        
        this.setSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_THEFT_BIT, newValue);
//        System.out.println("app state set anti theft asks to reset panes");
//        SkyRfidJavaApp.resetPanes();
    }
    public ReadWriteModeEnum getReadWriteMode() {
//        System.out.println("app state getReadWriteMode");
        //read file
        String value = getSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_R_W_MODE);
//        System.out.println("r/w mode text: " + value);        
        switch (value) {
            case AppConstants.R_W_MODE_IDLE:
                return ReadWriteModeEnum.IDLE_MODE;
            case AppConstants.R_W_MODE_READ:
                return ReadWriteModeEnum.READ_MODE;
            case AppConstants.R_W_MODE_WRITE:
                return ReadWriteModeEnum.WRITE_MODE;
            default:
                return ReadWriteModeEnum.IDLE_MODE;
        }        
    }
    public void setReadWriteMode(ReadWriteModeEnum readWriteMode) {
//        System.out.println("app state setReadWriteMode");
        //write file        
        String newValue = readWriteMode.name();
        
//        switch (readWriteMode) {                 
//            case READ_MODE:
//                newValue = AppConstants.R_W_MODE_READ;
//                break;
//            case WRITE_MODE:
//                newValue = ReadWriteModeEnum.WRITE_MODE.name();
//                newValue = AppConstants.R_W_MODE_WRITE;
//                
//                break;
//            case IDLE_MODE:
//                //fall through to default
//            default:                
//                newValue = AppConstants.R_W_MODE_IDLE;                
//        }        
        this.setSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_R_W_MODE, newValue);
//        System.out.println("app state set read/write asks to reset panes");
//        SkyRfidJavaApp.resetPanes();
        
    }

    public boolean isMultiRead() {
//        System.out.println("app state isMultiRead");
        //read file
        String value = getSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_TYPE_MULTI_READ);
//        System.out.println("theft mode text: " + value);           
            return (value.equalsIgnoreCase("true")) ;                    
    }
    
    public void setMultiRead(boolean multiRead) {
//        System.out.println("app state setMultiRead");
        //write file        
        String newValue = String.valueOf(multiRead);
        setSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_TYPE_MULTI_READ, newValue);  
//        System.out.println("app state set multi read asks to reset panes");
//        SkyRfidJavaApp.resetPanes();
    }
    /**
     * 
     */
    public void resetAppState() {
//        System.out.println("app state resetAppState");   
        //write file
//        setSettingValue(AppSettingsEnum.SETTINGS_CURRENT, AppConstants.APP_STATE_XML_ELE_THEFT_BIT, AppConstants.THEFT_MODE_NONE);
//        setSettingValue(AppSettingsEnum.SETTINGS_CURRENT, AppConstants.APP_STATE_XML_ELE_R_W_MODE, AppConstants.R_W_MODE_IDLE);
//        setSettingValue(AppSettingsEnum.SETTINGS_CURRENT, AppConstants.APP_STATE_XML_ELE_TYPE_MULTI_READ, "false");   
       setSettingValue(AppConstants.SETTINGS_CURRENT, AppConstants.APP_STATE_XML_ELE_THEFT_BIT, AppConstants.THEFT_MODE_NONE);
       setSettingValue(AppConstants.SETTINGS_CURRENT, AppConstants.APP_STATE_XML_ELE_R_W_MODE, AppConstants.R_W_MODE_IDLE);
       setSettingValue(AppConstants.SETTINGS_CURRENT, AppConstants.APP_STATE_XML_ELE_TYPE_MULTI_READ, "false");   
    }
    
    /**
     * @param config_name the set that you wish to retrieve: current or default
     * @param  setting_name the key that you wish to find: r/w, theft, single/multi
     * @return a String of the text content of that xml element
     */
//    private String getSettingValue(AppSettingsEnum settings_category, String setting_name) {
      private String getSettingValue(String config_name, String setting_name) {  
        String result = "";
        try {
//            configurationSection = configurationsCollection.get(settings_category.ordinal());
            configurationSection = this.getConfig(config_name);
            //risky to rely on certain order of blocks?
            Element ele = configurationSection.getFirstChildElement(setting_name);
            Text eleValue = (Text) ele.getChild(0);
            result = eleValue.getValue();
        }
        catch (Exception ex) {                
            System.out.println("error get setting value " + ex.getMessage());
            ex.printStackTrace();
        }     
        return result;
    }
    /**
     * @param configName the group that you wish to change: current or default
     * @param  setting_name the key that you wish to change: r/w, theft, single/multi
     * @param new_value the new value for this key
     */
//    private void setSettingValue(AppSettingsEnum settings_category, String setting_name, String new_value) {
      private void setSettingValue(String configName, String setting_name, String new_value) {  
        //get the element that is to be changed
        try {
//            configurationSection = configurationsCollection.get(settings_category.ordinal());
            //risky to rely on certain order of blocks
            configurationSection = this.getConfig(configName);
            
            Element ele = configurationSection.getFirstChildElement(setting_name);
            ele.removeChild(0);
            ele.appendChild(new_value);
//            FileWriter fw = new FileWriter(settingsPath);
            FileOutputStream fos = new FileOutputStream(settingsPath);
            Serializer xmlWriter = new Serializer(fos, "ISO-8859-1");
            xmlWriter.setIndent(4); //orig is 4 spaces
            xmlWriter.write(settingsDoc);
            xmlWriter.flush();
            xmlWriter.flush();
            fos.close();            
        }        
        catch (Exception ex) {                
            System.out.println("error set setting value " + ex.getMessage());
            ex.printStackTrace();
        }             
    }
    private Element getConfig(String configName) {
        //cycle through config coll, look for match to id
        //if none found, return default or error?
        Element testEle;
        String testId;
        Element desiredConfig = configurationsCollection.get(0);
        
        for (int i=0; i<configurationsCollection.size(); i++) {
            testEle = configurationsCollection.get(i);
            testId = testEle.getAttributeValue(AppConstants.APP_STATE_XML_ATTR_ID);
            if (configName.equals(testId)) { desiredConfig = testEle; }
        }
        
        return desiredConfig;
    }
//    public String[] getConfigNames() {
    public ArrayList<String> getConfigNames() {
//        String[] nameArray = new String[configurationsCollection.size()];
        ArrayList nameArray = new ArrayList(configurationsCollection.size());
        Element oneEle;
        for (int i=0; i<configurationsCollection.size(); i++) {
            oneEle = configurationsCollection.get(i);
//            nameArray[i] = oneEle.getAttributeValue(AppConstants.APP_STATE_XML_ATTR_ID); 
            nameArray.add(oneEle.getAttributeValue(AppConstants.APP_STATE_XML_ATTR_ID));
        }
        return nameArray;
    }
}
