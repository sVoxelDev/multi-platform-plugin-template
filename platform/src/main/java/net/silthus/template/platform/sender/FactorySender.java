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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.silthus.template.identity.Identity;

import static net.kyori.adventure.text.JoinConfiguration.noSeparators;

/**
 * Simple implementation of {@link Sender} using a {@link SenderFactory}.
 *
 * @param <T> the command sender type
 */
@EqualsAndHashCode(of = {"identity"})
final class FactorySender<T> implements Sender {

    private final SenderFactory<T> factory;
    @Getter(AccessLevel.PROTECTED)
    private final T handle;
    @Getter
    private final Identity identity;
    @Getter
    private final boolean console;

    FactorySender(SenderFactory<T> factory, T sender) {
        this.factory = factory;
        this.handle = sender;
        this.console = factory.isConsole(sender);
        this.identity = factory.getIdentity(sender);
    }

    // A small utility method which splits components built using
    // > join(newLine(), components...)
    // back into separate components.
    private static Iterable<Component> splitNewlines(Component message) {
        if (message instanceof TextComponent && message.style().isEmpty() && !message.children().isEmpty() && ((TextComponent) message).content().isEmpty()) {
            LinkedList<List<Component>> split = new LinkedList<>();
            split.add(new ArrayList<>());

            for (Component child : message.children()) {
                if (Component.newline().equals(child)) {
                    split.add(new ArrayList<>());
                } else {
                    Iterator<Component> splitChildren = splitNewlines(child).iterator();
                    if (splitChildren.hasNext()) {
                        split.getLast().add(splitChildren.next());
                    }
                    while (splitChildren.hasNext()) {
                        split.add(new ArrayList<>());
                        split.getLast().add(splitChildren.next());
                    }
                }
            }

            return split.stream().map(input -> switch (input.size()) {
                case 0 -> Component.empty();
                case 1 -> input.get(0);
                default -> Component.join(noSeparators(), input);
            }).collect(Collectors.toList());
        }

        return Collections.singleton(message);
    }

    @Override
    public void sendMessage(Component message) {
        if (isConsole()) {
            for (Component line : splitNewlines(message)) {
                factory.sendMessage(handle, line);
            }
        } else {
            factory.sendMessage(handle, message);
        }
    }

    @Override
    public boolean hasPermission(String permission) {
        return isConsole() || factory.hasPermission(handle, permission);
    }

    @Override
    public void performCommand(String commandLine) {
        factory.performCommand(handle, commandLine);
    }

    @Override
    public boolean isValid() {
        return isConsole() || factory.isPlayerOnline(getUniqueId());
    }
}
