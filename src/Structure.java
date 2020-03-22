import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Structure {
    int[][] structure;
    int size;
    public Structure (Path path, int size) throws IOException {
        this.size = size;
        Scanner s = null;
        structure = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                structure[i][j] = 0;
            }
        }

        try {
            s = new Scanner(new BufferedReader(new FileReader(path.toString())));
            for (int i = 0; i < 3; i++) {
                s.nextLine();
            }
            while (s.hasNextLine()) {
                String str = s.nextLine();
                structure[Integer.parseInt(str.split("\t")[0])][Integer.parseInt(str.split("\t")[1])] = 1;
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    private boolean isNode(int x, int y, int nodeSize) {
        int[] arr = new int[nodeSize * 4 + 4];
        for (int i = 0; i < nodeSize + 1; i++) {
            try {
                arr[i] = structure[x - 1][y + i];
            } catch (Exception e) {
                arr[i] = 0;
            }
            try {
                arr[i + nodeSize + 1] = structure[x + i][y + nodeSize];
            } catch (Exception e) {
                arr[i + nodeSize + 1] = 0;
            }
            try {
                arr[i + (nodeSize + 1) * 2] = structure[x + nodeSize][y + nodeSize - 1 - i];
            } catch (Exception e) {
                arr[i + (nodeSize + 1) * 2] = 0;
            }
            try {
                arr[i + (nodeSize + 1) * 3] = structure[x + nodeSize - 1 - i][y - 1];
            } catch (Exception e) {
                arr[i + (nodeSize + 1) * 3] = 0;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String str = "Structure: %n";
        for (int i = 0; i < size; i++) {
            str = str.concat(Arrays.toString(structure[i]) + "%n");
        }
        return str;
    }
}
