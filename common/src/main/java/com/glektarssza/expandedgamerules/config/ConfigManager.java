package com.glektarssza.expandedgamerules.config;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.glektarssza.expandedgamerules.Constants;

/**
 * The main manager for the mod configuration.
 */
public class ConfigManager {
    /**
     * The default mod configuration.
     */
    @Nonnull
    public static final ModConfig DEFAULT_CONFIG = new ModConfig();

    /**
     * The main mod configuration.
     */
    @Nonnull
    public static final ModConfig CONFIG = new ModConfig();

    /**
     * The service used to watch the file system for changes.
     */
    @Nullable
    private static WatchService fileWatchService;

    /**
     * The watch key to use to observe changes to the configuration file.
     */
    @Nullable
    private static WatchKey configWatchKey;

    public static void initializeConfig() {
        var configPath = Path.of(Constants.MOD_ID + ".json");
        load();
        try {
            fileWatchService = FileSystems.getDefault().newWatchService();
        } catch (IOException ex) {
            Constants.LOG.warn("Failed to initialize configuration watcher", ex);
            return;
        }
        try {
            configWatchKey = configPath.register(fileWatchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException ex) {
            try {
                if (fileWatchService != null) {
                    fileWatchService.close();
                    fileWatchService = null;
                }
            } catch (IOException ex2) {
                Constants.LOG.warn("Failed to tear down configuration watcher", ex2);
            }
            Constants.LOG.warn("Failed to initialize configuration watcher", ex);
            return;
        }
    }

    @SuppressWarnings("unchecked")
    public static void tick() {
        if (configWatchKey != null) {
            for (var event : configWatchKey.pollEvents()) {
                var kind = event.kind();
                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    // -- Ignore overflow events
                    continue;
                }
                var ev = (WatchEvent<Path>) event;
                var filename = ev.context();
                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    Constants.LOG.info("Configuration file at \"%s\" has been created, reloading...", filename);
                    load();
                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    Constants.LOG.info("Configuration file at \"%s\" has been deleted, recreating...", filename);
                    save();
                } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    Constants.LOG.info("Configuration file at \"%s\" has been modified, reloading...", filename);
                    load();
                }
            }
        }
    }

    /**
     * Load the main mod configuration.
     */
    public static void load() {
    }

    /**
     * Save the main mod configuration.
     */
    public static void save() {
    }
}
