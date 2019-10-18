package com.gxx.back.utils;

import java.util.Collection;
import java.util.Map;

public class StringUtil {
    /**
     * 判断是否为空字符串的最优代码
     * @param str 如果为空则返回 true
     * @return
     */
    public static boolean StringIsEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
    /**
     * 判断字符串是否非空
     * @param str 如果非空，则返回true
     * @return
     */
    public static boolean StringIsNotEmpty(String str) {
        return !StringIsEmpty(str);
    }
    /**
     * 判断数组是否为空
     * @param array 如果为空则返回true
     * @return
     */
    public static boolean ArrayIsEmpty(Object[] array) {
        return array == null || array.length == 0;
    }
    /**
     * 判断数组是否为非空
     * @param array 如果为非空则返回true
     * @return
     */
    public static boolean ArrayIsNotEmpty(Object[] array) {
        return !ArrayIsEmpty(array);
    }
    /**
     * 判断map是否为空
     * @param map 如果为空则返回true
     * @return
     */
    public static boolean MapIsEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
    /**
     * 判断map是否非空
     * @param map 如果为非空则返回true
     * @return
     */
    public static boolean MapIsNotEmpty(Map<?, ?> map) {
        return !MapIsEmpty(map);
    }
    /**
     * 判断集合是否为空
     * @param collection 如果集合为空则返回true
     * @return
     */
    public static boolean CollectionIsEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
    /**
     * 判断集合是否为非空
     * @param collection 如果集合为非空则返回true
     * @return
     */
    public static boolean CollectionIsNotEmpty(Collection<?> collection) {
        return !CollectionIsEmpty(collection);
    }
}
