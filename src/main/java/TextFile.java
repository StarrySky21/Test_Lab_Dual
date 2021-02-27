import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TextFile {
    TextFile(){

    }
    public List<String> readFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
    public void writeFile (List<String> poshList, List<String> grottyList) throws IOException {
        FileWriter writer = new FileWriter("output.txt");
        for (int i = 0; i < poshList.size(); i++) {
            writer.write(poshList.get(i));
            writer.write("\n");
            writer.flush();
        }
        writer.write("\n");
        for (int i = 0; i < grottyList.size(); i++) {
            writer.write(grottyList.get(i));
            writer.write("\n");
            writer.flush();
        }
    }

}
