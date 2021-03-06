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
 * The TagActor class combines the USB device methods that are common to reading
 * and writing, such as opening and closing the port and changing the AFI byte. 
 * For better performance, local variables store critical values of the app state.
 * The read and write classes are derived from TagActor. Methods specific to 
 * reading and writing are contained in the child classes.
 * @author Michal G. <Michal.G at cogitatummagnumtelae.com>
 */
public class TagActor {    
    protected final RfidNativeInterface rfidDll = RfidNativeInterface.INSTANCE;    
    protected final char TAG_START_BLOCK = 0x01; //0x01 for barcode
    protected final char TAG_BLOCK_LEN = 0x05; //0x05 for barcode
    
    protected final AppState state;
    protected final AntiTheftEnum theftAction;
    protected final char theftValue;
    
    
    protected final ReadWriteModeEnum r_w_mode;
    
    protected final int deviceHdl;
    /**
     * constructor
     */
    public TagActor() {
        
        System.out.println("tag actor constructor begins");
//        state = new AppState(AppSettingsEnum.SETTINGS_CURRENT);
        state = new AppState(AppConstants.SETTINGS_CURRENT);
        theftAction = state.getAntiTheftAction();
        if (theftAction.equals(AntiTheftEnum.TURN_ON)) {
//            theftValue = (char)Integer.parseInt(state.getAntiTheftOn(),16);
            theftValue = (char)Integer.decode(state.getAntiTheftOn()).intValue();
        }
        else {
//            theftValue = (char)Integer.parseInt(state.getAntiTheftOff(),16);
            theftValue = (char)Integer.decode(state.getAntiTheftOff()).intValue();
        }
        
        r_w_mode = state.getReadWriteMode();
        
        deviceHdl = this.getDeviceHandle();
        System.out.println("initialized reader. handle = " + deviceHdl);  
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
    public char[] selectTag() {
        return new char[] {(char)0x0};
    }
    
    public void closePort() {        
        //close reader
        try {
            int closeSuccess = rfidDll.fw_exit(deviceHdl);
            System.out.println("close reader sucess " + (closeSuccess==0));
        }
        catch (Exception ex) {
            System.out.println("cannot close reader. " + ex.getMessage());
        }
    }
    
    public void changeTheftBit(RfidNativeInterface dll, int device_handle, 
            char[] card_id) {
        System.out.print("tag actor is changing the AFI byte of card #");
        System.out.print(String.format("%04x", (int)card_id[0]));
//        System.out.println(" to value " + String.format("%04x", (int)TagActor.theftValue));
        System.out.println(" to value " + String.format("%04x", (int)this.theftValue));
        dll.fw_write_afi(device_handle, (char)0x22, this.theftValue , card_id);                
    }
    
}
