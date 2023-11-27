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

import org.instancio.Node;
import org.instancio.generator.Generator;
import org.instancio.generators.Generators;
import org.instancio.spi.InstancioServiceProvider;

import ch.movementsciences.instancio.vavr.internal.generator.SeqGenerator;
import io.vavr.collection.Array;
import io.vavr.collection.CharSeq;
import io.vavr.collection.HashSet;
import io.vavr.collection.LinkedHashSet;
import io.vavr.collection.List;
import io.vavr.collection.Queue;
import io.vavr.collection.Stream;
import io.vavr.collection.TreeSet;
import io.vavr.collection.Vector;

public class VavrProvider implements InstancioServiceProvider {
    @Override
    public GeneratorProvider getGeneratorProvider() {
        final java.util.Map<Class<?>, Generator<?>> generators = new java.util.HashMap<>();

        // Collections
        final Generator<?> seqGenerator = new SeqGenerator<>();
        generators.put(Array.class, seqGenerator);
        generators.put(CharSeq.class, seqGenerator);
        generators.put(Vector.class, seqGenerator);
        generators.put(List.class, seqGenerator);
        generators.put(Stream.class, seqGenerator);
        generators.put(Queue.class, seqGenerator);
        generators.put(LinkedHashSet.class, seqGenerator);
        generators.put(HashSet.class, seqGenerator);
        generators.put(TreeSet.class, seqGenerator);

        // Maps
        /*
        final Generator<?> mapGenerator = new MapGenerator<>();
        generators.put(LinkedHashMap.class, mapGenerator);
        generators.put(HashMap.class, mapGenerator);
        generators.put(TreeMap.class, mapGenerator);
*/
        return (Node node, Generators gen) -> generators.get(node.getTargetClass());
    }
}
