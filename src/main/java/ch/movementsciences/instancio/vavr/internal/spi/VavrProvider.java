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

package ch.movementsciences.instancio.vavr.internal.spi;

import ch.movementsciences.instancio.vavr.internal.generator.SeqGenerator;
import io.vavr.collection.CharSeq;
import io.vavr.collection.Seq;
import org.instancio.Node;
import org.instancio.generator.GeneratorContext;
import org.instancio.generators.Generators;
import org.instancio.spi.InstancioServiceProvider;
import org.instancio.spi.ServiceProviderContext;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

public class VavrProvider implements InstancioServiceProvider {

    private GeneratorContext generatorContext;

    @Override
    public void init(final ServiceProviderContext providerContext) {
        this.generatorContext = new GeneratorContext(
                providerContext.getSettings(),
                providerContext.random());
    }
    @Override
    public GeneratorProvider getGeneratorProvider() {
        return (Node node, Generators gen) -> Match(node.getTargetClass()).of(
                Case($(CharSeq.class::isAssignableFrom), CharSeqGenerator::new),
                Case($(Seq.class::isAssignableFrom), () -> new SeqGenerator<>(generatorContext)),
                Case($(), () -> null)
        );
    }
}
