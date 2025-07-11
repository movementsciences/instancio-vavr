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
import ch.movementsciences.instancio.vavr.internal.builder.SeqBuilder;
import io.vavr.collection.List;
import org.instancio.Random;
import org.instancio.generator.Generator;
import org.instancio.generator.GeneratorContext;
import org.instancio.generator.Hints;
import org.instancio.internal.ApiValidator;
import org.instancio.internal.generator.InternalContainerHint;
import org.instancio.internal.generator.InternalGeneratorHint;
import org.instancio.internal.util.Constants;
import org.instancio.internal.util.NumberUtils;

public class SeqGenerator<T> implements Generator<SeqBuilder<T>>, SeqSpecs<T> {

    private GeneratorContext context;
    private int minSize = Constants.MIN_SIZE;
    private int maxSize = Constants.MAX_SIZE;
    private Class<?> subtype;
    private List<T> withElements = List.empty();

    @Override
    public void init(final GeneratorContext context) {
        this.context = context;
    }

    @Override
    public SeqBuilder<T> generate(final Random random) {
        return SeqBuilder.from(withElements);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Hints hints() {
        final int generateEntries = context.random().intRange(minSize, maxSize);

        final var hintsBuilder = Hints.builder()
                .with(InternalContainerHint.builder()
                        .generateEntries(generateEntries)
                        .addFunction((SeqBuilder<T> builder, Object... args) -> builder.add((T) args[0]))
                        .build());

        if (subtype != null) {
            hintsBuilder.with(InternalGeneratorHint.builder()
                    .targetClass(subtype)
                    .build());
        }

        return hintsBuilder.build();
    }

    @Override
    public SeqSpecs<T> size(final int size) {
        this.minSize = ApiValidator.validateSize(size);
        this.maxSize = size;
        return this;
    }

    @Override
    public SeqSpecs<T> minSize(final int size) {
        this.minSize = ApiValidator.validateSize(size);
        this.maxSize = NumberUtils.calculateNewMaxSize(maxSize, minSize);
        return this;
    }

    @Override
    public SeqSpecs<T> maxSize(final int size) {
        this.maxSize = ApiValidator.validateSize(size);
        this.minSize = NumberUtils.calculateNewMinSize(minSize, maxSize);
        return this;
    }

    @Override
    public SeqSpecs<T> subtype(final Class<?> type) {
        this.subtype = ApiValidator.notNull(type, "type must not be null");
        return this;
    }

    @Override
    @SafeVarargs
    public final SeqSpecs<T> with(T... elements) {
        ApiValidator.notEmpty(elements, "'seq().with(...)' must contain at least one element");
        withElements = withElements.appendAll(List.of(elements));
        return this;
    }
}
