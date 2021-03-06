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
import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.captions.Caption;
import cloud.commandframework.context.CommandContext;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import net.silthus.template.platform.sender.Sender;
import org.jetbrains.annotations.NotNull;

import static cloud.commandframework.arguments.parser.ParserParameters.empty;
import static io.leangen.geantyref.TypeToken.get;
import static net.silthus.template.platform.sender.SenderMock.senderMock;
import static org.assertj.core.api.Assertions.assertThat;

@Getter
@Setter
public abstract class ParserTest<T> {

    private final CommandManager<Sender> commandManager;
    private ArgumentParser<Sender, T> parser;

    protected ParserTest() {
        this.commandManager = CommandTestUtils.createCommandManager();
    }

    @NotNull
    private CommandContext<Sender> createContext() {
        return new CommandContext<>(senderMock(), commandManager);
    }

    protected ArgumentParseResult<T> parse(String... input) {
        return parser.parse(createContext(), new ArrayDeque<>(List.of(input)));
    }

    protected void assertParseFailure(Class<? extends Throwable> exception, String... input) {
        final ArgumentParseResult<T> result = parse(input);
        assertThat(result.getFailure()).isPresent().get().isInstanceOf(exception);
    }

    protected void assertParseSuccessful(String input, T expected) {
        assertThat(parse(input).getParsedValue()).isPresent().get().isEqualTo(expected);
    }

    @NotNull
    protected String getCaption(Caption caption) {
        return commandManager.getCaptionRegistry().getCaption(caption, senderMock());
    }

    @NotNull
    protected Optional<ArgumentParser<Sender, T>> getParser(Class<T> type) {
        return commandManager.getParserRegistry().createParser(get(type), empty());
    }
}
