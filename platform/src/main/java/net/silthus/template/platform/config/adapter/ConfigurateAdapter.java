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

package net.silthus.template.platform.config.adapter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;
import net.kyori.adventure.text.Component;
import net.silthus.template.platform.config.serializers.MiniMessageComponentSerializer;
import net.silthus.template.platform.config.serializers.SettingsSerializer;
import net.silthus.template.pointer.Settings;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.loader.AbstractConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

public abstract class ConfigurateAdapter<T extends AbstractConfigurationLoader.Builder<T, L>, L extends AbstractConfigurationLoader<?>> extends ConfigurateConfigSection implements ConfigurationAdapter {

    private static final TypeSerializerCollection SERIALIZERS = TypeSerializerCollection.builder()
        .register(Component.class, new MiniMessageComponentSerializer())
        .register(Settings.class, new SettingsSerializer())
        .build();

    private static final Function<ConfigurationOptions, ConfigurationOptions> DEFAULT_OPTIONS = options ->
        options.serializers(serializers -> serializers.registerAll(SERIALIZERS));

    private final ConfigurationLoader<? extends ConfigurationNode> loader;

    protected ConfigurateAdapter(Path path) {
        final AbstractConfigurationLoader.Builder<T, L> loader = createLoader(path);
        this.loader = loader.defaultOptions(DEFAULT_OPTIONS.apply(loader.defaultOptions())).build();
    }

    protected abstract AbstractConfigurationLoader.Builder<T, L> createLoader(Path path);

    @Override
    public void save() {
        try {
            loader.save(getRoot());
        } catch (ConfigurateException e) {
            throw new SaveFailed(e);
        }
    }

    @Override
    public void load() {
        try {
            setRoot(loader.load());
        } catch (IOException e) {
            throw new LoadFailed(e);
        }
    }
}
