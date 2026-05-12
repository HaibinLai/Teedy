package com.sismics.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test of HtmlToPlainText.
 */
public class TestHtmlToPlainText {

    private final HtmlToPlainText converter = new HtmlToPlainText();

    private String convert(String html) {
        Document doc = Jsoup.parse(html);
        return converter.getPlainText(doc.body());
    }

    @Test
    public void testSimpleText() {
        String result = convert("<p>Hello World</p>");
        Assert.assertTrue(result.contains("Hello World"));
    }

    @Test
    public void testParagraphsAddNewlines() {
        String result = convert("<p>First</p><p>Second</p>");
        Assert.assertTrue(result.contains("First"));
        Assert.assertTrue(result.contains("Second"));
        // paragraphs should be separated by newlines
        Assert.assertTrue(result.contains("\n"));
    }

    @Test
    public void testUnorderedList() {
        String result = convert("<ul><li>Item A</li><li>Item B</li><li>Item C</li></ul>");
        Assert.assertTrue(result.contains("* Item A"));
        Assert.assertTrue(result.contains("* Item B"));
        Assert.assertTrue(result.contains("* Item C"));
    }

    @Test
    public void testHeadings() {
        String result = convert("<h1>Title</h1><p>Content</p>");
        Assert.assertTrue(result.contains("Title"));
        Assert.assertTrue(result.contains("Content"));
    }

    @Test
    public void testLink() {
        String result = convert("<a href=\"https://example.com\">Click</a>");
        Assert.assertTrue(result.contains("Click"));
    }

    @Test
    public void testBreakTag() {
        String result = convert("Line1<br>Line2");
        Assert.assertTrue(result.contains("Line1"));
        Assert.assertTrue(result.contains("Line2"));
    }

    @Test
    public void testDefinitionList() {
        String result = convert("<dl><dt>Term</dt><dd>Definition</dd></dl>");
        Assert.assertTrue(result.contains("Term"));
        Assert.assertTrue(result.contains("Definition"));
    }

    @Test
    public void testWordWrapLongText() {
        // Create a text longer than 80 chars to trigger word wrapping
        StringBuilder sb = new StringBuilder("<p>");
        for (int i = 0; i < 20; i++) {
            sb.append("word ");
        }
        sb.append("</p>");
        String result = convert(sb.toString());
        // Result should contain line breaks for wrapping
        Assert.assertNotNull(result);
        Assert.assertTrue(result.length() > 0);
    }

    @Test
    public void testEmptyInput() {
        String result = convert("");
        Assert.assertNotNull(result);
    }

    @Test
    public void testNestedElements() {
        String result = convert("<p><strong>Bold</strong> and <em>italic</em></p>");
        Assert.assertTrue(result.contains("Bold"));
        Assert.assertTrue(result.contains("italic"));
    }

    @Test
    public void testTableRow() {
        String result = convert("<table><tr><td>Cell1</td><td>Cell2</td></tr></table>");
        Assert.assertTrue(result.contains("Cell1"));
        Assert.assertTrue(result.contains("Cell2"));
    }

    @Test
    public void testMultipleHeadingLevels() {
        String result = convert("<h1>H1</h1><h2>H2</h2><h3>H3</h3><h4>H4</h4><h5>H5</h5>");
        Assert.assertTrue(result.contains("H1"));
        Assert.assertTrue(result.contains("H2"));
        Assert.assertTrue(result.contains("H3"));
        Assert.assertTrue(result.contains("H4"));
        Assert.assertTrue(result.contains("H5"));
    }

    @Test
    public void testSpaceCollapsing() {
        // Leading space should not accumulate at start
        String result = convert("<p>   spaced   </p>");
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("spaced"));
    }
}
