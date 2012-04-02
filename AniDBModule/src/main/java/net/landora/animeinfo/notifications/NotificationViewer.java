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
 * NotificationViewer.java
 *
 * Created on Jan 8, 2012, 7:15:38 PM
 */
package net.landora.animeinfo.notifications;

import java.util.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import net.landora.animeinfo.data.AnimeDBA;
import net.landora.animeinfo.data.AnimeEpisode;
import net.landora.animeinfo.data.AnimeNotification;
import net.landora.animeinfo.data.AnimeStub;
import net.landora.video.VideoManagerApp;
import net.landora.video.tasks.TaskCompletedEvent;
import net.landora.video.manager.ContentPanel;
import net.landora.video.utils.BusReciever;
import net.landora.video.utils.UIUtils;
import org.apache.commons.collections.map.MultiValueMap;

/**
 *
 * @author bdickie
 */
public class NotificationViewer extends ContentPanel {

    /** Creates new form NotificationViewer */
    public NotificationViewer() {
        initComponents();
        
        setTitle("Anime Notifications");
        
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        
        notificationsNode = new DefaultMutableTreeNode("Notifications");
        root.add(notificationsNode);
        
        treeModel = new DefaultTreeModel(root);
        
        treeItems.setModel(treeModel);
//        LazyTreeLoadingManager.getInstance().setup(treeItems);
        
        VideoManagerApp.getInstance().getEventBus().addHandlersWeak(this);
    }

    @Override
    public void loadView() {
        loadNotifications();
    }
    
    
    @BusReciever
    public void taskCompleted(TaskCompletedEvent event) {
        if (event.isTaskOf(NotificationsTask.class))
            loadNotifications();
    }
    
    
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode notificationsNode;
    
    public void loadNotifications() {
        List<AnimeNotification> notifications = AnimeDBA.getOutstandAnimeNotifications();
        
        Set<AnimeStub> animes = new HashSet<AnimeStub>();
        for(AnimeNotification notification: notifications) {
            animes.add(notification.getFile().getEpisode().getAnime());
        }
        
        List<AnimeStub> sortedAnime = new ArrayList<AnimeStub>(animes);
        Collections.sort(sortedAnime, UIUtils.LEXICAL_SORTER);
        
        notificationsNode.removeAllChildren();
        
        for(AnimeStub anime: sortedAnime) {
            DefaultMutableTreeNode animeNode = new DefaultMutableTreeNode(anime);
            
            MultiValueMap map = new MultiValueMap();
            for(AnimeNotification notificaton: notifications) {
                if (notificaton.getFile().getEpisode().getAnime().equals(anime)) {
                    map.put(notificaton.getFile().getEpisode(), notificaton);
                }
            }
            List<AnimeEpisode> episodes = new ArrayList<AnimeEpisode>(map.keySet());
            Collections.sort(episodes, UIUtils.LEXICAL_SORTER);
            
            for(AnimeEpisode episode: episodes) {
                DefaultMutableTreeNode episodeNode = new DefaultMutableTreeNode(episode);
                List<AnimeNotification> list = (List<AnimeNotification>) map.get(episode);
                Collections.sort(list, UIUtils.LEXICAL_SORTER);
                for(AnimeNotification notification: list) {
                    episodeNode.add(new DefaultMutableTreeNode(notification, false));
                }
                animeNode.add(episodeNode);
            }
            notificationsNode.add(animeNode);
        }
        
        
        treeModel.nodeStructureChanged(notificationsNode);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSplit = new javax.swing.JSplitPane();
        pnlTree = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        treeItems = new javax.swing.JTree();
        pnlDetails = new javax.swing.JPanel();
        pnlNoSelection = new javax.swing.JPanel();
        lblNoSelection = new javax.swing.JLabel();
        pnlNotification = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        pnlSplit.setName("pnlSplit"); // NOI18N

        pnlTree.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlTree.setName("pnlTree"); // NOI18N
        pnlTree.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        treeItems.setName("treeItems"); // NOI18N
        treeItems.setRootVisible(false);
        treeItems.setShowsRootHandles(true);
        treeItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                treeItemsMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                treeItemsMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                treeItemsMouseReleased(evt);
            }
        });
        treeItems.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeItemsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(treeItems);

        pnlTree.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnlSplit.setLeftComponent(pnlTree);

        pnlDetails.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlDetails.setName("pnlDetails"); // NOI18N
        pnlDetails.setLayout(new java.awt.CardLayout());

        pnlNoSelection.setName("pnlNoSelection"); // NOI18N
        pnlNoSelection.setLayout(new java.awt.GridBagLayout());

        lblNoSelection.setText("No Notification Selected"); // NOI18N
        lblNoSelection.setName("lblNoSelection"); // NOI18N
        pnlNoSelection.add(lblNoSelection, new java.awt.GridBagConstraints());

        pnlDetails.add(pnlNoSelection, "no_selection");

        pnlNotification.setName("pnlNotification"); // NOI18N
        pnlNotification.setLayout(new java.awt.GridBagLayout());

        jLabel1.setName("jLabel1"); // NOI18N
        pnlNotification.add(jLabel1, new java.awt.GridBagConstraints());

        jLabel2.setName("jLabel2"); // NOI18N
        pnlNotification.add(jLabel2, new java.awt.GridBagConstraints());

        pnlDetails.add(pnlNotification, "notification");

        pnlSplit.setRightComponent(pnlDetails);

        add(pnlSplit, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void treeItemsValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeItemsValueChanged
        List<Object> items = new ArrayList<Object>();
        for(TreePath path: treeItems.getSelectionPaths()) {
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)(path.getLastPathComponent());
            addChildren(treeNode, items);
        }
        setCurrentContext(items);
    }//GEN-LAST:event_treeItemsValueChanged

    private void treeItemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_treeItemsMouseClicked
        maybePopup(evt);
    }//GEN-LAST:event_treeItemsMouseClicked

    private void treeItemsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_treeItemsMousePressed
        maybePopup(evt);
    }//GEN-LAST:event_treeItemsMousePressed

    private void treeItemsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_treeItemsMouseReleased
        maybePopup(evt);
    }//GEN-LAST:event_treeItemsMouseReleased

    private void addChildren(DefaultMutableTreeNode node, List<Object> items) {
        if (node.getAllowsChildren()) {
            for(int i = 0; i < node.getChildCount(); i++) {
                DefaultMutableTreeNode n = (DefaultMutableTreeNode) node.getChildAt(i);
                addChildren(n, items);
            }
        } else {
            items.add(node.getUserObject());
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNoSelection;
    private javax.swing.JPanel pnlDetails;
    private javax.swing.JPanel pnlNoSelection;
    private javax.swing.JPanel pnlNotification;
    private javax.swing.JSplitPane pnlSplit;
    private javax.swing.JPanel pnlTree;
    private javax.swing.JTree treeItems;
    // End of variables declaration//GEN-END:variables
}
