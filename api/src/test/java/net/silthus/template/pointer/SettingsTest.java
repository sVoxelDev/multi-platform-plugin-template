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

package net.silthus.template.pointer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SettingsTest {

    static final Setting<String> DYNAMIC_TEST = Setting.setting(String.class, "dynamic", "foobar");
    static final Setting<String> DEFAULT_VAL_TEST = Setting.setting(String.class, "default", "test");
    static final Pointer<String> TEST_POINTER = Pointer.pointer(String.class, "test");

    @Test
    void create_isEmpty() {
        assertThat(Settings.createSettings().getSettings()).isEmpty();
    }

    @Test
    void populateWithInitialValues() {
        final Settings settings = Settings.settings()
            .withStatic(DEFAULT_VAL_TEST, "static")
            .withDynamic(DYNAMIC_TEST, () -> "dynamic")
            .create();
        assertThat(settings.get(DEFAULT_VAL_TEST)).isEqualTo("static");
        assertThat(settings.get(DYNAMIC_TEST)).isEqualTo("dynamic");
    }

    @Test
    void returnsDefaultValueIfNotSet() {
        final Settings settings = Settings.createSettings();
        assertThat(settings.get(DEFAULT_VAL_TEST)).isEqualTo("test");
    }

    @Test
    void givenNotSet_returnsProvidedDefault() {
        final Settings settings = Settings.createSettings();
        assertThat(settings.getOrDefault(DEFAULT_VAL_TEST, "foobar")).isEqualTo("foobar");
    }

    @Test
    void getOrDefault_valueIsSet_returnsValue() {
        final Settings settings = Settings.settings().withStatic(DEFAULT_VAL_TEST, "bob").create();
        assertThat(settings.getOrDefaultFrom(DEFAULT_VAL_TEST, () -> "bobby")).isEqualTo("bob");
    }

    @Test
    void set_updatesValue() {
        final Settings settings = Settings.settings().withStatic(DEFAULT_VAL_TEST, "bob").create();
        settings.set(DEFAULT_VAL_TEST, "bobby");
        assertThat(settings.get(DEFAULT_VAL_TEST)).isEqualTo("bobby");
    }

    @Test
    void copy_copiesAllSettings() {
        final Settings original = Settings.settings()
            .withStatic(DEFAULT_VAL_TEST, "static")
            .withDynamic(DYNAMIC_TEST, () -> "dynamic")
            .create();
        final Settings settings = original.copy().withStatic(DEFAULT_VAL_TEST, "foobar").create();

        assertThat(original.get(DEFAULT_VAL_TEST)).isEqualTo("static");
        assertThat(original.get(DYNAMIC_TEST)).isEqualTo("dynamic");

        assertThat(settings.get(DEFAULT_VAL_TEST)).isEqualTo("foobar");
        assertThat(settings.get(DYNAMIC_TEST)).isEqualTo("dynamic");
    }

    @Test
    void copy_copiesUnknown_Settings() {
        final Settings original = Settings.settings().withUnknown("test", setting -> "foobar").create();
        final Settings copy = original.copy().create();

        assertThat(copy.get(Setting.setting(String.class, "test", null))).isEqualTo("foobar");
    }

    @Test
    void given_unknown_type() {
        final Settings settings = Settings.settings().withUnknown("default", setting -> "foobar").create();
        assertThat(settings.get(DEFAULT_VAL_TEST)).isEqualTo("foobar");
    }

    @Test
    void given_point_in_settings_retrieves_pointer_value() {
        final Settings settings = Settings.settings()
            .withStatic(TEST_POINTER, "foobar")
            .withDynamic(DYNAMIC_TEST, () -> "barfoo")
            .create();
        assertThat(settings.get(TEST_POINTER)).isPresent().get().isEqualTo("foobar");
        assertThat(settings.get(DYNAMIC_TEST)).isEqualTo("barfoo");
    }
}
