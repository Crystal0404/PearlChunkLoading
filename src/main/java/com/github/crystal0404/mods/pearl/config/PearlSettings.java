package com.github.crystal0404.mods.pearl.config;

import com.github.crystal0404.mods.pearl.PearlChunkLoadingMod;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class PearlSettings {
    private static boolean enable = true;
    private static boolean save = true;
    private static int time = -1;
    private static int expiryTicks = 40;

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static Path PATH;
    private static final Settings defaultSetting = new Settings(true, true, -1, 40);

    public static void init() {
        ServerLifecycleEvents.SERVER_STARTING.register((server -> {
            PATH = server.session.getDirectory().path().resolve("pearl/Config.json");
            try {
                readOrCreateFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    private static void readOrCreateFile() throws IOException {
        File file = PATH.toFile();
        if (file.getParentFile().mkdirs()) {
            PearlChunkLoadingMod.LOGGER.info("The configuration folder was successfully created");
        }
        if (file.createNewFile()) {
            writeDefaultSettings(file);
            PearlChunkLoadingMod.LOGGER.info("Config.json profile was successfully created");
        } else {
            Settings settings;
            try {
                settings = gson.fromJson(Files.asCharSource(file, StandardCharsets.UTF_8).read(), Settings.class);
            } catch (JsonSyntaxException e) {
                PearlChunkLoadingMod.LOGGER.error(String.valueOf(e));
                PearlChunkLoadingMod.LOGGER.error("Deserialization error, restore default settings");
                writeDefaultSettings(file);
                settings = defaultSetting;
            }
            enable = settings.enable;
            save = settings.save;
            time = settings.time;
            expiryTicks = settings.expiryTicks;
        }
    }

    private static void writeDefaultSettings(File file) throws IOException {
        String defaultConfig = gson.toJson(defaultSetting);
        Files.asCharSink(file, StandardCharsets.UTF_8).write(defaultConfig);
    }

    private static void saveConfig() {
        if (PATH == null) {
            throw new NullPointerException();
        } else {
            File file = PATH.toFile();
            String defaultConfig = gson.toJson(new Settings(isEnable(), isSave(), getTime(), getExpiryTicks()));
            try {
                Files.asCharSink(file, StandardCharsets.UTF_8).write(defaultConfig);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private record Settings(boolean enable, boolean save, int time, int expiryTicks) {

    }

    public static boolean isEnable() {
        return enable;
    }

    public static void setEnable(boolean enable) {
        PearlSettings.enable = enable;
        saveConfig();
    }

    public static boolean isSave() {
        return enable && save;
    }

    public static void setSave(boolean save) {
        PearlSettings.save = save;
        saveConfig();
    }

    public static int getTime() {
        return time;
    }

    public static void setTime(int time) {
        PearlSettings.time = time;
        saveConfig();
    }

    public static int getExpiryTicks() {
        return expiryTicks;
    }

    public static void setExpiryTicks(int expiryTicks) {
        PearlSettings.expiryTicks = expiryTicks;
        saveConfig();
    }
}
