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
package org.smartparam.engine.core.engine;

import org.smartparam.engine.core.exception.SmartParamErrorCode;
import org.smartparam.engine.core.exception.SmartParamException;
import org.smartparam.engine.core.index.Matcher;
import org.smartparam.engine.core.repository.MatcherRepository;
import org.smartparam.engine.core.repository.TypeRepository;
import org.smartparam.engine.core.service.FunctionProvider;
import org.smartparam.engine.core.type.Type;
import org.smartparam.engine.model.Level;
import org.smartparam.engine.model.function.Function;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static com.googlecode.catchexception.CatchException.*;
import static org.mockito.Mockito.*;
import static org.smartparam.engine.test.assertions.Assertions.*;
import static org.smartparam.engine.test.builder.LevelTestBuilder.level;

/**
 *
 * @author Adam Dubiel
 */
public class BasicLevelPreparerTest {

    private BasicLevelPreparer basicLevelPreparer;

    private MatcherRepository matcherRepository;

    private TypeRepository typeRepository;

    private FunctionProvider functionProvider;

    @BeforeMethod
    public void initialize() {
        basicLevelPreparer = new BasicLevelPreparer();

        matcherRepository = mock(MatcherRepository.class);
        typeRepository = mock(TypeRepository.class);
        functionProvider = mock(FunctionProvider.class);

        basicLevelPreparer.setMatcherRepository(matcherRepository);
        basicLevelPreparer.setTypeRepository(typeRepository);
        basicLevelPreparer.setFunctionProvider(functionProvider);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldPrepareLevelResolvingAllDependenciesFromRepositories() {
        // given
        Level level = level().withName("level").withLevelCreator("levelCreator")
                .withMatcher("matcher").withType("type").build();
        Matcher matcher = mock(Matcher.class);
        when(matcherRepository.getMatcher("matcher")).thenReturn(matcher);
        Type type = mock(Type.class);
        when(typeRepository.getType("type")).thenReturn(type);
        Function levelCreator = mock(Function.class);
        when(functionProvider.getFunction("levelCreator")).thenReturn(levelCreator);

        // when
        PreparedLevel preparedLevel = basicLevelPreparer.prepare(level);

        // then
        assertThat(preparedLevel).hasName("level").hasLevelCreator(levelCreator)
                .hasMatcher(matcher).hasType(type);
    }

    @Test
    public void shouldPrepareLevelWithEmptyDependenciesWhenNotDefined() {
        // given
        Level level = level().withName("level").build();

        // when
        PreparedLevel preparedLevel = basicLevelPreparer.prepare(level);

        // then
        assertThat(preparedLevel).hasName("level").hasLevelCreator(null)
                .hasMatcher(null).hasType(null);
    }

    @Test
    public void shouldThrowExceptionWhenMatcherDefinedButNotFoundInRepo() {
        // given
        Level level = level().withMatcher("matcher").build();
        when(matcherRepository.getMatcher("matcher")).thenReturn(null);

        // when
        catchException(basicLevelPreparer).prepare(level);

        // then
        assertThat((SmartParamException) caughtException()).hasErrorCode(SmartParamErrorCode.UNKNOWN_MATCHER);
    }

    @Test
    public void shouldThrowExceptionWhenTypeDefinedButNotFoundInRepo() {
        // given
        Level level = level().withType("type").build();
        when(typeRepository.getType("type")).thenReturn(null);

        // when
        catchException(basicLevelPreparer).prepare(level);

        // then
        assertThat((SmartParamException) caughtException()).hasErrorCode(SmartParamErrorCode.UNKNOWN_PARAM_TYPE);
    }
}