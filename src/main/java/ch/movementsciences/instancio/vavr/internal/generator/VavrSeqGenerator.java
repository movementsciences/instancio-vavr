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

package ch.movementsciences.instancio.vavr.internal.generator;

import static io.vavr.API.*;
import static java.util.function.Predicate.isEqual;

import java.util.ArrayList;
import java.util.Collection;

import org.instancio.Random;
import org.instancio.generator.Generator;
import org.instancio.generator.GeneratorContext;
import org.instancio.generator.Hints;
import org.instancio.internal.ApiValidator;
import org.instancio.internal.generator.InternalContainerHint;
import org.instancio.internal.util.Constants;
import org.instancio.internal.util.NumberUtils;

import ch.movementsciences.instancio.vavr.generator.specs.VavrCollectionSpecs;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.collection.Queue;
import io.vavr.collection.Stream;
import io.vavr.collection.Traversable;
import io.vavr.collection.Vector;

public class VavrSeqGenerator<T> implements Generator<Collection<T>>, VavrCollectionSpecs<T> {
    private static final Class<?> DEFAULT_COLLECTION_TYPE = List.class; // NOPMD

    private GeneratorContext context;
    private int minSize = Constants.MIN_SIZE;
    private int maxSize = Constants.MAX_SIZE;
    private Class<?> vavrType = DEFAULT_COLLECTION_TYPE;

    @Override
    public void init(final GeneratorContext context) {
        this.context = context;
    }

    @Override
    public Collection<T> generate(final Random random) {
        return new ArrayList<>();
    }

    @Override
    public Hints hints() {
        final int generateEntries = context.random().intRange(minSize, maxSize);

        return Hints.builder()
                .with(InternalContainerHint.builder()
                        .generateEntries(generateEntries)
                        .addFunction((ArrayList<T> list, Object... args) -> list.add((T) args[0]))
                        .buildFunction(this::buildVavrCollection)
                        .build())
                .build();
    }

    @Override
    public VavrCollectionSpecs<T> size(final int size) {
        this.minSize = ApiValidator.validateSize(size);
        this.maxSize = size;
        return this;
    }

    @Override
    public VavrCollectionSpecs<T> minSize(final int size) {
        this.minSize = ApiValidator.validateSize(size);
        this.maxSize = NumberUtils.calculateNewMaxSize(maxSize, minSize);
        return this;
    }

    @Override
    public VavrCollectionSpecs<T> maxSize(final int size) {
        this.maxSize = ApiValidator.validateSize(size);
        this.minSize = NumberUtils.calculateNewMinSize(minSize, maxSize);
        return this;
    }

    @Override
    public VavrCollectionSpecs<T> subtype(final Class<?> type) {
        this.vavrType = ApiValidator.notNull(type, "type must not be null");
        return this;
    }

    private Traversable<T> buildVavrCollection(ArrayList<T> list) {
        return Match(vavrType).of(
                Case($(isEqual(Array.class)), () -> Array.ofAll(list)),
                Case($(isEqual(Vector.class)), () -> Vector.ofAll(list)),
                Case($(isEqual(List.class)), () -> List.ofAll(list)),
                Case($(isEqual(Stream.class)), () -> Stream.ofAll(list)),
                Case($(isEqual(Queue.class)), () -> Queue.ofAll(list)),
                Case($(), () -> null)
        );
    }
}
