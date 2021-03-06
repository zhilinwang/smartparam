/*
 * Copyright 2013 Adam Dubiel, Przemek Hertel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartparam.engine.core.prepared;

import org.assertj.core.api.AbstractAssert;
import org.smartparam.engine.core.prepared.PreparedLevel;
import org.smartparam.engine.core.matcher.Matcher;
import org.smartparam.engine.core.type.Type;
import org.smartparam.engine.core.function.Function;
import org.smartparam.engine.test.ParamEngineAssertions;

/**
 *
 * @author Adam Dubiel
 */
public class PreparedLevelAssert extends AbstractAssert<PreparedLevelAssert, PreparedLevel> {

    private PreparedLevelAssert(PreparedLevel actual) {
        super(actual, PreparedLevelAssert.class);
    }

    public static PreparedLevelAssert assertThat(PreparedLevel actual) {
        return new PreparedLevelAssert(actual);
    }

    public PreparedLevelAssert hasMatcher(Matcher matcher) {
        ParamEngineAssertions.assertThat(actual.getMatcher()).isSameAs(matcher);
        return this;
    }

    @SuppressWarnings("unchecked")
    public PreparedLevelAssert hasType(Type type) {
        ParamEngineAssertions.assertThat(actual.getType()).isSameAs(type);
        return this;
    }

    public PreparedLevelAssert hasLevelCreator(Function levelCreator) {
        ParamEngineAssertions.assertThat(actual.getLevelCreator()).isSameAs(levelCreator);
        return this;
    }

    public PreparedLevelAssert hasName(String name) {
        ParamEngineAssertions.assertThat(actual.getName()).isEqualTo(name);
        return this;
    }
}