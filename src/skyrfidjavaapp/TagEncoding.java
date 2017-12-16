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
     * @param encodedArray the array of char bytes as read by the USB device
     * @param arrayLength Is this necessary? Can it be derived within the fcn from inputStream?
     * @return a human-readable sequence of numbers representing a barcode
     */
    public static String decode(char[] encodedArray, int arrayLength) {
        System.out.println("decoding char array");
//        System.out.println("char array length " + inputStream.length);
//        for (int i = 0; i <arrayLength; i++) {
//            System.out.print("element" + i + ": " + String.format("%04x", (int)encodedArray[i]));
//            System.out.print(" break up into ");
//            System.out.print("1st: " + (char)(encodedArray[i] - (encodedArray[i]/0x100)*0x100)
//                    + ", 2nd: " + (char)(encodedArray[i]/0x100) + ", ");
//            System.out.println();
//        }
//        System.out.println();
        
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <arrayLength; i++) {
//            int chunk = (int)encodedArray[i];
            builder.append((char)(encodedArray[i] - (encodedArray[i]/0x100)*0x100));
            builder.append((char)(encodedArray[i]/0x100));
//            builder.append((chunk - (chunk/0x100)*0x100)-0x30);
//            builder.append((chunk/0x100)-0x30);
        }
        return builder.toString();
    }
    /**UNDER CONSTRUCTION
     * Used when writing data to a tag
     * @param barcode the human-readable barcode of the item to be tagged
     * @return an array of char bytes which are written to the tag
     */
    public static char[] encode(String barcode) {
//        System.out.println("tag encoding class is encoding a barcode string " + barcode);
        //if bc has odd length, pad right end with null 0x0 or space 0x20
        
        
//        char[] bcArray = new char[barcode.length()];
//        char[] bcArray = barcode.toCharArray();
//        System.out.println("the barcode char array is");
//        System.out.print("\t");
//        for (int i=0;i<bcArray.length;i++) {
//            System.out.print("element " + i + ": " + bcArray[i] + ", ");
//        }
//        System.out.println();
//        System.out.println("the buffer array is");
//        System.out.print("\t");
        char[] writeBuffer = new char[barcode.length()/2];
        for (int i = 0; i < barcode.length()/2; i++) {
            writeBuffer[i] = (char)(0x100*barcode.charAt(2*i+1) + barcode.charAt(2*i));
//            System.out.print("element " + i + ": " + String.format("%04x", (int)writeBuffer[i]) + ", ");
        }
//        System.out.println();
//        System.out.println("leaving encode fcn");
//        return new char[]{0x0, 0x1};
        return writeBuffer;
    }
}
