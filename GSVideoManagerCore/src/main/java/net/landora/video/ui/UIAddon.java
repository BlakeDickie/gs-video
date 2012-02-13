/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.ui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import net.landora.video.VideoManagerApp;
import net.landora.video.addons.AbstractAddon;
import net.landora.video.addons.AddonManager;
import net.landora.video.filestate.DirectoriesConfigPanel;
import net.landora.video.preferences.PreferencesAddon;
import net.landora.video.ui.config.ConfigurationDialog;
import net.landora.video.utils.UIUtils;
import org.apache.commons.collections.map.MultiValueMap;

/**
 *
 * @author bdickie
 */
public final class UIAddon extends AbstractAddon {
    
    public static final String ID = "net.landora.video.ui.UIAddon";
    
    public UIAddon() {
        super(ID, "GUI", PreferencesAddon.ID);
        
    }
    
    public static UIAddon getInstance() {
        return AddonManager.getInstance().getAddonInstance(UIAddon.class);
    }
    
    private ManagerFrame frame;

    @Override
    public void load() {
        VideoManagerApp.getInstance().addProfile(new ManagerProfile());
    }

    
    
    @Override
    public void start() {
        
        if (VideoManagerApp.getInstance().getProfile().isManager()) {
            frame = new ManagerFrame();
        }
        
        if (VideoManagerApp.getInstance().getProfile().isGUIEnabled()) {
            addConfigurationPanel(DirectoriesConfigPanel.class);
        }
        
        
    }

    @Override
    public void ready() {
        if (frame != null) {
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        }
    }

    @Override
    public void stop() {
        if (frame != null)
            frame.setVisible(false);
    }
    
    public void addContentPanel(ContentPanel panel) {
        if (frame != null)
            frame.addContentPanel(panel);
    }

    public void addInfoPanel(InfoPanel panel) {
        if (frame != null)
            frame.addInfoPanel(panel);
    }
    
    private List<Class<? extends ConfigurationPanel>> configPanels = new ArrayList<Class<? extends ConfigurationPanel>>();
    
    public void addConfigurationPanel(Class<? extends ConfigurationPanel> panelClass) {
        if (VideoManagerApp.getInstance().getProfile().isGUIEnabled()) {
            configPanels.add(panelClass);
        }
    }
    
    public void showConfigurationDialog(Window parent) {
        if (VideoManagerApp.getInstance().getProfile().isGUIEnabled()) {
            ConfigurationDialog dialog = new ConfigurationDialog(parent, configPanels);
            dialog.setVisible(true);
        }
    }
    
    private List<UIAction<?>> actions = new ArrayList<UIAction<?>>();
    
    public void addAction(UIAction<?>...action) {
        actions.addAll(Arrays.asList(action));
    }
    
    
    public JPopupMenu createPopupMenu(Collection<?> context) {
        
        MultiValueMap valuesByClass = UIUtils.createCompleteContextByClass(context);
        
        List<UIAction<?>> actionsToUse = new ArrayList();
        for(UIAction<?> action: actions) {
            Class<?> clazz = action.getRequiredClass();
            
            Collection<?> col = valuesByClass.getCollection(clazz);
            if (col == null || col.isEmpty())
                continue;
            
            if (action.isMultipuleObjectSupport() || col.size() == 1)
                actionsToUse.add(action);
        }
        
        if (actionsToUse.isEmpty())
            return null;
        
        JPopupMenu menu = new JPopupMenu();
        
        for(UIAction<?> action: actionsToUse) {
            JMenuItem item = new JMenuItem(action.getName());
            item.addActionListener(new UIActionAction(action, valuesByClass.getCollection(action.getRequiredClass())));
            menu.add(item);
        }
        
        return menu;
    }
    
    private static class UIActionAction<T> implements ActionListener {

        private UIAction<T> action;
        private Collection<T> col;

        public UIActionAction(UIAction<T> action, Collection<T> col) {
            this.action = action;
            this.col = col;
        }
        
        
        
        public void actionPerformed(ActionEvent e) {
            action.actionPerformed(e, col instanceof List ? (List<T>)col : new ArrayList<T>(col));
        }
        
    }
}
