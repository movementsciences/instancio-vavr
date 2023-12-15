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

import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.Seed;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(InstancioExtension.class)
class VavrRepeatabilityTest {

    private static final long SEED = 12345L;

    private static AllSupportedVavrTypes first;

    @Seed(SEED)
    @Order(1)
    @Test
    void first() {
        first = Instancio.create(AllSupportedVavrTypes.class);

        assertThat(first).hasNoNullFieldsOrProperties();
    }

    @Seed(SEED)
    @Order(2)
    @Test
    void second() {
        AllSupportedVavrTypes second = Instancio.create(AllSupportedVavrTypes.class);

        assertThat(second)
                .usingRecursiveComparison()
                .isEqualTo(first);
    }
}
