/*
 * sChat, a Supercharged Minecraft Chat Plugin
 * Copyright (C) Silthus <https://www.github.com/silthus>
 * Copyright (C) sChat team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.silthus.template.platform.sender;

import java.util.UUID;
import net.kyori.adventure.text.Component;
import net.silthus.template.identity.Identified;
import net.silthus.template.identity.Identity;
import org.jetbrains.annotations.NotNull;

/**
 * Wrapper interface to represent a CommandSender/CommandSource within the common command implementations.
 */
public interface Sender extends Identified {

    UUID CONSOLE_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    String CONSOLE_NAME = "Console";

    Identity CONSOLE = Identity.identity(
        CONSOLE_ID,
        CONSOLE_NAME
    );

    default @NotNull UUID getUniqueId() {
        return getIdentity().getUniqueId();
    }

    default @NotNull String getName() {
        return getIdentity().getName();
    }

    default @NotNull Component getDisplayName() {
        return getIdentity().getDisplayName();
    }

    /**
     * Send a json message to the Sender.
     *
     * @param message the message to send.
     */
    void sendMessage(Component message);

    /**
     * Check if the Sender has a permission.
     *
     * @param permission the permission to check for
     * @return true if the sender has the permission
     */
    boolean hasPermission(String permission);

    /**
     * Makes the sender perform a command.
     *
     * @param commandLine the command
     */
    void performCommand(String commandLine);

    /**
     * Gets whether this sender is the console.
     *
     * @return if the sender is the console
     */
    boolean isConsole();

    /**
     * Gets whether this sender is still valid & receiving messages.
     *
     * @return if this sender is valid
     */
    default boolean isValid() {
        return true;
    }
}