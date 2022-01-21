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

package net.silthus.template.platform.command.commands;

import net.silthus.template.platform.command.CommandTest;
import net.silthus.template.platform.config.TemplateConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static net.silthus.template.platform.config.ConfigKeys.TEST;
import static net.silthus.template.platform.config.TestConfigurationAdapter.testConfig;
import static net.silthus.template.platform.locale.Messages.TEST_MESSAGE;

class ExampleCommandTest extends CommandTest {

    private TemplateConfig config;

    @BeforeEach
    void setUp() {
        config = testConfig();
        register(new ExampleCommand(config));
    }

    @DisplayName("/myplugin test <text>")
    @Nested
    class testCommand {
        @Test
        void sends_input_message() {
            cmd("myplugin test Hi!");
            assertLastMessageIs(TEST_MESSAGE.build("Hi!"));
        }

        @Test
        void given_no_input_uses_config_value() {
            cmd("myplugin test");
            assertLastMessageIs(TEST_MESSAGE.build(config.get(TEST)));
        }

        @Test
        void given_multiple_words_prints_all() {
            cmd("myplugin test hi there");
            assertLastMessageIs(TEST_MESSAGE.build("hi there"));
        }
    }
}
