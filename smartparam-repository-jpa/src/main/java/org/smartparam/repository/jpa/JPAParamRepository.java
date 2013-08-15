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
package org.smartparam.repository.jpa;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.smartparam.engine.core.repository.ParamRepository;
import org.smartparam.engine.model.Parameter;
import org.smartparam.engine.model.ParameterEntry;
import org.smartparam.repository.jpa.model.JpaParameter;
import org.smartparam.repository.jpa.model.JpaParameterEntry;

/**
 * Loads parameters using provided connection.
 *
 * @author Adam Dubiel
 * @since 0.1.0
 */
public class JPAParamRepository implements ParamRepository {

    private static final int FIND_ENTRIES_QUERY_LENGTH = 100;

    private EntityManager entityManager;

    public JPAParamRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public Parameter load(String parameterName) {
        Query query = entityManager.createNamedQuery(JpaParameter.LOAD_PARAMETER_QUERY).setParameter("name", parameterName);
        return (Parameter) query.getSingleResult();
    }

    @Override
    public List<ParameterEntry> findEntries(String parameterName, String[] levelValues) {

        StringBuilder sb = new StringBuilder(FIND_ENTRIES_QUERY_LENGTH);
        sb.append(" select pe from JpaParameterEntry pe");
        sb.append(" where pe.parameter.name = ?");
        for (int i = 0; i < levelValues.length; i++) {
            sb.append(" and pe.level").append(i + 1).append(" = ?");
        }

        TypedQuery<JpaParameterEntry> query = entityManager.createQuery(sb.toString(), JpaParameterEntry.class);
        query.setParameter(1, parameterName);

        String value;
        for (int i = 0; i < levelValues.length; i++) {
            value = levelValues[i];
            query.setParameter(2 + i, value);
        }

        return new LinkedList<ParameterEntry>(query.getResultList());
    }
}
