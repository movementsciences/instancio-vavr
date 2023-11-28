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

import static io.vavr.API.*;
import static java.util.function.Predicate.isEqual;

import java.util.ArrayList;
import java.util.Collection;

import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.collection.Queue;
import io.vavr.collection.Seq;
import io.vavr.collection.Stream;
import io.vavr.collection.Vector;

// TODO delete
public record SeqBuilder<T>(Class<?> type, Collection<T> items) {

    public static <S> SeqBuilder<S> of(Class<?> type) {
        return new SeqBuilder<>(type, new ArrayList<>());
    }

    public void add(T item) {
        items.add(item);
    }

    public Seq<T> build() {
        return Match(type).of(
                Case($(isEqual(Array.class)), () -> Array.ofAll(items)),
                Case($(isEqual(Vector.class)), () -> Vector.ofAll(items)),
                Case($(isEqual(List.class)), () -> List.ofAll(items)),
                Case($(isEqual(Stream.class)), () -> Stream.ofAll(items)),
                Case($(isEqual(Queue.class)), () -> Queue.ofAll(items)),
                Case($(), () -> null)
        );  
    }
    
}
