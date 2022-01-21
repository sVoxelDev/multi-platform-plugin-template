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

    @DisplayName("/template test <text>")
    @Nested
    class testCommand {
        @Test
        void sends_input_message() {
            cmd("template test Hi!");
            assertLastMessageIs(TEST_MESSAGE.build("Hi!"));
        }

        @Test
        void given_no_input_uses_config_value() {
            cmd("template test");
            assertLastMessageIs(TEST_MESSAGE.build(config.get(TEST)));
        }
    }
}