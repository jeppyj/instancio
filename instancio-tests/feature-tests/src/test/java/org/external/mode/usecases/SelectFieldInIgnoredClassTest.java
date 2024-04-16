/*
 * Copyright 2022-2024 the original author or authors.
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
package org.external.mode.usecases;

import org.instancio.Instancio;
import org.instancio.InstancioApi;
import org.instancio.junit.InstancioExtension;
import org.instancio.test.support.pojo.person.Address;
import org.instancio.test.support.pojo.person.Phone;
import org.instancio.test.support.tags.Feature;
import org.instancio.test.support.tags.FeatureTag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.instancio.Select.all;
import static org.instancio.Select.field;
import static org.instancio.test.support.UnusedSelectorsAssert.assertThrowsUnusedSelectorException;
import static org.instancio.test.support.UnusedSelectorsAssert.line;

@FeatureTag(Feature.MODE)
@ExtendWith(InstancioExtension.class)
class SelectFieldInIgnoredClassTest {

    @Test
    void unusedClassSelector() {
        final InstancioApi<Address> api = Instancio.of(Address.class)
                .ignore(all(Phone.class))
                .set(field(Phone::getNumber), "any");

        assertThrowsUnusedSelectorException(api)
                .hasUnusedSelectorCount(1)
                .generatorSelector(field(Phone.class, "number"),
                        line(getClass(), 41));
    }
}
