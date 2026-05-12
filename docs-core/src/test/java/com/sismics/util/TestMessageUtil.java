package com.sismics.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Test of MessageUtil.
 */
public class TestMessageUtil {

    @Test
    public void testGetMessageExistingKey() {
        String result = MessageUtil.getMessage(Locale.SIMPLIFIED_CHINESE, "email.template.password_recovery.subject");
        Assert.assertEquals("请重置您的密码", result);
    }

    @Test
    public void testGetMessageWithArgs() {
        String result = MessageUtil.getMessage(Locale.SIMPLIFIED_CHINESE, "email.template.password_recovery.hello", "John");
        Assert.assertEquals("您好 John.", result);
    }

    @Test
    public void testGetMessageMissingKey() {
        String result = MessageUtil.getMessage(Locale.ENGLISH, "non.existent.key");
        Assert.assertEquals("**non.existent.key**", result);
    }

    @Test
    public void testGetMessageBundle() {
        ResourceBundle bundle = MessageUtil.getMessage(Locale.ENGLISH);
        Assert.assertNotNull(bundle);
        Assert.assertTrue(bundle.containsKey("email.template.password_recovery.subject"));
    }

    @Test
    public void testGetMessageChineseLocale() {
        // Chinese locale properties file exists
        ResourceBundle bundle = MessageUtil.getMessage(Locale.SIMPLIFIED_CHINESE);
        Assert.assertNotNull(bundle);
    }

    @Test
    public void testGetMessageFrenchLocale() {
        ResourceBundle bundle = MessageUtil.getMessage(Locale.FRENCH);
        Assert.assertNotNull(bundle);
    }
}
