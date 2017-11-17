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

import com.sun.jna.Library;
import com.sun.jna.Native;


/**
 *
 * @author Michal G. <Michal.G at cogitatummagnumtelae.com>
 */

public interface RfidNativeInterface extends Library {
    RfidNativeInterface INSTANCE = (RfidNativeInterface) Native.loadLibrary("umf", RfidNativeInterface.class);
    
    
    /**
     * Open the port to the RFID device
     * @param port This application is designed exclusively for the USB reader.
     * See manufacturer's documentation for serial device.
     * @param baud_rate Ignored for USB.
     * @return handle to device. >0 = success. <0 = fail.
     */
    int fw_init(int port, long baud_rate);   
    
    /**
     * Configures the USB device to read the desired type of tag.
     * @param handle handle to USB device
     * @param card_type  This application uses ISO15693 exclusively.
     * See manufacturer's documentation for ISO14443 Type A and B.
     * @return 0 = success. Nonzero = fail.
     */
    int fw_config_card(int handle,char card_type); 
    
    /**
     * 
     * @param handle handle to USB device
     * @param single_multi_flag
     * @param app_id Can be 0
     * @param mask_length
     * @param returned_length
     * @param returned_buffer
     * @return 
     */
    int fw_inventory(int handle, char single_multi_flag, char app_id, 
            char mask_length, char[] returned_length, char[] returned_buffer);   
    
    /**
     * 
     * @param handle handle to USB device
     * @param flags
     * @param card_id
     * @return 
     */
    int fw_select_uid(int handle,char flags, char[] card_id); 
    
    
    int fw_reset_to_ready(int handle,char flags, char[] card_id);
     
    int fw_get_securityinfo(int handle,char flags,char startblock,
            char number_of_blocks_to_read, char[] card_id, char[] returned_data_length, char[] returned_data);
    
    /**
     * 
     * @param handle
     * @param flags
     * @param startblock first block 
     * @param number_of_blocks_to_read
     * @param card_id
     * @param returned_data_length
     * @param returned_data
     * @return 0 = success. Nonzero = fail.
     */
    int fw_readblock(int handle,char flags, char startblock,
            char number_of_blocks_to_read, char[] card_id, char[] returned_data_length, char[] returned_data);
    
    /**
     * 
     * @param handle handle to the RFID device
     * @param flags
     * @param card_id
     * @return 0 = success. Nonzero = fail.
     */
    int fw_stay_quiet(int handle, char flags, char[] card_id);
//    st=fw_stay_quiet(icdev,0x22,&UID[1])
    
    //__int16 fw_stay_quiet(HANDLE icdev,unsigned char flags,unsigned char *UID);
    //st=fw_stay_quiet(icdev,0x22,&UID[1])
    
    
    //add anticoll
    
    //write
    //__int16 fw_writeblock(HANDLE icdev,unsigned char flags, unsigned charstartblock,
    //unsigned char blocknum,unsigned char *UID,unsigned char wlen,unsigned char *rbuffer);
    
    
    /**
     * 
     * @param handle handle to the RFID device
     * @return 
     */
    short fw_exit(int handle);
}
