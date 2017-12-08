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

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * @author Michal G. <Michal.G at cogitatummagnumtelae.com>
 * Technique borrowed from code.msdn.microsoft.com topic
 * "How to get the title of the current active window in Windows"
 * and SO 1173926, 20596453
 */
public interface User32Interface extends Library {
    User32Interface INSTANCE = (User32Interface) Native.loadLibrary("user32", User32Interface.class);
    
    int GetForegroundWindow();
    int GetWindowTextLengthA(int handle);
    int GetWindowTextA(int handle, byte[] winText, int length);    
}
