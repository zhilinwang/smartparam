/*
 * Copyright 2014 Adam Dubiel, Przemek Hertel.
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
package org.smartparam.editor.core.entry;

import org.smartparam.engine.core.ParamEngine;
import org.smartparam.engine.core.ParamEngineRuntimeConfig;
import org.smartparam.engine.core.output.entry.MapEntry;
import org.smartparam.engine.core.parameter.Parameter;
import org.smartparam.engine.core.parameter.entry.ParameterEntry;

/**
 *
 * @author Adam Dubiel
 */
public class ParameterEntryMapConverter {

    private final EntryToMapConverter entryToMap;

    private final MapToEntryConverter mapToEntry;

    public ParameterEntryMapConverter(ParamEngine paramEngine) {
        ParamEngineRuntimeConfig engineConfig = paramEngine.runtimeConfiguration();
        entryToMap = new EntryToMapConverter(engineConfig);
        mapToEntry = new MapToEntryConverter(engineConfig);
    }

    public ParameterEntry asEntry(Parameter metadata, MapEntry entryMap) {
        return mapToEntry.asEntry(metadata, entryMap);
    }

    public MapEntry asMap(Parameter metadata, ParameterEntry parameterEntry) {
        return entryToMap.asMap(metadata, parameterEntry);
    }

}
