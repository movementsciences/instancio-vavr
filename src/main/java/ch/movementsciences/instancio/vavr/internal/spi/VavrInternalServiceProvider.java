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

import ch.movementsciences.instancio.vavr.internal.builder.VavrBuilder;
import io.vavr.collection.CharSeq;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.collection.Traversable;
import io.vavr.control.Option;
import org.instancio.internal.spi.InternalServiceProvider;

import java.util.function.Function;
import java.util.function.Predicate;

public class VavrInternalServiceProvider implements InternalServiceProvider {

    private static final Seq<Predicate<Class<?>>> VAVR_CONTAINERS = List.of(
        (Class<?> type) -> Traversable.class.isAssignableFrom(type) && !CharSeq.class.isAssignableFrom(type),
        Option.class::isAssignableFrom
    );

    @Override
    public InternalContainerFactoryProvider getContainerFactoryProvider() {
        return new InternalContainerFactoryProvider() {

            @Override
            public <T, R> Function<T, R> getMappingFunction(Class<R> type, java.util.List<Class<?>> typeArguments) {
                if (VAVR_CONTAINERS.find(predicate -> predicate.test(type)).isDefined()) {
                    return (t) -> {
                        if (t instanceof VavrBuilder<?> builder) {
                            final var builtType = builder.build(type);
                            return type.cast(builtType);
                        } else {
                            return type.cast(t);
                        }
                    };
                }
                return null;
            }

            @Override
            public boolean isContainer(final Class<?> type) {
                return VAVR_CONTAINERS.find(predicate -> predicate.test(type)).isDefined();
            }
        };
    }
}
