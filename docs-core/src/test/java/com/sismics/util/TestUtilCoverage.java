package com.sismics.util;

import com.sismics.util.mime.MimeType;
import com.sismics.util.mime.MimeTypeUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Additional tests for utility classes to improve JaCoCo coverage.
 *
 * Targets:
 *   - {@link MimeTypeUtil#getFileExtension(String)} (all switch branches)
 *   - {@link LocaleUtil#getLocale(String)} (null / empty / lang / lang_country / lang_country_variant)
 *   - {@link MessageUtil#getMessage} (existing key, missing key, bundle accessor)
 */
public class TestUtilCoverage {

    // ---- MimeTypeUtil.getFileExtension --------------------------------------------------

    @Test
    public void testGetFileExtension_allKnownTypes() {
        Assert.assertEquals("zip", MimeTypeUtil.getFileExtension(MimeType.APPLICATION_ZIP));
        Assert.assertEquals("gif", MimeTypeUtil.getFileExtension(MimeType.IMAGE_GIF));
        Assert.assertEquals("jpg", MimeTypeUtil.getFileExtension(MimeType.IMAGE_JPEG));
        Assert.assertEquals("png", MimeTypeUtil.getFileExtension(MimeType.IMAGE_PNG));
        Assert.assertEquals("pdf", MimeTypeUtil.getFileExtension(MimeType.APPLICATION_PDF));
        Assert.assertEquals("odt", MimeTypeUtil.getFileExtension(MimeType.OPEN_DOCUMENT_TEXT));
        Assert.assertEquals("docx", MimeTypeUtil.getFileExtension(MimeType.OFFICE_DOCUMENT));
        Assert.assertEquals("txt", MimeTypeUtil.getFileExtension(MimeType.TEXT_PLAIN));
        Assert.assertEquals("csv", MimeTypeUtil.getFileExtension(MimeType.TEXT_CSV));
        Assert.assertEquals("mp4", MimeTypeUtil.getFileExtension(MimeType.VIDEO_MP4));
        Assert.assertEquals("webm", MimeTypeUtil.getFileExtension(MimeType.VIDEO_WEBM));
    }

    @Test
    public void testGetFileExtension_unknownTypeReturnsBin() {
        Assert.assertEquals("bin", MimeTypeUtil.getFileExtension("application/x-unknown"));
        Assert.assertEquals("bin", MimeTypeUtil.getFileExtension(MimeType.DEFAULT));
    }

    // ---- LocaleUtil.getLocale -----------------------------------------------------------

    @Test
    public void testGetLocale_nullReturnsEnglish() {
        Assert.assertEquals(Locale.ENGLISH, LocaleUtil.getLocale(null));
    }

    @Test
    public void testGetLocale_emptyReturnsEnglish() {
        Assert.assertEquals(Locale.ENGLISH, LocaleUtil.getLocale(""));
    }

    @Test
    public void testGetLocale_languageOnly() {
        Locale locale = LocaleUtil.getLocale("fr");
        Assert.assertEquals("fr", locale.getLanguage());
        Assert.assertEquals("", locale.getCountry());
        Assert.assertEquals("", locale.getVariant());
    }

    @Test
    public void testGetLocale_languageAndCountry() {
        Locale locale = LocaleUtil.getLocale("fr_FR");
        Assert.assertEquals("fr", locale.getLanguage());
        Assert.assertEquals("FR", locale.getCountry());
        Assert.assertEquals("", locale.getVariant());
    }

    @Test
    public void testGetLocale_languageCountryVariant() {
        Locale locale = LocaleUtil.getLocale("zh_CN_Hans");
        Assert.assertEquals("zh", locale.getLanguage());
        Assert.assertEquals("CN", locale.getCountry());
        Assert.assertEquals("Hans", locale.getVariant());
    }

    // ---- MessageUtil.getMessage ---------------------------------------------------------

    @Test
    public void testMessageUtil_missingKey() {
        String message = MessageUtil.getMessage(Locale.ENGLISH, "non_existing_key_xyz");
        Assert.assertEquals("**non_existing_key_xyz**", message);
    }

    @Test
    public void testMessageUtil_returnsBundle() {
        ResourceBundle bundle = MessageUtil.getMessage(Locale.ENGLISH);
        Assert.assertNotNull(bundle);
    }

    @Test
    public void testMessageUtil_existingKeyWithFormatting() {
        // Use a key that exists in messages.properties; pick one robustly by querying the bundle.
        ResourceBundle bundle = MessageUtil.getMessage(Locale.ENGLISH);
        String anyKey = bundle.getKeys().nextElement();
        String expected = bundle.getString(anyKey);
        String actual = MessageUtil.getMessage(Locale.ENGLISH, anyKey);
        // MessageFormat.format may transform braces; for keys without placeholders the result is unchanged.
        Assert.assertNotNull(actual);
        if (!expected.contains("{")) {
            Assert.assertEquals(expected, actual);
        }
    }
}
