package org.smartparam.engine.annotations.scanner;

import java.util.Map;
import org.smartparam.engine.annotations.SmartParamMatcher;
import org.smartparam.engine.annotations.SmartParamType;
import org.smartparam.engine.annotations.SmartParamFunctionInvoker;
import org.smartparam.engine.annotations.SmartParamFunctionRepository;
import org.smartparam.engine.bean.PackageList;
import org.smartparam.engine.bean.RepositoryObjectKey;
import org.smartparam.engine.test.scan.annotation.DummyAnnotationWithoutInstances;
import static org.smartparam.engine.test.assertions.Assertions.*;
import static org.smartparam.engine.test.builder.PackageListTestBuilder.*;
import static com.googlecode.catchexception.CatchException.*;
import org.junit.Before;
import org.junit.Test;
import org.smartparam.engine.core.exception.SmartParamErrorCode;
import org.smartparam.engine.core.exception.SmartParamException;
import org.smartparam.engine.test.scan.annotation.DummyAnnotationWithoutOrder;
import org.smartparam.engine.test.scan.annotation.DummyAnnotationWithoutValue;
import org.smartparam.engine.test.scan.annotation.DummyAnnotationWithoutValues;

/**
 *
 * @author Adam Dubiel <dubiel.adam@gmail.com>
 * @since 0.1.0
 */
public class AnnotatedObjectsScannerIntegrationTest {

    private static final String TEST_PACKAGE = "org.smartparam.engine.test.scan";

    private PackageList packageList;

    @Before
    public void setUp() {
        packageList = packageList().withPackage(TEST_PACKAGE).build();
    }

    @Test
    public void shouldFindSingleBeanWhenScanningForParamMatchers() {
        // given
        AnnotatedObjectsScanner<Object> scanner = new AnnotatedObjectsScanner<Object>(packageList);

        // when
        Map<RepositoryObjectKey, Object> foundObjects = scanner.getAnnotatedObjects(SmartParamMatcher.class);

        // then
        assertThatItemMap(foundObjects).containsRepositoryKey("dummyMatcher").hasSize(1);
    }

    @Test
    public void shouldCreateTwoInstancesOfSingleBeanClass() {
        // given
        AnnotatedObjectsScanner<Object> scanner = new AnnotatedObjectsScanner<Object>(packageList);

        // when
        Map<RepositoryObjectKey, Object> foundObjects = scanner.getAnnotatedObjects(SmartParamType.class);

        // then
        assertThatItemMap(foundObjects).containsObjectsThatAreNotSame("typeInstanceOne", "typeInstanceTwo").hasSize(2);
    }

    @Test
    public void shouldRegisterSingleBeanUnderMultipleNames() {
        // given
        AnnotatedObjectsScanner<Object> scanner = new AnnotatedObjectsScanner<Object>(packageList);

        // when
        Map<RepositoryObjectKey, Object> foundObjects = scanner.getAnnotatedObjects(SmartParamFunctionInvoker.class);

        // then
        assertThatItemMap(foundObjects).containsObjectsThatAreSame("nameOne", "nameTwo").hasSize(2);
    }

    @Test
    public void shouldReturnBeansInOrder() {
        // given
        AnnotatedObjectsScanner<Object> scanner = new AnnotatedObjectsScanner<Object>(packageList);

        // when
        Map<RepositoryObjectKey, Object> foundObjects = scanner.getAnnotatedObjects(SmartParamFunctionRepository.class);

        // then
        assertThatItemMap(foundObjects).containsRepositoryKeys("primaryRepository", "secondaryRepsitory");
    }

    @Test
    public void shouldFailToScanAnnotationWithoutInstancesMethod() {
        // given
        AnnotatedObjectsScanner<Object> scanner = new AnnotatedObjectsScanner<Object>(packageList);

        // when
        catchException(scanner).getAnnotatedObjects(DummyAnnotationWithoutInstances.class);

        // then
        assertThat(caughtException()).isInstanceOf(SmartParamException.class);
        assertThat((SmartParamException) caughtException()).hasErrorCode(SmartParamErrorCode.ANNOTATION_INITIALIZER_ERROR);
    }

    @Test
    public void shouldFailToScanAnnotationWithoutValueMethod() {
        // given
        AnnotatedObjectsScanner<Object> scanner = new AnnotatedObjectsScanner<Object>(packageList);

        // when
        catchException(scanner).getAnnotatedObjects(DummyAnnotationWithoutValue.class);

        // then
        assertThat(caughtException()).isInstanceOf(SmartParamException.class);
        assertThat((SmartParamException) caughtException()).hasErrorCode(SmartParamErrorCode.ANNOTATION_INITIALIZER_ERROR);
    }

    @Test
    public void shouldFailToScanAnnotationWithoutValuesMethod() {
        // given
        AnnotatedObjectsScanner<Object> scanner = new AnnotatedObjectsScanner<Object>(packageList);

        // when
        catchException(scanner).getAnnotatedObjects(DummyAnnotationWithoutValues.class);

        // then
        assertThat(caughtException()).isInstanceOf(SmartParamException.class);
        assertThat((SmartParamException) caughtException()).hasErrorCode(SmartParamErrorCode.ANNOTATION_INITIALIZER_ERROR);
    }

    @Test
    public void shouldFailToScanAnnotationWithoutOrderMethod() {
        // given
        AnnotatedObjectsScanner<Object> scanner = new AnnotatedObjectsScanner<Object>(packageList);

        // when
        catchException(scanner).getAnnotatedObjects(DummyAnnotationWithoutOrder.class);

        // then
        assertThat(caughtException()).isInstanceOf(SmartParamException.class);
        assertThat((SmartParamException) caughtException()).hasErrorCode(SmartParamErrorCode.ANNOTATION_INITIALIZER_ERROR);
    }
}