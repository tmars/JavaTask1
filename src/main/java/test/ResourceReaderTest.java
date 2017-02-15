package test;

import com.talipov.*;
import org.junit.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Марсель on 15.02.2017.
 */
public class ResourceReaderTest {

    @Test
    public void read() throws ParserErrorException {
        Totalizer totalizer = new Totalizer();
        Parser parser = mock(Parser.class);
        when(parser.getNext())
                .thenReturn(1)
                .thenReturn(2)
                .thenReturn(4)
                .thenReturn(5)
                .thenReturn(null);

        ResourceReader reader = new ResourceReader(parser, totalizer);
        reader.read();
        assertEquals("Чтение данных из ресурса", 6, totalizer.getResult());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getStream() throws ResourceNotFoundException{
        ResourceReader.getStream("C:C");
    }

    @AfterClass
    public static void finish() {
        System.out.println("Тесты класса ResourceReader завершены.");
    }
}