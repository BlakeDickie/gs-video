/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.actions;

import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URI;
import net.landora.animeinfo.data.AnimeGroup;
import net.landora.animeinfo.data.AnimeManager;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "AnimeGroup",
id = "net.landora.animeinfo.actions.ViewAnimeGroupPage")
@ActionRegistration(displayName = "#CTL_ViewAnimeGroupPage")
@ActionReferences({})
@Messages("CTL_ViewAnimeGroupPage=View Group Site")
public final class ViewAnimeGroupPage implements ActionListener {

    private final AnimeGroup context;

    public ViewAnimeGroupPage(AnimeGroup context) {
        this.context = context;
    }

    public void actionPerformed(ActionEvent ev) {
        try {
            AnimeManager.getInstance().ensureGroupIsFullyLoaded(context);
            if (context.getUrl() == null || context.getUrl().equals(""))
                Desktop.getDesktop().browse(new URI("http://anidb.net/perl-bin/animedb.pl?show=group&gid=" + context.getGroupId()));
            else
                Desktop.getDesktop().browse(new URI(context.getUrl()));
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
