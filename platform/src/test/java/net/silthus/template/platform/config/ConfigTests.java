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

import java.io.File;
import net.silthus.template.platform.config.adapter.ConfigurationAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static net.silthus.template.platform.config.ConfigKeys.TEST;
import static net.silthus.template.platform.config.TestConfigurationAdapter.testConfigAdapter;
import static org.assertj.core.api.Assertions.assertThat;

class ConfigTests {
    private TemplateConfig config;

    @BeforeEach
    void setUp(@TempDir File temp) {
        final ConfigurationAdapter adapter = testConfigAdapter(new File(temp, "test-config.yaml"));
        config = new TemplateConfig(adapter);
        config.load();
    }

    @Test
    void values_are_loaded() {
        assertThat(config.get(TEST)).isEqualTo("Hi from the config!");
    }

    @Test
    void given_modified_value_then_it_is_loaded_on_reload() {
        config.set(TEST, "test");
        config.reload();
        assertThat(config.get(TEST)).isEqualTo("test");
    }
}
