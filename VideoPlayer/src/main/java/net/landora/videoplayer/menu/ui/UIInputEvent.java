/**
 *     Copyright (C) 2012 Blake Dickie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.videoplayer.menu.ui;

import java.awt.event.KeyEvent;

/**
 *
 * @author bdickie
 */
public enum UIInputEvent {
    Up(KeyEvent.VK_UP, KeyEvent.VK_KP_UP),
    Down(KeyEvent.VK_DOWN, KeyEvent.VK_KP_DOWN),
    Left(KeyEvent.VK_LEFT, KeyEvent.VK_KP_LEFT),
    Right(KeyEvent.VK_RIGHT, KeyEvent.VK_KP_RIGHT),
    Enter(KeyEvent.VK_ENTER),
    Back(KeyEvent.VK_BACK_SPACE),
    Escape(KeyEvent.VK_ESCAPE),
    PageUp(KeyEvent.VK_PAGE_UP),
    PageDown(KeyEvent.VK_PAGE_DOWN);
    
    private int[] defaultKeyCodes;

    private UIInputEvent(int...defaultKeyCodes) {
        this.defaultKeyCodes = defaultKeyCodes;
    }

    public int[] getDefaultKeyCodes() {
        return defaultKeyCodes;
    }
    
}
