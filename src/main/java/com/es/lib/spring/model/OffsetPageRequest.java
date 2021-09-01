/*
 * Copyright 2020 E-System LLC
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
package com.es.lib.spring.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

@EqualsAndHashCode
@ToString
public class OffsetPageRequest implements Pageable, Serializable {

    private final int limit;
    private final long offset;
    private final Sort sort;

    public static OffsetPageRequest of(long offset, int limit) {
        return of(offset, limit, Sort.unsorted());
    }

    public static OffsetPageRequest of(long offset, int limit, Sort sort) {
        return new OffsetPageRequest(offset, limit, sort);
    }

    public static OffsetPageRequest of(long offset, int limit, Sort.Direction direction, String... properties) {
        return of(offset, limit, Sort.by(direction, properties));
    }

    protected OffsetPageRequest(long offset, int limit, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }
        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    protected OffsetPageRequest(int offset, int limit, Sort.Direction direction, String... properties) {
        this(offset, limit, Sort.by(direction, properties));
    }

    protected OffsetPageRequest(int offset, int limit) {
        this(offset, limit, Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public int getPageNumber() {
        return (int) (offset / limit);
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetPageRequest(getOffset() + getPageSize(), getPageSize(), getSort());
    }

    public OffsetPageRequest previous() {
        return hasPrevious() ? new OffsetPageRequest(getOffset() - getPageSize(), getPageSize(), getSort()) : this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetPageRequest(0, getPageSize(), getSort());
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return new OffsetPageRequest((long) pageNumber * limit, this.getPageSize(), this.getSort());
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }
}