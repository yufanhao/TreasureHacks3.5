public class Vacuum {
    public static int rows;
    public static int cols;

    private static class Node implements Comparable<Node> {
        int x;
        int y;
        char[][] world;
        int cost;
        String path;

    }
}
