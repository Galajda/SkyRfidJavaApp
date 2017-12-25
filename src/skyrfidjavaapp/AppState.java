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
import nu.xom.Attribute;
import nu.xom.Text;
import nu.xom.Serializer;
import java.util.ArrayList;

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
            FxMsgBox.show("Unknown problem with settings file\nPlease take a screen shot and notify author\n" 
                    + ex.getMessage(), "Program error");
            System.exit(1);
        }        
    }
    
    public AntiTheftEnum getAntiTheftAction() {
//        System.out.println("app state getAntiTheftAction");
        //read file
        String value = getSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_THEFT_ACTION);
//        System.out.println("anti theft text value" + value);
        return AppState.theftStringToEnum(value);
    }        
    public void setAntiTheftAction(AntiTheftEnum antiTheftAction) {
//        System.out.println("app state setAntiTheftAction");
        //write file
        String newValue = antiTheftAction.name();
        this.setSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_THEFT_ACTION, newValue);
//        System.out.println("app state set anti theft asks to reset panes");
//        SkyRfidJavaApp.resetPanes();
    }
    public ReadWriteModeEnum getReadWriteMode() {
//        System.out.println("app state getReadWriteMode");
        //read file
        String value = getSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_R_W_MODE);
//        System.out.println("r/w mode text: " + value);     
        return AppState.readWriteStringToEnum(value);
//        switch (value) {
//            case AppConstants.R_W_MODE_IDLE:
//                return ReadWriteModeEnum.IDLE_MODE;
//            case AppConstants.R_W_MODE_READ:
//                return ReadWriteModeEnum.READ_MODE;
//            case AppConstants.R_W_MODE_WRITE:
//                return ReadWriteModeEnum.WRITE_MODE;
//            default:
//                return ReadWriteModeEnum.IDLE_MODE;
//        }        
    }
    public void setReadWriteMode(ReadWriteModeEnum readWriteMode) {
//        System.out.println("app state setReadWriteMode");
        //write file        
        String newValue = readWriteMode.name();        
        this.setSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_R_W_MODE, newValue);
//        System.out.println("app state set read/write asks to reset panes");
//        SkyRfidJavaApp.resetPanes();        
    }
    public boolean isMultiRead() {
//        System.out.println("app state isMultiRead");
        //read file
        String value = getSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE__MULTI_READ);
//        System.out.println("theft mode text: " + value);           
            return (value.equalsIgnoreCase("true")) ;                    
    }    
    public void setMultiRead(boolean multiRead) {
//        System.out.println("app state setMultiRead");
        //write file        
        String newValue = String.valueOf(multiRead);
        setSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE__MULTI_READ, newValue);  
