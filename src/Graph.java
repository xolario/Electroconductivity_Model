import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Graph {
    int[][] structure;
    Graph (Path path) throws IOException {
        Scanner s = null;

        try {
            s = new Scanner(new BufferedReader(new FileReader(path.toString())));
            while (s.hasNextLine()) {

            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
}
