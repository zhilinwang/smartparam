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
package org.smartparam.engine.report.space;

import org.smartparam.engine.report.ReportingTreeLevel;
import org.smartparam.engine.report.ReportingTreeNode;
import org.smartparam.engine.report.ReportingTreePath;

/**
 *
 * @author Adam Dubiel
 */
public interface ReportLevelValuesSpace<V> {

    void unsafePut(Object key, ReportingTreeNode<V> node);

    boolean insertPath(Object key, ReportingTreePath<V> path, ReportingTreeLevel levelDescriptor);

    Iterable<ReportingTreeNode<V>> values();

    boolean empty();

    ReportLevelValuesSpace<V> cloneSpace(ReportingTreeNode<V> newParent);
}
