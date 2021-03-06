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
package org.smartparam.engine.matchers.type;

import org.smartparam.engine.core.index.Star;
import org.smartparam.engine.core.matcher.Matcher;
import org.smartparam.engine.core.matcher.MatcherType;
import org.smartparam.engine.core.type.Type;

/**
 *
 * @author Adam Dubiel
 */
public class SimpleMatcherType implements MatcherType<Object> {

    @Override
    public Object decode(String value, Type<?> type, Matcher matcher) {
        if (value != null && value.equals("*")) {
            return Star.star();
        }

        if (type != null) {
            return type.decode(value).getValue();
        }

        if (value != null && value.isEmpty()) {
            return null;
        }

        return value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String encode(Object object, Type type, Matcher matcher) {
        if (object instanceof Star) {
            return "*";
        }

        String value;
        if (type != null) {
            value = type.encode(type.convert(object));
        } else {
            value = object == null ? "" : object.toString();
        }

        return value == null ? "*" : value;
    }
}
