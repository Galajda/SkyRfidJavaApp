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
     * Theft bit uses AFI. On = 07. Off = 00.
     * @param device_handle
     * @param card_id
     * @param new_state 
     */
    public static void changeTheftBit(int device_handle, char[] card_id, AntiTheftEnum new_state) {
        System.out.print("theft bit worker is pretending to change the theft bit of card #");
        System.out.println(String.format("%04x", (int)card_id[0]));
        
    }
}
