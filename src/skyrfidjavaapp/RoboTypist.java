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

import com.sun.jna.Native;
//import java.awt.Robot;
//import java.awt.AWTException;
//import java.lang.SecurityException;
//import java.awt.event.KeyEvent;
//import static java.lang.System.out;

import cogimag.java.keyboard.AwtKeyMap;
import cogimag.java.keyboard.AwtRoboSteno;

//import javafx.scene.input.KeyCode;
/**
 *
 * @author Michal G. <Michal.G at cogitatummagnumtelae.com>
 */
public class RoboTypist extends AwtRoboSteno {
//    private static Robot r;
//    private static boolean isRobotOnline;    
    
//    public RoboTypist() {
//        isRobotOnline = true;
//        try {
//            r = new Robot();
//        }        
//        catch (AWTException | SecurityException ex)
//        {
//            out.println("cannot initialize robot" + ex.getMessage());
//            isRobotOnline = false;
//        }
//    }
    
    public RoboTypist(AwtKeyMap m) {
        super(m);
    }
    
    /**
     * This function shall decide whether to send keys or not, depending
     * upon the active window on the screen. If the RFID program (or IDE) is active,
     * keys will not be sent.
     * @param data the string to be sent to the current input focus, plus any extra characters
     */
    @Override
    public void type(String data) {
//        out.println("robo typist does app have focus? " + this.doesAppHaveFocus());
//        if (!this.doesAppHaveFocus() && isRobotOnline) {
        if (!this.doesAppHaveFocus()) {
//            System.out.println("typing " + data + " to anyone");
            super.type(data);
//            cogimag.java.keyboard.AwtKeyEventSteno.fireEvent(new cogimag.java.keyboard.AwtKeyMap_EN_US(), card);

            
        }      
    }
    
//    private static int getKeycode(char ch) {
//        //convert a char to the Java keycode values VK_A, VK_B, VK_1, VK_2, etc.
//        int kc=88;  //default is the letter x
//        if (ch >= 'a' && ch <= 'z') {
//            kc = ch-32;
//        }
//        if ((ch >= 'A' && ch <= 'Z' ) | (ch >= '0' && ch <= '9')) {
//            kc=ch+0;
//        }
//        return kc;
//    }
    public boolean doesAppHaveFocus () {
//        out.println("looking for active window");
        final User32Interface winDll = User32Interface.INSTANCE;
        final int hWnd = winDll.GetForegroundWindow();
//        out.println("foreground window handle " + hWnd);
//        final int titleLength = winDll.GetWindowTextLengthA(hWnd);
//        out.println("foreground window title length from dll fcn " + titleLength);
        byte[] winText = new byte[128];
//        out.println("does winText start as null? " + (winText == null));
        winDll.GetWindowTextA(hWnd, winText, 128); //fcn returns value through var winText
//        final String activeWindowTitle = (Native.toString(winText)==null)? "" : Native.toString(winText);
        final String activeWindowTitle = Native.toString(winText); 
        
            //winText is initialized in declaration, therefore never null
//        out.println("win text " + activeWindowTitle);
//        out.println("foreground window title length from string length " + activeWindowTitle.length());     
        final String pkgNameLc = getClass().getPackage().getName().toLowerCase();
        //package name should be in lower case. change it to be certain
//        out.println("pkg name " + pkgNameLc);
//        out.println("does win title contain pkg name? " + activeWindowTitle.toLowerCase().contains(pkgNameLc));
//        out.println("does win title contain app title? " + activeWindowTitle.contains(AppConstants.APP_TITLE));
        return activeWindowTitle.contains(AppConstants.APP_TITLE) || 
                activeWindowTitle.toLowerCase().contains(pkgNameLc);
        
    }
    
//    public static boolean isShiftedChar(char c) {        
//        return true;
//    }
}