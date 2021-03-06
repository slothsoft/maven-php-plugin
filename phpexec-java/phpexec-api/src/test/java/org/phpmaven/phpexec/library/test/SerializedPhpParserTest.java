/**
 * Copyright 2010-2012 by PHP-maven.org
 *
 * This file is part of phpexec-java.
 *
 * phpexec-java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * phpexec-java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with phpexec-java.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.phpmaven.phpexec.library.test;

/*
Copyright (c) 2007 Zsolt SzÃ¡sz <zsolt at lorecraft dot com>

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.phpmaven.phpexec.library.DeserializePhp;


/**
 * Test case.
 *
 * @author http://code.google.com/p/serialized-php-parser/
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 0.1.7
 */
public class SerializedPhpParserTest {

	/**
	 * Tests parsing NULL.
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testParseNull() throws Exception {
		final String input = "N;";
		final DeserializePhp serializedPhpParser = new DeserializePhp(input);
		final Object result = serializedPhpParser.parse();
		Assert.assertEquals(DeserializePhp.NULL, result);
	}

	/**
	 * Tests illegal string.
	 * @throws Exception thrown on errors.
	 */

	@Test
	public void testIllegal() throws Exception {
		try {
			final String input = "A;";
			final DeserializePhp serializedPhpParser = new DeserializePhp(input);
			serializedPhpParser.parse();
			Assert.fail("Expected exception not thrown");
		} catch (final IllegalStateException ex) {
			if (!ex.getMessage().startsWith("Encountered unknown type")) {
				Assert.fail("Expected exception message not thrown: " + ex.getMessage());
			}
		}
	}

	/**
	 * Tests parsing integers.
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testParseInteger() throws Exception {
		assertPrimitive("i:123;", 123);
	}

	/**
	 * Tests parsing floats.
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testParseFloat() throws Exception {
		assertPrimitive("d:123.123;", 123.123d);
	}

	// TODO the following are failing.
//    public void testParseFloatRecognizedAsInteger1() throws Exception {
//        assertPrimitive("i:3422865137422183;", 3.422865137422183E15);
//    }
//    public void testParseFloatRecognizedAsInteger2() throws Exception {
//        assertPrimitive("i:100010001804;", 1.00010001804E11);
//    }

	/**
	 * Tests parsing booleans.
	 * @throws Exception thrown on errors.
	 */
	@Test
	public void testParseBoolean() throws Exception {
		assertPrimitive("b:1;", Boolean.TRUE);
		assertPrimitive("b:0;", Boolean.FALSE);
		assertPrimitive("b:false;", Boolean.FALSE);
	}

	/**
	 * Tests parsing strings.
	 * @throws Exception thrown on errors.
	 */
	@Test
	public void testParseString() throws Exception {
		assertPrimitive("s:6:\"string\";", "string");
	}

	/**
	 * Tests parsing strings with multi-byte utf8 characters.
	 * @throws Exception thrown on errors.
	 */
	@Test
	public void testParseStringUtf8() throws Exception {
		// ä is 2-byte: 0xc3 0xa4
		// € is 3-byte: 0xe2 0x82 0xac
		// 3 extra characters = 9 characters
		assertPrimitive("s:9:\"strä€g\";", "strä€g");
	}

	// java does currently not support this 4-byte-character
//    /**
//     * Tests parsing strings with 4-byte utf8 characters.
//     * @throws Exception thrown on errors.
//     */
//    public void testParseStringUtf8With4Byte() throws Exception {
//        // 𝄞 is 4-byte (violin key U+1D11E): 0xf0 0x9d 0x84 0x9e
//        // 3 extra characters = 9 characters
//        assertPrimitive("s:9:\"strin𝄞\";", "strin𝄞");
//    }

