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
package org.smartparam.engine.core.repository;

import org.smartparam.engine.util.Objects;

/**
 *
 * @author Adam Dubiel
 */
public class RepositoryName {

    private final String name;

    public RepositoryName(String name) {
        this.name = name;
    }

    public static RepositoryName from(String name) {
        return new RepositoryName(name);
    }

    public String value() {
        return name;
    }

    @Override
    public String toString() {
        return "Repository[" + name + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RepositoryName other = (RepositoryName) obj;
        return !((this.name == null) ? (other.name != null) : !this.name.equals(other.name));
    }

}
