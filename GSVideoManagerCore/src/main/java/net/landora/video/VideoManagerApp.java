/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video;

import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.JFrame;
import net.landora.video.addons.AddonManager;
import net.landora.video.preferences.FileSyncProperties;
import net.landora.video.utils.NamedThreadFactory;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.jdesktop.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class VideoManagerApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(VideoManagerApp.class);
    
    private FileSyncProperties localProperties;
    private ScheduledExecutorService scheduledExecutor;
    
    public static VideoManagerApp getInstance() {
        return getInstance(VideoManagerApp.class);
    }

    public synchronized FileSyncProperties getLocalProperties() {
        if (localProperties == null)
            localProperties = new FileSyncProperties("user.properties");
        return localProperties;
    }
    
    public synchronized ScheduledExecutorService getScheduledExecutor() {
        if (scheduledExecutor == null)
            scheduledExecutor = Executors.newScheduledThreadPool(2, new NamedThreadFactory("Scheduled", true));
        return scheduledExecutor;
    }
    
    private boolean guiMode;
    private boolean daemonMode;
    private boolean systemTray;

    public boolean isGuiMode() {
        return guiMode;
    }

    public boolean isDaemonMode() {
        return daemonMode;
    }

    public boolean isSystemTray() {
        return systemTray;
    }

    
    
    @Override
    protected void startup() {
        
    }
    
    @Override
    protected void initialize(String[] args) {
        Options options = new Options();
        options.addOption("h", "help", false, "Display this help message.");
        options.addOption("d", "daemon", false, "Start in background daemon mode.");
        options.addOption("t", "tray", false, "Show system tray icon when in daemon mode.");
        CommandLineParser parser = new PosixParser();
        
        boolean showHelp = false;
        String message = null;
        
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("h"))
                showHelp = true;
            
            daemonMode = line.hasOption("d");
            guiMode = !daemonMode;
            systemTray = line.hasOption("t");
            
        } catch (ParseException e) {
            message = e.getLocalizedMessage();
            showHelp = true;
        }
        
        if (showHelp) {
            HelpFormatter formatter = new HelpFormatter();
            PrintWriter writer = new PrintWriter(System.err);
            formatter.printHelp(writer, 60,  "gsvideomanager", message, options, 2, 3, null );
            writer.flush();
            
            guiMode = false;
            daemonMode = false;
            return;
        }
        
        
        AddonManager.getInstance().startAddons();
        
    }

    @Override
    protected void ready() {
        
        if (!guiMode && !daemonMode)
            return;
        
        AddonManager.getInstance().readyAddons();
//        Server server = new Server(8080);
//        Context root = new Context(server,"/",Context.SESSIONS);
//        ServletHolder holder = new ServletHolder(HessianServlet.class);
//        holder.setInitParameter("home-api", TestAPI.class.getName());
//        holder.setInitParameter("home-class", TestImpl.class.getName());
//        root.addServlet(holder, "/Hessian");
//        try {
//            server.start();
//            
//        } catch (Exception ex) {
//            java.util.logging.Logger.getLogger(VideoManagerApp.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    
    
    
}
