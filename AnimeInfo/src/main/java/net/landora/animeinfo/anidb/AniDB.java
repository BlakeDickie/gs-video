/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.anidb;

import net.landora.animeinfo.data.RelationType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import net.landora.animeinfo.data.Anime;
import net.landora.animeinfo.data.AnimeCategory;
import net.landora.animeinfo.data.AnimeCategoryWeight;
import net.landora.animeinfo.data.AnimeDBA;
import net.landora.animeinfo.data.AnimeEpisode;
import net.landora.animeinfo.data.AnimeFile;
import net.landora.animeinfo.data.AnimeGroup;
import net.landora.animeinfo.data.AnimeListItem;
import net.landora.animeinfo.data.AnimeListState;
import net.landora.animeinfo.data.AnimeManager;
import net.landora.animeinfo.data.AnimeMessage;
import net.landora.animeinfo.data.AnimeName;
import net.landora.animeinfo.data.AnimeNameLookup;
import net.landora.animeinfo.data.AnimeNotification;
import net.landora.animeinfo.data.AnimeRelation;
import net.landora.gsuiutils.Touple;

/**
 *
 * @author bdickie
 */
public class AniDB {

    public static Anime downloadAnime(int animeId) {
        AnimeRecord record = getAnimeRecord(animeId);
        if (record == null)
            return null;

        Anime anime = new Anime();
        anime.setAnimeId(animeId);
        anime.setDescription(getAnimeDescriptin(animeId));
        anime.setEndDate(record.getEndDate());
        anime.setEpisodeCount(record.getEpisodes() > 0 ? record.getEpisodes() : null);
        anime.setHentai(record.isAdultOnly());
        anime.setLastLoaded(Calendar.getInstance());
        anime.setNames(Collections.EMPTY_LIST);
        anime.setPictureFileName(record.getPicName());
        anime.setStartDate(record.getAirDate());
        anime.setType(record.getType());

        if (record.getRatingCount() > 0) {
            anime.setRatingPermanent(record.getRating() / 100f);
            anime.setRatingPermanentVotes(record.getRatingCount());
        }

        if (record.getTempRatingCount() > 0) {
            anime.setRatingTemporary(record.getTempRating() / 100f);
            anime.setRatingTemporaryVotes(record.getTempRatingCount());
        }

        List<AnimeCategoryWeight> categories = new ArrayList<AnimeCategoryWeight>();
        for(AnimeRecord.Category cat: record.getCategory()) {
            AnimeCategory category = AnimeDBA.getAnimeCategory(cat.getCategoryId());
            if (category != null) {
                AnimeCategoryWeight weight = new AnimeCategoryWeight();
                weight.setAnime(anime);
                weight.setCategory(category);
                weight.setWeight(cat.getWeight());
                categories.add(weight);
                
            }
        }
        anime.setCategories(categories);

        List<AnimeNameLookup> lookupNames = AnimeDBA.getLookupNames(animeId);
        List<AnimeName> names = new ArrayList<AnimeName>(lookupNames.size());
        for(AnimeNameLookup lookup: lookupNames) {
            AnimeName name = new AnimeName();
            name.setAnime(anime);
            name.setLanguage(lookup.getLanguage());
            name.setName(lookup.getName());
            name.setType(lookup.getType());
            names.add(name);
        }

        for(AnimeName name: names) {
            if (name.getType().equalsIgnoreCase("main")) {
                anime.setNameMain(name.getName());
            } else if (name.getType().equalsIgnoreCase("official") && name.getLanguage().equalsIgnoreCase("en")) {
                anime.setNameEnglish(name.getName());
            }
        }
        anime.setNames(names);


        List<AnimeRelation> relations = new ArrayList<AnimeRelation>();
        for(AnimeRecord.Relation r: record.getRelatedAnime()) {
            AnimeRelation relation = new AnimeRelation();
            relation.setAnime(anime);
            relation.setRelatedAnimeId(r.getAid());
            relation.setRelationType(r.getType());
            relations.add(relation);
        }
        anime.setRelations(relations);

        // ToDo Ratings

        return anime;
    }


