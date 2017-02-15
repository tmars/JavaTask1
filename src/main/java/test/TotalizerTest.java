package test;

import com.talipov.Totalizer;
import org.junit.*;
import static junit.framework.TestCase.*;

/**
 * Created by Марсель on 07.02.2017.
 * Модульный тест общего обработчика данных всех ресурсов
 */
public class TotalizerTest {

    @Test
    public void test1() {
        Totalizer totalizer = new Totalizer();
        totalizer.add(1);
        totalizer.add(2);
        assertEquals("Четное и нечетное число", 2, totalizer.getResult());
    }

    @Test
    public void test2() {
        Totalizer totalizer = new Totalizer();
        totalizer.add(-2);
        totalizer.add(3);
        assertEquals("Отрицательно и нечетное", 0, totalizer.getResult());
    }

    @Test
    public void test3() {
        Totalizer totalizer = new Totalizer();
        totalizer.add(4);
        totalizer.add(8);
        assertEquals("Два четных числа",12, totalizer.getResult());
    }

    @AfterClass
    public static void finish() {
        System.out.println("Тесты класса Totalizer завершены.");
    }
}
