/*
 * Copyright (c) E-System LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2018
 */

package com.es.lib.spring.service.converter;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 05.02.2018
 */
public abstract class BaseConverterService<R, T> {

    public Collection<R> convert(Collection<T> items) {
        return items.stream().filter(Objects::nonNull).map(this::convert).collect(Collectors.toList());
    }

    public R convert(T item) {
        if (item == null) {
            return null;
        }
        return realConvert(item);
    }

    protected abstract R realConvert(T item);
}
