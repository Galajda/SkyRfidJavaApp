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
public class InputErrorMsg {
    public static final String ERR_INPUT_TITLE = "Invalid input";
    public static final String ERR_CONFIG_NAME = "The configuration name must consist of" +
            "\nletters, numbers and punctuation." +
            "\nMaximum length is 25 characters.";
    public static final String ERR_READ_FREQ = "The read frequency is an integer" +
            "\nbetween 10 and 10000.";
    public static final String ERR_XTRA_KEYS = "Extra keystrokes must be" +
            "\nless than 20 characters long.";
    public static final String ERR_THEFT_VALUE = "The anti-theft (AFI) value" +
            "\nmust be a hexadecimal number" +
            "\nbetween 0x00 and 0xff.";
    public static final String ERR_BARCODE = "The barcode must consist of" +
            "\nletters, numbers and punctuation." +
            "\nand be 14 characters long.";
    public static final String ERR_CANNOT_SAVE_TITLE = "Cannot save configuration";
    public static final String ERR_CANNOT_USE_TITLE = "Cannot use configuration";
    
    public static final String ERR_INVALID_PAGE_MSG = "Some value is invalid.\nPlease check your work.";
    public static final String ERR_CANNOT_DELETE_TITLE = "Cannot delete configuration";
    public static final String ERR_CANNOT_DELETE_MSG = "Possibly no configuration was selected." +
            "\nAlso, the built-in configurations current" +
            "\nand default must remain.";
}
