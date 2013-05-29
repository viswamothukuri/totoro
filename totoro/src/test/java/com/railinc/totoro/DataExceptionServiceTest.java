package com.railinc.totoro;

import java.lang.reflect.Type;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.integration.Message;

import com.railinc.totoro.dataexception.DataExceptionService;

public class DataExceptionServiceTest {
	@Test
	public void testUpdateResponsiblePerson_withMessageAsParameter_hasVoidReturnType() throws SecurityException, NoSuchMethodException{
		Type type=DataExceptionService.class.getDeclaredMethod("updateResponsiblePerson", Message.class).getGenericReturnType();
		Assert.assertEquals(void.class, type);
	}
}
