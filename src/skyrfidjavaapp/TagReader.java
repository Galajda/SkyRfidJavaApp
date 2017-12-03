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
public class TagReader extends TagActor{
    
    
    /**
     * Use local variables for application state to save processor time.
     * Since the reader is initialized in the timer start, and the timer
     * is restarted when the state changes, the local variables always
     * have the current values.
     */
    TagReader() {
        super();
        System.out.println("tag reader constructor begins");
        
        System.out.println("initialized reader. handle = " + deviceHdl);  
        
    }
    
    /**
     * Use same method for single and multi?
     * @return String array of decoded numbers from all tags in RF field.
     */
    public String[] readOneCard() {
        System.out.println("*****************************************");        
        System.out.println("read one card fcn begins");        
        if (deviceHdl<0) {
            return new String[]{DeviceErrorCodes.ERR_NO_HANDLE};
        }
        int configStatus = rfidDll.fw_config_card(deviceHdl, AppConstants.CARD_TYPE_ISO15693);        
        System.out.println("config card status " + (configStatus==0));
        
        char[] returnedUidLen = {0x0};
        System.out.println("returned len init hex val " + String.format("%04x", (int)returnedUidLen[0]) );
        char[] uidBuffer = new char[256];
//        int inventoryStatus = rfidDll.fw_inventory(deviceHdl, AppConstants.READ_SINGLE_CARD,
//                TAG_START_BLOCK, TAG_BLOCK_LEN, returnedUidLen, uidBuffer);
        char[] afi = {0x3}; //set it to a non-standard value to see if the fcn changes it
        int inventoryStatus = rfidDll.fw_inventory(deviceHdl, AppConstants.READ_SINGLE_CARD,
                afi, (char)0x0, returnedUidLen, uidBuffer);
        System.out.println("inventory status " + (inventoryStatus==0));
        if (inventoryStatus !=0 ) {return new String[] {DeviceErrorCodes.ERR_NO_CARD};}
        
        System.out.println("afi " + String.format("%04x", (int) afi[0]));
        System.out.println("\treturned uid length hex val" + String.format("%04x", (int)returnedUidLen[0]));
        System.out.println("\tuid buffer hex vals");
        System.out.print("\t");
        for (int i = 0; i <256; i++) {
            //stop at i = (int)returnedUidLen[0]?
            System.out.print("element" + i + ": " + String.format("%04x", (int)uidBuffer[i]) + ", ");
        }
        System.out.println();
                
        int selectStatus = rfidDll.fw_select_uid(deviceHdl, (char)0x22, uidBuffer);
        System.out.println("select status " + (selectStatus==0));
        
//        int resetReadyStatus = rfidDll.fw_reset_to_ready(deviceHdl, (char)0x22, uidBuffer);
//        System.out.println("reset to ready status " + (resetReadyStatus==0));
        
        char[] returnedReadLength = {0x0};
        System.out.println("returned read block len init hex val " + String.format("%04x", (int)returnedReadLength[0]) );
        char[] readDataBuffer = new char[256];
        int securityInfoStatus = rfidDll.fw_get_securityinfo(deviceHdl, (char)0x22, TAG_START_BLOCK, 
            TAG_BLOCK_LEN, uidBuffer, returnedReadLength, readDataBuffer);
        System.out.println("get security info status " + (securityInfoStatus==0));
        System.out.println("\treturned read block length hex val " + String.format("%04x", (int)returnedReadLength[0]));
        for (int i = 0; i < (int)returnedReadLength[0]; i++) {
            System.out.print("element" + i + ": " + String.format("%04x", (int)readDataBuffer[i]) + ", ");
        }
        System.out.println();
         
//        security info status does not appear to be necessary for reading
        
        
        
//        char[] aidLength = {0x0};
//        char[] aidBuffer = {0x0};
//        int sysInfoStatus = rfidDll.fw_get_systeminfo(deviceHdl, (char)0x22, uidBuffer, aidLength, aidBuffer);
//        System.out.println("get sys info status " + (sysInfoStatus==0));
        
        int authenticationStatus = rfidDll.fw_authentication(deviceHdl, (char)0, (char)4);
        System.out.println("authentication status " + (authenticationStatus==0) + " " + authenticationStatus);
        
        int readBlockStatus = rfidDll.fw_readblock(deviceHdl, (char)0x22, TAG_START_BLOCK, 
            TAG_BLOCK_LEN, uidBuffer, returnedReadLength, readDataBuffer);
        System.out.println("read block status " + (readBlockStatus==0) + " " + readBlockStatus);
        //len of returned data is a better indicator than read block status
        System.out.println("\treturned read block length hex val " + String.format("%04x", (int)returnedReadLength[0]));
        System.out.println("\tread block buffer hex vals");
        System.out.print("\t");
//        int lastReadBlock = 
        for (int i = 0; i <=TAG_BLOCK_LEN + 1; i++) {
            System.out.print("element" + i + ": " + String.format("%04x", (int)readDataBuffer[i]) + ", ");
        }
        System.out.println();
        String decodedData = TagEncoding.decode(readDataBuffer, TAG_BLOCK_LEN + 2);
        System.out.println("tag reader got decoded data " + decodedData);
        
        if (!(this.theftAction.equals(AntiTheftEnum.NO_ACTION))) {
            TheftBitWorker.changeTheftBit(deviceHdl, uidBuffer, this.theftAction);
        }
        //halt the tag
        int haltStatus = rfidDll.fw_halt(deviceHdl);
        System.out.println("halted tag " + (haltStatus==0) + " return val " + haltStatus);
        
        return new String[] {decodedData};
    }
    
