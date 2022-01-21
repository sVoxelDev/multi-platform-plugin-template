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

package net.silthus.template.platform.command;

import cloud.commandframework.CommandManager;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import net.silthus.template.platform.sender.Sender;
import net.silthus.template.platform.sender.SenderMock;
import org.junit.jupiter.api.BeforeEach;

import static net.silthus.template.platform.command.CommandTestUtils.createCommandManager;
import static net.silthus.template.platform.sender.SenderMock.senderMock;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class CommandTest {

    protected CommandManager<Sender> commandManager;
    protected SenderMock sender;
    protected Commands commands;

    @BeforeEach
    void setUpBase() {
        commandManager = createCommandManager();
        commands = new Commands(commandManager);
        sender = senderMock();
    }

    protected void register(Command command) {
        commands.register(command);
    }

    @SneakyThrows
    protected Sender cmd(String command) {
        return commandManager.executeCommand(sender, command).get().getCommandContext().getSender();
    }

    protected void cmdFails(String command, Class<? extends Exception> expectedException) {
        try {
            cmd(command);
        } catch (Exception e) {
            assertThat(e).getRootCause().isInstanceOf(expectedException);
        }
    }

    protected void assertLastMessageIs(Component component) {
        sender.assertLastMessageIs(component);
    }

}
