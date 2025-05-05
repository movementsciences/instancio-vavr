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

import ch.movementsciences.instancio.vavr.internal.builder.MapBuilder;
import io.vavr.Tuple2;
import org.instancio.generator.specs.SizeGeneratorSpec;
import org.instancio.generator.specs.SubtypeGeneratorSpec;

import java.util.Comparator;

public interface MapSpecs<K, V> extends
        SizeGeneratorSpec<MapBuilder<K, V>>,
        SubtypeGeneratorSpec<MapBuilder<K, V>>
{
    @Override
    MapSpecs<K, V> size(int size);

    @Override
    MapSpecs<K, V> minSize(int size);

    @Override
    MapSpecs<K, V> maxSize(int size);

    @Override
    MapSpecs<K, V> subtype(Class<?> type);

    @SuppressWarnings("unchecked")
    MapSpecs<K, V> with(Tuple2<K, V>... elements);

    MapSpecs<K, V> comparator(Comparator<K> comparator);
}