    private static AnimeRecord getAnimeRecord(int animeId) {

        AniDBCommand command = new AniDBCommand("ANIME");
        command.addArgument("aid", animeId);
        command.addArgument("amask", "1D00FBF10100F8");
        //command.addArgument("amask", "3C00FAF1D100F8");

        AniDBReply reply = AniDBUDPManager.getInstance().sendData(command);
        if (reply.getReturnCode() == 330)
            return null;
        else if (reply.getReturnCode() != 230)
            throw new AniDBConnectionError(reply);

        AnimeRecord record = new AnimeRecord();
        record.setAid(animeId);

        AniDBReply.ReplyLine line = reply.getFirstLine();
        int col = 0;

        record.setType(line.getValue(col++));

        String relationIds = line.getValue(col++);
        String relationTypes = line.getValue(col++);

        if (!relationIds.equals("")) {
            String[] ids = relationIds.split("'");
            String[] types = relationTypes.split("'");
            for(int i = 0; i < Math.min(ids.length, types.length); i++) {
                AnimeRecord.Relation relation = new AnimeRecord.Relation(Integer.parseInt(ids[i]), RelationType.lookupType(Integer.parseInt(types[i])));
                record.getRelatedAnime().add(relation);
            }
        }

        String categoryWeights = line.getValue(col++);

        record.setEpisodes(line.getValueAsInt(col++));
        record.setNormalEpisodes(line.getValueAsInt(col++));
        record.setSpecialEpisodes(line.getValueAsInt(col++));

        record.setAirDate(line.getValueAsDate(col++));
        record.setEndDate(line.getValueAsDate(col++));

        record.setPicName(line.getValue(col++));

        String categoryIds = line.getValue(col++);
        if (!categoryIds.equals("")) {
            String[] ids = categoryIds.split(",");
            String[] weights = categoryWeights.split(",");
            for(int i = 0; i < Math.min(ids.length, weights.length); i++) {
                AnimeRecord.Category category = new AnimeRecord.Category(Integer.parseInt(ids[i]), Integer.parseInt(weights[i]));
                record.getCategory().add(category);
            }
        }
        
        record.setRating(line.getValueAsInt(col++));
        record.setRatingCount(line.getValueAsInt(col++));
        record.setTempRating(line.getValueAsInt(col++));
        record.setTempRatingCount(line.getValueAsInt(col++));
        record.setAdultOnly(line.getValueAsBoolean(col++));

        record.setLastUpdated(line.getValueAsInt(col++));

        record.setSpecialsCount(line.getValueAsInt(col++));
        record.setCreditsCount(line.getValueAsInt(col++));
        record.setOtherCount(line.getValueAsInt(col++));
        record.setTrailerCount(line.getValueAsInt(col++));
        record.setParodyCount(line.getValueAsInt(col++));

        return record;
    }

    public static String getAnimeDescriptin(int animeId) {

        StringBuilder buffer = new StringBuilder();

        int parts = 1; // There will be at least 1 part, once we load the first part was can find the number of parts.
        for(int part = 0; part < parts; part++) {

            AniDBCommand call = new AniDBCommand("ANIMEDESC");
            call.addArgument("aid", animeId);
            call.addArgument("part", part);

            AniDBReply reply = AniDBUDPManager.getInstance().sendData(call);
            if (part == 0 && reply.getReturnCode() == 333)
                return null;
            if (reply.getReturnCode() != 233) {
                throw new AniDBConnectionError(reply);
            }

            parts = reply.getFirstValueAsInt(1);

            buffer.append(reply.getFirstValue(2));

        }

        return buffer.toString();
    }

    public static AnimeEpisode getAnimeEpisode(int episodeId) {

        AniDBCommand command = new AniDBCommand("EPISODE");
        command.addArgument("eid", episodeId);
        

        AniDBReply reply = AniDBUDPManager.getInstance().sendData(command);
        return handleEpisodeResult(reply);
    }

    public static AnimeEpisode getAnimeEpisode(int animeId, int episodeNumber) {

        AniDBCommand command = new AniDBCommand("EPISODE");
        command.addArgument("aid", animeId);
        command.addArgument("epno", episodeNumber);


        AniDBReply reply = AniDBUDPManager.getInstance().sendData(command);
        return handleEpisodeResult(reply);
    }

    public static AnimeEpisode getAnimeEpisode(int animeId, String episodeNumber) {

        AniDBCommand command = new AniDBCommand("EPISODE");
        command.addArgument("aid", animeId);
        command.addArgument("epno", episodeNumber);


        AniDBReply reply = AniDBUDPManager.getInstance().sendData(command);
        return handleEpisodeResult(reply);
    }

