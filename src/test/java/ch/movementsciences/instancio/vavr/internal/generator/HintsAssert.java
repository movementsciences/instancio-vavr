/*
 * Copyright 2022-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.movementsciences.instancio.vavr.internal.generator;

import org.assertj.core.api.AbstractAssert;
import org.instancio.generator.AfterGenerate;
import org.instancio.generator.Hints;
import org.instancio.internal.generator.InternalContainerHint;
import org.instancio.internal.generator.InternalGeneratorHint;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnusedReturnValue")
public class HintsAssert extends AbstractAssert<HintsAssert, Hints> {

    private HintsAssert(Hints actual) {
        super(actual, HintsAssert.class);
    }

    public static HintsAssert assertHints(Hints actual) {
        return new HintsAssert(actual);
    }

    public HintsAssert afterGenerate(AfterGenerate expected) {
        assertThat(actual.afterGenerate()).isEqualTo(expected);
        return this;
    }

    public HintsAssert containerHintGenerateEntriesIsBetween(int min, int max) {
        assertThat(actual.get(InternalContainerHint.class).generateEntries()).isBetween(min, max);
        return this;
    }

    public HintsAssert nullableResult(boolean expected) {
        assertThat(actual.get(InternalGeneratorHint.class).nullableResult()).isEqualTo(expected);
        return this;
    }
}