	/**
	 * Tests parsing arrays.
	 * @throws Exception throw on errors.
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testParseArray() throws Exception {
		final String input = "a:1:{i:1;i:2;}";
		final DeserializePhp serializedPhpParser = new DeserializePhp(input);
		final Object result = serializedPhpParser.parse();
		Assert.assertTrue(result instanceof Map);
		Assert.assertEquals(1, ((Map) result).size());
		Assert.assertEquals(2, ((Map) result).get(1));
	}

	/**
	 * Tests parsing objects.
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testParseObject() throws Exception {
		final String input = "O:8:\"TypeName\":1:{s:3:\"foo\";s:3:\"bar\";}";
		final DeserializePhp serializedPhpParser = new DeserializePhp(input);
		final Object result = serializedPhpParser.parse();
		Assert.assertTrue(result instanceof DeserializePhp.PhpObject);
		Assert.assertEquals(1, ((DeserializePhp.PhpObject) result).attributes.size());
		Assert.assertEquals("bar", ((DeserializePhp.PhpObject) result).attributes.get("foo"));

	}

	/**
	 * Tests parsing objects.
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testParseObjectAcceptedAttributes() throws Exception {
		final String input = "O:8:\"TypeName\":2:{s:3:\"foo\";s:3:\"bar\";s:3:\"baz\";s:6:\"foobar\";}";
		final DeserializePhp serializedPhpParser = new DeserializePhp(input);
		serializedPhpParser.setAcceptedAttributeNameRegex("foo");
		final Object result = serializedPhpParser.parse();
		Assert.assertTrue(result instanceof DeserializePhp.PhpObject);
		Assert.assertEquals(1, ((DeserializePhp.PhpObject) result).attributes.size());
		Assert.assertEquals("bar", ((DeserializePhp.PhpObject) result).attributes.get("foo"));
		Assert.assertNull(((DeserializePhp.PhpObject) result).attributes.get("baz"));
	}

	/**
	 * Tests parsing complex structures.
	 * @throws Exception thrown on errors.
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testParseComplexDataStructure() throws Exception {
		String input =
				"a:2:{i:0;a:8:{s:5:\"class\";O:7:\"MyClass\":1:{s:5:\"pippo\";s:4:\"test\";}" +
						"i:0;i:1;i:1;d:0.19999998807907104;i:2;b:1;i:3;b:0;i:4;N;i:5;a:1:{i:0;s:42:\"\"" +
						";\";\";\";\";ÎÑTËRÑÅTÌÔñÁL\";\";\";\";\";\";}i:6;O:6:\"Object\":0:{}}i:1;a:8:{" +
						"s:5:\"class\";O:7:\"MyClass\":1:{s:5:\"pippo\";s:4:\"test\";}i:0;i:1;i:1;d:" +
						"0.19999998807907104;i:2;b:1;i:3;b:0;i:4;N;i:5;a:1:{i:0;s:42:\"\";\";\";\";\";" +
						"ÎÑTËRÑÅTÌÔñÁL\";\";\";\";\";\";}i:6;O:6:\"Object\":0:{}}}";
		new DeserializePhp(input).parse();

		// sample output of a yahoo web image search api call
		input = "a:1:{s:9:\"ResultSet\";a:4:{s:21:\"totalResultsAvailable\";s:7:\"1177824\";s:20:\"" +
				"totalResultsReturned\";i:2;s:19:\"firstResultPosition\";i:1;s:6:\"Result\";a:2:{" +
				"i:0;a:10:{s:5:\"Title\";s:12:\"corvette.jpg\";s:7:\"Summary\";s:150:\"bluefirebar.gif" +
				" 03-Nov-2003 19:02 22k burning_frax.jpg 05-Jul-2002 14:34 169k corvette.jpg " +
				"21-Jan-2004 01:13 101k coupleblack.gif 03-Nov-2003 19:00 3k\";s:3:\"Url\";" +
				"s:48:\"http://www.vu.union.edu/~jaquezk/MG/corvette.jpg\";s:8:\"ClickUrl\";" +
				"s:48:\"http://www.vu.union.edu/~jaquezk/MG/corvette.jpg\";s:10:\"RefererUrl\";" +
				"s:35:\"http://www.vu.union.edu/~jaquezk/MG\";s:8:\"FileSize\";" +
				"s:7:\"101.5kB\";s:10:\"FileFormat\";s:4:\"jpeg\";s:6:\"Height\";s:3:\"768\";" +
				"s:5:\"Width\";s:4:\"1024\";s:9:\"Thumbnail\";a:3:{s:3:\"Url\";s:42:\"" +
				"http://sp1.mm-a1.yimg.com/image/2178288556\";" +
				"s:6:\"Height\";s:3:\"120\";s:5:\"Width\";s:3:\"160\";}}i:1;a:10:{s:5:\"Title\";" +
				"s:23:\"corvette_c6_mini_me.jpg\";s:7:\"Summary\";s:48:\"Corvette I , Corvette II , " +
				"Diablo , Enzo , Lotus\";" +
				"s:3:\"Url\";s:54:\"http://www.ku4you.com/minicars/corvette_c6_mini_me.jpg\";s:8:\"ClickUrl\";" +
				"s:54:\"http://www.ku4you.com/minicars/corvette_c6_mini_me.jpg\";s:10:\"RefererUrl\";" +
				"s:61:\"http://mik-blog.blogspot.com/2005_03_01_mik-blog_archive.html\";s:8:\"" +
				"FileSize\";s:4:\"55kB\";" +
				"s:10:\"FileFormat\";s:4:\"jpeg\";s:6:\"Height\";s:3:\"518\";s:5:\"Width\";s:3:\"700\";" +
				"s:9:\"Thumbnail\";a:3:{s:3:\"Url\";s:42:\"http://sp1.mm-a2.yimg.com/image/2295545420\";" +
				"s:6:\"Height\";s:3:\"111\";s:5:\"Width\";s:3:\"150\";}}}}}";
		final Map results = (Map) new DeserializePhp(input).parse();
		Assert.assertEquals(2, ((Map) ((Map) results.get("ResultSet")).get("Result")).size());
	}

	/**
	 * helper method to assert a primitive.
	 * @param input the serialized input
	 * @param expected the expected value
	 */
	private void assertPrimitive(String input, Object expected) {
		Assert.assertEquals(expected, new DeserializePhp(input).parse());
	}

