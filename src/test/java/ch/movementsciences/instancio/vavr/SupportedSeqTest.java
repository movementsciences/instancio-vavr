/*
 * Copyright (c) 2023 MovementSciences AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.movementsciences.instancio.vavr;

import io.vavr.collection.*;
import org.instancio.Instancio;
import org.instancio.TypeToken;
import org.instancio.internal.util.Constants;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.root;

class SupportedSeqTest {
    private static java.util.List<Arguments> args() {
        return java.util.List.of(
                Arguments.of(new TypeToken<Seq<UUID>>() {}, Seq.class),
                Arguments.of(new TypeToken<IndexedSeq<UUID>>() {}, IndexedSeq.class),
                Arguments.of(new TypeToken<Array<UUID>>() {}, Array.class),
                Arguments.of(new TypeToken<Vector<UUID>>() {}, Vector.class),
                Arguments.of(new TypeToken<LinearSeq<UUID>>() {}, LinearSeq.class),
                Arguments.of(new TypeToken<List<UUID>>() {}, List.class),
                Arguments.of(new TypeToken<Stream<UUID>>() {}, Stream.class),
                Arguments.of(new TypeToken<Queue<UUID>>() {}, Queue.class)
        );
    }

    @ParameterizedTest
    @MethodSource("args")
    <C extends Seq<UUID>> void verify(final TypeToken<C> type, final Class<?> expectedSubtype) {
        verifyCreate(type, expectedSubtype);
        verifyCreateWithSize(type, expectedSubtype);
    }

    private static <C extends Seq<UUID>> void verifyCreate(final TypeToken<C> type, final Class<?> expectedSubtype) {
        final var result = Instancio.create(type);

        assertThat(result)
                .as("Failed type: %s, expected subtype: %s", type.get(), expectedSubtype)
                .isInstanceOf(expectedSubtype);
        assertThat(result.size())
                .isBetween(Constants.MIN_SIZE, Constants.MAX_SIZE);
    }

    private static <C extends Seq<UUID>> void verifyCreateWithSize(final TypeToken<C> type,
                                                                 final Class<?> expectedSubtype) {
        final int size = 5;
        final var expected = Instancio.create(UUID.class);
        final var result = Instancio.of(type)
                .generate(root(), gen -> GenVavr.seq().size(size).with(expected))
                .create();

        assertThat(result)
                .as("Failed type: %s, expected subtype: %s", type.get(), expectedSubtype)
                .hasSize(size + 1) // plus expected element
                .contains(expected)
                .isInstanceOf(expectedSubtype);
    }
}
