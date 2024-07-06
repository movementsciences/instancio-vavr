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

package ch.movementsciences.instancio.vavr;

import ch.movementsciences.instancio.vavr.generator.specs.CharSeqSpecs;
import ch.movementsciences.instancio.vavr.generator.specs.SeqSpecs;
import ch.movementsciences.instancio.vavr.generator.specs.SetSpecs;
import ch.movementsciences.instancio.vavr.internal.generator.CharSeqGenerator;
import ch.movementsciences.instancio.vavr.internal.generator.SeqGenerator;
import ch.movementsciences.instancio.vavr.internal.generator.SetGenerator;

public class GenVavr {

    private GenVavr() {
        // non-instantiable
    }

    /**
     * Generator spec for seq.
     *
     * @param <T> the type of the seq elements
     * @return generator spec
     * @since 0.1.0
     */
    public static <T> SeqSpecs<T> seq() {
        return new SeqGenerator<>();
    }



    /**
     * Generator spec for charSeq.
     *
     * @return generator spec
     * @since 0.1.0
     */
    public static CharSeqSpecs charSeq() {
        return new CharSeqGenerator();
    }

    /**
     * Generator spec for set.
     *
     * @param <T> the type of the set elements
     * @return generator spec
     * @since 0.1.0
     */
    public static <T> SetSpecs<T> set() {
        return new SetGenerator<>();
    }
}
