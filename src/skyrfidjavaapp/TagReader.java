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
    private final char READ_TAG_START_BLOCK = 0x01; //0x01 for barcode
    private final char READ_TAG_BLOCK_LEN = 0x05; //0x05 for barcode
    
    private final AppState state;
    private final AntiTheftEnum theftAction;
    private final ReadWriteModeEnum r_w_mode;
    
    /**
     * Use local vars for app state to save processor time.
     */
    TagReader() {
        System.out.println("tag reader constructor begins");
        state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        theftAction = state.getAntiTheftAction();
        r_w_mode = state.getReadWriteMode();
        
    }
    
    
    /**
     * Use same method for single and multi
     * @return String array of decoded numbers from all tags in RF field.
     */
    public String[] readOneCard() {
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
        char[] afi = {0x3}; //set it to a non-standard value to see if the fcn changes it
        int inventoryStatus = rfidDll.fw_inventory(readerHdl, AppConstants.READ_SINGLE_CARD,
                afi, (char)0x0, returnedUidLen, uidBuffer);
        System.out.println("inventory status " + (inventoryStatus==0));
        System.out.println("afi " + String.format("%04x", (int) afi[0]));
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
        int securityInfoStatus = rfidDll.fw_get_securityinfo(readerHdl, (char)0x22, READ_TAG_START_BLOCK, 
            READ_TAG_BLOCK_LEN, uidBuffer, returnedReadLength, readDataBuffer);
        System.out.println("get security info status " + (securityInfoStatus==0));
        System.out.println("\treturned read block length hex val " + String.format("%04x", (int)returnedReadLength[0]));
        for (int i = 0; i < (int)returnedReadLength[0]; i++) {
            System.out.print("element" + i + ": " + String.format("%04x", (int)readDataBuffer[i]) + ", ");
        }
        System.out.println();
         
//        security info status does not appear to be necessary for reading
        
        
        
//        char[] aidLength = {0x0};
//        char[] aidBuffer = {0x0};
//        int sysInfoStatus = rfidDll.fw_get_systeminfo(readerHdl, (char)0x22, uidBuffer, aidLength, aidBuffer);
//        System.out.println("get sys info status " + (sysInfoStatus==0));
        
        int authenticationStatus = rfidDll.fw_authentication(readerHdl, (char)0, (char)4);
        System.out.println("authentication status " + (authenticationStatus==0) + " " + authenticationStatus);
        
        int readBlockStatus = rfidDll.fw_readblock(readerHdl, (char)0x22, READ_TAG_START_BLOCK, 
            READ_TAG_BLOCK_LEN, uidBuffer, returnedReadLength, readDataBuffer);
        System.out.println("read block status " + (readBlockStatus==0) + " " + readBlockStatus);
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
        
        if (!(this.theftAction.equals(AntiTheftEnum.NO_ACTION))) {
            TheftBitWorker.changeTheftBit(readerHdl, uidBuffer, this.theftAction);
        }
        //halt the tag
        
        //close reader
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
     * The device may not be available immediately. Try for 1 second.
     * Typical time is 30-140 ms.
     * @return A positive integer if reader is found, -1 if fail
     */
    private int getDeviceHandle() {
        int h = -1;
        long startTime = System.currentTimeMillis();        
        do {
            h = rfidDll.fw_init(AppConstants.USB_PORT, 0); //ignore baud rate on USB port
        }
        while (((System.currentTimeMillis()-startTime)<1000) && (h < 0));  
        System.out.println("it took " + (System.currentTimeMillis()-startTime) + 
                " millis to get the handle"); //about 30 to 140 msec
        return h;        
    }
    
}
