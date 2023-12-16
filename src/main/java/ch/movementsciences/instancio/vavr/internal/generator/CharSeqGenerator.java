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

import ch.movementsciences.instancio.vavr.generator.specs.CharSeqSpecs;
import io.vavr.collection.CharSeq;
import io.vavr.collection.List;
import org.instancio.Random;
import org.instancio.generator.GeneratorContext;
import org.instancio.internal.ApiValidator;
import org.instancio.internal.generator.AbstractGenerator;
import org.instancio.internal.generator.lang.StringGenerator;
import org.instancio.internal.util.NumberUtils;
import org.instancio.settings.Keys;
import org.instancio.support.Global;

public class CharSeqGenerator extends AbstractGenerator<CharSeq> implements CharSeqSpecs {

    protected int minSize;
    protected int maxSize;
    private CharSeq withElements = CharSeq.empty();

    public CharSeqGenerator(final GeneratorContext context) {
        super(context);
        super.nullable(context.getSettings().get(Keys.COLLECTION_NULLABLE));
        this.minSize = context.getSettings().get(Keys.COLLECTION_MIN_SIZE);
        this.maxSize = context.getSettings().get(Keys.COLLECTION_MAX_SIZE);
    }

    public CharSeqGenerator() {
        this(Global.generatorContext());
    }

    @Override
    public String apiMethod() {
        return null;
    }

    @Override
    protected CharSeq tryGenerateNonNull(final Random random) {
        final var str = new StringGenerator(getContext())
                .minLength(maxSize)
                .maxLength(maxSize)
                .generate(random);

        return CharSeq.of(str);
    }

    @Override
    public CharSeqSpecs size(final int size) {
        this.minSize = ApiValidator.validateSize(size);
        this.maxSize = size;
        return this;
    }

    @Override
    public CharSeqSpecs minSize(final int size) {
        this.minSize = ApiValidator.validateSize(size);
        this.maxSize = NumberUtils.calculateNewMaxSize(maxSize, minSize);
        return this;
    }

    @Override
    public CharSeqSpecs maxSize(final int size) {
        this.maxSize = ApiValidator.validateSize(size);
        this.minSize = NumberUtils.calculateNewMinSize(minSize, maxSize);
        return this;
    }

    @Override
    public final CharSeqSpecs with(String str) {
        ApiValidator.isFalse(str.isEmpty(), "'chaSeq().with(...)' must contain a non-empty string");
        withElements = withElements.appendAll(List.ofAll(str.toCharArray()));
        return this;
    }

    @Override
    public CharSeqSpecs nullable() {
        super.nullable(true);
        return this;
    }

    @Override
    public CharSeqSpecs nullable(final boolean isNullable) {
        super.nullable(isNullable);
        return this;
    }
}
