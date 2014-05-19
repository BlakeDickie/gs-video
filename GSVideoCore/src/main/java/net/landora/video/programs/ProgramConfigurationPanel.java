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
package net.landora.video.programs;

import net.landora.video.ui.ConfigurationPanel;
import net.landora.video.utils.ConfigurableTableModel;
import net.landora.video.utils.OSUtils;

/**
 *
 * @author bdickie
 */
public class ProgramConfigurationPanel extends ConfigurationPanel {

    private ConfigurableTableModel<Program> tableModel;

    /**
     * Creates new form ProgramConfigurationPanel
     */
    public ProgramConfigurationPanel() {
        initComponents();

        tableModel = new ConfigurableTableModel<Program>( "Program", "Path" );
        tableModel.setColumnEditable( 1, true );
        tableModel.setColumnClass( 1, String.class );

        for ( Program program : ProgramsAddon.getInstance().getAllPrograms() ) {
            if ( ( program.getDefaultUnixPath() == null && OSUtils.isUnix() )
                 || ( program.getDefaultWindowsPath() == null && OSUtils.isWindows() ) ) {
                continue;
            }

            Object[] row = new Object[2];
            row[0] = program.getName();
            row[1] = ProgramsAddon.getInstance().getConfiguredPath( program );

            tableModel.addRow( row, program );
        }

        tblPaths.setModel( tableModel );
    }

    @Override
    public void load() {

    }

    @Override
    public void store() {

    }

    @Override
    public boolean valid() {

        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPaths = new javax.swing.JTable();

        setPanelName("Program Paths");

        jLabel1.setText("Enter the Paths for the Following Programs:");

        jScrollPane1.setViewportView(tblPaths);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
                .addGap(13, 13, 13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPaths;
    // End of variables declaration//GEN-END:variables
}
