/*
 * Copyright 2016 E-System LLC
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

package com.es.lib.spring.service.file.model;

import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Zuzoev Dmitry - zuzoev.d@ext-system.com
 * @since 19.03.16
 */
public class Thumb {

    public static final int DEFAULT_WIDTH = 128;
    public static final int DEFAULT_HEIGHT = 128;

    private int width;
    private int height;

    public Thumb() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public Thumb(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isDefaultSize() {
        return getWidth() == DEFAULT_WIDTH && getHeight() == DEFAULT_HEIGHT;
    }

    public static Thumb extract(boolean need, String tw, String th) {
        if (!need) {
            return null;
        }
        return new Thumb(
            NumberUtils.toInt(tw, Thumb.DEFAULT_WIDTH),
            NumberUtils.toInt(th, Thumb.DEFAULT_HEIGHT)
        );
    }

    public static Thumb extract(Map<String, String> params) {
        return extract(
            params.containsKey("thumb"),
            params.get("tw"),
            params.get("th")
        );
    }

    public static Thumb extract(HttpServletRequest req, Map<String, String[]> params) {
        return extract(
            params.containsKey("thumb"),
            req.getParameter("tw"),
            req.getParameter("th")
        );
    }

    @Override
    public String toString() {
        return "Thumb{" +
               "width=" + width +
               ", height=" + height +
               '}';
    }
}
