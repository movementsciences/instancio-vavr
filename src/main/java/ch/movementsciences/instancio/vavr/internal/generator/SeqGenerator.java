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

import ch.movementsciences.instancio.vavr.generator.specs.SeqSpecs;
import org.instancio.Random;
import org.instancio.generator.Generator;
import org.instancio.generator.GeneratorContext;
import org.instancio.generator.Hints;
import org.instancio.internal.ApiValidator;
import org.instancio.internal.generator.InternalContainerHint;
import org.instancio.internal.generator.InternalGeneratorHint;
import org.instancio.internal.util.Constants;
import org.instancio.internal.util.NumberUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SeqGenerator<T> implements Generator<Collection<T>>, SeqSpecs<T> {

    private GeneratorContext context;
    private int minSize = Constants.MIN_SIZE;
    private int maxSize = Constants.MAX_SIZE;
    private Class<?> type;
    protected List<Object> withElements;

    @Override
    public void init(final GeneratorContext context) {
        this.context = context;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<T> generate(final Random random) {
        final List<T> list = new ArrayList<>();
        if (withElements != null) {
            list.addAll((Collection<? extends T>) withElements);
        }
        return list;
    }

    @Override
    public Hints hints() {
        final int generateEntries = context.random().intRange(minSize, maxSize);

        final Hints.Builder builder = Hints.builder()
                .with(InternalContainerHint.builder()
                        .generateEntries(generateEntries)
                        .addFunction((List<T> list, Object... args) -> list.add((T) args[0]))
                        .build());

        if (type != null) {
            builder.with(InternalGeneratorHint.builder()
                    .targetClass(type)
                    .build());
        }

        return builder.build();
    }

    @Override
    public SeqGenerator<T> size(final int size) {
        this.minSize = ApiValidator.validateSize(size);
        this.maxSize = size;
        return this;
    }

    @Override
    public SeqGenerator<T> minSize(final int size) {
        this.minSize = ApiValidator.validateSize(size);
        this.maxSize = NumberUtils.calculateNewMaxSize(maxSize, minSize);
        return this;
    }

    @Override
    public SeqGenerator<T> maxSize(final int size) {
        this.maxSize = ApiValidator.validateSize(size);
        this.minSize = NumberUtils.calculateNewMinSize(minSize, maxSize);
        return this;
    }

    @Override
    public SeqGenerator<T> subtype(final Class<?> type) {
        this.type = ApiValidator.notNull(type, "type must not be null");
        return this;
    }

    @Override
    @SafeVarargs
    public final SeqGenerator<T> with(Object... elements) {
        ApiValidator.notEmpty(elements, "'seq().with(...)' must contain at least one element");
        if (withElements == null) {
            withElements = new ArrayList<>();
        }
        Collections.addAll(withElements, elements);
        return this;
    }

}
