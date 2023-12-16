package ch.movementsciences.instancio.vavr.internal.generator;

import io.vavr.collection.CharSeq;
import org.instancio.Random;
import org.instancio.generator.AfterGenerate;
import org.instancio.generator.GeneratorContext;
import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import org.instancio.support.DefaultRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Percentage.withPercentage;

class CharSeqGeneratorTest {
    private static final int MIN_SIZE = 101;
    private static final int MAX_SIZE = 102;
    private static final int SAMPLE_SIZE = 10_000;
    private static final int PERCENTAGE_THRESHOLD = 10;
    private static final Settings settings = Settings.defaults()
            .set(Keys.COLLECTION_MIN_SIZE, MIN_SIZE)
            .set(Keys.COLLECTION_MAX_SIZE, MAX_SIZE)
            .set(Keys.COLLECTION_NULLABLE, true);
    private static final Random random = new DefaultRandom();
    private static final GeneratorContext context = new GeneratorContext(settings, random);
    private final CharSeqGenerator generator = new CharSeqGenerator(context);

    @Test
    void apiMethod() {
        assertThat(generator.apiMethod()).isNull();
    }

    @Test
    @DisplayName("Should generate either an empty charSeq or null")
    void generateNullableSeq() {
        final Set<Object> results = new HashSet<>();
        final int[] counts = new int[2];

        for (int i = 0; i < SAMPLE_SIZE; i++) {
            final var result = generator.generate(random);
            results.add(result);
            counts[result == null ? 0 : 1]++;
        }

        assertThat(results)
                .containsNull()
                .hasAtLeastOneElementOfType(CharSeq.class);

        assertThat(counts[1])
                .as("Expecting 5/6 of results to be non-null")
                .isCloseTo((5 * SAMPLE_SIZE) / 6, withPercentage(PERCENTAGE_THRESHOLD));

        HintsAssert.assertHints(generator.hints())
                .isEmpty()
                .afterGenerate(AfterGenerate.DO_NOT_MODIFY);
    }
}
