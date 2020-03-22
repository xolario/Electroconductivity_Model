import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Structure {
    private int[][] structure;
    private int size;
    private int maxId = 0;
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

    @Override
    public String toString() {
        String str = "Structure: %n";
        for (int i = 0; i < size; i++) {
            str = str.concat(Arrays.toString(structure[i]) + "%n");
        }
        return str;
    }

    /**
     * Mathod that finds and marks nodes in the structure.
     * @param maxNodeSize maximum size of the node. It shouldn't be greater than size of the structure.
     */
    public void markNodes(int maxNodeSize) throws IllegalArgumentException {
        //TODO add structure processing for marking 2 opposite sides of the structure as nodes.
        //TODO mark each node independently (with different ids).
        if (maxNodeSize > size) {
            throw new IllegalArgumentException("maxNodeSize shouldn't be greater than size of the structure");
        }

        for (int k = 1; k <= maxNodeSize; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    try {
                        if (this.isNode(i, j, k) && !this.markedAsNode(i, j, k)) {
                            this.markAsNode(i, j, k, k+1);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Method that checks that part of the structure is a node. A node is a part of a structure that has more than 2 branches.
     * Node can be a square of any size less than a structure.
     * @param x x-coordinate of the start of a node (co-directed with i).
     * @param y y-coordinate of the start of a node (co-directed with j).
     * @param nodeSize size of a node.
     * @return true if a part of the structure is node.
     * @throws ArrayIndexOutOfBoundsException if node provided finisher outside the structure.
     */
    private boolean isNode(int x, int y, int nodeSize) throws ArrayIndexOutOfBoundsException {
        if (x + nodeSize >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        int[] arr = new int[nodeSize * 4 + 4];
        int counter = 0;

        for (int i = 0; i < nodeSize; i++) {
            for (int j = 0; j < nodeSize; j++) {
                if (structure[x + i][y + j] == 0) return false;
            }
        }

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

        for (int i = 0; i < arr.length - 1; i++) {
            if ((arr[i] == 0 && arr[i + 1] != 0) || (arr[i] != 0 && arr[i + 1] == 0)) {
                counter++;
            }
        }

        if ((arr[0] == 0 && arr[arr.length - 1] != 0) || (arr[arr.length - 1] != 0 && arr[0] == 0)) {
            counter++;
        }

        if (counter >= 6) {
            return true;
        }
        return false;
    }

    /**
     * Method that checks if a part of the structure is marked as node.
     * @param x x-coordinate of the start of a node (co-directed with i).
     * @param y y-coordinate of the start of a node (co-directed with j).
     * @param nodeSize size of a node.
     * @return true if a part of the structure is marked as node.
     * @throws ArrayIndexOutOfBoundsException if node provided finisher outside the structure.
     */
    private boolean markedAsNode (int x, int y, int nodeSize) throws ArrayIndexOutOfBoundsException {
        if (x + nodeSize >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        for (int i = 0; i < nodeSize; i++) {
            for (int j = 0; j < nodeSize; j++) {
                if (structure[x + i][y + j] != 0 && structure[x + i][y + j] != 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method that marks a part of the structure as node with given id.
     * @param x x-coordinate of the start of a node (co-directed with i).
     * @param y y y-coordinate of the start of a node (co-directed with j).
     * @param nodeSize size of a node.
     * @param id a number with which node is marked.
     * @throws ArrayIndexOutOfBoundsException if node provided finisher outside the structure.
     * @throws IllegalArgumentException if id provided is less than 2
     */
    private void markAsNode (int x, int y, int nodeSize, int id) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (x + nodeSize >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        if (id < 2) {
            throw new IllegalArgumentException("id must be not less than 2.");
        }
        for (int i = 0; i < nodeSize; i++) {
            for (int j = 0; j < nodeSize; j++) {
                if (structure[x + i][y + j] == 1) {
                    structure[x + i][y + j] = id;
                }
            }
        }
    }

}
