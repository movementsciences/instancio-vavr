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

package ch.movementsciences.instancio.vavr.internal.spi;

import static ch.movementsciences.instancio.vavr.internal.util.VavrFunctions.fromCollection;
import static ch.movementsciences.instancio.vavr.internal.util.VavrFunctions.fromMap;

import java.util.Collections;
import java.util.function.Function;

import org.instancio.internal.spi.InternalContainerFactoryProvider;
import org.instancio.internal.util.CollectionUtils;

import io.vavr.collection.Array;
import io.vavr.collection.CharSeq;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.LinkedHashSet;
import io.vavr.collection.List;
import io.vavr.collection.Queue;
import io.vavr.collection.Stream;
import io.vavr.collection.TreeMap;
import io.vavr.collection.TreeSet;
import io.vavr.collection.Vector;

public class VavrContainerFactory implements InternalContainerFactoryProvider {

    private static final java.util.Map<Class<?>, Function<?, ?>> MAPPING_FUNCTIONS = getMappingFunctions();
    private static final java.util.Set<Class<?>> CONTAINER_CLASSES = getContainerClasses();


    @SuppressWarnings("unchecked")
    private static java.util.Map<Class<?>, Function<?, ?>> getMappingFunctions() {
        final java.util.Map<Class<?>, Function<?, ?>> map = new java.util.HashMap<>();

        // Collections
        map.put(Array.class, fromCollection(Array::ofAll));
        map.put(CharSeq.class, fromCollection(CharSeq::ofAll));
        map.put(Vector.class, fromCollection(Vector::ofAll));
        map.put(List.class, fromCollection(List::ofAll));
        map.put(Stream.class, fromCollection(Stream::ofAll));
        map.put(Queue.class, fromCollection(Queue::ofAll));
        map.put(LinkedHashSet.class, fromCollection(LinkedHashSet::ofAll));
        map.put(HashSet.class, fromCollection(HashSet::ofAll));
        map.put(TreeSet.class, fromCollection(TreeSet::ofAll));

        // Maps
        map.put(LinkedHashMap.class, fromMap(LinkedHashMap::ofAll));
        map.put(HashMap.class, fromMap(HashMap::ofAll));
        map.put(TreeMap.class, fromMap(TreeMap::ofAll));

        return Collections.unmodifiableMap(map);
    }

    private static java.util.Set<Class<?>> getContainerClasses() {
        return Collections.unmodifiableSet(CollectionUtils.asSet(
                Array.class,
                CharSeq.class,
                Vector.class,
                List.class,
                Stream.class,
                Queue.class,
                LinkedHashSet.class,
                HashSet.class,
                TreeSet.class,
                LinkedHashMap.class,
                HashMap.class,
                TreeMap.class
        ));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, R> Function<T, R> getMappingFunction(Class<R> type, java.util.List<Class<?>> typeArguments) {
        return null;
        //return (Function<T, R>) MAPPING_FUNCTIONS.get(type);
    }

    @Override
    public boolean isContainer(final Class<?> type) {
        return CONTAINER_CLASSES.contains(type);
    }
}
