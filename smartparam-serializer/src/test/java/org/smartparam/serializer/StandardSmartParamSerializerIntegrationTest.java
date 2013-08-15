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
package org.smartparam.serializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import org.junit.Before;
import org.junit.Test;
import org.smartparam.engine.model.Level;
import org.smartparam.engine.model.Parameter;
import org.smartparam.engine.model.ParameterEntry;
import org.smartparam.engine.model.editable.SimpleEditableLevel;
import org.smartparam.engine.model.editable.SimpleEditableParameter;
import org.smartparam.engine.model.editable.SimpleEditableParameterEntry;
import static org.smartparam.engine.test.assertions.Assertions.*;
import static org.smartparam.engine.test.builder.LevelTestBuilder.level;
import static org.smartparam.engine.test.builder.ParameterEntryTestBuilder.parameterEntry;
import static org.smartparam.engine.test.builder.ParameterTestBuilder.parameter;

/**
 *
 * @author Adam Dubiel
 */
public class StandardSmartParamSerializerIntegrationTest {

    private StandardParamSerializer serializer;

    private StandardParamDeserializer deserializer;

    @Before
    public void initialize() {
        SerializationConfig config = new StandardSerializationConfig('"', ';', '#', "\n", "UTF-8");
        serializer = new StandardParamSerializer(config, SimpleEditableParameterEntry.class);
        deserializer = new StandardParamDeserializer(config, SimpleEditableParameter.class, SimpleEditableLevel.class, SimpleEditableParameterEntry.class);
    }

    @Test
    public void shouldDeserializeParameterFromTestFile() throws Exception {
        // given
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/sampleParam.csv")));

        // when
        Parameter parameter = deserializer.deserialize(reader);

        // then
        assertThat(parameter).hasName("testParameter").isCacheable().isNotNullable()
                .hasInputLevels(4).hasLevels(3).hasEntries(2);
    }

    @Test
    public void shouldBeAbleToDeserializeOnceSerializedParameter() throws Exception {
        // given
        Level[] levels = new Level[] {
            level().withName("level1").build(),
            level().withName("level2").build()
        };
        ParameterEntry[] entries = new ParameterEntry[] {
            parameterEntry().withLevels("level1").build(),
            parameterEntry().withLevels("level1").build()
        };
        Parameter parameter = parameter().withName("parameter").withInputLevels(3)
                .withLevels(levels).withEntries(entries).build();
        StringWriter paramWriter = new StringWriter();
        serializer.serialize(parameter, paramWriter);

        BufferedReader reader = new BufferedReader(new StringReader(paramWriter.toString()));

        // when
        Parameter processedParameter = deserializer.deserialize(reader);

        // then
        assertThat(processedParameter).hasName("parameter").isNotNullable()
                .isCacheable().hasInputLevels(3).hasLevels(2).hasEntries(2);
    }
}
