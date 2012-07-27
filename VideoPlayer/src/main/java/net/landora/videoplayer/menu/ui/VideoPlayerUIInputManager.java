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

import java.awt.Component;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import net.landora.video.utils.UIUtils;
import net.landora.videoplayer.PlayerFrame;

/**
 *
 * @author bdickie
 */
public class VideoPlayerUIInputManager {

    private PlayerFrame frame;
    private PlayerMenu menu;
    
    public VideoPlayerUIInputManager(PlayerFrame frame) {
        this.frame = frame;
        menu = frame.getMenuComponent();
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyboardMonitor());
        
        keyBoardEventMapping = new HashMap<Integer, UIInputEvent>();
        for(UIInputEvent evt: UIInputEvent.values()) {
            for(int code: evt.getDefaultKeyCodes())
                keyBoardEventMapping.put(code, evt);
        }
    }
    
    public void fireUIEvent(final UIInputEvent event) {
        // Call is wrapped to ensure it is always called in the AWT Event thread.
        Runnable runnable = new Runnable() {
            public void run() {
                Component comp = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
                while (comp != null && comp != frame && comp != menu) {
                    if (comp instanceof UIEventHandler) {
                        if (((UIEventHandler) comp).handleEvent(event)) {
                            return;
                        }
                    }
                    comp = comp.getParent();
                }
                
                if (menu instanceof UIEventHandler) {
                    if (((UIEventHandler) menu).handleEvent(event)) {
                        return;
                    }
                }
                
                if (frame instanceof UIEventHandler) {
                    if (((UIEventHandler) frame).handleEvent(event)) {
                        return;
                    }
                }
            }
        };
        UIUtils.invokeInSwingThread(runnable);
    }
    
    
    private Map<Integer,UIInputEvent> keyBoardEventMapping;
    
    private boolean keyEvent(KeyEvent e) {
        int code = e.getKeyCode();
        UIInputEvent event = keyBoardEventMapping.get(code);
        if (event == null)
            return false;
        
        Window focusWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
        if (focusWindow != frame)
            return false;
        
        if (e.getID() == KeyEvent.KEY_PRESSED)
            fireUIEvent(event);
        
        e.consume();
        return true;
    }
    
    
    
    
    private class KeyboardMonitor implements KeyEventDispatcher {

        public boolean dispatchKeyEvent(KeyEvent e) {
            return keyEvent(e);
        }
        
    }
}
