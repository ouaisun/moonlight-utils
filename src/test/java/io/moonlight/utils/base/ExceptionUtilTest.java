/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package io.moonlight.utils.base;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ExceptionUtilTest {

	private static RuntimeException TIMEOUT_EXCEPTION = ExceptionUtil.setStackTrace(new RuntimeException("Timeout"),
			ExceptionUtilTest.class, "hello");

	private static ExceptionUtil.CloneableException TIMEOUT_EXCEPTION2 = new ExceptionUtil.CloneableException("Timeout")
			.setStackTrace(ExceptionUtilTest.class, "hello");

	private static ExceptionUtil.CloneableRuntimeException TIMEOUT_EXCEPTION3 = new ExceptionUtil.CloneableRuntimeException("Timeout")
			.setStackTrace(ExceptionUtilTest.class, "hello");

	@Test
	public void unchecked() {
		// convert Exception to RuntimeException with cause
		Exception exception = new Exception("my exception");
		try {
			ExceptionUtil.unchecked(exception);
			fail("should fail before");
		} catch (Throwable t) {
			assertThat(t.getCause()).isSameAs(exception);
		}

		// do nothing of Error
		Error error = new LinkageError();
		try {
			ExceptionUtil.unchecked(error);
			fail("should fail before");
		} catch (Throwable t) {
			assertThat(t).isSameAs(error);
		}

		// do nothing of RuntimeException
		RuntimeException runtimeException = new RuntimeException("haha");
		try {
			ExceptionUtil.unchecked(runtimeException);
			fail("should fail before");
		} catch (Throwable t) {
			assertThat(t).isSameAs(runtimeException);
		}

	}

	@Test
	public void unwrap() {
		RuntimeException re = new RuntimeException("my runtime");
		assertThat(ExceptionUtil.unwrap(re)).isSameAs(re);

		ExecutionException ee = new ExecutionException(re);
		assertThat(ExceptionUtil.unwrap(ee)).isSameAs(re);

		InvocationTargetException ie = new InvocationTargetException(re);
		assertThat(ExceptionUtil.unwrap(ie)).isSameAs(re);

		Exception e = new Exception("my exception");
		ExecutionException ee2 = new ExecutionException(e);
		try{
		ExceptionUtil.uncheckedAndWrap(ee2);
		}catch (Throwable t) {
			assertThat(t).isInstanceOf(ExceptionUtil.UncheckedException.class).hasCauseExactlyInstanceOf(Exception.class);
		}
	}

	@Test
	public void getStackTraceAsString() {
		Exception exception = new Exception("my exception");
		RuntimeException runtimeException = new RuntimeException(exception);

		String stack = ExceptionUtil.stackTraceText(runtimeException);
		System.out.println(stack);
	}

	@Test
	public void isCausedBy() {
		IOException ioexception = new IOException("my exception");
		IllegalStateException illegalStateException = new IllegalStateException(ioexception);
		RuntimeException runtimeException = new RuntimeException(illegalStateException);

		assertThat(ExceptionUtil.isCausedBy(runtimeException, IOException.class)).isTrue();
		assertThat(ExceptionUtil.isCausedBy(runtimeException, IllegalStateException.class, IOException.class)).isTrue();
		assertThat(ExceptionUtil.isCausedBy(runtimeException, Exception.class)).isTrue();
		assertThat(ExceptionUtil.isCausedBy(runtimeException, IllegalAccessException.class)).isFalse();
	}

	@Test
	public void getRootCause() {
		IOException ioexception = new IOException("my exception");
		IllegalStateException illegalStateException = new IllegalStateException(ioexception);
		RuntimeException runtimeException = new RuntimeException(illegalStateException);

		assertThat(ExceptionUtil.getRootCause(runtimeException)).isSameAs(ioexception);
		// 无cause
		assertThat(ExceptionUtil.getRootCause(ioexception)).isSameAs(ioexception);
	}

	@Test
	public void buildMessage() {
		IOException ioexception = new IOException("my exception");
		assertThat(ExceptionUtil.toStringWithShortName(ioexception)).isEqualTo("IOException: my exception");
		assertThat(ExceptionUtil.toStringWithShortName(null)).isEqualTo("");

		RuntimeException runtimeExcetpion = new RuntimeException("my runtimeException", ioexception);
		assertThat(ExceptionUtil.toStringWithRootCause(runtimeExcetpion))
				.isEqualTo("RuntimeException: my runtimeException; <---IOException: my exception");

		assertThat(ExceptionUtil.toStringWithRootCause(null)).isEqualTo("");
		// 无cause
		assertThat(ExceptionUtil.toStringWithRootCause(ioexception)).isEqualTo("IOException: my exception");
	}

	@Test
	public void clearStackTrace() {
		IOException ioexception = new IOException("my exception");
		RuntimeException runtimeException = new RuntimeException(ioexception);

		System.out.println(ExceptionUtil.stackTraceText(ExceptionUtil.clearStackTrace(runtimeException)));

	}

	@Test
	public void staticException() {
		assertThat(ExceptionUtil.stackTraceText(TIMEOUT_EXCEPTION)).hasLineCount(2)
				.contains("java.lang.RuntimeException: Timeout")
				.contains("at org.springside.modules.utils.base.ExceptionUtilTest.hello(Unknown Source)");

		assertThat(ExceptionUtil.stackTraceText(TIMEOUT_EXCEPTION2)).hasLineCount(2)
				.contains("org.springside.modules.utils.base.ExceptionUtil$CloneableException: Timeout")
				.contains("at org.springside.modules.utils.base.ExceptionUtilTest.hello(Unknown Source)");

		ExceptionUtil.CloneableException timeoutException = TIMEOUT_EXCEPTION2.clone("Timeout for 30ms");
		assertThat(ExceptionUtil.stackTraceText(timeoutException)).hasLineCount(2)
				.contains("org.springside.modules.utils.base.ExceptionUtil$CloneableException: Timeout for 30ms")
				.contains("at org.springside.modules.utils.base.ExceptionUtilTest.hello(Unknown Source)");

		assertThat(ExceptionUtil.stackTraceText(TIMEOUT_EXCEPTION3)).hasLineCount(2)
				.contains("org.springside.modules.utils.base.ExceptionUtil$CloneableRuntimeException: Timeout")
				.contains("at org.springside.modules.utils.base.ExceptionUtilTest.hello(Unknown Source)");

		ExceptionUtil.CloneableRuntimeException timeoutRuntimeException = TIMEOUT_EXCEPTION3.clone("Timeout for 40ms");
		assertThat(ExceptionUtil.stackTraceText(timeoutRuntimeException)).hasLineCount(2)
				.contains("org.springside.modules.utils.base.ExceptionUtil$CloneableRuntimeException: Timeout for 40ms")
				.contains("at org.springside.modules.utils.base.ExceptionUtilTest.hello(Unknown Source)");

	}

}
