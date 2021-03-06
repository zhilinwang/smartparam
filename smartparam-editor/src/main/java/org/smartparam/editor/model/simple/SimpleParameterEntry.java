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
package org.smartparam.editor.model.simple;

import java.util.Arrays;
import org.smartparam.engine.core.parameter.entry.ParameterEntry;
import org.smartparam.engine.core.parameter.entry.ParameterEntryKey;
import org.smartparam.engine.core.parameter.identity.EmptyEntityKey;

/**
 *
 * @author Adam Dubiel
 */
public class SimpleParameterEntry implements ParameterEntry {

    private static final String[] EMPTY_LEVELS = {};

    private String[] levels = EMPTY_LEVELS;

    public SimpleParameterEntry() {
    }

    public SimpleParameterEntry(String... levels) {
        this.levels = levels;
    }

    public SimpleParameterEntry(ParameterEntry entry) {
        if (entry.getLevels() != null) {
            this.levels = Arrays.copyOf(entry.getLevels(), entry.getLevels().length);
        }
    }

    @Override
    public ParameterEntryKey getKey() {
        return EmptyEntityKey.emptyKey();
    }

    @Override
    public String[] getLevels() {
        return levels;
    }

    public void setLevels(String[] levels) {
        this.levels = Arrays.copyOf(levels, levels.length);
    }
}
