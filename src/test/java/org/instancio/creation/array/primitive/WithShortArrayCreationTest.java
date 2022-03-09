package org.instancio.creation.array.primitive;

import org.instancio.pojo.arrays.primitive.WithDoubleArray;
import org.instancio.pojo.arrays.primitive.WithShortArray;
import org.instancio.testsupport.annotations.NonDeterministic;
import org.instancio.testsupport.templates.ArrayCreationTestTemplate;
import org.instancio.testsupport.templates.NumberOfExecutions;
import org.instancio.testsupport.utils.ArrayUtils;

public class WithShortArrayCreationTest extends ArrayCreationTestTemplate<WithShortArray> {

    @Override
    @NonDeterministic
    @NumberOfExecutions(20)
    protected void verify(WithShortArray result) {
        generatedValues.addAll(ArrayUtils.toList(result.getValues()));
    }

}
