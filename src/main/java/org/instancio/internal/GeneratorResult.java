package org.instancio.internal;

import javax.annotation.Nullable;
import java.util.StringJoiner;

class GeneratorResult<T> {
    private final T value;
    private final boolean ignoreChildren;
    private final GeneratorSettings generatorSettings;

    private GeneratorResult(@Nullable final T value,
                            final boolean ignoreChildren,
                            @Nullable final GeneratorSettings generatorSettings) {
        this.value = value;
        this.ignoreChildren = ignoreChildren;
        this.generatorSettings = generatorSettings;
    }

    private GeneratorResult(final Builder<T> builder) {
        value = builder.value;
        ignoreChildren = builder.ignoreChildren;
        generatorSettings = builder.generatorSettings;
    }

    static <T> GeneratorResult<T> nullResult() {
        return new GeneratorResult<>(null, true, null);
    }

    static <T> Builder<T> builder(T value) {
        return new Builder<>(value);
    }

    static <T> GeneratorResult<T> build(T value) {
        return new Builder<>(value).build();
    }

    T getValue() {
        return value;
    }

    boolean ignoreChildren() {
        return ignoreChildren;
    }

    GeneratorSettings getSettings() {
        return generatorSettings;
    }

    static final class Builder<T> {
        private final T value;
        private boolean ignoreChildren;
        private GeneratorSettings generatorSettings;

        private Builder(@Nullable final T value) {
            this.value = value;
        }

        public Builder<T> withSettings(@Nullable final GeneratorSettings generatorSettings) {
            this.generatorSettings = generatorSettings;
            if (generatorSettings != null) {
                this.ignoreChildren = generatorSettings.ignoreChildren();
            }
            return this;
        }

        public GeneratorResult<T> build() {
            return new GeneratorResult<>(this);
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GeneratorResult.class.getSimpleName() + "[", "]")
                .add("value=" + value)
                .add("ignoreChildren=" + ignoreChildren)
                .add("settings=" + generatorSettings)
                .toString();
    }
}