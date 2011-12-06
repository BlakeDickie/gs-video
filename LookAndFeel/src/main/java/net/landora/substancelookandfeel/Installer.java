/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.substancelookandfeel;

import com.sun.java.swing.plaf.gtk.GTKLookAndFeel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;
import org.openide.util.Utilities;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        if (Utilities.isMac() || Utilities.isWindows())
            return;
        
        final Class<? extends LookAndFeel> lookAndFeelClass;
        
        String lookAndFeelType = LookAndFeelPreferences.UnixLookAndFeelType.getString();
        if (lookAndFeelType.equalsIgnoreCase("GTK"))
            lookAndFeelClass = GTKLookAndFeel.class;
        else if (lookAndFeelType.equalsIgnoreCase("Substance"))
            lookAndFeelClass = null; //SubstanceBusinessBlackSteelLookAndFeel.class;
        else
            lookAndFeelClass = null;
        
        if (lookAndFeelClass != null) {
            Runnable runnable = new Runnable() {

                public void run() {
                    try {
                        UIManager.setLookAndFeel(lookAndFeelClass.getName());
                        JFrame.setDefaultLookAndFeelDecorated(LookAndFeelPreferences.DecorateFrames.getBoolean());
                        JDialog.setDefaultLookAndFeelDecorated(LookAndFeelPreferences.DecorateFrames.getBoolean());
                    } catch (Exception ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
    }
}
