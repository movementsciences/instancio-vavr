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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.movementsciences.instancio.vavr.internal.generator.CharSeqGenerator;
import io.vavr.collection.CharSeq;
import org.instancio.Random;
import org.instancio.exception.InstancioApiException;
import org.instancio.exception.InstancioException;
import org.instancio.generator.GeneratorContext;
import org.instancio.internal.util.Constants;
import org.instancio.settings.Settings;
import org.instancio.support.DefaultRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CharSeqSpecsTest {

    private static final int PERCENTAGE = Constants.RANGE_ADJUSTMENT_PERCENTAGE;
    private final GeneratorContext context = new GeneratorContext(Settings.defaults(), new DefaultRandom());
    private final CharSeqGeneratorExt<?> generator = new CharSeqGeneratorExt<>(context);

    @BeforeEach
    void setUp() {
        generator.minSize(0).minSize(1);
    }

    @Test
    @DisplayName("newMax < min: new min should be less than newMax by PERCENTAGE")
    void newMaxIsLessThanMin() {
        final var newMax = 50;

        generator.minSize(Integer.MAX_VALUE).maxSize(newMax);

        assertThat(generator.getMinSize())
                .isEqualTo(calculatePercentage(newMax, -PERCENTAGE));
    }

    @Test
    @DisplayName("newMax == min: new min should be less than newMax by PERCENTAGE")
    void newMaxIsEqualToMin() {
        final var newMax = 100;

        generator.minSize(Integer.MAX_VALUE).maxSize(newMax);

        assertThat(generator.getMinSize())
                .isEqualTo(calculatePercentage(newMax, -PERCENTAGE));
    }

    @Test
    @DisplayName("newMin > max: new max should be set to greater than newMin by PERCENTAGE")
    void newMinIsGreaterThanMax() {
        final var newMin = 100;

        generator.minSize(newMin);

        assertThat(generator.getMaxSize())
                .isEqualTo(calculatePercentage(newMin, PERCENTAGE));
    }

    @Test
    @DisplayName("newMin == max: new max should be set to greater than newMin by PERCENTAGE")
    void newMinIsEqualToMax() {
        final var newMin = 100;

        generator.maxSize(0).minSize(newMin);

        assertThat(generator.getMaxSize())
                .isEqualTo(calculatePercentage(newMin, PERCENTAGE));
    }

    @Test
    void size() {
        final var size = 2;

        generator.size(size);

        assertThat(generator.getMinSize()).isEqualTo(size);
        assertThat(generator.getMaxSize()).isEqualTo(size);
    }

    @Test
    void minValidation() {
        assertThatThrownBy(() -> generator.minSize(-1))
                .isExactlyInstanceOf(InstancioApiException.class)
                .hasMessageContaining("size must not be negative: -1");
    }

    @Test
    void maxValidation() {
        assertThatThrownBy(() -> generator.maxSize(-1))
                .isExactlyInstanceOf(InstancioApiException.class)
                .hasMessageContaining("size must not be negative: -1");
    }

    private int calculatePercentage(final int initial, final int percentage) {
        return initial + initial * percentage / 100;
    }

    /**
     * Subclass to expose getters.
     */
    private static class CharSeqGeneratorExt<T> extends CharSeqGenerator {

        public CharSeqGeneratorExt(final GeneratorContext context) {
            super(context);
        }

        @Override
        protected CharSeq tryGenerateNonNull(final Random random) {
            throw new InstancioException(getClass() + " should delegate to another generator");
        }

        int getMinSize() {
            return minSize;
        }

        int getMaxSize() {
            return maxSize;
        }
    }

}
