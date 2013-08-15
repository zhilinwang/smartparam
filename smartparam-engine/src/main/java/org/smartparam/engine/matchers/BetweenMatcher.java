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
package org.smartparam.engine.matchers;

import org.smartparam.engine.annotations.ParamMatcher;
import org.smartparam.engine.annotations.ObjectInstance;
import org.smartparam.engine.core.index.Matcher;
import org.smartparam.engine.core.type.AbstractHolder;
import org.smartparam.engine.core.type.Type;
import org.smartparam.engine.util.EngineUtil;

/**
 * Matcher przedzialowy. Sprawdza, czy podana wartosc (uwzgledniajac typ)
 * znajduje sie w przedziale zdefiniowanym we wzorcu.
 * <p>
 * W zaleznosci od konfiguracji granice przedzialu moga byc ostre lub nieostre.
 * Dodatkwo konfigurowalne sa znaki, ktore moga byc separatorami.
 *
 * @author Przemek Hertel
 * @since 1.0.0
 */
@ParamMatcher(value = "", instances = {
    @ObjectInstance(value = "between/ie", constructorArgs = { "true", "false" }),
    @ObjectInstance(value = "between/ii", constructorArgs = { "true", "true" }),
})
public class BetweenMatcher implements Matcher {

    /**
     * Domyslne separatory dzielace wzorzec na 2 tokeny.
     */
    private static final char[] DEFAULT_SEPARATORS = {':', '-', ','};

    /**
     * Czy dolna granica przedzialu nalezy do przedzialu.
     */
    private boolean lowerInclusive = true;

    /**
     * Czy gorna granica przedzialu nalezy do przedzialu.
     */
    private boolean upperInclusive = false;

    /**
     * Tablica znakow, ktore beda wyprobowywane jako separatory.
     */
    private char[] separators = DEFAULT_SEPARATORS;

    /**
     * Konstruktor domyslny.
     */
    public BetweenMatcher() {
    }

    public BetweenMatcher(String lowerInclusive, String upperInclusive) {
        setLowerInclusive(Boolean.parseBoolean(lowerInclusive));
        setUpperInclusive(Boolean.parseBoolean(upperInclusive));
    }
    
    public BetweenMatcher(String lowerInclusive, String upperInclusive, String separators) {
        this(Boolean.parseBoolean(lowerInclusive), Boolean.parseBoolean(upperInclusive), separators);
    }

    /**
     * Konstruktor inicjalizujacy matcher.
     *
     * @param lowerInclusive przedzial wlacznie z dolnym ograniczeniem
     * @param upperInclusive przedzial wlacznie z gornym ograniczeniem
     * @param separators znaki separatora
     */
    public BetweenMatcher(boolean lowerInclusive, boolean upperInclusive, String separators) {
        setLowerInclusive(lowerInclusive);
        setUpperInclusive(upperInclusive);
        setSeparators(separators);
    }

    @Override
    public <T extends AbstractHolder> boolean matches(String value, String pattern, Type<T> type) {

        // znajduje znak separatora, ktory zostanie uzyty
        char separator = findSeparator(pattern);

        // dzieli wzorzec wedlug schematu: (lower)(separator)(upper)
        String[] tokens = EngineUtil.split2(pattern, separator);
        String lower = tokens[0].trim();
        String upper = tokens[1].trim();

        // zamienia wartosc ze stringa w obiekt holdera
        T v = type.decode(value);

        // sprawdza warunki na dolne i gorne ograniczenie
        return lowerCondition(v, lower, type) && upperCondition(v, upper, type);
    }

    /**
     * Przeglada dostepne znaki separatorow w kolejnosci ich wystepowania w
     * tablicy. Zwraca ten, ktory jako pierwszy zostal znaleziony we wzorcu.
     *
     * @param pattern wzorzec
     * @return znak separatora, ktory zostanie zastosowany
     */
    private char findSeparator(String pattern) {
        for (char ch : separators) {
            if (pattern.indexOf(ch) >= 0) {
                return ch;
            }
        }
        return DEFAULT_SEPARATORS[0];
    }

    private <T extends AbstractHolder> boolean lowerCondition(T v, String lower, Type<T> type) {
        if ("*".equals(lower) || "".equals(lower)) {
            return true;
        }

        // zamienia dolne ograniczenie ze stringa w obiekt holdera
        T l = type.decode(lower);

        return lowerInclusive ? l.compareTo(v) <= 0 : l.compareTo(v) < 0;
    }

    private <T extends AbstractHolder> boolean upperCondition(T v, String upper, Type<T> type) {
        if ("*".equals(upper) || "".equals(upper)) {
            return true;
        }

        // zamienia gorne ograniczenie ze stringa w obiekt holdera
        T u = type.decode(upper);

        return upperInclusive ? v.compareTo(u) <= 0 : v.compareTo(u) < 0;
    }

    /**
     * Setter dla flagi lowerInclusive.
     *
     * @param lowerInclusive wartosc flagi
     */
    public final void setLowerInclusive(boolean lowerInclusive) {
        this.lowerInclusive = lowerInclusive;
    }

    /**
     * Setter dla flagi upperInclusive.
     *
     * @param upperInclusive wartosc flagi
     */
    public final void setUpperInclusive(boolean upperInclusive) {
        this.upperInclusive = upperInclusive;
    }

    /**
     * Traktuje znaki zawarte w <tt>separators</tt>
     * jako kolejne znaki dostepnych separatorow.
     *
     * @param separators string ze znakami separatorow
     */
    public final void setSeparators(String separators) {
        if (separators != null) {
            this.separators = separators.toCharArray();
        }
    }
}
