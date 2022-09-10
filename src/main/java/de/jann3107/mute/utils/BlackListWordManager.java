package de.jann3107.mute.utils;

import de.jann3107.mute.Mute;

import java.util.ArrayList;

public class BlackListWordManager {

    public boolean isMessageBlackListed(String message) {
        message = message.toLowerCase();
        message = removeBypasses(message);
        System.out.println("Checking: " + message);
        System.out.println(getBlackListWords());
        for(String blword : getBlackListWords()) {
            if(message.contains(blword)) {
                return true;
            }
        }
        return false;
    }
    private void punishPlayer(String uuid, int level) {
        switch (level){
            case 0:
                Mute.instance.muteManager.incrementTimesMuted(uuid);
                break;
            case 1:
                Mute.instance.muteManager.mutePlayer(uuid, String.valueOf(System.currentTimeMillis() + (10 * 60 * 1000)));
                break;
            case 2:
                Mute.instance.muteManager.mutePlayerForHours(uuid, 1);
                break;
            case 3:
                Mute.instance.muteManager.mutePlayerForHours(uuid, 24);
                break;
            case 4:
                Mute.instance.muteManager.mutePlayerForHours(uuid, 24 * 7);
                break;
            case 5:
                Mute.instance.muteManager.mutePlayerForHours(uuid, 24 * 30);
                break;
            default:
                Mute.instance.muteManager.mutePlayerForHours(uuid, 24 * 365);
                break;
        }
    }
    private int incrementLevel(String uuid, int level) {
        if(level == 0){
            return level;
        }
        if(Mute.instance.getConfig().getBoolean("blacklist.increase")){
            if(Mute.instance.muteManager.getTimesMuted(uuid) >= Mute.instance.getConfig().getInt("blacklist.increaseafter")){
                return level + (Mute.instance.muteManager.getTimesMuted(uuid) - Mute.instance.getConfig().getInt("blacklist.increaseafter"));
            } else {
                return level;
            }
        } else {
            return level;
        }
    }
    public void punish(String uuid, String message){
        message = removeBypasses(message);
        if(!get10MinsWords().isEmpty()){
            for(String blword : get10MinsWords()) {
                if(message.contains(blword)) {
                    punishPlayer(uuid, incrementLevel(uuid, 1));
                }
            }
        }
        if(!get1HWords().isEmpty()){
            for(String blword : get1HWords()) {
                if(message.contains(blword)) {
                    punishPlayer(uuid, incrementLevel(uuid, 2));
                }
            }
        }
        if(!get1DWords().isEmpty()){
            for(String blword : get1DWords()) {
                if(message.contains(blword)) {
                    punishPlayer(uuid, incrementLevel(uuid, 3));
                }
            }
        }
        if(!get1WWords().isEmpty()){
            for(String blword : get1WWords()) {
                if(message.contains(blword)) {
                    punishPlayer(uuid, incrementLevel(uuid, 4));
                }
            }
        }
        if(!get1MWords().isEmpty()){
            for(String blword : get1MWords()) {
                if(message.contains(blword)) {
                    punishPlayer(uuid, incrementLevel(uuid, 5));
                }
            }
        }
        if(!get1YWords().isEmpty()){
            for(String blword : get1YWords()) {
                if(message.contains(blword)) {
                    punishPlayer(uuid, incrementLevel(uuid, 6));
                }
            }
        }
        if(!getBlockedWords().isEmpty()){
            for(String blword : getBlockedWords()) {
                if(message.contains(blword)) {
                    return;
                }
            }
        }
    }
    private ArrayList<String> getBlackListWords() {
        ArrayList<String> words = new ArrayList<>();
        if(!getBlockedWords().isEmpty()){
            words.addAll(getBlockedWords());
        }
        System.out.println(get10MinsWords());
        if(!get10MinsWords().isEmpty()){
            words.addAll(get10MinsWords());
        }
        if(!get1HWords().isEmpty()){
            words.addAll(get1HWords());
            System.out.println(get1HWords());
        }
        if(!get1DWords().isEmpty()){
            words.addAll(get1DWords());
        }
        if(!get1WWords().isEmpty()){
            words.addAll(get1WWords());
        }
        if(!get1MWords().isEmpty()){
            words.addAll(get1MWords());
        }
        System.out.println(get1YWords());
        if(!get1YWords().isEmpty()){
            words.addAll(get1YWords());
            System.out.println(get1YWords());
        }
        return words;
    }
    private ArrayList<String> getBlockedWords(){
        return (ArrayList<String>) Mute.instance.getConfig().getList("blacklist.block", new ArrayList<String>());
    }
    private ArrayList<String> get10MinsWords(){
        return (ArrayList<String>) Mute.instance.getConfig().getList("blacklist.punish.min", new ArrayList<String>());
    }

    private ArrayList<String> get1HWords(){
        return (ArrayList<String>) Mute.instance.getConfig().getList("blacklist.punish.h", new ArrayList<String>());
    }

    private ArrayList<String> get1DWords(){
        return (ArrayList<String>) Mute.instance.getConfig().getList("blacklist.punish.d", new ArrayList<String>());
    }

    private ArrayList<String> get1WWords(){
        return (ArrayList<String>) Mute.instance.getConfig().getList("blacklist.punish.w", new ArrayList<String>());
    }

    private ArrayList<String> get1MWords(){
        return (ArrayList<String>) Mute.instance.getConfig().getList("blacklist.punish.m", new ArrayList<String>());
    }

    private ArrayList<String> get1YWords(){
        return (ArrayList<String>) Mute.instance.getConfig().getList("blacklist.punish.y", new ArrayList<String>());
    }

    private String removeBypasses(String message) {
        message = message.replaceAll("[^a-zA-Z]", "");
        message = message.toLowerCase();
        return message;
    }
}
