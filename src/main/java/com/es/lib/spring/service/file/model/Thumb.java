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
