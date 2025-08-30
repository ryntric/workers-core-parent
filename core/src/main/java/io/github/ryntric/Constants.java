package io.github.ryntric;

import sun.misc.Unsafe;

/**
 * author: ryntric
 * date: 8/14/25
 * time: 12:33 PM
 **/

public interface Constants {
    int CACHE_LINE_SIZE = 64;
    int OBJECT_ARRAY_PADDING = CACHE_LINE_SIZE / Unsafe.ARRAY_OBJECT_INDEX_SCALE;
    int BYTE_BUFFER_PADDING =  64;
}
