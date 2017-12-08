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

/**
 *
 * @author MichalG
 */
public class AppConstants 
{
    //title
    public static final String APP_TITLE = "RFID program";
    //RFID reader constants
    public static final char READ_SINGLE_CARD = 0x36;
    public static final char READ_MULTI_CARDS = 0x16;
    public static final char CARD_TYPE_ISO15693 = 0x31;    
    public static final int USB_PORT = 100;
    
    //app state constants
    public static final String SETTINGS_DEFAULT = "default";
    public static final String SETTINGS_CURRENT = "current";
    
    public static final String R_W_MODE_IDLE = "IDLE_MODE";
    public static final String R_W_MODE_READ = "READ_MODE";
    public static final String R_W_MODE_WRITE = "WRITE_MODE";
    
    public static final String THEFT_MODE_ON = "TURN_ON";
    public static final String THEFT_MODE_OFF = "TURN_OFF";
    public static final String THEFT_MODE_NONE = "NO_ACTION";
    
    //xml fields
    public static final String XML_ELE_ROOT = "settings";
    public static final String XML_ELE_PARM_SET = "parameter_set";            
    public static final String XML_ELE_TYPE = "type";   
    public static final String XML_ELE_THEFT_BIT = "theft_bit_action";
    public static final String XML_ELE_R_W_MODE = "read_write_mode";
    public static final String XML_ELE_TYPE_MULTI_READ = "multi_read";
    
}
