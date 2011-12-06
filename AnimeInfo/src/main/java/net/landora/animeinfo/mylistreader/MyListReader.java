/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.mylistreader;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "Tools",
id = "net.landora.animemylistreader.MyListReader")
@ActionRegistration(displayName = "#CTL_MyListReader")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 0)
})
@Messages("CTL_MyListReader=Import MyList")
public final class MyListReader implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;
                
                String filename = f.getName();
                return filename.matches("(?i)\\d+-\\d+-\\d+.tgz");
            }

            @Override
            public String getDescription() {
                return "MyList Exports";
            }
        });
        int reply = chooser.showOpenDialog(KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow());
        if (reply != JFileChooser.APPROVE_OPTION)
            return;
        
        File file = chooser.getSelectedFile();
        ListReaderTask task = new ListReaderTask(file);
        task.startTask();
    }
}
