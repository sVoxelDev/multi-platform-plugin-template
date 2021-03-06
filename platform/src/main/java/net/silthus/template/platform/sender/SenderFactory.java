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

import net.kyori.adventure.text.Component;
import net.silthus.template.identity.Identity;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Factory class to make a thread-safe sender instance.
 *
 * @param <T> the command sender type
 */
public abstract class SenderFactory<T> implements AutoCloseable, PlayerOnlineChecker {

    protected abstract Class<T> getSenderType();

    protected abstract Identity getIdentity(T sender);

    protected abstract void sendMessage(T sender, Component message);

    protected abstract boolean hasPermission(T sender, String node);

    protected abstract void performCommand(T sender, String command);

    protected abstract boolean isConsole(T sender);

    public final Sender wrap(@NonNull T sender) {
        if (!getSenderType().isInstance(sender))
            throw new IllegalSenderType();
        return new FactorySender<>(this, sender);
    }

    @SuppressWarnings("unchecked")
    public final T unwrap(@NonNull Sender sender) {
        if (sender instanceof FactorySender<?> factorySender)
            return (T) factorySender.getHandle();
        throw new IllegalSenderType();
    }

    @Override
    public void close() {

    }

    public static final class IllegalSenderType extends RuntimeException {
    }
}
