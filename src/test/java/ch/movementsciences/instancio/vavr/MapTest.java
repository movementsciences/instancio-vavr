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

import io.vavr.collection.HashMap;
import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.Map;
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
class MapTest {
    private static final int EXPECTED_SIZE = 10;

    private static class Holder {
        public Map<Long, String> map;
    }

    @Test
    void createMapViaTypeToken() {
        final var result = Instancio.create(new TypeToken<Map<Long, String>>() {});

        assertThat(result).isInstanceOf(HashMap.class);
        assertThat(result.size()).isBetween(Constants.MIN_SIZE, Constants.MAX_SIZE);
    }

    @Test
    void generatorSpecSize() {
        final var result = Instancio.of(new TypeToken<Map<Long, String>>() {})
                .generate(types().of(Map.class), GenVavr.map().size(EXPECTED_SIZE))
                .create();

        assertThat(result)
                .isInstanceOf(Map.class)
                .hasSize(EXPECTED_SIZE)
                .doesNotContainNull();
    }

    @Test
    void generatorSpecSubtype() {
        final var result = Instancio.of(Holder.class)
                .subtype(all(Map.class), LinkedHashMap.class)
                .create();

        assertThat(result.map).isInstanceOf(LinkedHashMap.class);
    }

    @Test
    void subtype() {
        final var result = Instancio.of(Holder.class)
                .subtype(all(Map.class), LinkedHashMap.class)
                .generate(all(Map.class), GenVavr.map().size(EXPECTED_SIZE))
                .create();

        assertThat(result.map)
                .isInstanceOf(LinkedHashMap.class)
                .hasSize(EXPECTED_SIZE)
                .doesNotContainNull();
    }

    @Test
    void defaultType() {
        final var result = Instancio.of(Holder.class)
                .create();

        assertThat(result.map)
                .isInstanceOf(Map.class)
                .doesNotContainNull();
    }
}