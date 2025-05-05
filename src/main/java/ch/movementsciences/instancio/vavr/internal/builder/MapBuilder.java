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

package ch.movementsciences.instancio.vavr.internal.builder;

import io.vavr.collection.HashMap;
import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.Map;
import io.vavr.collection.TreeMap;

import java.util.Comparator;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static java.util.function.Predicate.isEqual;

public record MapBuilder<K, V>(java.util.Map<K, V> items, Comparator<K> comparator) implements VavrBuilder<Map<K, V>> {

    public static <K, V> MapBuilder<K, V> from(Map<K, V> elements, Comparator<K> comparator) {
        return new MapBuilder<>(elements.toJavaMap(), comparator);
    }

    public void add(K key, V value) {
        items.put(key, value);
    }

    @Override
    public Map<K, V> build(Class<?> type) {
        return Match(type).of(
                Case($(isEqual(LinkedHashMap.class)), () -> LinkedHashMap.ofAll(items)),
                Case($(isEqual(HashMap.class)), () -> HashMap.ofAll(items)),
                Case($(isEqual(TreeMap.class)), () -> TreeMap.ofAll(comparator, items)),
                Case($(), () -> HashMap.ofAll(items))
        );  
    }
    
}
