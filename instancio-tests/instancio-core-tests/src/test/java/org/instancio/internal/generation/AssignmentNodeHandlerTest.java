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
package org.instancio.internal.generation;

import org.instancio.Assign;
import org.instancio.internal.context.ModelContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;

class AssignmentNodeHandlerTest {
    // required ctor args but not needed for the test
    private final GeneratedObjectStore objectStore = null;
    private final UserSuppliedGeneratorProcessor generatorProcessor = null;

    @Test
    void createShouldReturnNoopClass_whenModelContextHasNoAssignments() {
        final ModelContext<Object> ctx = ModelContext.builder(String.class).build();
        final NodeHandler result = AssignmentNodeHandler.create(ctx, objectStore, generatorProcessor);

        assertThat(result).isExactlyInstanceOf(AssignmentNodeHandler.NoopAssignmentNodeHandler.class);
    }

    @Test
    void createShouldReturnAssignmentHandler_whenModelContextHasAssignments() {
        final ModelContext<Object> ctx = ModelContext.builder(String.class)
                .withAssignments(Assign.valueOf(String.class).to(all(String.class)))
                .build();

        final NodeHandler result = AssignmentNodeHandler.create(ctx, objectStore, generatorProcessor);

        assertThat(result).isExactlyInstanceOf(AssignmentNodeHandler.class);
    }
}