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
package org.smartparam.editor.viewer;

/**
 *
 * @author Adam Dubiel
 */
public class LevelSorting {

    private final int levelIndex;

    private final SortDirection direction;

    public LevelSorting(int levelIndex, SortDirection direction) {
        this.levelIndex = levelIndex;
        this.direction = direction;
    }

    public LevelSorting(int levelIndex) {
        this(levelIndex, SortDirection.DESC);
    }

    public int levelIndex() {
        return levelIndex;
    }

    public SortDirection direction() {
        return direction;
    }

}
