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

import java.util.Map;
import org.smartparam.engine.annotations.ParamFunctionInvoker;
import org.smartparam.engine.bean.RepositoryObjectKey;
import org.smartparam.engine.annotations.scanner.TypeScanner;
import org.smartparam.engine.core.MapRepository;
import org.smartparam.engine.core.invoker.FunctionInvoker;
import org.smartparam.engine.model.function.Function;

/**
 * @author Przemek Hertel
 * @since 1.0.0
 */
public class BasicInvokerRepository implements InvokerRepository, TypeScanningRepository {

    private MapRepository<FunctionInvoker> innerRepository = new MapRepository<FunctionInvoker>(FunctionInvoker.class);

    @Override
    public void scanAnnotations(TypeScanner scanner) {
        Map<RepositoryObjectKey, FunctionInvoker> invokers = scanner.scanTypes(ParamFunctionInvoker.class);
        innerRepository.registerAll(invokers);
    }

    @Override
    public FunctionInvoker getInvoker(Function function) {
        return innerRepository.getItem(function.getType());
    }

    @Override
    public void register(String code, FunctionInvoker invoker) {
        innerRepository.register(code, invoker);
    }

    @Override
    public Map<String, FunctionInvoker> registeredItems() {
        return innerRepository.getItemsUnordered();
    }

    @Override
    public void registerAll(Map<String, FunctionInvoker> invokers) {
        innerRepository.registerAllUnordered(invokers);
    }
}
