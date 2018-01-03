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
 * No constructor. Single static method.
 * @author Michal G. <Michal.G at cogitatummagnumtelae.com>
 */
public class TheftBitWorker {
    /**
     * The "theft bit" uses the AFI byte. 
     * @param dll copy of JNA instance, avoiding creation of a new instance
     * @param device_handle handle to the RFID device
     * @param card_id ID of the card to be changed
     * @param new_state enum indicating new value of AFI byte. The function 
     *      translates the enum to the actual value written to the tag.
     */
    public static void changeTheftBit(RfidNativeInterface dll, int device_handle, 
            char[] card_id, AntiTheftEnum new_state) {
        System.out.print("theft bit worker is changing the theft bit of card #");
        System.out.print(String.format("%04x", (int)card_id[0]));
        System.out.println(" to value " + new_state);
        switch (new_state) {
            case TURN_ON:
                dll.fw_write_afi(device_handle, (char)0x22, (char)0x01, card_id);
                break;
            case TURN_OFF:
                dll.fw_write_afi(device_handle, (char)0x22, (char)0x00, card_id);
                break;
            case NO_ACTION:
            default:
                    
        }        
    }
}
