/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2015
 */

package com.es.lib.spring.security.event;

import com.es.lib.spring.security.model.SecurityRole;
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
public class PermissionAvailableCheckEvent {

    private final SecurityRole role;
    private final String target;
    private final String action;
}