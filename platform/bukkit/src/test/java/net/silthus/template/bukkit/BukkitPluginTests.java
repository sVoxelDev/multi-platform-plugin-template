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

package net.silthus.template.bukkit;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.ChatColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BukkitPluginTests extends BukkitTests {
    private BukkitLoader plugin;

    @BeforeEach
    void setUp() {
        plugin = MockBukkit.load(BukkitLoader.class);
    }

    @Nested
    class CommandTests {
        private PlayerMock player;

        @BeforeEach
        void setUp() {
            player = server.addPlayer();
        }

        @Test
        void given_player_has_no_permission_cannot_execute_command() {
            player.performCommand("myplugin test");
            assertLastMessageContains(player, "you do not have permission to perform this command");
        }

        @Nested
        class given_player_with_permission {
            @BeforeEach
            void setUp() {
                player.setOp(true);
            }

            @Test
            void then_prints_config_message() {
                player.performCommand("myplugin test");
                assertLastMessageIs(player, ChatColor.GREEN + "Message: Hi from the bukkit config.");
            }

            @Test
            void given_argument_prints_argument() {
                player.performCommand("myplugin test it works ;)");
                assertLastMessageIs(player, ChatColor.GREEN + "Message: it works ;).");
            }
        }
    }
}
