/*
 * Copyright 2014 Adam Dubiel.
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
package org.smartparam.engine.core.output.factory;

import org.smartparam.engine.core.parameter.entry.ParameterEntryKey;
import org.smartparam.engine.core.prepared.IdentifiablePreparedEntry;
import org.smartparam.engine.core.prepared.PreparedEntry;

/**
 *
 * @author Adam Dubiel
 */
final class ParameterEntryKeyExtractor {

    private ParameterEntryKeyExtractor() {
    }

    static ParameterEntryKey extractEntryKey(PreparedEntry entry) {
        if (entry instanceof IdentifiablePreparedEntry) {
            return ((IdentifiablePreparedEntry) entry).getKey();
        }
        return null;
    }

}
