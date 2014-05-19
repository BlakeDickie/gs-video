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

import net.landora.video.VideoManagerApp;
import net.landora.video.addons.AbstractAddon;
import net.landora.video.addons.AddonManager;
import net.landora.video.filestate.DirectoriesConfigPanel;
import net.landora.video.preferences.PreferencesAddon;
import net.landora.video.ui.config.ConfigurationDialog;
import net.landora.video.utils.UIUtils;
import org.apache.commons.collections.map.MultiValueMap;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author bdickie
 */
public final class UIAddon extends AbstractAddon {

    public static final String ID = "net.landora.video.ui.UIAddon";

    public UIAddon() {
        super( ID, "GUI", PreferencesAddon.ID );

    }

    public static UIAddon getInstance() {
        return AddonManager.getInstance().getAddonInstance( UIAddon.class );
    }

    @Override
    public void start() {

        if ( VideoManagerApp.getInstance().getProfile().isGUIEnabled() ) {
            addConfigurationPanel( DirectoriesConfigPanel.class );
        }

    }
    private List<Class<? extends ConfigurationPanel>> configPanels = new ArrayList<Class<? extends ConfigurationPanel>>();

    public void addConfigurationPanel( Class<? extends ConfigurationPanel> panelClass ) {
        if ( VideoManagerApp.getInstance().getProfile().isGUIEnabled() ) {
            configPanels.add( panelClass );
        }
    }

    public void showConfigurationDialog( Window parent ) {
        if ( VideoManagerApp.getInstance().getProfile().isGUIEnabled() ) {
            ConfigurationDialog dialog = new ConfigurationDialog( parent, configPanels );
            dialog.setVisible( true );
        }
    }

    private List<UIAction<?>> actions = new ArrayList<UIAction<?>>();

    public void addAction( UIAction<?>... action ) {
        actions.addAll( Arrays.asList( action ) );
    }

    public JPopupMenu createPopupMenu( Collection<?> context ) {

        MultiValueMap valuesByClass = UIUtils.createCompleteContextByClass( context );

        List<UIAction<?>> actionsToUse = new ArrayList();
        for ( UIAction<?> action : actions ) {
            Class<?> clazz = action.getRequiredClass();

            Collection<?> col = valuesByClass.getCollection( clazz );
            if ( col == null || col.isEmpty() ) {
                continue;
            }

            if ( action.isMultipuleObjectSupport() || col.size() == 1 ) {
                actionsToUse.add( action );
            }
        }

        if ( actionsToUse.isEmpty() ) {
            return null;
        }

        JPopupMenu menu = new JPopupMenu();

        for ( UIAction<?> action : actionsToUse ) {
            JMenuItem item = new JMenuItem( action.getName() );
            item.addActionListener( new UIActionAction( action, valuesByClass.getCollection( action.getRequiredClass() ) ) );
            menu.add( item );
        }

        return menu;
    }

    private static class UIActionAction<T> implements ActionListener {

        private UIAction<T> action;
        private Collection<T> col;

        public UIActionAction( UIAction<T> action, Collection<T> col ) {
            this.action = action;
            this.col = col;
        }

        public void actionPerformed( ActionEvent e ) {
            action.actionPerformed( e, col instanceof List ? (List<T>) col : new ArrayList<T>( col ) );
        }

    }
}
