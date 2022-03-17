/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.spring.security.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Dmitriy Zuzoev - zuzoev.d@ext-system.com
 * @since 15.03.2022
 */
@Getter
@ToString
@RequiredArgsConstructor
public class PermissionReloadEvent {

    private final boolean reloadAll;
    private final String scopeGroup;
    private final Number idScope;
}
