import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_J {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        HashMap<String, ArrayList<String>> list = new HashMap<>();
        HashMap<String, Integer> indegree = new HashMap<>();
        int n = Integer.parseInt(br.readLine());

        for (int i = 0 ; i < n ; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            String start = st.nextToken();
            String end = st.nextToken();

            list.putIfAbsent(start, new ArrayList<>());
            list.putIfAbsent(end, new ArrayList<>());
            list.get(start).add(end);

            indegree.putIfAbsent(end, 0);
            indegree.putIfAbsent(start, 0);
            indegree.put(end, indegree.get(end) + 1);
        }

        topological_sort(list, indegree);
    }
    static void topological_sort(HashMap<String, ArrayList<String>> list, HashMap<String, Integer> indegree) {
        Queue<Node2> q = new LinkedList<>();
        PriorityQueue<Node2> result = new PriorityQueue<>();

        for (String k : indegree.keySet()) {
            int v = indegree.get(k);
            if (v == 0) q.offer(new Node2(k, 0));
        }

        while (!q.isEmpty()) {
            Node2 cur = q.poll();
            result.offer(cur);

            for (String item : list.get(cur.value)) {
                indegree.put(item, indegree.get(item) - 1);
                if (indegree.get(item) == 0) {
                    q.offer(new Node2(item, cur.priority + 1));
                }
            }
        }

        if (result.size() != list.size()) {
            System.out.println(-1);
            return;
        }

        StringBuilder sb = new StringBuilder();
        while (!result.isEmpty()) {
            sb.append(result.poll().value + " ");
        }
        System.out.println(sb.toString());
    }
}
class Node2 implements Comparable<Node2>{
    String value;
    int priority;
    Node2(String value, int priority) {
        this.value = value;
        this.priority = priority;
    }
    @Override
    public int compareTo(Node2 other) {
        if (this.priority == other.priority) {
            return this.value.compareTo(other.value);
        }
        return this.priority - other.priority;
    }

}