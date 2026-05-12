package com.sismics.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

/**
 * Test of LocaleUtil.
 */
public class TestLocaleUtil {

    @Test
    public void testNullLocaleCode() {
        Locale result = LocaleUtil.getLocale(null);
        Assert.assertEquals(Locale.ENGLISH, result);
    }

    @Test
    public void testEmptyLocaleCode() {
        Locale result = LocaleUtil.getLocale("");
        Assert.assertEquals(Locale.ENGLISH, result);
    }

    @Test
    public void testLanguageOnly() {
        Locale result = LocaleUtil.getLocale("fr");
        Assert.assertEquals("fr", result.getLanguage());
        Assert.assertEquals("", result.getCountry());
    }

    @Test
    public void testLanguageAndCountry() {
        Locale result = LocaleUtil.getLocale("fr_FR");
        Assert.assertEquals("fr", result.getLanguage());
        Assert.assertEquals("FR", result.getCountry());
    }

    @Test
    public void testLanguageCountryAndVariant() {
        Locale result = LocaleUtil.getLocale("fr_FR_POSIX");
        Assert.assertEquals("fr", result.getLanguage());
        Assert.assertEquals("FR", result.getCountry());
        Assert.assertEquals("POSIX", result.getVariant());
    }

    @Test
    public void testEnglishLocale() {
        Locale result = LocaleUtil.getLocale("en");
        Assert.assertEquals("en", result.getLanguage());
    }

    @Test
    public void testChineseLocale() {
        Locale result = LocaleUtil.getLocale("zh_CN");
        Assert.assertEquals("zh", result.getLanguage());
        Assert.assertEquals("CN", result.getCountry());
    }

    @Test
    public void testGermanLocale() {
        Locale result = LocaleUtil.getLocale("de_DE");
        Assert.assertEquals("de", result.getLanguage());
        Assert.assertEquals("DE", result.getCountry());
    }
}
