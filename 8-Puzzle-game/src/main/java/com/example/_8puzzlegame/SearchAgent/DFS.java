package com.example._8puzzlegame.SearchAgent;

import com.example._8puzzlegame.StateNode.Node;

import java.util.*;

public class DFS extends Agent {

    //Stack
    @Override
    public void solve(int[] startState) {
        Node root = new Node(startState);
        Stack<Node> stack = new Stack<>();
        Set<String> visited = new HashSet<>();
        stack.push(root);

        long start = System.currentTimeMillis();

        while (!stack.isEmpty()) {
            Node state = stack.pop();
            visited.add(Arrays.toString(state.puzzle));

            this.maxDepth = Math.max(this.maxDepth, state.getDepth());
            if (state.goalTest()) {
                System.out.println("Goal Found");
                goal = state;
                break;
            }


            state.expand();

            for (Node child : state.getChildren()) {
                if (!visited.contains(Arrays.toString(child.puzzle))) {
                    stack.add(child);
                }
            }

        }
        long executionTime = System.currentTimeMillis() - start;

        if (goal != null) {
            tracePath(goal);
            System.out.println("Time taken by SearchAgent DFS " + executionTime + " ms");
        } else
            System.out.println(" Not solvable Example  !!!! ");
    }

}