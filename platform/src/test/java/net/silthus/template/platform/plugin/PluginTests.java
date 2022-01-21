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
}
