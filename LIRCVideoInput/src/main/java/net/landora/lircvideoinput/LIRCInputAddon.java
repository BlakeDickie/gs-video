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
package net.landora.lircvideoinput;

import gsilva.lirc.lircclient.LircClient;
import gsilva.lirc.lircclient.LircClientEvent;
import gsilva.lirc.lircclient.LircClientListener;
import net.landora.video.VideoManagerApp;
import net.landora.video.addons.AbstractAddon;
import net.landora.videoplayer.VideoPlayerAddon;
import net.landora.videoplayer.menu.ui.UIInputEvent;

/**
 *
 * @author bdickie
 */
public class LIRCInputAddon extends AbstractAddon implements LircClientListener {

    public static final String ID = "net.landora.lircvideoinput.LIRCInputAddon";

    public LIRCInputAddon() {
        super(ID, "LIRC Input", VideoPlayerAddon.ID);
    }

    private LircClient client;

    @Override
    public void start() {
        if (VideoManagerApp.getInstance().getProfile().isVideo()) {
            client = new LircClient("gsvideo");
            client.addLircClientListener(this);
            client.start();
        }
    }

    @Override
    public void stop() {
        if (client != null) {
            client.stop();
        }
    }

    @Override
    public void lircCommandRecieved(LircClientEvent e) {
        String cmd = e.getLircCommand();
        log.trace("LIRC Event Received: " + cmd);

        for (UIInputEvent event : UIInputEvent.values()) {
            if (event.name().equalsIgnoreCase(cmd)) {
                VideoPlayerAddon.getInstance().getInputManager().fireUIEvent(event);
                return;
            }
        }

        log.warn("Unknown input event requested by LIRC: " + cmd);
    }

}
