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

import ch.movementsciences.instancio.vavr.internal.builder.SetBuilder;
import org.instancio.generator.specs.SizeGeneratorSpec;
import org.instancio.generator.specs.SubtypeGeneratorSpec;

public interface SetSpecs<T> extends
        SizeGeneratorSpec<SetBuilder<T>>,
        SubtypeGeneratorSpec<SetBuilder<T>>
{
    @Override
    SetSpecs<T> size(int size);

    @Override
    SetSpecs<T> minSize(int size);

    @Override
    SetSpecs<T> maxSize(int size);

    @Override
    SetSpecs<T> subtype(Class<?> type);

    @SuppressWarnings("unchecked")
    SetSpecs<T> with(T ...elements);
}
