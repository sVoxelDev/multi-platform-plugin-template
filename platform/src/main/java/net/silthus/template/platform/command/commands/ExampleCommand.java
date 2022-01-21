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

    public ExampleCommand(Config config) {
        this.config = config;
    }

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
