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
 * @author Michal G. <Michal.G at cogitatummagnumtelae.com>
 * 
 */
public class TagReader {
    private final RfidNativeInterface rfidDll = RfidNativeInterface.INSTANCE;    
    private final char READ_TAG_START_BLOCK = 0x1;
    private final char READ_TAG_BLOCK_LEN = 0x05;
    
    private final AppState state;
    
    /**
     * 
     * @param hDev handle to reader device
     * @param single_multi takes only two values 
     *      0x36 for single cards
     *      0x16 for multiple cards
     *      this is the value sent to the device, 
     *      not the boolean stored in AppState
     */
    TagReader() {
        System.out.println("tag reader constructor begins");
        state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        if (state.isMultiRead()) {
            
        }
//        switch () {
//            case AppConstants.READ_SINGLE_CARD:
//                
//            case AppConstants.READ_MULTI_CARDS:
//                
//            default:
//                System.out.println("TagReader constructor error. ");
//        }
    }
    
    
    /**
     * Use same method for single and multi
     * @return String array of decoded numbers from all tags in RF field.
     */
    public String[] readCards() {
        System.out.println("read cards fcn begins");        
        int readerHdl;
        readerHdl = this.getDeviceHandle();
        System.out.println("initialized reader. handle = " + readerHdl);        
        if (readerHdl<0) {
            return new String[]{DeviceErrorCodes.ERR_NO_HANDLE};
        }
        int configStatus = rfidDll.fw_config_card(readerHdl, AppConstants.CARD_TYPE_ISO15693);        
        System.out.println("config card status " + (configStatus==0));
        
        
        char[] returnedUidLen = {0x0};
        System.out.println("returned len init hex val " + String.format("%04x", (int)returnedUidLen[0]) );
        char[] uidBuffer = new char[256];
//        int inventoryStatus = rfidDll.fw_inventory(readerHdl, AppConstants.READ_SINGLE_CARD,
//                READ_TAG_START_BLOCK, READ_TAG_BLOCK_LEN, returnedUidLen, uidBuffer);
        int inventoryStatus = rfidDll.fw_inventory(readerHdl, AppConstants.READ_SINGLE_CARD,
                (char)0x0, (char)0x0, returnedUidLen, uidBuffer);
        System.out.println("inventory status " + (inventoryStatus==0));
        System.out.println("\treturned uid length hex val" + String.format("%04x", (int)returnedUidLen[0]));
        System.out.println("\tuid buffer hex vals");
        System.out.print("\t");
        for (int i = 0; i <256; i++) {
            //stop at i = (int)returnedUidLen[0]?
            System.out.print("element" + i + ": " + String.format("%04x", (int)uidBuffer[i]) + ", ");
        }
        System.out.println();
                
        int selectStatus = rfidDll.fw_select_uid(readerHdl, (char)0x22, uidBuffer);
        System.out.println("select status " + (selectStatus==0));
        
        int resetReadyStatus = rfidDll.fw_reset_to_ready(readerHdl, (char)0x22, uidBuffer);
        System.out.println("reset to ready status " + (resetReadyStatus==0));
        
        
        char[] returnedReadLength = {0x0};
        System.out.println("returned read block len init hex val " + String.format("%04x", (int)returnedReadLength[0]) );
        char[] readDataBuffer = new char[256];
//        int securityInfoStatus = rfidDll.fw_get_securityinfo(readerHdl, (char)0x22, READ_TAG_START_BLOCK, 
//            READ_TAG_BLOCK_LEN, uidBuffer, returnedReadLength, readDataBuffer);
//        System.out.println("get security info status " + (securityInfoStatus==0));
//        System.out.println("\treturned read block length hex val " + String.format("%04x", (int)returnedReadLength[0]));
        //security info status does not appear to be necessary
        
        int readBlockStatus = rfidDll.fw_readblock(readerHdl, (char)0x22, READ_TAG_START_BLOCK, 
            READ_TAG_BLOCK_LEN, uidBuffer, returnedReadLength, readDataBuffer);
        System.out.println("read block status " + (readBlockStatus==0));
        //len of returned data is a better indicator than read block status
        System.out.println("\treturned read block length hex val " + String.format("%04x", (int)returnedReadLength[0]));
        System.out.println("\tread block buffer hex vals");
        System.out.print("\t");
//        int lastReadBlock = 
        for (int i = 0; i <=READ_TAG_BLOCK_LEN + 1; i++) {
            System.out.print("element" + i + ": " + String.format("%04x", (int)readDataBuffer[i]) + ", ");
        }
        System.out.println();
        String decodedData = TagEncoding.decode(readDataBuffer, READ_TAG_BLOCK_LEN + 2);
        System.out.println("tag reader got decoded data " + decodedData);
        
        
        String[] deck = new String[256];
        
        try {
            int closeSuccess = rfidDll.fw_exit(readerHdl);
            System.out.println("close reader sucess " + (closeSuccess==0));
        }
        catch (Exception ex) {
            System.out.println("cannot close reader. " + ex.getMessage());
        }
        
//        return new String[] {"hello"};
        return new String[] {decodedData};
    }
    
    /**
     * The device may not be available immediately. Try for 2 seconds.
     * @return a positive integer if reader is found, -1 if fail
     */
    private int getDeviceHandle() {
        int h = -1;
        long startTime = System.currentTimeMillis();        
        do {
            h = rfidDll.fw_init(AppConstants.USB_PORT, 0); //ignore baud rate on USB port
        }
        while (((System.currentTimeMillis()-startTime)<2000) && (h < 0));  
        System.out.println("it took " + (System.currentTimeMillis()-startTime) + 
                " millis to get the handle"); //about 28 msec
        return h;        
    }
    
}
