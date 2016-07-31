package generics;

public class LinkedStack<T> {
    private static class Node<U> {
        U item;
        Node<U> next;
        Node() { item = null; next = null; }
        Node(U item, Node<U> next) { this.item = item; this.next = next; }
        boolean end() { return item == null && next == null; }
    }

    private Node<T> top = new Node<>();

    public void push(T item) {
        top = new Node<>(item, top);
    }

    public T pop() {
        T item = top.item;
        if (!top.end()) top = top.next;
        return item;
    }

    public static void main(String[] args) {
        LinkedStack<String> stack = new LinkedStack<>();
        for (String s : "hello from the sentence".split(" ")) {
            stack.push(s);
        }

        String ss;
        while ((ss = stack.pop()) != null) {
            System.out.println(ss);
        }
    }
}
