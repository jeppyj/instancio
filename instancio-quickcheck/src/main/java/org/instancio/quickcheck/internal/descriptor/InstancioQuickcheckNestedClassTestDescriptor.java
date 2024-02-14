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
package org.instancio.quickcheck.internal.descriptor;

import org.junit.platform.commons.support.ReflectionSupport;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.ClassSource;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class InstancioQuickcheckNestedClassTestDescriptor extends InstancioClassBasedTestDescriptor {
    public static final String SEGMENT_TYPE = "nested-class";

    public InstancioQuickcheckNestedClassTestDescriptor(UniqueId uniqueId, Class<?> testClass) {
        super(uniqueId,
                testClass,
                DisplayNameUtils.getDisplayNameGenerator().generateDisplayNameForNestedClass(testClass),
                ClassSource.from(testClass));
    }

    @Override
    public List<Class<?>> getEnclosingTestClasses() {
        TestDescriptor parent = getParent().orElse(null);
        if (parent instanceof InstancioClassBasedTestDescriptor) {
            InstancioClassBasedTestDescriptor parentClassDescriptor = (InstancioClassBasedTestDescriptor) parent;
            List<Class<?>> result = new ArrayList<>(parentClassDescriptor.getEnclosingTestClasses());
            result.add(parentClassDescriptor.getTestClass());
            return result;
        }
        return emptyList();
    }

    @Override
    public Object createTestInstance() {
        final List<Class<?>> parents = getEnclosingTestClasses();
        if (parents.isEmpty()) {
            return ReflectionSupport.newInstance(getTestClass());
        } else {
            final Object parent = createParentInstance(ReflectionSupport.newInstance(parents.get(0)), parents.subList(1, parents.size()));
            return ReflectionSupport.newInstance(getTestClass(), parent);
        }
    }

    private static Object createParentInstance(final Object parent, List<Class<?>> enclosed) {
        if (enclosed.isEmpty()) {
            return parent;
        } else {
            return createParentInstance(ReflectionSupport.newInstance(enclosed.get(0), parent),
                    enclosed.subList(1, enclosed.size()));
        }
    }
}