    private static AnimeEpisode handleEpisodeResult(AniDBReply reply) {
        if (reply.getReturnCode() == 340)
            return null;
        else if (reply.getReturnCode() != 240)
            throw new AniDBConnectionError(reply);

        AniDBReply.ReplyLine line = reply.getFirstLine();
        int col = 0;

        AnimeEpisode episode = new AnimeEpisode();

        episode.setEpisodeId(line.getValueAsInt(col++));
        episode.setAnime(AnimeManager.getInstance().getAnime(line.getValueAsInt(col++)));

        int length = line.getValueAsInt(col++);
        episode.setLength(length > 0 ? length : null);

        int rating = line.getValueAsInt(col++);
        int ratingCount = line.getValueAsInt(col++);
        if (ratingCount > 0) {
            episode.setRating(rating / 100f);
            episode.setRatingVotes(ratingCount);
        }

        episode.setEpisodeNumber(line.getValue(col++).trim());
        episode.setNameEnglish(line.getValue(col++));
        episode.setNameRomaji(line.getValue(col++));
        episode.setNameKanji(line.getValue(col++));

        episode.setAirDate(line.getValueAsDate(col++));


        return episode;
    }

    public static AnimeFile getAnimeFile(int fileId) {

        AniDBCommand command = new AniDBCommand("FILE");
//        command.addArgument("ed2k", "57560987b519c1c62c15cdd9c89affd6");
//        command.addArgument("size", "346918566");
        command.addArgument("fid", fileId);
        command.addArgument("fmask", FILE_FMASK);
        command.addArgument("amask", FILE_AMASK);


        AniDBReply reply = AniDBUDPManager.getInstance().sendData(command);
        return handleFileResult(reply);
    }

    public static AnimeFile getAnimeFile(String ed2k, long size) {

        AniDBCommand command = new AniDBCommand("FILE");
        command.addArgument("ed2k", ed2k);
        command.addArgument("size", size);
        command.addArgument("fmask", FILE_FMASK);
        command.addArgument("amask", FILE_AMASK);


        AniDBReply reply = AniDBUDPManager.getInstance().sendData(command);
        return handleFileResult(reply);
    }



    static final String FILE_FMASK = "71C04B0000";
    static final String FILE_AMASK = "000000C1";
    
    private static AnimeFile handleFileResult(AniDBReply reply) {
        if (reply.getReturnCode() == 320)
            return null;
        else if (reply.getReturnCode() != 220)
            throw new AniDBConnectionError(reply);

        AniDBReply.ReplyLine line = reply.getFirstLine();
        int col = 0;

        AnimeFile file = new AnimeFile();
        file.setFileId(line.getValueAsInt(col++));

        int animeId = line.getValueAsInt(col++);
        int episodeId = line.getValueAsInt(col++);

        AnimeEpisode episode = AnimeManager.getInstance().findEpisode(episodeId);
        file.setEpisode(episode);

        int groupId = line.getValueAsInt(col++);

        file.setState(line.getValueAsInt(col++));

        long length = line.getValueAsLong(col++);
        String ed2k = line.getValue(col++);
        if (length > 0) {
            file.setSize(length);
            file.setEd2k(ed2k);
        } else {
            file.setGeneric(true);
        }
        
        
        file.setSource(line.getValue(col++));
        file.setVideoCodec(line.getValue(col++));
        file.setVideoResolution(line.getValue(col++));
        file.setFileType(line.getValue(col++));
        

        String longGroupName = line.getValue(col++);
        String shortGroupName = line.getValue(col++);

        Calendar animeLastUpdates = line.getValueAsDateTime(col++);

        if (groupId > 0) {
            AnimeGroup group = AnimeDBA.getAnimeGroup(groupId);
            if (group == null) {
                group = new AnimeGroup();
                group.setGroupId(groupId);
                group.setLostName(longGroupName);
                group.setShortName(shortGroupName);
                AnimeDBA.saveGroup(group);
            }
            file.setGroup(group);
        }


        return file;
    }
    
    
    public static AnimeListItem getAnimeListItemByFileId(int fileId) {

        AniDBCommand command = new AniDBCommand("MYLIST");
//        command.addArgument("ed2k", "57560987b519c1c62c15cdd9c89affd6");
//        command.addArgument("size", "346918566");
        command.addArgument("fid", fileId);


        AniDBReply reply = AniDBUDPManager.getInstance().sendData(command);
        return handleListResult(reply);
    }

