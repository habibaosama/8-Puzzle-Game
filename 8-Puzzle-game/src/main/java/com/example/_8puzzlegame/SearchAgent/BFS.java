package com.example._8puzzlegame.SearchAgent;

import com.example._8puzzlegame.StateNode.Node;

import java.util.*;

public class BFS extends Agent {
    @Override
    public void solve(int[] startState) {
        Node root = new Node(startState);
        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(root);

        long start = System.currentTimeMillis();

        while (!queue.isEmpty() ) {
            Node state = queue.poll();
            visited.add(Arrays.toString(state.puzzle));

            // found goal when dequeue
            this.maxDepth = Math.max(this.maxDepth, state.getDepth());
            if (state.goalTest()) {
                System.out.println("Goal Found");
                goal = state;
                break;
            }

            state.expand();

            for (Node child : state.getChildren()) {

                if (!visited.contains(Arrays.toString(child.puzzle)))
                    queue.add(child); //if not  visited add to closed set

            }

        }

        long executionTime = System.currentTimeMillis() - start;

        // return tracePath(goal);
        if (goal != null) {
            tracePath(goal);
            System.out.println("Time taken by SearchAgent BFS " + executionTime + " ms");
        } else
            System.out.println(" Not solvable Example  !!!! ");

    }
}
