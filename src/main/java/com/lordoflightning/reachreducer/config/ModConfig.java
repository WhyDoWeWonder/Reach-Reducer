package com.lordoflightning.reachreducer.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.lordoflightning.reachreducer.ReachReducerConstants;


public class ModConfig {

    private static File configFolder = new File("config");
    private static File configFile;
    private static Gson config = new GsonBuilder().setPrettyPrinting().create();
    public static ConfigInstance INSTANCE;

    public static void init() {
        loadDefaults();
        generateFoldersAndFiles();
        readJson();
        writeJson(); //Write to file new config options
    }

    public static void loadDefaults() {
        INSTANCE = new ConfigInstance();
    }

    private static void generateFoldersAndFiles() {
        if (!configFolder.exists()) {
            configFolder.mkdir();
        }
        if (configFolder.isDirectory()) {
            configFile = new File(configFolder, ReachReducerConstants.configFileName);

            if (!configFile.exists()) {
                System.out.println("Creating new config file");
                try {
                    configFile.createNewFile();
                    loadDefaults();
                    String json = config.toJson(INSTANCE);
                    FileWriter writer = new FileWriter(configFile);
                    writer.write(json);
                    writer.close();
                } catch (IOException e) {
                    throw new IllegalStateException("Can't create config file", e);
                }
            } else if (configFile.isDirectory()) {
                throw new IllegalStateException(ReachReducerConstants.configFileName + " must be a file!");
            } else {
            }
        } else {
            throw new IllegalStateException("'config' must be a folder!");
        }
    }

    public static void readJson() {
        try {
            INSTANCE = config.fromJson(new FileReader(configFile), ConfigInstance.class);
            if (INSTANCE == null) {
                throw new IllegalStateException("Null configuration");
            }

            if (INSTANCE.getAttackReachDistance() > ReachReducerConstants.maxAttackReach
                    || INSTANCE.getAttackReachDistance() < 0) {
                INSTANCE.setAttackReachDistance(ReachReducerConstants.maxAttackReach);
            }
            if (INSTANCE.getBlockReachDistance() > ReachReducerConstants.maxBlockReach
                    || INSTANCE.getBlockReachDistance() < 0) {
                INSTANCE.setBlockReachDistance(ReachReducerConstants.maxBlockReach);
            }
            if (INSTANCE.getCreativeAttackReachDistance() > ReachReducerConstants.maxCreativeAttackReach
                    || INSTANCE.getCreativeAttackReachDistance() < 0) {
                INSTANCE.setCreativeAttackReachDistance(ReachReducerConstants.maxCreativeAttackReach);
            }
            if (INSTANCE.getCreativeBlockReachDistance() > ReachReducerConstants.maxCreativeBlockReach
                    || INSTANCE.getCreativeBlockReachDistance() < 0) {
                INSTANCE.setCreativeBlockReachDistance(ReachReducerConstants.maxCreativeBlockReach);
            }


        } catch (JsonSyntaxException e) {
            System.err.println("Invalid configuration!");
            e.printStackTrace();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
        }
    }

    public static void writeJson() {
        try {
            String json = config.toJson(INSTANCE);
            FileWriter writer = new FileWriter(configFile, false);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            throw new IllegalStateException("Can't update config file", e);
        }
    }

}
