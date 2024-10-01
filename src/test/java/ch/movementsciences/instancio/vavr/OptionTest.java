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

import io.vavr.control.Option;
import org.instancio.Instancio;
import org.instancio.TypeToken;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.types;

@ExtendWith(InstancioExtension.class)
class OptionTest {

    private static class Holder {
        public Option<String> option;
    }

    @Test
    void createOptionViaTypeToken() {
        final var result = Instancio.create(new TypeToken<Option<String>>() {});

        assertThat(result).isInstanceOf(Option.class);
        assertThat(result.get()).isInstanceOf(String.class);
    }

    @Test
    void generatorSpecNone() {
        final var result = Instancio.of(new TypeToken<Option<String>>() {})
                .generate(types().of(Option.class), GenVavr.option().none())
                .create();

        assertThat(result).isInstanceOf(Option.class);
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void defaultType() {
        final var result = Instancio.of(OptionTest.Holder.class)
            .create();

        assertThat(result.option)
            .isInstanceOf(Option.class)
            .doesNotContainNull();
    }
}