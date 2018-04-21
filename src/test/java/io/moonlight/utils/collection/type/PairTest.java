package io.moonlight.utils.collection.type;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PairTest {

    @Test
    public void test() {
        Pair<String,Integer> pair  = Pair.of("haha", 1);
        Pair<String,Integer> pair2 = Pair.of("haha", 2);

        assertThat(pair.equals(pair2)).isFalse();
        assertThat(pair.hashCode() != pair2.hashCode()).isTrue();
        assertThat(pair.toString()).isEqualTo("Pair [first=haha, second=1]");
    }

}
