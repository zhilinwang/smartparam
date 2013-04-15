package org.smartparam.spring.function;

import org.junit.Before;
import org.junit.Test;
import org.smartparam.engine.core.context.DefaultContext;
import org.smartparam.engine.core.context.ParamContext;
import org.smartparam.engine.core.exception.SmartParamException;
import org.smartparam.spring.mockBuilders.SpringFunctionMockBuilder;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.smartparam.engine.core.exception.SmartParamErrorCode;

/**
 *
 * @author Przemek Hertel
 */
public class SpringFunctionInvokerTest {

    private SpringFunctionInvoker invoker;

    @Before
    public void init() {

        // zaleznosci
        ApplicationContext appContext = mock(ApplicationContext.class);

        // konfiguracja zaleznosci
        when(appContext.getBean("bean1")).thenReturn(new Bean1());
        when(appContext.getBean("other")).thenThrow(new BeanInstantiationException(this.getClass(), ""));

        // konfiguracja invokera
        invoker = new SpringFunctionInvoker();
        invoker.setApplicationContext(appContext);
    }

    @Test
    public void testInvoke__ctx() {

        // przypadki testowe - funkcje
        SpringFunction[] functions = {
            f("bean1", "someMethod"),
            f("bean1", "someMethod")
        };

        // przypadki testowe - argumenty przekazywane do funkcji
        ParamContext[] arguments = {
            new DefaultContext(),
            null
        };

        // przypadki testowe - oczekiwany wynik
        Object[] expectations = {
            "bean1.ctx=notnull",
            "bean1.ctx=null"
        };

        // testy
        for (int i = 0; i < functions.length; i++) {
            SpringFunction f = functions[i];
            ParamContext ctx = arguments[i];
            Object expectedResult = expectations[i];

            Object result = invoker.invoke(f, ctx);
            assertEquals(expectedResult, result);
        }
    }

    @Test
    public void testInvoke__args() {

        // przypadki testowe - funkcje
        SpringFunction[] functions = {
            f("bean1", "someMethod"),
            f("bean1", "someMethod"),
            f("bean1", "someMethod")
        };

        // przypadki testowe - argumenty przekazywane do funkcji
        Object[][] arguments = {
            {8, 2},
            {4, 4},
            {4, 4L}
        };

        // przypadki testowe - oczekiwany wynik
        Object[] expectations = {
            "div=4",
            "div=1",
            "bean1.args=4,4"
        };

        // testy
        for (int i = 0; i < functions.length; i++) {
            SpringFunction f = functions[i];
            Object[] args = arguments[i];
            Object expectedResult = expectations[i];

            Object result = invoker.invoke(f, args);
            assertEquals(expectedResult, result);
        }
    }

    @Test
    public void testInvoke__exception() {

        // przypadki testowe - funkcje
        SpringFunction[] functions = {
            f("other", "someMethod"), // brak beana
            f("other", "someMethod"), // brak beana
            f("bean1", "yMethod"), // brak metody
            f("bean1", "someMethod"), // wyjatek w metodzie
            f("bean1", "privMethod"), // prywatna metoda
            f("bean1", "someMethod") // niepoprawne argumenty
        };

        // przypadki testowe - argumenty przekazywane do funkcji
        Object[][] arguments = {
            {new DefaultContext()},
            {null},
            {1, 2, 3},
            {4, 0}, // div: dzielenie przez 0
            {7},
            {"a", "b"}
        };

        // testy
        for (int i = 0; i < functions.length; i++) {
            SpringFunction f = functions[i];
            Object[] args = arguments[i];

            try {
                invoker.invoke(f, args);
                fail();

            } catch (SmartParamException e) {
                assertEquals(SmartParamErrorCode.FUNCTION_INVOKE_ERROR, e.getErrorCode());
                assertNotNull(e.getCause());
                System.out.println("OK[" + i + "] : " + e.getCause());
            }
        }
    }

    private SpringFunction f(String beanName, String methodName) {
        return SpringFunctionMockBuilder.function(beanName, methodName);
    }

    /**
     * Przykladowy bean springowy.
     */
    public class Bean1 {

        public String someMethod(ParamContext ctx) {
            return "bean1.ctx=" + (ctx != null ? "notnull" : "null");
        }

        public String someMethod(int a, Number b) {
            return "bean1.args=" + a + "," + b;
        }

        public String someMethod(int a, int b) {
            return "div=" + (a / b);
        }

        private String privMethod(int a) {
            return null;
        }
    }
}