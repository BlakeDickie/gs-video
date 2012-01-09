/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.anidb;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Properties;
import net.landora.animeinfo.AnimeInfoPreference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class AniDBUDPManager {

    private static final String UDP_ENCODING = "UTF8";
    private static final int UDP_MAX_PACKET_LENGTH = 1400;
    private static final int UDP_API_PROTO_VERSION = 3;
    private static final String UDP_CLIENT_NAME = "gsvideo";
    private static final int UDP_CLIENT_VER = 1;

    private Logger log = LoggerFactory.getLogger(getClass());

    private boolean isShutdown;

    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static AniDBUDPManager instance = new AniDBUDPManager();
    }

    public static AniDBUDPManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>


    private AniDBUDPManager() {
        try {
            properties = new Properties();
            properties.load(getClass().getResourceAsStream("anidb.properties"));

            serverAddress = InetAddress.getByName(properties.getProperty("host"));
            serverPort = Integer.parseInt(properties.getProperty("port"));

            socket = new DatagramSocket(Integer.parseInt(properties.getProperty("localport")));
            socket.setSoTimeout(Integer.parseInt(properties.getProperty("timeout")));

            autoLogoutDelay = Long.parseLong(properties.getProperty("autologoutdelay"));
            messageDelay = Long.parseLong(properties.getProperty("delay"));
            quickInitialCalls = Integer.parseInt(properties.getProperty("quickinitialcalls"));

            Runtime.getRuntime().addShutdownHook(new LogoutHandler());

            new AutoLogoutThread().start();
        } catch (Exception e) {
            log.error("Error loading properties.", e);
        }
    }

    private InetAddress serverAddress;
    private int serverPort;
    private Properties properties;
    
    private DatagramSocket socket;

    private long autoLogoutDelay;
    private long messageDelay;
    private int quickInitialCalls;

    private int initialCalls = 0;

    private long lastMessageSent;
    
    private String sessionId;

    public synchronized String getSessionId() {
        return getSessionId(true);
    }

    public synchronized String getSessionId(boolean autoLogin) {
        if (sessionId == null && autoLogin) {
            AniDBCommand loginCommand = new AniDBCommand("AUTH", false);
            loginCommand.addArgument("user", AnimeInfoPreference.AniDBUsername.getString());
            loginCommand.addArgument("pass", AnimeInfoPreference.AniDBPassword.getString());
            loginCommand.addArgument("protover", UDP_API_PROTO_VERSION);
            loginCommand.addArgument("client", UDP_CLIENT_NAME);
            loginCommand.addArgument("clientver", UDP_CLIENT_VER);
//            loginCommand.addArgument("comp", "1");
            loginCommand.addArgument("enc", UDP_ENCODING);

            AniDBReply reply = sendData(loginCommand);
            if (reply == null)
                throw new AniDBConnectionError("Unable to login to AniDB.");
            
            switch(reply.getReturnCode()) {
                case 200:
                case 201:
                    String message = reply.getReturnMessage();
                    sessionId = message.split("\\s", 2)[0];
                    break;
                default:
                    throw new AniDBConnectionError("Recieved login error: " + reply.getReturnCode() + " " + reply.getReturnMessage());
            }

        }

        return sessionId;
    }

    private synchronized void logout() {
        if (sessionId == null)
            return;

        AniDBCommand logoutCommand = new AniDBCommand("LOGOUT");
        sendData(logoutCommand);
        sessionId = null;
    }


    public synchronized AniDBReply sendData(AniDBCommand call) {
        if (isShutdown)
            throw new AniDBConnectionError("Shutdown is progress.");


        for(int i = 0; i < 2; i++) {
            if (i == 1 && log.isTraceEnabled())
                log.trace("Retrying failed AniDB call.");

            String replyStr = sendData(call.getResult());
            
            if (replyStr == null)
                continue;

            AniDBReply reply = new AniDBReply(replyStr);

            switch(reply.getReturnCode()) {
                case 501:
                case 506:
                    // Need to reauthenticate.
                    
                    sessionId = null;
                    break;

                default:
                    return reply;
            }
            
        }


        return null;
    }

    @SuppressWarnings({"CallToNativeMethodWhileLocked", "SleepWhileHoldingLock"})
    private synchronized String sendData(String message) {
        try {
            if (initialCalls < quickInitialCalls) {
                initialCalls++;
            } else {
                long nextMessageTime = lastMessageSent + messageDelay;

                while(System.currentTimeMillis() < nextMessageTime) {
                    Thread.sleep(Math.max(1, nextMessageTime - System.currentTimeMillis()));
                }
            }


            if (log.isTraceEnabled()) {
                log.trace("Sending AniDB Command:\n" + message);
            }

            byte[] data = message.getBytes(UDP_ENCODING);

            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);

            socket.send(packet);

            lastMessageSent = System.currentTimeMillis();

            packet = new DatagramPacket(new byte[UDP_MAX_PACKET_LENGTH], UDP_MAX_PACKET_LENGTH);

            socket.receive(packet);

            String result = new String(packet.getData(), packet.getOffset(), packet.getLength(), UDP_ENCODING);

            if (log.isTraceEnabled()) {
                log.trace("Recieved AniDB Reply:\n" + result);
            }

            return result;
        } catch (Exception e) {
            log.error("Error sending message: " + message, e);
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        
        AniDBCommand command = new AniDBCommand("NOTIFYGET");
//        command.addArgument("aid", "5025");
//        command.addArgument("fid", "299092");
//        command.addArgument("generic", "1");
//        command.addArgument("epno", "2");
//        command.addArgument("fmask", AniDB.FILE_FMASK);
//        command.addArgument("amask", AniDB.FILE_AMASK);
//        command.addArgument("gid", "0");
//        command.addArgument("epno", "1");
        command.addArgument("type", "M");
        command.addArgument("id", "1814551");
//        
        String reply = AniDBUDPManager.getInstance().sendData(command.getResult());
        System.out.println(reply);
        
//        AniDBReply reply = new AniDBReply(testCase);
//        System.out.println(reply);

    }

    public void shutdown() {
        logout();

        socket.close();

        isShutdown = true;
    }
    
    private static String testCase =
            "233 ANIMEDESC\n" +
            "0|1|In this world, there exist people with special abilities to manipulate objects and transform those objects into other objects. These people are known as alchemist. However, this manipulation process does not come without cost, as the basic alchemy rules stated that something with equivalent cost is needed to perform the manipulation.<br /><br />The main character is a famous alchemist named Edward Elric, who loses his little brother Alphonse in an accident. Edward manages to contain his brother`s soul in a large piece of armor suit. But by doing so Edward loses his arm and leg, so now they are on a journey to regain their original bodies.(from AnimeNFO).\n";


    private class LogoutHandler extends Thread {

        @Override
        public void run() {
            synchronized(AniDBUDPManager.this) {
                
            }
        }
        
    }

    private class AutoLogoutThread extends Thread {

        public AutoLogoutThread() {
            super("AniDB Logout");
            setDaemon(true);
        }

        @Override
        public void run() {
            while(!isShutdown) {
                try {
                    // Check every 1 min.
                    Thread.sleep(60000);
                } catch (InterruptedException ignore) { }


                synchronized(AniDBUDPManager.this) {
                    if (sessionId != null && System.currentTimeMillis() > lastMessageSent + autoLogoutDelay) {
                        logout();
                    }
                }
            }
        }


        
    }

}
