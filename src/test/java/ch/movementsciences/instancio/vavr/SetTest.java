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

import io.vavr.collection.HashSet;
import io.vavr.collection.LinkedHashSet;
import io.vavr.collection.Set;
import org.instancio.Instancio;
import org.instancio.TypeToken;
import org.instancio.internal.util.Constants;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;
import static org.instancio.Select.types;

@ExtendWith(InstancioExtension.class)
class SetTest {
    private static final int EXPECTED_SIZE = 10;

    private static class Holder {
        public Set<String> set;
    }

    @Test
    void createListViaTypeToken() {
        final var result = Instancio.create(new TypeToken<Set<String>>() {});

        assertThat(result).isInstanceOf(Set.class);
        assertThat(result.size()).isBetween(Constants.MIN_SIZE, Constants.MAX_SIZE);
    }

    @Test
    void generatorSpecSize() {
        final var result = Instancio.of(new TypeToken<Set<String>>() {})
                .generate(types().of(Set.class), GenVavr.set().size(EXPECTED_SIZE))
                .create();

        assertThat(result)
                .isInstanceOf(HashSet.class)
                .hasSize(EXPECTED_SIZE)
                .doesNotContainNull();
    }

    @Test
    void generatorSpecSubtype() {
        final var result = Instancio.of(Holder.class)
                .subtype(all(Set.class), LinkedHashSet.class)
                .create();

        assertThat(result.set).isInstanceOf(LinkedHashSet.class);
    }

    @Test
    void subtype() {
        final var result = Instancio.of(Holder.class)
                .subtype(all(Set.class), LinkedHashSet.class)
                .generate(all(Set.class), GenVavr.set().size(EXPECTED_SIZE))
                .create();

        assertThat(result.set)
                .isInstanceOf(LinkedHashSet.class)
                .hasSize(EXPECTED_SIZE)
                .doesNotContainNull();
    }

    @Test
    void defaultType() {
        final var result = Instancio.of(Holder.class)
                .create();

        assertThat(result.set)
                .isInstanceOf(HashSet.class)
                .doesNotContainNull();
    }
}