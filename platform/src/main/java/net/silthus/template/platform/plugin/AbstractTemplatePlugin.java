/*
 * sChat, a Supercharged Minecraft Chat Plugin
 * Copyright (C) Silthus <https://www.github.com/silthus>
 * Copyright (C) sChat team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.silthus.template.platform.plugin;

import cloud.commandframework.CommandManager;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.Getter;
import net.silthus.template.platform.command.Commands;
import net.silthus.template.platform.config.TemplateConfig;
import net.silthus.template.platform.config.adapter.ConfigurationAdapter;
import net.silthus.template.platform.sender.Sender;
import org.jetbrains.annotations.ApiStatus;

@Getter
public abstract class AbstractTemplatePlugin implements TemplatePlugin {

    private TemplateConfig config;
    private Commands commands;

    @Override
    public final void load() {

    }

    @Override
    public final void enable() {
        setupSenderFactory();

        config = new TemplateConfig(provideConfigurationAdapter());
        config.load();

        commands = new Commands(provideCommandManager());
        registerCommands();
    }

    @Override
    public final void disable() {

    }

    protected abstract ConfigurationAdapter provideConfigurationAdapter();

    protected abstract void setupSenderFactory();

    protected abstract CommandManager<Sender> provideCommandManager();

    private void registerCommands() {
        registerNativeCommands();
        registerCustomCommands(commands);
    }

    private void registerNativeCommands() {
        commands.register();
    }

    @ApiStatus.OverrideOnly
    protected void registerCustomCommands(Commands commands) {
    }

    protected final Path resolveConfig(String fileName) {
        Path configFile = getBootstrap().getConfigDirectory().resolve(fileName);

        if (!Files.exists(configFile)) {
            createConfigDirectory(configFile);
            copyDefaultConfig(fileName, configFile);
        }

        return configFile;
    }

    private void copyDefaultConfig(String fileName, Path configFile) {
        try (InputStream is = getBootstrap().getResourceStream(fileName)) {
            Files.copy(is, configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createConfigDirectory(Path configFile) {
        try {
            Files.createDirectories(configFile.getParent());
        } catch (IOException ignored) {
        }
    }
}
