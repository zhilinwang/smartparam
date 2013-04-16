package org.smartparam.engine.core.repository;

import org.smartparam.engine.core.index.Matcher;

/**
 *
 * @author Adam Dubiel
 * @since 0.1.0
 */
public interface MatcherRepository {

    void registerMatcher(String code, Matcher matcher);

    Iterable<String> registeredMatchers();

    Matcher getMatcher(String code);
}
