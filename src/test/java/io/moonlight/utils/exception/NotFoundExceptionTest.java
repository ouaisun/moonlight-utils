package io.moonlight.utils.exception;

import org.junit.Test;

/**
 * Created by qt on 2017/8/15.
 */
public class NotFoundExceptionTest {

    public void test1(){
        throw new NotFoundException(11,"e");
    }

    @Test
    public void test2() throws Exception{
        try {
            test1();
        } catch (NotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getCode());
        }
    }
}