	/**
	 * Tests special characters.
	 * @throws Exception thrown on errors.
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testParseStructureWithSpecialChars() throws Exception {
		final String input = "a:1:{i:0;O:9:\"albumitem\":19:{s:5:\"image\";O:5:\"image\":12:{s:4:\"name\";" +
				"s:26:\"top_story_promo_transition\";s:4:\"type\";s:3:\"png\";s:5:\"width\";i:640;" +
				"s:6:\"height\";i:212;s:11:\"resizedName\";s:32:\"top_story_promo_transition.sized\";" +
				"s:7:\"thumb_x\";N;s:7:\"thumb_y\";N;s:11:\"thumb_width\";N;s:12:\"thumb_height\";N;" +
				"s:9:\"raw_width\";i:900;s:10:\"raw_height\";i:298;s:7:\"version\";i:37;}s:9:\"thumbnail\";" +
				"O:5:\"image\":12:{s:4:\"name\";" +
				"s:32:\"top_story_promo_transition.thumb\";s:4:\"type\";s:3:\"png\";s:5:\"width\";i:150;" +
				"s:6:\"height\";" +
				"i:50;s:11:\"resizedName\";N;s:7:\"thumb_x\";N;s:7:\"thumb_y\";N;s:11:\"thumb_width\";" +
				"N;s:12:\"thumb_height\";N;s:9:\"raw_width\";i:150;s:10:\"raw_height\";i:50;s:7:\"version" +
				"\";i:37;}s:7:\"preview\";" +
				"N;s:7:\"caption\";s:6:\"supérb\";s:6:\"hidden\";N;s:9:\"highlight\";b:1;s:14:\"highlightImage" +
				"\";O:5:\"image\":12:{s:4:\"name\";" +
				"s:36:\"top_story_promo_transition.highlight\";s:4:\"type\";s:3:\"png\";s:5:\"width\";" +
				"i:150;s:6:\"height\";i:50;" +
				"s:11:\"resizedName\";N;s:7:\"thumb_x\";N;s:7:\"thumb_y\";N;s:11:\"thumb_width\";N;s:12:" +
				"\"thumb_height\";N;s:9:\"raw_width\";" +
				"i:150;s:10:\"raw_height\";i:50;s:7:\"version\";i:37;}s:11:\"isAlbumName\";N;s:6:" +
				"\"clicks\";N;s:8:\"keywords\";s:0:\"\";" +
				"s:8:\"comments\";N;s:10:\"uploadDate\";i:1196339460;s:15:\"itemCaptureDate\";i:1196339460;" +
				"s:8:\"exifData\";N;s:5:\"owner\";" +
				"s:20:\"1156837966_352721747\";s:11:\"extraFields\";a:1:{s:11:\"Description\";s:0:\"\";}" +
				"s:4:\"rank\";N;s:7:\"version\";i:37;s:7:\"emailMe\";N;}}";
		final Map results = (Map) new DeserializePhp(input, false).parse();
		Assert.assertTrue(results.toString().indexOf("supérb") > 0);
	}

	/**
	 * Tests with accepted attribute names.
	 * @throws Exception thrown on errors.
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testAcceptedAttributeNames() throws Exception {
		// sample output of a yahoo web image search api call
		final String input = "a:1:{s:9:\"ResultSet\";a:4:{s:21:\"totalResultsAvailable\";s:7:\"1177824\";s:20:" +
				"\"totalResultsReturned\";" +
				"i:2;s:19:\"firstResultPosition\";i:1;s:6:\"Result\";a:2:{i:0;a:10:{s:5:\"Title\";s:12:\"corvette.jpg\";" +
				"s:7:\"Summary\";s:150:\"bluefirebar.gif 03-Nov-2003 19:02 22k burning_frax.jpg 05-Jul-2002 " +
				"14:34 169k corvette.jpg " +
				"21-Jan-2004 01:13 101k coupleblack.gif 03-Nov-2003 19:00 3k\";s:3:\"Url\";" +
				"s:48:\"http://www.vu.union.edu/~jaquezk/MG/corvette.jpg\";s:8:\"ClickUrl\";" +
				"s:48:\"http://www.vu.union.edu/~jaquezk/MG/corvette.jpg\";s:10:\"RefererUrl\";" +
				"s:35:\"http://www.vu.union.edu/~jaquezk/MG\";s:8:\"FileSize\";" +
				"s:7:\"101.5kB\";s:10:\"FileFormat\";s:4:\"jpeg\";s:6:\"Height\";s:3:\"768\";" +
				"s:5:\"Width\";s:4:\"1024\";s:9:\"Thumbnail\";a:3:{s:3:\"Url\";s:42:\"" +
				"http://sp1.mm-a1.yimg.com/image/2178288556\";" +
				"s:6:\"Height\";s:3:\"120\";s:5:\"Width\";s:3:\"160\";}}i:1;a:10:{s:5:\"Title\";" +
				"s:23:\"corvette_c6_mini_me.jpg\";s:7:\"Summary\";s:48:\"Corvette I , " +
				"Corvette II , Diablo , Enzo , Lotus\";" +
				"s:3:\"Url\";s:54:\"http://www.ku4you.com/minicars/corvette_c6_mini_me.jpg\";s:8:\"ClickUrl\";" +
				"s:54:\"http://www.ku4you.com/minicars/corvette_c6_mini_me.jpg\";s:10:\"RefererUrl\";" +
				"s:61:\"http://mik-blog.blogspot.com/2005_03_01_mik-blog_archive.html\";s:8:\"FileSize\";s:4:\"55kB\";" +
				"s:10:\"FileFormat\";s:4:\"jpeg\";s:6:\"Height\";s:3:\"518\";s:5:\"Width\";s:3:\"700\";" +
				"s:9:\"Thumbnail\";a:3:{s:3:\"Url\";s:42:\"http://sp1.mm-a2.yimg.com/image/2295545420\";" +
				"s:6:\"Height\";s:3:\"111\";s:5:\"Width\";s:3:\"150\";}}}}}";

		final DeserializePhp serializedPhpParser = new DeserializePhp(input);
		serializedPhpParser.setAcceptedAttributeNameRegex("ResultSet|totalResultsReturned");
		final Object result = serializedPhpParser.parse();
		// available
		Assert.assertEquals(2, ((Map) ((Map) result).get("ResultSet")).get("totalResultsReturned"));
		// not available
		Assert.assertNull(((Map) ((Map) result).get("ResultSet")).get("totalResultsAvailable"));
	}

}
