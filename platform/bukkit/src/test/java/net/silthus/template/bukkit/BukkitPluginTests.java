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
