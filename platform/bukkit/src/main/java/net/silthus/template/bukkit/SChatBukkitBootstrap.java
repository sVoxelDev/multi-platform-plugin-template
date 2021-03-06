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

package net.silthus.template.bukkit;

import java.nio.file.Path;
import lombok.Getter;
import net.silthus.template.bukkit.adapter.BukkitSchedulerAdapter;
import net.silthus.template.platform.plugin.bootstrap.Bootstrap;
import net.silthus.template.platform.plugin.bootstrap.LoaderBootstrap;
import net.silthus.template.platform.plugin.logging.JavaPluginLogger;
import net.silthus.template.platform.plugin.logging.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SChatBukkitBootstrap implements Bootstrap, LoaderBootstrap {

    private final JavaPlugin loader;
    private final SChatBukkitPlugin plugin;

    private final PluginLogger pluginLogger;
    private final BukkitSchedulerAdapter scheduler;

    public SChatBukkitBootstrap(JavaPlugin loader) {
        this.loader = loader;

        this.pluginLogger = new JavaPluginLogger(loader.getLogger());
        this.scheduler = new BukkitSchedulerAdapter(loader);
        this.plugin = new SChatBukkitPlugin(this);
    }

    @Override
    public void onLoad() {
        plugin.load();
    }

    @Override
    public void onEnable() {
        plugin.enable();
    }

    @Override
    public void onDisable() {
        plugin.disable();
    }

    @Override
    public Path getDataDirectory() {
        return getLoader().getDataFolder().toPath().toAbsolutePath();
    }
}
