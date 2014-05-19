/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.video.ui;

import org.apache.commons.collections.map.MultiValueMap;
import javax.swing.JPanel;

/**
 *
 * @author bdickie
 */
public class InfoPanel extends JPanel {

    public InfoPanel() {
    }

    protected String title;
    public static final String PROP_TITLE = "title";

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        String oldTitle = this.title;
        this.title = title;
        firePropertyChange( PROP_TITLE, oldTitle, title );
    }

    public boolean supportsContext( MultiValueMap context ) {
        return false;
    }

    public void loadContext( MultiValueMap context ) {

    }

    public void clearCurrentContext() {

    }
}
