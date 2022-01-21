package net.silthus.template.platform.command.commands;

import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import net.silthus.template.platform.command.Command;
import net.silthus.template.platform.config.Config;
import net.silthus.template.platform.sender.Sender;

import static net.silthus.template.platform.config.ConfigKeys.TEST;
import static net.silthus.template.platform.locale.Messages.TEST_MESSAGE;

public class ExampleCommand implements Command {
    private final Config config;

    public ExampleCommand(Config config) {this.config = config;}

    @Override
    public void register(CommandManager<Sender> commandManager, AnnotationParser<Sender> parser) {
        parser.parse(this);
    }

    @CommandMethod("template test [text]")
    public void example(Sender sender, @Argument("text") String text) {
        if (text == null)
            text = config.get(TEST);
        TEST_MESSAGE.send(sender, text);
    }
}
