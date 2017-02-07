import com.talipov.Parser;
import com.talipov.ParserErrorException;
import org.junit.*;

import static junit.framework.TestCase.assertEquals;
import java.util.Arrays;


/**
 * Created by Марсель on 07.02.2017.
 */
public class ParserTest {
    private Parser parser;

    public ParserTest() {
        parser = new Parser();
    }

    @Test
    public void correctTest() throws ParserErrorException {
        assertEquals(Arrays.asList(1, 2), parser.parse("1 2"));
        assertEquals(Arrays.asList(3, -4), parser.parse("3 -4"));
    }

    @Test
    public void spaceTest() throws ParserErrorException {
        assertEquals("Лидирующие пробелы", Arrays.asList(1, 2), parser.parse("   1 2"));
        assertEquals("Завершающие пробелы", Arrays.asList(3, -4), parser.parse("3 -4   "));
        assertEquals("Лишние пробелы между числами", Arrays.asList(5, -6), parser.parse("5   -6"));
    }

    @Test(expected = ParserErrorException.class)
    public void floatTest() throws ParserErrorException {
        parser.parse("1 2.3");
    }

    @Test(expected = ParserErrorException.class)
    public void incorrectStringTest() throws ParserErrorException {
        parser.parse("1 error 2");
    }

    @AfterClass
    public static void finish() {
        System.out.println("Тесты завершены.");
    }
}