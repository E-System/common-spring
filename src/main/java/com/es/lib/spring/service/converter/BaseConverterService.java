/*
 * Copyright 2018 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.es.lib.spring.service.converter;

import com.es.lib.common.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 05.02.2018
 */
public abstract class BaseConverterService<R, T> {

    public Collection<R> convert(Collection<T> items) {
        return convert(items, null);
    }

    public Collection<R> convert(Collection<T> items, BiConsumer<T, R> enhancer) {
        if (CollectionUtil.isEmpty(items)) {
            return new ArrayList<>();
        }
        return items.stream().filter(Objects::nonNull).map(v -> convert(v, enhancer)).collect(Collectors.toList());
    }

    public R convert(T item) {
        return convert(item, null);
    }

    public R convert(T item, BiConsumer<T, R> enhancer) {
        if (item == null) {
            return null;
        }
        R result = realConvert(item);
        if (enhancer != null) {
            enhancer.accept(item, result);
        }
        return result;
    }

    protected abstract R realConvert(T item);
}
