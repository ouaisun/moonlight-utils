package io.moonlight.utils.concurrent;

import io.moonlight.utils.concurrent.jsr166e.LongAdder;
import org.junit.Test;

public class ConcurrentToolsTest {

    @Test
    public void longAdder() {
        LongAdder counter = ConcurrentTools.longAdder();
        counter.increment();
        counter.add(2);
        //Assert.assertThat(counter.longValue()).isEqualTo(3L);
    }

}
