/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyrfidjavaapp;

import com.sun.jna.Library;
import com.sun.jna.Native;
//import com.sun.jna.Platform;
//import com.sun.jna.*;

/**
 *
 * @author Michal G. <Michal.G at cogitatummagnumtelae.com>
 */

public interface RfidNativeInterface extends Library {
//    RfidNativeInterface INSTANCE = (RfidNativeInterface) Native.loadLibrary("C:\\Windows\\System32\\umf.dll", RfidNativeInterface.class);
    
//    addSearchPath("lib name", "path");
    RfidNativeInterface INSTANCE = (RfidNativeInterface) Native.loadLibrary("umf", RfidNativeInterface.class);
//    RfidNativeInterface INSTANCE = (RfidNativeInterface) Native.
    
    int fw_init(int i, long l);   
    int fw_config_card(int handle,char card_type); 
    int fw_inventory(int handle, char single_multi_flag, char app_id, 
            char mask_length, char[] returned_length, char[] returned_buffer);   
    
    int fw_select_uid(int handle,char flags, char[] card_id); 
    
    int fw_reset_to_ready(int handle,char flags, char[] car_id);
     
    int fw_get_securityinfo(int handle,char flags,char startblock,
            char number_of_blocks_to_read, char[] card_id, char[] returned_data_length, char[] returned_data);
    
    int fw_readblock(int handle,char flags, char startblock,
            char number_of_blocks_to_read, char[] card_id, char[] returned_data_length, char[] returned_data);
    //readblock(HANDLE icdev,unsigned char flags, unsigned char startblock,
    //unsigned char blocknumm,unsigned char *UID,unsigned char *rlen,unsigned char *rbuffer)
    
    
    short fw_exit(int i);
}