//        System.out.println("app state set multi read asks to reset panes");
//        SkyRfidJavaApp.resetPanes();
    }
    public int getReadFreq() {
        String value = getSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_READ_FREQ);
        Integer freq;
        try {
            freq = Integer.parseInt(value);
        }
        catch (NumberFormatException ex) {
            freq = 0;
        }
        return freq;
    }
    public void setReadFreq(int freq) {
        String value = String.valueOf(freq);
        setSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_READ_FREQ, value);
    }
    public String getExtraKeys() {
        String value = this.getSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_EXTRA_KEYS);
        return value;
    }
    public void setExtraKeys(String keys) {
        setSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_EXTRA_KEYS, keys);
    }
    public String getAntiTheftOn() {
        String value = this.getSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_THEFT_ON);
        return value;
    }
    public void setAntiTheftOn(String theft_on_value) {
        setSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_THEFT_ON, theft_on_value);
    }    
    public String getAntiTheftOff() {
        String value = getSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_THEFT_OFF);
        return value;
    }
    public void setAntiTheftOff(String theft_off_value) {
        setSettingValue(configurationName, AppConstants.APP_STATE_XML_ELE_THEFT_OFF, theft_off_value);
    }
    /**
     * 
     */
    public void resetAppState() {
//        System.out.println("app state resetAppState");   
        //write file        
        String defaultTheftMode = getSettingValue(AppConstants.SETTINGS_DEFAULT, AppConstants.APP_STATE_XML_ELE_THEFT_ACTION);
        setSettingValue(AppConstants.SETTINGS_CURRENT, AppConstants.APP_STATE_XML_ELE_THEFT_ACTION, defaultTheftMode);
        String defaultRWMode = getSettingValue(AppConstants.SETTINGS_DEFAULT, AppConstants.APP_STATE_XML_ELE_R_W_MODE);
        setSettingValue(AppConstants.SETTINGS_CURRENT, AppConstants.APP_STATE_XML_ELE_R_W_MODE, defaultRWMode);
        String defaultMultiRead = getSettingValue(AppConstants.SETTINGS_DEFAULT, AppConstants.APP_STATE_XML_ELE__MULTI_READ);
        setSettingValue(AppConstants.SETTINGS_CURRENT, AppConstants.APP_STATE_XML_ELE__MULTI_READ, defaultMultiRead);
        String defaultReadFreq = getSettingValue(AppConstants.SETTINGS_DEFAULT, AppConstants.APP_STATE_XML_ELE_READ_FREQ);
        setSettingValue(AppConstants.SETTINGS_CURRENT, AppConstants.APP_STATE_XML_ELE_READ_FREQ, defaultReadFreq);
        String defaultXtraKeys = getSettingValue(AppConstants.SETTINGS_DEFAULT, AppConstants.APP_STATE_XML_ELE_EXTRA_KEYS);
        setSettingValue(AppConstants.SETTINGS_CURRENT, AppConstants.APP_STATE_XML_ELE_EXTRA_KEYS, defaultXtraKeys);
        String defaultTheftOnValue = getSettingValue(AppConstants.SETTINGS_DEFAULT, AppConstants.APP_STATE_XML_ELE_THEFT_ON);
        setSettingValue(AppConstants.SETTINGS_CURRENT, AppConstants.APP_STATE_XML_ELE_THEFT_ON, defaultTheftOnValue);
        String defaultTheftOffValue = getSettingValue(AppConstants.SETTINGS_DEFAULT, AppConstants.APP_STATE_XML_ELE_THEFT_OFF);
        setSettingValue(AppConstants.SETTINGS_CURRENT, AppConstants.APP_STATE_XML_ELE_THEFT_OFF, defaultTheftOffValue);  
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
            if (ele.getChildCount() > 0) {
                //extra keys ele may be empty
                Text eleValue = (Text) ele.getChild(0);
                result = eleValue.getValue();
            }
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
            if (ele.getChildCount() > 0) {ele.removeChild(0);} //extra keys element may be empty
            ele.appendChild(new_value);

            Boolean saveXmlSuccess = this.saveXml();
        }        
        catch (Exception ex) {                
            System.out.println("error set setting value " + ex.getMessage());
            ex.printStackTrace();
        }             
    }
      /**
       * This function will check if the configuration name exists.
       * Caller is responsible for validating the remaining parameters. 
       * @param configuration_name
       * @param theft_action
       * @param read_write_idle
       * @param multi_read
       * @param read_frequency
       * @param extra_keys
       * @param theft_on_value
       * @param theft_off_value
       * @return true if successfully created, false if failed
       */
    public Boolean createConfig(String configuration_name, AntiTheftEnum theft_action, 
            ReadWriteModeEnum read_write_idle, Boolean multi_read, int read_frequency, 
            String extra_keys, String theft_on_value, String theft_off_value) {        
        Boolean success = true;        
        //check if it exists
        Element testEle;
        String testId;
        for (int i=0; i<configurationsCollection.size(); i++) {
            testEle = configurationsCollection.get(i);
            testId = testEle.getAttributeValue(AppConstants.APP_STATE_XML_ATTR_ID);
            if (testId.equals(configuration_name)) { success = false; }
        }
        if (success) {
            //make a new element
            Element newConfig = new Element(AppConstants.APP_STATE_XML_ELE_PARM_SET);
            Attribute newId = new Attribute(AppConstants.APP_STATE_XML_ATTR_ID, configuration_name);
            
            newConfig.addAttribute(newId);
            Element theft = new Element(AppConstants.APP_STATE_XML_ELE_THEFT_ACTION);
            theft.appendChild(theft_action.name());
            newConfig.appendChild(theft); 
            Element readWrite = new Element(AppConstants.APP_STATE_XML_ELE_R_W_MODE);
            readWrite.appendChild(read_write_idle.name());
            newConfig.appendChild(readWrite);
            Element multiRead = new Element(AppConstants.APP_STATE_XML_ELE__MULTI_READ);
            multiRead.appendChild(multi_read.toString());
            newConfig.appendChild(multiRead);
            Element readFreq = new Element(AppConstants.APP_STATE_XML_ELE_READ_FREQ);
            readFreq.appendChild(Integer.toString(read_frequency));
            newConfig.appendChild(readFreq);
            Element xtraKeys = new Element(AppConstants.APP_STATE_XML_ELE_EXTRA_KEYS);
            xtraKeys.appendChild(extra_keys);
            newConfig.appendChild(xtraKeys);
            Element theftOn = new Element(AppConstants.APP_STATE_XML_ELE_THEFT_ON);
            theftOn.appendChild(theft_on_value);
            newConfig.appendChild(theftOn);
            Element theftOff = new Element(AppConstants.APP_STATE_XML_ELE_THEFT_OFF);
            theftOff.appendChild(theft_off_value);
            newConfig.appendChild(theftOff);
            
            docRoot.appendChild(newConfig);
            //use set setting value fcn to fill the elements?
            //write the changes
            success = this.saveXml();
        }
        return success;
    }
    /**
     * This function will verify the name parameter
     * @param configuration_name
     * @return true if deleted successfully, false if failed
     */
    public Boolean deleteConfiguration(String configuration_name) {
        
        Boolean success = false;
        try {
            configurationSection = this.getConfig(configuration_name);
            //get config may return default config if name is invalid
            String returnedName = configurationSection.getAttributeValue(AppConstants.APP_STATE_XML_ATTR_ID);
            Boolean okToDelete = !returnedName.equals(AppConstants.SETTINGS_CURRENT) &&
                    !returnedName.equals(AppConstants.SETTINGS_DEFAULT); 
            if (okToDelete) {
                docRoot.removeChild(configurationSection);
                success = this.saveXml();
                System.out.println("app state removed the config with result " + success);
                
            }
            
        }
        catch (Exception ex) {
            
        }
        
        return success;
    }
    private Boolean saveXml() {
        Boolean success = false;
        try {
                FileOutputStream fos = new FileOutputStream(settingsPath);
                Serializer xmlWriter = new Serializer(fos, "ISO-8859-1");
                xmlWriter.setIndent(4); //orig is 4 spaces
                xmlWriter.write(settingsDoc);
                xmlWriter.flush();
                xmlWriter.flush();
                fos.close();     
                success = true;
        }
        catch (Exception ex) {                             
            System.out.println("error create config " + ex.getMessage());
            ex.printStackTrace();
        }
        return success;
    }
    private Element getConfig(String configName) {
        //cycle through config coll, look for match to id
        //if none found, return default. better to return error?
        //
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
        ArrayList<String> nameArray = new ArrayList<>(configurationsCollection.size());
        Element oneEle;
        for (int i=0; i<configurationsCollection.size(); i++) {
            oneEle = configurationsCollection.get(i);
//            nameArray[i] = oneEle.getAttributeValue(AppConstants.APP_STATE_XML_ATTR_ID); 
            nameArray.add(oneEle.getAttributeValue(AppConstants.APP_STATE_XML_ATTR_ID));
        }
        return nameArray;
    }
    public static AntiTheftEnum theftStringToEnum(String theft_mode) {
        switch (theft_mode) {
            case AppConstants.THEFT_MODE_ON:
                return AntiTheftEnum.TURN_ON;                
            case AppConstants.THEFT_MODE_OFF:
                return AntiTheftEnum.TURN_OFF;                
            case AppConstants.THEFT_MODE_NONE:
            default:
                return AntiTheftEnum.NO_ACTION;
        }        
    }
    public static ReadWriteModeEnum readWriteStringToEnum(String read_write_mode) {
        switch (read_write_mode) {
            case AppConstants.R_W_MODE_READ:
                return ReadWriteModeEnum.READ_MODE;
            case AppConstants.R_W_MODE_WRITE:
                return ReadWriteModeEnum.WRITE_MODE;
            case AppConstants.R_W_MODE_IDLE:
            default:
                return ReadWriteModeEnum.IDLE_MODE;                
        }
    }
    
    
}
