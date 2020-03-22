import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        /**
         * args:
         * 0 - text-file
         * 1 - size of the structure
         */
        Structure structure = new Structure(Paths.get(args[0]), Integer.parseInt(args[1]));
        System.out.format(structure.toString());
    }
}
