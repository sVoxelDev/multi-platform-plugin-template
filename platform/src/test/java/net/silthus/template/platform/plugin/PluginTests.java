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
import net.silthus.template.platform.command.Command;
import net.silthus.template.platform.command.Commands;
import net.silthus.template.platform.config.adapter.ConfigurationAdapter;
import net.silthus.template.platform.plugin.bootstrap.Bootstrap;
import net.silthus.template.platform.sender.Sender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static net.silthus.template.platform.command.CommandTestUtils.createCommandManager;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PluginTests {

    @Nested
    class given_new_plugin {
        private TestPlugin plugin;

        @BeforeEach
        void setUp() {
            plugin = new TestPlugin();
        }

        @Nested
        class when_enable_is_called {
            @BeforeEach
            void setUp() {
                plugin.enable();
            }

            @Test
            void then_commands_are_registered() {
                verify(TestPlugin.command).register(any(), any());
            }

            @Test
            void then_config_is_loaded() {
                assertThat(plugin.getConfig()).isNotNull();
            }
        }
    }

    private static class TestPlugin extends AbstractTemplatePlugin {

        static Command command = mock(Command.class);

        @Override
        protected ConfigurationAdapter provideConfigurationAdapter() {
            return mock(ConfigurationAdapter.class);
        }

        @Override
        protected void setupSenderFactory() {

        }

        @Override
        protected CommandManager<Sender> provideCommandManager() {
            return createCommandManager();
        }

        @Override
        protected void registerCustomCommands(Commands commands) {
            commands.register(command);
        }

        @Override
        public Bootstrap getBootstrap() {
            return mock(Bootstrap.class);
        }
    }
}
