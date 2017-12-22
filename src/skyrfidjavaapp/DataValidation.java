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
public class DataValidation {
    public static Boolean isValidConfigName(String testName) {
        return true;
    }
    public static Boolean isValidReadFreq(String testName) {
        //what is reasonable range? indicate this on settings page
        return true;
    }
    public static Boolean isValidXtraKeys(String testName) {
        return true;
    }
    public static Boolean isValidTheftValue(String testName) {
        //must be string in hex number format 0x00 to 0xff
        return true;
    }
    
    
}
