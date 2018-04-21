package io.moonlight.utils.collection;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

/**
 * Created by qt on 2017/8/15.
 */
public class Maps {

    public static <K, V> Map<K,V> with(Entry<K,V>... entries) {
        Map<K,V> ret = new HashMap<>();
        Stream.of(entries).forEach(item -> ret.put(item.getKey(), item.getValue()));
        return ret;
    }

    public static <K, V> Entry<K,V> entry(K k, V v) {
        return new SimpleEntry<>(k, v);
    }

    public static <K, V> Map<K,V> of(K k, V v, K k1, V v1) {
        return with(entry(k, v), entry(k1, v1));
    }

    public static <K, V> Map<K,V> of(K k, V v, K k1, V v1, K k2, V v2) {
        return with(entry(k, v), entry(k1, v1), entry(k2, v2));
    }

    public static <K, V> Map<K,V> of(K k, V v) {
        return with(entry(k, v));
    }

}
