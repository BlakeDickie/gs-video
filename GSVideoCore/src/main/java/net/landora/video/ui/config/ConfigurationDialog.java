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


/*
 * ConfigurationDialog.java
 *
 * Created on Dec 31, 2011, 8:24:04 PM
 */
package net.landora.video.ui.config;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import net.landora.video.ui.ConfigurationPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public final class ConfigurationDialog extends javax.swing.JDialog implements ConfigurationChangedListener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<ConfigurationPanel> panels;
    
    /** Creates new form ConfigurationDialog */
    public ConfigurationDialog(java.awt.Window parent, List<Class<? extends ConfigurationPanel>> classes) {
        super(parent, DEFAULT_MODALITY_TYPE);
        initComponents();
        
        panels = new ArrayList<ConfigurationPanel>(classes.size());
        for (Class<? extends ConfigurationPanel> clazz : classes) {
            try {
                ConfigurationPanel panel = clazz.newInstance();
                panels.add(panel);
            } catch (Exception ex) {
                log.error("Error creating panel: " + clazz.getName(), ex);
                continue;
            }
        }
        
        // TODO: Add sorting to panels.
        
        for(ConfigurationPanel panel: panels) {
            panel.load();
            
            panel.addConfigurationChangedListener(this);
            
            tabPanels.addTab(panel.getPanelName(), panel);
            
            
        }
        
        setHasChanges(false);
        
        pack();
    }

    protected boolean hasChanges;
    public static final String PROP_HASCHANGES = "hasChanges";

    /**
     * Get the value of hasChanges
     *
     * @return the value of hasChanges
     */
    public boolean isHasChanges() {
        return hasChanges;
    }
    
    /**
     * Set the value of hasChanges
     *
     * @param hasChanges new value of hasChanges
     */
    public void setHasChanges(boolean hasChanges) {
        boolean oldHasChanges = this.hasChanges;
        this.hasChanges = hasChanges;
        firePropertyChange(PROP_HASCHANGES, oldHasChanges, hasChanges);
    }

    public void configurationPanelChanged(ConfigurationChangedEvent e) {
        setHasChanges(true);
    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPanel1 = new javax.swing.JPanel();
        btnApply = new javax.swing.JButton();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        tabPanels = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Configuration"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setName("jPanel1"); // NOI18N

        btnApply.setText("Apply");
        btnApply.setName("btnApply"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${hasChanges}"), btnApply, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        btnApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplyActionPerformed(evt);
            }
        });
        jPanel1.add(btnApply);

        btnOK.setText("OK");
        btnOK.setName("btnOK"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${hasChanges}"), btnOK, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });
        jPanel1.add(btnOK);

        btnCancel.setText("Cancel");
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancel);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        tabPanels.setName("tabPanels"); // NOI18N
        getContentPane().add(tabPanels, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (!isHasChanges())
            cancel();
        else {
            int reply = JOptionPane.showConfirmDialog(this, "Save outstanding changes before closing?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
            if (reply == JOptionPane.YES_OPTION)
                saveAndClose();
            else if (reply == JOptionPane.NO_OPTION)
                cancel();
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApplyActionPerformed
        save();
    }//GEN-LAST:event_btnApplyActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        cancel();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        saveAndClose();
    }//GEN-LAST:event_btnOKActionPerformed
    
    public void cancel() {
        setVisible(false);
    }
    
    public void saveAndClose() {
        if (save())
            setVisible(false);
    }

    public boolean save() {
        for(ConfigurationPanel panel: panels) {
            if (!panel.valid()) {
                JOptionPane.showMessageDialog(this, "There are issues in " + panel.getPanelName() + " that must be fixed before saving.");
                return false;
            }
        }
        
        for(ConfigurationPanel panel: panels) {
            panel.store();
        }
        
        setHasChanges(false);
        
        return true;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApply;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane tabPanels;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
