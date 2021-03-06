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
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import lombok.SneakyThrows;
import net.silthus.template.platform.config.adapter.ConfigurationAdapter;
import net.silthus.template.platform.config.adapter.ConfigurationAdapters;

public final class TestConfigurationAdapter {

    public static final String TEST_CONFIG_NAME = "test-config.yaml";

    private TestConfigurationAdapter() {
    }

    @SneakyThrows
    public static TemplateConfig testConfig() {
        return new TemplateConfig(testConfigAdapter(File.createTempFile("template-config-", ".yaml")));
    }

    @SneakyThrows
    public static ConfigurationAdapter testConfigAdapter(File target) {
        return testConfigAdapter(getTestConfigAsStream(), target);
    }

    @SneakyThrows
    public static ConfigurationAdapter testConfigAdapter(InputStream source, File target) {
        copyConfig(source, target);
        return ConfigurationAdapters.YAML.create(target);
    }

    private static InputStream getTestConfigAsStream() {
        return Objects.requireNonNull(TestConfigurationAdapter.class.getClassLoader().getResourceAsStream(TEST_CONFIG_NAME));
    }

    private static void copyConfig(InputStream source, File target) throws IOException {
        Files.copy(source, target.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
