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

package net.silthus.template.platform.config;

import java.util.List;
import net.silthus.template.platform.config.key.ConfigKey;
import net.silthus.template.platform.config.key.KeyedConfiguration;

import static net.silthus.template.platform.config.key.ConfigKeyFactory.key;
import static net.silthus.template.platform.config.key.ConfigKeyFactory.modifiable;

public final class ConfigKeys {

    private ConfigKeys() {
    }

    public static final ConfigKey<String> TEST = modifiable(key(
            config -> config.getString("test", "Hi from template :)")),
        (config, value) -> config.set("test", value)
    );

    /**
     * A list of the keys defined in this class.
     */
    private static final List<? extends ConfigKey<?>> KEYS = KeyedConfiguration.initialise(ConfigKeys.class);

    public static List<? extends ConfigKey<?>> getKeys() {
        return KEYS;
    }

    public static final class InvalidConfig extends RuntimeException {
    }
}
