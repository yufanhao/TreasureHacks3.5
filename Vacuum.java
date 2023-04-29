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

    }


}
