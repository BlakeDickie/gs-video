/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.actions;

import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URI;
import net.landora.animeinfo.data.AnimeStub;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "AnimeStub",
id = "net.landora.animeinfo.actions.ViewAnimePage")
@ActionRegistration(displayName = "#CTL_ViewAnimePage")
@ActionReferences({})
@Messages("CTL_ViewAnimePage=View Anime Page")
public final class ViewAnimePage implements ActionListener {

    private final AnimeStub context;

    public ViewAnimePage(AnimeStub context) {
        this.context = context;
    }

    public void actionPerformed(ActionEvent ev) {
        try {
            Desktop.getDesktop().browse(new URI("http://anidb.net/perl-bin/animedb.pl?show=anime&aid=" + context.getAnimeId()));
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
