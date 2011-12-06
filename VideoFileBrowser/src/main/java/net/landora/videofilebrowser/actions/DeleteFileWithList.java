/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videofilebrowser.actions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import net.landora.videofilebrowser.VideoFile;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "DeleteFile",
id = "net.landora.videofilebrowser.actions.DeleteFileWithList")
@ActionRegistration(displayName = "#CTL_DeleteFileWithList")
@ActionReferences({})
@Messages("CTL_DeleteFileWithList=Delete File & List Entry")
public final class DeleteFileWithList implements ActionListener {

    private final List<VideoFile> context;

    public DeleteFileWithList(List<VideoFile> context) {
        this.context = context;
    }

    public void actionPerformed(ActionEvent ev) {
        for (VideoFile videoFile : context) {
            
            // TODO use videoFile
        }
    }
}
