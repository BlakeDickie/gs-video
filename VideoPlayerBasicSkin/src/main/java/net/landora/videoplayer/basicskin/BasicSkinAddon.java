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
package net.landora.videoplayer.basicskin;

import net.landora.video.addons.AbstractAddon;
import net.landora.videoplayer.VideoPlayerAddon;

/**
 *
 * @author bdickie
 */
public class BasicSkinAddon extends AbstractAddon {

    public static final String ID = "net.landora.videoplayer.basicskin.BasicSkinAddon";
    
    public BasicSkinAddon() {
        super(ID, "Basic Skin", VideoPlayerAddon.ID);
    }

    @Override
    public void start() {
        VideoPlayerAddon.getInstance().registerSkin(new BasicSkin());
    }
    
    
    
}
