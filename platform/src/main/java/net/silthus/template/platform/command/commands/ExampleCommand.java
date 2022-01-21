package net.silthus.template.platform.command.commands;

import cloud.commandframework.CommandManager;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import net.silthus.template.platform.command.Command;
import net.silthus.template.platform.config.Config;
import net.silthus.template.platform.sender.Sender;

import static net.silthus.template.platform.config.ConfigKeys.TEST;
import static net.silthus.template.platform.locale.Messages.CONFIG_RELOADED;
import static net.silthus.template.platform.locale.Messages.TEST_MESSAGE;
import static net.silthus.template.platform.plugin.TemplatePlugin.NAMESPACE;

public class ExampleCommand implements Command {
    private final Config config;

    public ExampleCommand(Config config) {this.config = config;}

    @Override
    public void register(CommandManager<Sender> commandManager, AnnotationParser<Sender> parser) {
        parser.parse(this);
    }

    @CommandMethod(NAMESPACE + " reload")
    @CommandPermission(NAMESPACE + ".command.reload")
    public void reload(Sender sender) {
        config.reload();
        CONFIG_RELOADED.send(sender);
    }

    @CommandMethod(NAMESPACE + " test [text]")
    @CommandPermission(NAMESPACE + ".command.template")
    public void example(Sender sender, @Argument("text") @Greedy String text) {
        if (text == null)
            text = config.get(TEST);
        TEST_MESSAGE.send(sender, text);
    }
}
