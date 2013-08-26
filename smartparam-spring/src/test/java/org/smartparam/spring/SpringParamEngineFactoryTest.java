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

package org.smartparam.spring;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.smartparam.engine.config.pico.PicoParamEngineConfig;
import org.smartparam.engine.core.engine.ParamEngine;
import org.smartparam.engine.core.repository.ParamRepository;
import static org.smartparam.engine.config.pico.ParamEngineConfigBuilder.paramEngineConfig;
import static org.smartparam.engine.test.assertions.Assertions.*;

/**
 *
 * @author Adam Dubiel
 */
public class SpringParamEngineFactoryTest {

    private SpringParamEngineFactory springParamEngineFactory;

    @Before
    public void initialize() {
        springParamEngineFactory = new SpringParamEngineFactory();
    }

    @Test
    public void shouldReturnParamEngineCreatedUsingProvidedConfig() throws Exception {
        // given
        PicoParamEngineConfig config = paramEngineConfig().build();
        springParamEngineFactory.setConfig(config);

        // when
        ParamEngine paramEngine = springParamEngineFactory.getObject();

        // then
        assertThat(paramEngine).hasInitializedTree();
    }

    @Test
    public void shouldReturnParamEngineWithAnnotationScanningEnabled() throws Exception {
        // given
        PicoParamEngineConfig config = paramEngineConfig().withAnnotationScanEnabled("test").build();
        springParamEngineFactory.setConfig(config);

        // when
        ParamEngine paramEngine = springParamEngineFactory.getObject();

        // then
        assertThat(paramEngine).hasInitializedTreeWithScannedItems();
    }

    @Test
    public void shouldCreateNewConfigObjectIfNoneSpecified() throws Exception {
        // given
        // noop

        // when
        ParamEngine paramEngine = springParamEngineFactory.getObject();

        // then
        assertThat(paramEngine).hasInitializedTree();
    }

    @Test
    public void shouldInsertProvidedRepositoryIntoConfig() throws Exception {
        // given
        ParamRepository repository = Mockito.mock(ParamRepository.class);
        springParamEngineFactory.setParamRepository(repository);

        // when
        ParamEngine paramEngine = springParamEngineFactory.getObject();

        // then
        assertThat(paramEngine).hasRepository(repository);
    }
}