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

package net.landora.video.filestate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.landora.video.filestate.data.LocalPathManager;
import net.landora.video.filestate.data.SharedDirectory;
import net.landora.video.filestate.data.SharedDirectoryDBA;
import net.landora.video.ui.ConfigurationPanel;

public final class DirectoriesConfigPanel extends ConfigurationPanel {

    private DefaultListModel model;
    private Map<SharedDirectory,File> localPath;
    private Map<String,SharedDirectory> directories;
    private LocalPathManager pathManager = LocalPathManager.getInstance();

    public DirectoriesConfigPanel() {
        initComponents();
        
        
        addEditWatcher(txtDirName);
        addEditWatcher(txtDefaultPath);
    }
    
    private void addEditWatcher(final JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                fieldChanged(field);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fieldChanged(field);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fieldChanged(field);
            }
        });
    }
    
    private boolean loadingDir;
    
    private void fieldChanged(JTextField field) {
        if (field.isEnabled() && !loadingDir)
            changed();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstDirectories = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtDirName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDefaultPath = new javax.swing.JTextField();
        lblLocalPath = new javax.swing.JLabel();
        txtLocalPath = new javax.swing.JTextField();
        btnFindPath = new javax.swing.JButton();
        chkRenameNewFiles = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        btnAddDirectory = new javax.swing.JButton();

        setPanelName("Shared Directories");
        setLayout(new java.awt.BorderLayout());

        lstDirectories.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstDirectories.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstDirectoriesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstDirectories);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Directory Name");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtDirName, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), jLabel2, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        jPanel1.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtDirName, gridBagConstraints);

        jLabel3.setText("Default Path");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtDirName, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), jLabel3, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        txtDefaultPath.setColumns(30);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, txtDirName, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), txtDefaultPath, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel1.add(txtDefaultPath, gridBagConstraints);

        lblLocalPath.setText("Local Path");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, txtDirName, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), lblLocalPath, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        jPanel1.add(lblLocalPath, gridBagConstraints);

        txtLocalPath.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, txtDirName, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), txtLocalPath, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel1.add(txtLocalPath, gridBagConstraints);

        btnFindPath.setText("Find");
        btnFindPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindPathActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        jPanel1.add(btnFindPath, gridBagConstraints);

        chkRenameNewFiles.setText("Rename New Files");
        chkRenameNewFiles.setBorder(null);
        chkRenameNewFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRenameNewFilesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel1.add(chkRenameNewFiles, gridBagConstraints);

        jSplitPane1.setRightComponent(jPanel1);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);

        btnAddDirectory.setText("Add");
        btnAddDirectory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDirectoryActionPerformed(evt);
            }
        });
        jPanel2.add(btnAddDirectory);

        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void lstDirectoriesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstDirectoriesValueChanged
        if (!evt.getValueIsAdjusting())
            setCurrentDirectory((SharedDirectory)lstDirectories.getSelectedValue());
    }//GEN-LAST:event_lstDirectoriesValueChanged

    private void btnFindPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindPathActionPerformed
        
        JFileChooser chooser = new JFileChooser();
        File path = localPath.get(currentDirectory);
        if (path != null)
            chooser.setCurrentDirectory(path);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        
        path = chooser.getSelectedFile();
        
        if (!DirectoryUUIDChecker.validateUUID(path, currentDirectory.getUuid())) {
            JOptionPane.showMessageDialog(this, "The selected directory does not have the folder id.");
        } else {
            localPath.put(currentDirectory, path);
            txtLocalPath.setText(path.getPath());
            
            changed();
        }
        
    }//GEN-LAST:event_btnFindPathActionPerformed

    private void btnAddDirectoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDirectoryActionPerformed
        
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        
        File path = chooser.getSelectedFile();
        
        for(SharedDirectory directory: directories.values()) {
            if (DirectoryUUIDChecker.validateUUID(path, directory.getUuid())) {
                JOptionPane.showMessageDialog(this, "The selected directory is already a shared directory.");
                return;
            }
        }
        SharedDirectory directory = new SharedDirectory();
        directory.setUuid(UUID.randomUUID().toString());
        directory.setName(path.getName());
        directory.setDefaultPath(path.getAbsolutePath());
        directories.put(directory.getUuid(), directory);
        localPath.put(directory, path);
        model.addElement(directory);
        
        lstDirectories.setSelectedValue(directory, true);
        
        changed();
    }//GEN-LAST:event_btnAddDirectoryActionPerformed

    private void chkRenameNewFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRenameNewFilesActionPerformed
        if (chkRenameNewFiles.isEnabled() && !loadingDir)
            changed();
    }//GEN-LAST:event_chkRenameNewFilesActionPerformed

    private SharedDirectory currentDirectory;
    
    @Override
    public void load() {
        model = new DefaultListModel();
        localPath = new HashMap<SharedDirectory, File>();
        directories = new HashMap<String, SharedDirectory>();
        
        for(SharedDirectory dir: SharedDirectoryDBA.getSharedDirectories()) {
            model.addElement(dir);
            directories.put(dir.getUuid(), dir);
            File currentPath = pathManager.getLocalPath(dir);
            if (currentPath != null)
                localPath.put(dir, currentPath);
        }
        
        
        
        lstDirectories.setModel(model);
        
        if (model.getSize() > 0)
            lstDirectories.setSelectedIndex(0);
        else
            setCurrentDirectory(null);
    }
    
    private void setCurrentDirectory(SharedDirectory dir) {
        boolean oldLoading = loadingDir;
        try {
            loadingDir = true;
            currentDirectory = dir;
            
            if (currentDirectory == null) {
                txtDirName.setEnabled(false);
                btnFindPath.setEnabled(false);
                
                txtDefaultPath.setText("");
                txtDirName.setText("");
                chkRenameNewFiles.setSelected(false);
            } else {
                txtDirName.setEnabled(true);
                btnFindPath.setEnabled(dir.getDirectoryId() > 0);
                
                txtDefaultPath.setText(dir.getDefaultPath());
                txtDirName.setText(dir.getName());
                chkRenameNewFiles.setSelected(dir.isRenameNewFiles());
                
                File path = localPath.get(dir);
                if (path == null)
                    txtLocalPath.setText("--Not Assigned--");
                else
                    txtLocalPath.setText(path.getPath());
            }
        } finally {
            loadingDir = oldLoading;
        }
    }

    @Override
    public void store() {
        for(int i = 0; i < model.getSize(); i++) {
            SharedDirectory dir = (SharedDirectory) model.getElementAt(i);
            boolean newDir = dir.getDirectoryId() < 1;
            SharedDirectoryDBA.saveSharedDirectory(dir);
            if (newDir) {
                DirectoryUUIDChecker.setUUID(localPath.get(dir), dir.getUuid());
            }
        }
        
        for (Map.Entry<SharedDirectory,File> entry : localPath.entrySet()) {
            pathManager.setLocalPath(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public boolean valid() {
        if (currentDirectory != null) {
            currentDirectory.setName(txtDirName.getText());
            currentDirectory.setDefaultPath(txtDefaultPath.getText());
            currentDirectory.setRenameNewFiles(chkRenameNewFiles.isSelected());
            
            lstDirectories.repaint();
        }
        
        for(int i = 0; i < model.getSize(); i++) {
            SharedDirectory dir = (SharedDirectory) model.getElementAt(i);
            if (dir.getDefaultPath() == null || dir.getName() == null || dir.getDefaultPath().trim().isEmpty() || dir.getName().trim().isEmpty())
                return false;
        }
        
        
        return true;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddDirectory;
    private javax.swing.JButton btnFindPath;
    private javax.swing.JCheckBox chkRenameNewFiles;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblLocalPath;
    private javax.swing.JList lstDirectories;
    private javax.swing.JTextField txtDefaultPath;
    private javax.swing.JTextField txtDirName;
    private javax.swing.JTextField txtLocalPath;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
