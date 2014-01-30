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
package org.smartparam.engine.report;

import org.smartparam.engine.report.space.ReportLevelValuesSpace;
import org.smartparam.engine.core.matcher.Matcher;
import org.smartparam.engine.core.matcher.MatcherAwareDecoder;
import org.smartparam.engine.core.type.Type;
import org.smartparam.engine.report.space.ReportLevelValuesSpaceFactory;

/**
 *
 * @author Adam Dubiel
 */
public class ReportingTreeLevel {

    private final boolean ambiguous;

    private final Matcher matcher;

    private final Type<?> type;

    private final MatcherAwareDecoder matcherDecoder;

    private final ReportLevelValuesSpaceFactory ambiguousSpaceFactory;

    public ReportingTreeLevel(boolean ambiguous, Matcher matcher,
            Type<?> type, MatcherAwareDecoder<?> matcherDecoder,
            ReportLevelValuesSpaceFactory ambiguousSpaceFactory) {
        this.ambiguous = ambiguous;
        this.matcher = matcher;
        this.type = type;
        this.matcherDecoder = matcherDecoder;
        this.ambiguousSpaceFactory = ambiguousSpaceFactory;
    }

    boolean ambiguous() {
        return ambiguous;
    }

    @SuppressWarnings("unchecked")
    public <T> T decode(String string) {
        return (T) matcherDecoder.decode(string, type, matcher);
    }

    @SuppressWarnings("unchecked")
    public <T> String encode(T object) {
        return matcherDecoder.encode(object, type, matcher);
    }

    public <V> ReportLevelValuesSpace<V> createSpace() {
        return ambiguousSpaceFactory.createSpace();
    }

    <T> T chooseValue(T existing, T incoming) {
        if (existing != null) {
            return existing;
        }
        return incoming;
    }
}
