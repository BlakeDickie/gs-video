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

package net.landora.videoplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JFrame;
import net.landora.video.VideoManagerApp;
import net.landora.video.addons.AbstractAddon;
import net.landora.video.addons.AddonManager;
import net.landora.video.preferences.PreferencesAddon;
import net.landora.video.ui.UIAddon;
import net.landora.videoplayer.files.FilesMenu;
import net.landora.videoplayer.menu.Menu;
import net.landora.videoplayer.menu.MenuItem;
import net.landora.videoplayer.menu.MenuLink;
import net.landora.videoplayer.menu.ui.MenuSkin;
import net.landora.videoplayer.menu.ui.VideoPlayerUIInputManager;

/**
 *
 * @author bdickie
 */
public class VideoPlayerAddon extends AbstractAddon {
    public static final String ID = "net.landora.videoplayer.VideoPlayerAddon";

    public VideoPlayerAddon() {
        super(ID, "Video Player", UIAddon.ID, PreferencesAddon.ID);
    }
    
    public static VideoPlayerAddon getInstance() {
        return AddonManager.getInstance().getAddonInstance(VideoPlayerAddon.class);
    }

    @Override
    public void load() {
        VideoManagerApp.getInstance().addProfile(new VideoPlayerProfile());
    }

    private PlayerFrame frame;
    
    @Override
    public void start() {
        if (VideoManagerApp.getInstance().getProfile().isVideo()) {
            frame = new PlayerFrame();
            
            inputManager = new VideoPlayerUIInputManager(frame);
            
            registerTopLevelMenu(new MenuLink(MenuItem.Icon.Folder, "Local File", new FilesMenu()), TopLevelMenuType.Primary);
        }
    }
    
    
    @Override
    public void ready() {
        if (frame != null) {
            frame.getMenuComponent().setSkin(skins.get(0));
            frame.getMenuComponent().getSkin().initSkin();
            frame.prepareToDisplay();
            
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
//            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
        }
    }

    @Override
    public void stop() {
        if (frame != null)
            frame.setVisible(false);
    }
    
    private TopLevelMenu topLevelMenu = new TopLevelMenu();
    private VideoPlayerUIInputManager inputManager;

    public Menu getTopLevelMenu() {
        return topLevelMenu;
    }

    public VideoPlayerUIInputManager getInputManager() {
        return inputManager;
    }
    
    private MenuLink quitLink = new MenuLink("Quit", new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    });
    
    private List<MenuLink> primaryMenuItems = new ArrayList<MenuLink>();
    private List<MenuLink> secondaryMenuItems = new ArrayList<MenuLink>();
    private List<MenuLink> extraMenuItems = new ArrayList<MenuLink>();
    
    public void registerTopLevelMenu(MenuLink menu, TopLevelMenuType type) {
        switch(type) {
            case Primary:
                primaryMenuItems.add(menu);
                break;
            case Secondary:
                secondaryMenuItems.add(menu);
                break;
            case Extra:
                extraMenuItems.add(menu);
                break;
            default:
                throw new AssertionError(type.name());
        }
        
    }
    
    private List<MenuSkin> skins = new ArrayList<MenuSkin>();
    
    public void registerSkin(MenuSkin skin) {
        skins.add(skin);
    }
    
    public static enum TopLevelMenuType {
        Primary,
        Secondary,
        Extra;
    }
    
    private class TopLevelMenu extends Menu {

        private List<MenuLink> items = Collections.EMPTY_LIST;
        
        @Override
        public void refresh() {
            items = new ArrayList<MenuLink>(primaryMenuItems.size() + secondaryMenuItems.size() + extraMenuItems.size());
            MenuLinkComparator cmp = new MenuLinkComparator();
            Collections.sort(primaryMenuItems, cmp);
            Collections.sort(secondaryMenuItems, cmp);
            Collections.sort(extraMenuItems, cmp);
            
            items.addAll(primaryMenuItems);
            items.addAll(secondaryMenuItems);
            items.addAll(extraMenuItems);
            items.add(quitLink);
        }

        @Override
        public List<? extends MenuItem> getMenuItems() {
            return items;
        }
        
    }
    
    private class MenuLinkComparator implements Comparator<MenuLink> {

        public int compare(MenuLink o1, MenuLink o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
        
    }
}
