package test;

import com.talipov.ResourceNotFoundException;
import com.talipov.parser.ScannerParser;
import org.junit.*;
import static junit.framework.TestCase.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.talipov.parser.Parser;
import com.talipov.ParserErrorException;

/**
 * Created by Марсель on 07.02.2017.
 * Модульные тест парсера данных из ресурса
 */
public class ParserTest {
    private Parser parser;

    private ArrayList<Integer> test(String input) throws ParserErrorException {
        Parser parser = null;
        try {
            parser = new ScannerParser(input);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> nums = new ArrayList<Integer>();
        Integer value;
        while ((value = parser.getNext()) != null) {
            nums.add(value);
        }

        return nums;
    }

    @Test
    public void correctTest() throws ParserErrorException {
        assertEquals(Arrays.asList(1, 2), test("1 2"));
        assertEquals(Arrays.asList(3, -4), test("3 -4"));
    }

    @Test
    public void spaceTest() throws ParserErrorException {
        assertEquals("Лидирующие пробелы", Arrays.asList(1, 2), test("   1 2"));
        assertEquals("Завершающие пробелы", Arrays.asList(3, -4), test("3 -4   "));
        assertEquals("Лишние пробелы между числами", Arrays.asList(5, -6), test("5   -6"));
    }

    @Test
    public void floatTest() {
        try {
            test("1 2.3");
            fail("Числа с плавающей запятой");
        } catch (ParserErrorException e) {
        }
    }

    @Test
    public void incorrectStringTest() throws ParserErrorException {
        try {
            test("1 error 2");
            fail("Некорректные символы");
        } catch (ParserErrorException e) {
        }
    }

    @AfterClass
    public static void finish() {
        System.out.println("Тесты класса Parser завершены.");
    }
}