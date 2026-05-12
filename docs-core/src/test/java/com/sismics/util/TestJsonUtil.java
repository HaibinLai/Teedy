package com.sismics.util;

import jakarta.json.JsonValue;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test of JsonUtil.
 */
public class TestJsonUtil {

    @Test
    public void testNullableStringNull() {
        JsonValue result = JsonUtil.nullable((String) null);
        Assert.assertEquals(JsonValue.NULL, result);
    }

    @Test
    public void testNullableStringNonNull() {
        JsonValue result = JsonUtil.nullable("hello");
        Assert.assertNotNull(result);
        Assert.assertNotEquals(JsonValue.NULL, result);
        Assert.assertEquals("hello", ((jakarta.json.JsonString) result).getString());
    }

    @Test
    public void testNullableStringEmpty() {
        JsonValue result = JsonUtil.nullable("");
        Assert.assertNotNull(result);
        Assert.assertNotEquals(JsonValue.NULL, result);
        Assert.assertEquals("", ((jakarta.json.JsonString) result).getString());
    }

    @Test
    public void testNullableIntegerNull() {
        JsonValue result = JsonUtil.nullable((Integer) null);
        Assert.assertEquals(JsonValue.NULL, result);
    }

    @Test
    public void testNullableIntegerNonNull() {
        JsonValue result = JsonUtil.nullable(42);
        Assert.assertNotNull(result);
        Assert.assertNotEquals(JsonValue.NULL, result);
        Assert.assertEquals(42, ((jakarta.json.JsonNumber) result).intValue());
    }

    @Test
    public void testNullableIntegerZero() {
        JsonValue result = JsonUtil.nullable(0);
        Assert.assertNotNull(result);
        Assert.assertEquals(0, ((jakarta.json.JsonNumber) result).intValue());
    }

    @Test
    public void testNullableLongNull() {
        JsonValue result = JsonUtil.nullable((Long) null);
        Assert.assertEquals(JsonValue.NULL, result);
    }

    @Test
    public void testNullableLongNonNull() {
        JsonValue result = JsonUtil.nullable(123456789L);
        Assert.assertNotNull(result);
        Assert.assertNotEquals(JsonValue.NULL, result);
        Assert.assertEquals(123456789L, ((jakarta.json.JsonNumber) result).longValue());
    }

    @Test
    public void testNullableIntegerNegative() {
        JsonValue result = JsonUtil.nullable(-10);
        Assert.assertNotNull(result);
        Assert.assertEquals(-10, ((jakarta.json.JsonNumber) result).intValue());
    }

    @Test
    public void testNullableLongZero() {
        JsonValue result = JsonUtil.nullable(0L);
        Assert.assertNotNull(result);
        Assert.assertEquals(0L, ((jakarta.json.JsonNumber) result).longValue());
    }
}
