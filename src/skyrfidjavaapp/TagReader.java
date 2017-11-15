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
public class TagReader {
    
    /**
     * 
     * @param hDev handle to reader device
     * @param single_multi takes only two values 
     *      0x36 for single cards
     *      0x16 for multiple cards
     */
    TagReader(int hDev, char single_multi) {
        switch (single_multi) {
            case AppConstants.READ_SINGLE_CARD:
                
            case AppConstants.READ_MULTI_CARDS:
                
            default:
                System.out.println("TagReader constructor error. ");
        }
    }
    
    public String readOneCard() {
        return "hello";
    }
}
