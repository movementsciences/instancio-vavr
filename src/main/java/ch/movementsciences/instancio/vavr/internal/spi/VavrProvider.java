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

import ch.movementsciences.instancio.vavr.internal.generator.CharSeqGenerator;
import ch.movementsciences.instancio.vavr.internal.generator.MapGenerator;
import ch.movementsciences.instancio.vavr.internal.generator.OptionGenerator;
import ch.movementsciences.instancio.vavr.internal.generator.SeqGenerator;
import ch.movementsciences.instancio.vavr.internal.generator.SetGenerator;
import io.vavr.collection.CharSeq;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import org.instancio.Node;
import org.instancio.generators.Generators;
import org.instancio.spi.InstancioServiceProvider;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

public class VavrProvider implements InstancioServiceProvider {
    @Override
    public GeneratorProvider getGeneratorProvider() {
        return (Node node, Generators gen) -> Match(node.getTargetClass()).of(
                Case($(CharSeq.class::isAssignableFrom), CharSeqGenerator::new),
                Case($(Seq.class::isAssignableFrom), () -> new SeqGenerator<>()),
                Case($(Set.class::isAssignableFrom), () -> new SetGenerator<>()),
                Case($(Map.class::isAssignableFrom), () -> new MapGenerator<>()),
                Case($(Option.class::isAssignableFrom), () -> new OptionGenerator<>()),
                Case($(), () -> null)
        );
    }
}