    public String[] readDeck() {
        System.out.println("*****************************************");        
        System.out.println("read deck fcn begins");       
        String[] deck = new String[] {"0"};
             
        if (deviceHdl<0) {return new String[]{DeviceErrorCodes.ERR_NO_HANDLE};}
        
        
        int authenticationStatus = rfidDll.fw_authentication(deviceHdl, (char)0, (char)4);
        System.out.println("authentication status " + (authenticationStatus==0) + " " + authenticationStatus);
                
        long[] cardSerialNumbers = {0};
        int cardSerNrStatus = rfidDll.fw_card(deviceHdl, (char)0x1, cardSerialNumbers);
        System.out.println("card ser no status " + cardSerNrStatus);
        System.out.print("\t");
        for (int i=0;i<cardSerialNumbers.length;i++) {
            System.out.print("element " + i + ": " + cardSerialNumbers[i]);
        }
        System.out.println();
//        int configStatus = rfidDll.fw_config_card(deviceHdl, AppConstants.CARD_TYPE_ISO15693);        
//        System.out.println("config card status " + (configStatus==0));
        
//        char[] returnedUidLen = {0x0};
//        System.out.println("returned len init hex val " + String.format("%04x", (int)returnedUidLen[0]) );
//                char[] uidBuffer = new char[256];
//        int inventoryStatus = rfidDll.fw_inventory(deviceHdl, AppConstants.READ_SINGLE_CARD,
//                TAG_START_BLOCK, TAG_BLOCK_LEN, returnedUidLen, uidBuffer);
//        char[] afi = {0x3}; //set it to a non-standard value to see if the fcn changes it
//        int inventoryStatus = rfidDll.fw_inventory(deviceHdl, AppConstants.READ_MULTI_CARDS,
//                afi, (char)0x0, returnedUidLen, uidBuffer);
//        System.out.println("inventory status " + (inventoryStatus==0));
//        if (inventoryStatus !=0) {return new String[] {DeviceErrorCodes.ERR_NO_CARD};}
        
//        System.out.println("afi " + String.format("%04x", (int) afi[0]));
//        System.out.println("\treturned uid length hex val" + String.format("%04x", (int)returnedUidLen[0]));
//        System.out.println("\tuid buffer hex vals");
//        System.out.print("\t");
//        for (int i = 0; i <256; i++) {
            //stop at i = (int)returnedUidLen[0]?
//            System.out.print("element" + i + ": " + String.format("%04x", (int)uidBuffer[i]) + ", ");
//        }
//        System.out.println();
                
//        int selectStatus = rfidDll.fw_select_uid(deviceHdl, (char)0x22, uidBuffer);
//        System.out.println("select status " + (selectStatus==0));
        
//        int resetReadyStatus = rfidDll.fw_reset_to_ready(deviceHdl, (char)0x22, uidBuffer);
//        System.out.println("reset to ready status " + (resetReadyStatus==0));
        
//        char[] returnedReadLength = {0x0};
//        System.out.println("returned read block len init hex val " + String.format("%04x", (int)returnedReadLength[0]) );
//        char[] readDataBuffer = new char[256];
//        int securityInfoStatus = rfidDll.fw_get_securityinfo(deviceHdl, (char)0x22, TAG_START_BLOCK, 
//            TAG_BLOCK_LEN, uidBuffer, returnedReadLength, readDataBuffer);
//        System.out.println("get security info status " + (securityInfoStatus==0));
//        System.out.println("\treturned read block length hex val " + String.format("%04x", (int)returnedReadLength[0]));
//        for (int i = 0; i < (int)returnedReadLength[0]; i++) {
//            System.out.print("element" + i + ": " + String.format("%04x", (int)readDataBuffer[i]) + ", ");
//        }
//        System.out.println();
         
//        security info status does not appear to be necessary for reading
        
        
        
//        char[] aidLength = {0x0};
//        char[] aidBuffer = {0x0};
//        int sysInfoStatus = rfidDll.fw_get_systeminfo(deviceHdl, (char)0x22, uidBuffer, aidLength, aidBuffer);
//        System.out.println("get sys info status " + (sysInfoStatus==0));
        
//        int readBlockStatus = rfidDll.fw_readblock(deviceHdl, (char)0x22, TAG_START_BLOCK, 
//            TAG_BLOCK_LEN, uidBuffer, returnedReadLength, readDataBuffer);
//        System.out.println("read block status " + (readBlockStatus==0) + " " + readBlockStatus);
        //len of returned data is a better indicator than read block status
//        System.out.println("\treturned read block length hex val " + String.format("%04x", (int)returnedReadLength[0]));
//        System.out.println("\tread block buffer hex vals");
//        System.out.print("\t");
//        int lastReadBlock = 
//        for (int i = 0; i <=TAG_BLOCK_LEN + 1; i++) {
//            System.out.print("element" + i + ": " + String.format("%04x", (int)readDataBuffer[i]) + ", ");
//        }
//        System.out.println();
//        String decodedData = TagEncoding.decode(readDataBuffer, TAG_BLOCK_LEN + 2);
//        System.out.println("tag reader got decoded data " + decodedData);
        
//        if (!(this.theftAction.equals(AntiTheftEnum.NO_ACTION))) {
//            TheftBitWorker.changeTheftBit(deviceHdl, uidBuffer, this.theftAction);
//        }
        //halt the tag
        

        return deck;
    }
    
    
}
