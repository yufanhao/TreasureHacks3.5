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

        public Node(int x, int y, char[][] world, int cost, String path) {
            this.x = x;
            this.y = y;
            this.world = world;
            this.cost = cost;
            this.path = path;
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
                Node newNode = new Node(x, y - 1, newWorld, cost + 1, path + "N\n");
                children.add(newNode);
            }

            // move down
            if (isValidCell(y+1, x)) {
                Node newNode = new Node(x, y + 1, newWorld, cost + 1, path + "S\n");
                children.add(newNode);
            }

            // move left
            if (isValidCell(y, x-1)) {
                Node newNode = new Node(x - 1 , y, newWorld, cost + 1, path + "W\n");
                children.add(newNode);
            }

            // move right
            if (isValidCell(y, x+1)) {
                Node newNode = new Node(x + 1, y, newWorld, cost + 1, path + "E\n");
                children.add(newNode);
            }

            // vacuum
            if (this.world[y][x] == '*') {
                char[][] newWorldV = new char[rows][cols];
                for(int i = 0; i < rows; i++) {
                    newWorldV[i] = this.world[i].clone();
                }
                Node newNode = new Node(x, y, newWorld, cost + 1, path + "V\n");
                children.add(newNode);
                newWorldV[y][x] = '_';
            }
            return children;
        }

        private boolean isClean(char[][] world) {
            for(int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    if (world[j][i] == '*') {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private static int dirt(char[][] world) {
        int d = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (world[i][j] == '*') {
                    d++;
                }
            }
        }
        return d;
    }

    private static int[] nodeInfo(Node node) {
        return new int[] {node.x, node.y, dirt(node.world), node.cost};
    }

    public static void uniformCostSearch(int x, int y, char[][] world) {
        // Create the initial state and the priority queue
        Node initialState = new Node(x, y, world, 0, "");
        PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>();
        priorityQueue.add(initialState);

        // Apply the Uniform Cost Search algorithm
        ArrayList<int[]> visited = new ArrayList<int[]>();
        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();
            boolean visit = false;
            for (int[] ints : visited) {
                if (Arrays.equals(ints, nodeInfo(currentNode))) {
                    visit = true;
                }
            }
            if (visit) {
                continue;
            }

            visited.add(nodeInfo(currentNode));

            if (currentNode.isClean(currentNode.world)) {
                System.out.println(currentNode.path);
                return;
            }


            for (Node child : currentNode.children()) {
                if (!visited.contains(nodeInfo(child))) {
                    priorityQueue.add(child);
                }
            }
        }
        System.out.println("No solution found.");
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int startY = 0;
        int startX = 0;
        cols = scanner.nextInt();
        rows = scanner.nextInt();
        scanner.nextLine();
        char[][] world = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < cols; j++) {
                world[i][j] = line.charAt(j);
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (world[i][j] == '@') {
                    startX = j;
                    startY = i;
                    world[i][j] = '_';
                }
            }
        }
        uniformCostSearch(startX, startY, world);
    }
}
