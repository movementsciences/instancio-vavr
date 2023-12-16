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

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import org.instancio.Instancio;
import org.instancio.TypeToken;
import org.instancio.internal.util.Constants;
import org.instancio.junit.InstancioExtension;
import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;
import static org.instancio.Select.types;

@ExtendWith(InstancioExtension.class)
class SeqTest {
    private static final int EXPECTED_SIZE = 10;

    private static class Holder {
        public Seq<String> seq;
    }

    @Test
    void createListViaTypeToken() {
        final var result = Instancio.create(new TypeToken<Seq<String>>() {});

        assertThat(result)
                .isInstanceOf(List.class)
                .hasSizeBetween(Constants.MIN_SIZE, Constants.MAX_SIZE);
    }

    @Test
    void generatorSpecSize() {
        final var result = Instancio.of(new TypeToken<Seq<String>>() {})
                .generate(types().of(Seq.class), GenVavr.seq().size(EXPECTED_SIZE))
                .create();

        assertThat(result)
                .isInstanceOf(List.class)
                .hasSize(EXPECTED_SIZE)
                .doesNotContainNull();
    }

    @Test
    void generatorSettingsSize() {
        final var result = Instancio.of(new TypeToken<Seq<String>>() {})
                .withSettings(Settings.create()
                        .set(Keys.COLLECTION_MIN_SIZE, 1)
                        .set(Keys.COLLECTION_MAX_SIZE, 10))
                .create();

        assertThat(result)
                .isInstanceOf(List.class)
                .hasSizeBetween(1, 10);
    }

    @Test
    void generatorSpecSubtype() {
        final var result = Instancio.of(Holder.class)
                .subtype(all(Seq.class), Vector.class)
                .create();

        assertThat(result.seq)
                .isInstanceOf(Seq.class)
                .isExactlyInstanceOf(Vector.class);
    }

    @Test
    void subtype() {
        final var result = Instancio.of(Holder.class)
                .subtype(all(Seq.class), Vector.class)
                .generate(all(Seq.class), GenVavr.seq().size(EXPECTED_SIZE))
                .create();

        assertThat(result.seq)
                .isInstanceOf(Seq.class)
                .isExactlyInstanceOf(Vector.class)
                .hasSize(EXPECTED_SIZE)
                .doesNotContainNull();
    }

    @Test
    void defaultType() {
        final var result = Instancio.of(Holder.class)
                .create();

        assertThat(result.seq)
                .isInstanceOf(List.class)
                .doesNotContainNull();
    }
}
