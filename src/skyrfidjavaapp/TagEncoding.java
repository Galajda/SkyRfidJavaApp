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
public class TagEncoding {
    
    /**
     * 
     * @param inputStream the array of char bytes as read by the USB device
     * @return a human-readable sequence of numbers representing a barcode
     */
    public static String Decode(char[] inputStream, int arrayLength) {
        System.out.println("decoding char array");
//        System.out.println("char array length " + inputStream.length);
        for (int i = 0; i <arrayLength; i++) {
            System.out.print("element" + i + ": " + String.format("%04x", (int)inputStream[i]) + ", ");
        }
        System.out.println();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <arrayLength; i++) {
            int chunk = (int)inputStream[i];
            builder.append((chunk - (chunk/256)*256)-48);
            builder.append((chunk/256)-48);
        }
        return builder.toString();
    }
    /**
     * Used when writing data to a tag
     * @param barcode the human-readable barcode of the item to be tagged
     * @return an array of char bytes
     */
    public static char[] Encode(String barcode) {
        //what if bc has odd length?
        char[] writeBuffer = new char[256];
        
        char[] bcArray = new char[barcode.length()];
        
        for (int i = 0; i < barcode.length(); i++) {
            writeBuffer[2*i] = barcode.charAt(i);
        }
        return new char[]{0x0, 0x1};
    }
}
