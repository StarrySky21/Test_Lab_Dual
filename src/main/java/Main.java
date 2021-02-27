import java.io.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src\\main\\resources\\language.properties");

//создаем объект Properties и загружаем в него данные из файла.
        Properties properties = new Properties();
        properties.load(new FileReader(file));

//получаем значения свойств из объекта Properties
        String input = properties.getProperty("lang");

        Conversion con = new Conversion();
        con.makeTimetable(input);
    }
}