    public static AnimeListItem getAnimeListItemByListId(int listId) {

        AniDBCommand command = new AniDBCommand("MYLIST");
        command.addArgument("lid", listId);


        AniDBReply reply = AniDBUDPManager.getInstance().sendData(command);
        return handleListResult(reply);
    }

    
    private static AnimeListItem handleListResult(AniDBReply reply) {
        if (reply.getReturnCode() == 321)
            return null;
        else if (reply.getReturnCode() != 221 && reply.getReturnCode() != 310)
            throw new AniDBConnectionError(reply);

        AniDBReply.ReplyLine line = reply.getFirstLine();
        int col = 0;

        AnimeListItem item = new AnimeListItem();
        int listId = line.getValueAsInt(col++);
        
        int fileId = line.getValueAsInt(col++);
        int episodeId = line.getValueAsInt(col++);
        int animeId = line.getValueAsInt(col++);
        int groupId = line.getValueAsInt(col++);
        item.setFile(AnimeManager.getInstance().findFile(fileId));
        
        item.setAddedDate(line.getValueAsDateTime(col++));
        item.setState(AnimeListState.lookupType(line.getValueAsInt(col++)));
        item.setViewDate(line.getValueAsDateTime(col++));
        
        item.setStorage(line.getValue(col++));
        item.setSource(line.getValue(col++));
        item.setOther(line.getValue(col++));
        item.setFileStateId(line.getValueAsInt(col++));

        return item;
    }
    
    public static AnimeListItem addListitem(int fileId, Calendar viewDate, AnimeListState listState) {
        AniDBCommand command = new AniDBCommand("MYLISTADD");
        command.addArgument("fid", fileId);
        command.addArgument("state", listState.getStateId());
        command.addArgument("viewed", (viewDate != null ? 1 : 0));
        if (viewDate != null)
            command.addArgument("viewdate", viewDate);


        AniDBReply reply = AniDBUDPManager.getInstance().sendData(command);
        switch(reply.getReturnCode()) {
            case 210:
                // Item added
                break;
            case 310:
                return handleListResult(reply);
            case 320:
                // No Such File
            case 330:
                // No Such Anime
            case 350:
                // No Such Group
            default:
                throw new AniDBConnectionError(reply);
        }
        
        return getAnimeListItemByFileId(fileId);
    }
    
    public static AnimeListItem addGenericListitem(AnimeEpisode episode, Calendar viewDate, AnimeListState listState) {
        AniDBCommand command = new AniDBCommand("MYLISTADD");
        command.addArgument("aid", episode.getAnime().getAnimeId());
        command.addArgument("generic", "1");
        command.addArgument("epno", episode.getEpisodeNumber());


        AniDBReply reply = AniDBUDPManager.getInstance().sendData(command);
        switch(reply.getReturnCode()) {
            case 210:
                // Item added
                break;
            case 310:
                return handleListResult(reply);
            case 320:
                // No Such File
            case 330:
                // No Such Anime
            case 350:
                // No Such Group
            default:
                throw new AniDBConnectionError(reply);
        }
        
        command = new AniDBCommand("MYLIST");
        command.addArgument("aid", episode.getAnime().getAnimeId());
        command.addArgument("gname", "none");
        command.addArgument("epno", episode.getEpisodeNumber());
        command.addArgument("state", listState.getStateId());
        command.addArgument("viewed", (viewDate != null ? 1 : 0));
        if (viewDate != null)
            command.addArgument("viewdate", viewDate);
        
        reply = AniDBUDPManager.getInstance().sendData(command);
        
        return handleListResult(reply);
    }
    
