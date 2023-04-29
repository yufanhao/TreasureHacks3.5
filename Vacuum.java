import java.util.*;

public class Vacuum {
    public static int rows;
    public static int cols;

    private static class Node implements Comparable<Node> {
        int x;
        int y;
        char[][] world;
        int cost;
        String path;

        public Node(int x, int y, char[][] world, int cost) {
            this.x = x;
            this.y = y;
            this.world = world;
            this.cost = cost;
        }

        public int compareTo(Node n){
            if (this.cost < n.cost) {
                return -1;
            }
            else if (this.cost > n.cost) {
                return 1;
            }
            else {
                return 0;
            }
        }

        private boolean isValidCell(int row, int col) {
            if (row < 0 || col < 0 || row >= rows || col >= cols) {
                return false;
            }
            if (world[row][col] == '#') {
                return false;
            }
            return true;
        }

        public List<Node> children() {
            List<Node> children = new ArrayList<Node>();
            char[][] newWorld = this.world;

            // move up
            if (isValidCell(y-1, x)) {
                Node newNode = new Node(x, y - 1, newWorld, cost + 1);
                newNode.path = newNode.path + "N\n";
                children.add(newNode);
            }

            // move down
            if (isValidCell(y+1, x)) {
                Node newNode = new Node(x, y + 1, newWorld, cost + 1);
                newNode.path = newNode.path + "S\n";
                children.add(newNode);
            }

            // move left
            if (isValidCell(y, x-1)) {
                Node newNode = new Node(x - 1 , y, newWorld, cost + 1);
                newNode.path = newNode.path + "W\n";
                children.add(newNode);
            }

            // move right
            if (isValidCell(y, x+1)) {
                Node newNode = new Node(x + 1, y, newWorld, cost + 1);
                newNode.path = newNode.path + "E\n";
                children.add(newNode);
            }

            // vacuum
            if (this.world[y][x] == '*') {
                char[][] newWorldV = new char[rows][cols];
                for(int i = 0; i < rows; i++) {
                    newWorldV[i] = this.world[i].clone();
                }
                Node newNode = new Node(x, y, newWorld, cost + 1);
                newNode.path = newNode.path + "V\n";
                children.add(newNode);
                newWorldV[y][x] = '_';
            }
            return children;
        }
    }
}
