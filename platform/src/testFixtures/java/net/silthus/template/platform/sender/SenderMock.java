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

package net.silthus.template.platform.sender;

import java.util.LinkedList;
import java.util.Queue;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.silthus.template.identity.Identity;

import static net.silthus.template.IdentityHelper.randomIdentity;
import static org.assertj.core.api.Assertions.assertThat;

@Getter
public class SenderMock implements Sender {

    private final Identity identity;
    private final Queue<Component> messages = new LinkedList<>();

    public SenderMock(Identity identity) {
        this.identity = identity;
    }

    public static SenderMock senderMock() {
        return new SenderMock(randomIdentity());
    }

    public static SenderMock senderMock(Identity identity) {
        return new SenderMock(identity);
    }

    @Override
    public void sendMessage(Component message) {
        messages.add(message);
    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    @Override
    public void performCommand(String commandLine) {

    }

    @Override
    public boolean isConsole() {
        return false;
    }

    public void assertLastMessageIs(Component component) {
        assertThat(messages.peek()).isEqualTo(component);
    }
}
