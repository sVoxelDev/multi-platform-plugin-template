/*
 * This file is part of multi-platform-template, licensed under the MIT License.
 * Copyright (C) Silthus <https://www.github.com/silthus>
 * Copyright (C) multi-platform-template team and contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package net.silthus.template.platform.plugin;

import cloud.commandframework.CommandManager;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.Getter;
import net.silthus.template.platform.command.Commands;
import net.silthus.template.platform.command.commands.ExampleCommand;
import net.silthus.template.platform.config.TemplateConfig;
import net.silthus.template.platform.config.adapter.ConfigurationAdapter;
import net.silthus.template.platform.locale.TranslationManager;
import net.silthus.template.platform.sender.Sender;
import org.jetbrains.annotations.ApiStatus;

@Getter
public abstract class AbstractTemplatePlugin implements TemplatePlugin {

    private TranslationManager translationManager;
    private TemplateConfig config;
    private Commands commands;

    @Override
    public final void load() {
        this.translationManager = new TranslationManager(getBootstrap().getConfigDirectory());
        this.translationManager.reload();
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
        commands.register(new ExampleCommand(getConfig()));
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
