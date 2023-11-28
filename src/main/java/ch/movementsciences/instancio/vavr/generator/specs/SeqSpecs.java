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

package ch.movementsciences.instancio.vavr.generator.specs;

import org.instancio.generator.specs.SizeGeneratorSpec;
import org.instancio.generator.specs.SubtypeGeneratorSpec;

import java.util.Collection;

public interface SeqSpecs<T> extends
        SizeGeneratorSpec<Collection<T>>,
        SubtypeGeneratorSpec<Collection<T>> {

    @Override
    SeqSpecs<T> size(int size);

    @Override
    SeqSpecs<T> minSize(int size);

    @Override
    SeqSpecs<T> maxSize(int size);

    @Override
    SeqSpecs<T> subtype(Class<?> type);

    SeqSpecs<T> with(Object... elements);
}
