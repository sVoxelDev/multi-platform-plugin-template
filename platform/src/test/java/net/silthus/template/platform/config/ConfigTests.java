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
