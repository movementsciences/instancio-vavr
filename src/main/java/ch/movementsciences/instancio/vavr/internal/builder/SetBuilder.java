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

import io.vavr.collection.HashSet;
import io.vavr.collection.LinkedHashSet;
import io.vavr.collection.Set;

import java.util.Collection;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static java.util.function.Predicate.isEqual;

public record SetBuilder<T>(Collection<T> items) implements VavrBuilder<Set<T>> {

    public static <T> SetBuilder<T> from(Set<T> elements) {
        return new SetBuilder<>(elements.toJavaList());
    }

    public void add(T item) {
        items.add(item);
    }

    @Override
    public Set<T> build(Class<?> type) {
        return Match(type).of(
                Case($(isEqual(LinkedHashSet.class)), () -> LinkedHashSet.ofAll(items)),
                Case($(isEqual(HashSet.class)), () -> HashSet.ofAll(items)),
                //Case($(isEqual(TreeSet.class)), () -> TreeSet.ofAll(items)),
                Case($(), () -> HashSet.ofAll(items))
        );  
    }
    
}
