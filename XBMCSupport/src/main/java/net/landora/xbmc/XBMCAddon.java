/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.xbmc;

import net.landora.video.addons.AbstractAddon;
import net.landora.video.info.file.FileInfoManager;
import net.landora.video.info.file.FileManager;
import net.landora.video.manager.ManagerAddon;
import net.landora.video.preferences.PreferencesAddon;
import net.landora.xbmc.nfo.NFOManager;

/**
 *
 * @author bdickie
 */
public class XBMCAddon extends AbstractAddon {

    public static final String ID = XBMCAddon.class.getName();

    public XBMCAddon() {
        super(ID, "XBMC Support", PreferencesAddon.ID, ManagerAddon.ID);
    }

    private NFOManager nfoManager;

    @Override
    public void start() {
        nfoManager = new NFOManager();
        FileManager.getInstance().addCheckFileExtension(nfoManager);
        FileInfoManager.getInstance().addFileInfoChangedListener(nfoManager);
    }

}
