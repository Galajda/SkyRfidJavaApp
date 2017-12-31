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
 */
public class TagWriter extends TagActor {
    
    public TagWriter() {
        super();
        System.out.println("tag writer constructor called");
        
        System.out.println("initialized writer. handle = " + super.deviceHdl);  
    }
    
    public boolean WriteOneTag(String barcode) {
//        System.out.println("write one tag got barcode " + barcode);
        
        if (super.deviceHdl<0) {
//            return new String[]{DeviceErrorCodes.ERR_NO_HANDLE};
            return false;
        }
        int configStatus = super.rfidDll.fw_config_card(super.deviceHdl, AppConstants.CARD_TYPE_ISO15693);        
        System.out.println("config card status " + (configStatus==0));
        
        char[] returnedUidLen = {0x0};
        System.out.println("returned len init hex val " + String.format("%04x", (int)returnedUidLen[0]) );
        char[] uidBuffer = new char[256];
//        int inventoryStatus = rfidDll.fw_inventory(deviceHdl, AppConstants.READ_SINGLE_CARD,
//                TAG_START_BLOCK, TAG_BLOCK_LEN, returnedUidLen, uidBuffer);
        char[] afi = {0x3}; //set it to a non-standard value to see if the fcn changes it
        int inventoryStatus = super.rfidDll.fw_inventory(super.deviceHdl, AppConstants.READ_SINGLE_CARD,
                afi, (char)0x0, returnedUidLen, uidBuffer);
        System.out.println("inventory status " + (inventoryStatus==0));
        if (inventoryStatus !=0 ) {
//            return new String[] {DeviceErrorCodes.ERR_NO_CARD};
            return false;
        }
        
        System.out.println("afi " + String.format("%04x", (int) afi[0]));
        System.out.println("\treturned uid length hex val" + String.format("%04x", (int)returnedUidLen[0]));
        System.out.println("\tuid buffer hex vals");
        System.out.print("\t");
        for (int i = 0; i <256; i++) {
            //stop at i = (int)returnedUidLen[0]?
            System.out.print("element" + i + ": " + String.format("%04x", (int)uidBuffer[i]) + ", ");
        }
        System.out.println();
                
        int selectStatus = super.rfidDll.fw_select_uid(super.deviceHdl, (char)0x22, uidBuffer);
        System.out.println("select status " + (selectStatus==0));
        
        
        
//        char[] card_id;
        
        char[] write_buffer = TagEncoding.encode(barcode);
        System.out.println("tag encoding returned encoded value");    
        System.out.print("\t");
        for (int i=0;i<write_buffer.length;i++) {
            System.out.print("element " + i + ": " + String.format("%04x", (int)write_buffer[i]) + ", ");
        }
        char[] write_buffer_length = {(char)write_buffer.length};
        
        int writeResponse = super.rfidDll.fw_writeblock(super.deviceHdl, (char)0x22, 
                TAG_START_BLOCK, TAG_BLOCK_LEN, uidBuffer, write_buffer_length, write_buffer);
        boolean writeSuccess = (writeResponse==0);
        System.out.println("write status " + writeSuccess);
        if (writeSuccess && !(super.theftAction.equals(AntiTheftEnum.NO_ACTION))) {
            
            TagActor.changeTheftBit(rfidDll, deviceHdl, uidBuffer);
//            TheftBitWorker.changeTheftBit(super.rfidDll, super.deviceHdl, uidBuffer, super.theftAction);
        }
        
        return writeSuccess;
    }
    
}
