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

import ch.movementsciences.instancio.vavr.internal.generator.VavrCollectionGenerator;
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
        final Generator<?> collectionGenerator = new VavrCollectionGenerator<>();
        generators.put(Array.class, collectionGenerator);
        generators.put(CharSeq.class, collectionGenerator);
        generators.put(Vector.class, collectionGenerator);
        generators.put(List.class, collectionGenerator);
        generators.put(Stream.class, collectionGenerator);
        generators.put(Queue.class, collectionGenerator);
        generators.put(LinkedHashSet.class, collectionGenerator);
        generators.put(HashSet.class, collectionGenerator);
        generators.put(TreeSet.class, collectionGenerator);

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
