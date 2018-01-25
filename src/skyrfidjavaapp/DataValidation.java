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

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Class of static methods to test user input.
 * @author Michal G. <Michal.G at cogitatummagnumtelae.com>
 */
public class DataValidation {
    public static Boolean isValidConfigName(String testName) {
        //want word or number char, underscore, no space.
        //search for the unwanted, success if not found
        
        //positive match proposal
        Pattern posMatch = Pattern.compile("\\w{" + testName.length() + "}");
        System.out.println("proposed name tester says " + posMatch.matcher(testName).find());
        
        Pattern p = Pattern.compile("\\W");
        Matcher m = p.matcher(testName);
        
        return !m.find() && (testName.length() > 0) && (testName.length() < 25);
    }
    public static Boolean isValidReadFreq(String testFreq) {
        //what is reasonable range? indicate this on settings page
        //digits only. if find a non-digit, fail
        Pattern p = Pattern.compile("\\D");
        Matcher m = p.matcher(testFreq);
        //parse value between 10 and 10000
        Integer freq;
        try {
            freq = Integer.parseInt(testFreq);
        }
        catch (NumberFormatException ex) {
            freq = 0;
        }
        
        return !m.find() && (freq > 9) && (freq < 10001);
    }
    public static Boolean isValidXtraKeys(String testValue) {
        //TODO: accept comma-separated list of hex-format strings,
        //as for isValidTheftValue
        //idea: split string on comma, test each element with isValidTheftValue
        return (testValue.length()<20);
    }
    public static Boolean isValidTheftValue(String testValue) {
        //string in hex number format 0x00 to 0xff
        Pattern p = Pattern.compile("0x[0-9a-fA-F]{2}"); //digits
        Matcher m = p.matcher(testValue);
        
        return m.find() && (testValue.length() == 4);
    }
    /**
     * Tests format of barcode before it is written to the tag. Currently 
     * accepting word characters. Length must be 14 characters.
     * @param barcode Candidate barcode.
     * @return <code>true</code> if input satisfies criteria, painful shock if not.
     */
    public static Boolean isValidBarcode(String barcode) {
        //word chars
        Pattern p = Pattern.compile("\\w{14}");
        Matcher m = p.matcher(barcode);
        
        return m.find() && (barcode.length() == 14);
    }
    
}
