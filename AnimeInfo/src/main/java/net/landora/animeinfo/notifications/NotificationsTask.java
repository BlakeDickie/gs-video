/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.notifications;

import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import net.landora.animeinfo.anidb.AniDB;
import net.landora.animeinfo.data.AnimeDBA;
import net.landora.animeinfo.data.AnimeMessage;
import net.landora.animeinfo.data.AnimeNotification;
import net.landora.animeinfo.data.MessageType;
import net.landora.animeinfo.mylistreader.ListReader;
import net.landora.gsuiutils.NBTask;
import net.landora.gsuiutils.Touple;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.Exceptions;

/**
 *
 * @author bdickie
 */
public class NotificationsTask extends NBTask<Boolean, Object> {

    public NotificationsTask() {
        super("Checking Notifications");
    }

    private Pattern MYLIST_PATTERN = Pattern.compile(
            "Direct Download: \\[url=(http://export.anidb.net/export/[0-9-]+.tgz)\\]",
            Pattern.CASE_INSENSITIVE);
    
    private void checkMessage(AnimeMessage msg) {
        if (msg.getTitle().equalsIgnoreCase("[EXPORT] Mylist Export is ready for download") && msg.getType() == MessageType.System) {
            Matcher m = MYLIST_PATTERN.matcher(msg.getBody());
            if (m.find()) {
                progress("Downloading MyList Export");
                try {
                    String url = m.group(1);
                    
                    if (new ListReader().download(new URL(url))) {
                        msg.setRemovedDate(Calendar.getInstance());
                        AnimeDBA.saveAnimeMessage(msg);
                    }
                } catch (Throwable ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        
    }
    
    @Override
    protected Boolean doInBackground() throws Throwable {
        start();
        
        progress("Checking for New Notifications");
        
        if (AniDB.hasNotifications()) {
            
            progress("Downloading New Notifications");
            
            List<Touple<String, String>> notifications = AniDB.getNotifications();
            for (Touple<String, String> notification : notifications) {
                String type = notification.getFirst();
                String id = notification.getSecond();
                
                if (type.equalsIgnoreCase("N")) {
                    AniDB.getNotification(id);
                } else if (type.equalsIgnoreCase("M")) {
                    AniDB.getMessage(id);
                }
            }
            
            
        }
        
        AnimeDBA.markDownloadedEpisodesAsCompletedNotifications();
        
        List<AnimeMessage> outstandAnimeMessages = AnimeDBA.getOutstandAnimeMessages();
        List<AnimeNotification> outstandAnimeNotifications = AnimeDBA.getOutstandAnimeNotifications();
        
        Iterator<AnimeMessage> i = outstandAnimeMessages.iterator();
        while(i.hasNext()) {
            AnimeMessage msg = i.next();
            
            checkMessage(msg);
            
            if (msg.getRemovedDate() != null)
                i.remove();
        }
        
        return !outstandAnimeMessages.isEmpty() || !outstandAnimeNotifications.isEmpty();
    }

    @Override
    protected void success(Boolean result) {
        if (result) {
            NotificationDisplayer.getDefault().notify("New Notifications", 
                    new ImageIcon(getClass().getResource("/net/landora/gsuiutils/dialog-information.png"))
                    , "Unviewed notifications are avaliable.", null, NotificationDisplayer.Priority.LOW);
        }
    }
    
}
