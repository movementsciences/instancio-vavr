/*
 * Copyright (c) 2023-2024 MovementSciences AG.
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

package ch.movementsciences.instancio.vavr.internal.generator;

import ch.movementsciences.instancio.vavr.generator.specs.OptionSpecs;
import io.vavr.control.Option;
import org.instancio.Random;
import org.instancio.generator.Generator;
import org.instancio.generator.GeneratorSpec;
import org.instancio.generator.Hints;
import org.instancio.internal.generator.InternalContainerHint;

public class OptionGenerator<T> implements Generator<Option<T>>, OptionSpecs<T> {

    private boolean none = false;
    private boolean allowNone = false;

    @Override
    public Option<T> generate(Random random) {
        if (none || random.diceRoll(allowNone)) {
            return Option.none();
        }
        return null; // must be null to delegate creation to the engine
    }

    @Override
    public Hints hints() {
        return Hints.builder()
            .with(InternalContainerHint.builder()
                .generateEntries(1)
                .createFunction(args -> Option.of(args[0]))
                .build())
            .build();
    }

    @Override
    public GeneratorSpec<Option<T>> allowNone() {
        allowNone = true;
        return this;
    }

    @Override
    public GeneratorSpec<Option<T>> none() {
        none = true;
        return this;
    }
}