    public static boolean updateListItem(AnimeListItem item) {
        AniDBCommand command = new AniDBCommand("MYLISTADD");
        command.addArgument("fid", item.getFile().getFileId());
        command.addArgument("edit", 1);
        command.addArgument("state", item.getState().getStateId());
        command.addArgument("viewed", (item.getViewDate() != null ? 1 : 0));
        if (item.getViewDate() != null)
            command.addArgument("viewdate", item.getViewDate());
        command.addArgument("source", item.getSource());
        command.addArgument("storage", item.getStorage());
        command.addArgument("other", item.getOther());
        command.addArgument("filestate", item.getFileState().getStateId());
        
        AniDBReply reply = AniDBUDPManager.getInstance().sendData(command);
        switch(reply.getReturnCode()) {
            case 311:
                // Item edited
                return true;
            case 411:
                // No such item.
                return false;
            default:
                throw new AniDBConnectionError(reply);
        }
    }
    
    
    public static boolean hasNotifications() {

        AniDBCommand call = new AniDBCommand("NOTIFY");

        AniDBReply reply = AniDBUDPManager.getInstance().sendData(call);
        if (reply.getReturnCode() != 290) {
            throw new AniDBConnectionError(reply);
        }
        return reply.getFirstValueAsInt(0) > 0 || reply.getFirstValueAsInt(1) > 0;
    }
    
    
    public static List<Touple<String,String>> getNotifications() {

        AniDBCommand call = new AniDBCommand("NOTIFYLIST");

        AniDBReply reply = AniDBUDPManager.getInstance().sendData(call);
        if (reply.getReturnCode() != 291) {
            throw new AniDBConnectionError(reply);
        }
        
        List<Touple<String,String>> notifications = new ArrayList<Touple<String,String>>();
        for(AniDBReply.ReplyLine line: reply.getLines()) {
            notifications.add(new Touple<String, String>(line.getValue(0), line.getValue(1)));
        }
        
        return notifications;
    }
    
    
    public static void getNotification(String id) {

        AniDBCommand call = new AniDBCommand("NOTIFYGET");
        call.addArgument("type", "N");
        call.addArgument("id", id);

        AniDBReply reply = AniDBUDPManager.getInstance().sendData(call);
        if (reply.getReturnCode() != 293) {
            throw new AniDBConnectionError(reply);
        }
        
        int typeId = reply.getFirstValueAsInt(1);
        Calendar date = reply.getFirstLine().getValueAsDateTime(3);
        
        boolean allSaved = true;
        for(String fileIdStr: reply.getFirstValue(5).split(",")) {
            int fileId = Integer.parseInt(fileIdStr);
            AnimeNotification notification = AnimeDBA.getAnimeNotificationByFileId(fileId);
            if (notification == null) {
                notification = new AnimeNotification();
                notification.setFile(AnimeManager.getInstance().findFile(fileId));
                notification.setAddedDate(date);
                notification.setTypeId(typeId);
                if (!AnimeDBA.saveAnimeNotification(notification))
                    allSaved = false;
            }
        }
        
        if (allSaved) {
            call = new AniDBCommand("NOTIFYACK");
            call.addArgument("type", "N");
            call.addArgument("id", id);
            AniDBUDPManager.getInstance().sendData(call);
        }
    }
    
    
    public static void getMessage(String id) {

        AniDBCommand call = new AniDBCommand("NOTIFYGET");
        call.addArgument("type", "M");
        call.addArgument("id", id);

        AniDBReply reply = AniDBUDPManager.getInstance().sendData(call);
        if (reply.getReturnCode() != 292) {
            throw new AniDBConnectionError(reply);
        }
        
        int msgId = reply.getFirstValueAsInt(0);
        int typeId = reply.getFirstValueAsInt(4);
        Calendar date = reply.getFirstLine().getValueAsDateTime(3);
        String fromUser = reply.getFirstValue(2);
        String title = reply.getFirstValue(5);
        String body = reply.getFirstValue(6);
        
        
        boolean allSaved = true;
        AnimeMessage msg = AnimeDBA.getAnimeMessageByMessageId(msgId);
        if (msg == null) {
            msg = new AnimeMessage();
            msg.setMessageId(msgId);
            msg.setBody(body);
            msg.setDate(date);
            msg.setFromUser(fromUser);
            msg.setTitle(title);
            msg.setTypeId(typeId);
            
            if (!AnimeDBA.saveAnimeMessage(msg))
                allSaved = false;
        }
        
        if (allSaved) {
            call = new AniDBCommand("NOTIFYACK");
            call.addArgument("type", "M");
            call.addArgument("id", id);
            AniDBUDPManager.getInstance().sendData(call);
        }
    }
    
    public static boolean queueMyListExport(String templateName) {
        
                
        AniDBCommand call = new AniDBCommand("MYLISTEXPORT");
        call.addArgument("template", templateName);

        AniDBReply reply = AniDBUDPManager.getInstance().sendData(call);
        switch(reply.getReturnCode()) {
            case 217:
                return true;
            case 318:
                return false;
            default:
                throw new AniDBConnectionError(reply);
        }
    }
    
    public static AnimeGroup getAnimeGroup(int groupId) {
        AniDBCommand call = new AniDBCommand("GROUP");
        call.addArgument("gid", groupId);
        AniDBReply reply = AniDBUDPManager.getInstance().sendData(call);
        if (reply.getReturnCode() == 350)
            return null;
        else if (reply.getReturnCode() != 250)
            throw new AniDBConnectionError(reply);
        
        AnimeGroup group = new AnimeGroup();
        
        int col = 0;
        group.setGroupId(reply.getFirstValueAsInt(col++));
        
        col++; // Rating
        col++; //Votes
        col++; //acount
        col++; //fcount
        group.setLostName(reply.getFirstValue(col++));
        group.setShortName(reply.getFirstValue(col++));
        col++; //irc channel
        col++; //irc server
        group.setUrl(reply.getFirstValue(col++));
        col++; //pic name
        
        group.setFullyLoaded(true);
        
        return group;
    }
}
