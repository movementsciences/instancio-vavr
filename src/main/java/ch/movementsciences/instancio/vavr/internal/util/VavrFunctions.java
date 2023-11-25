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

package ch.movementsciences.instancio.vavr.internal.util;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public class VavrFunctions {

    private VavrFunctions() {
        // non-instantiable
    }

    @SuppressWarnings("rawtypes")
    public static <R> Function<Collection, R> fromCollection(Function<Collection, R> fn) {
        return fn;
    }


    @SuppressWarnings("rawtypes")
    public static <R> Function<Map, R> fromMap(Function<Map, R> fn) {
        return fn;
    }
}
