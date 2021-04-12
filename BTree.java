package com.company;

public class BTree {

    public int t;

    public class Node {
        public int n;
        public int[] elements;
        public Node[] children;
        boolean isLeaf;

        public Node() {
            this.isLeaf = true;
            this.elements = new int[2 * t - 1];
            this.children = new Node[2 * t];
        }

        public int find(int value) {
            for (int i = 0; i < this.n; i++) {
                if (this.elements[i] == value) {
                    return i;
                }
            }
            return -1;
        }
    }

    public Node root;

    public BTree(int t) {
        this.t = t;
        root = new Node();
        root.n = 0;
        root.isLeaf = true;
    }

    private Node search(Node node, int value) {
        int i = 0;
        if (node == null) {
            return null;
        }
        for (i = 0; i < node.n; i++) {
            if (value < node.elements[i]) {
                break;
            }
            if (value == node.elements[i]) {
                return node;
            }
        }
        if (node.isLeaf) {
            return null;
        } else {
            return search(node.children[i], value);
        }
    }

    private void split(Node xNode, int position, Node yNode) {
        Node zNode = new Node();
        zNode.isLeaf = yNode.isLeaf;
        zNode.n = t - 1;
        if (t - 1 >= 0) System.arraycopy(yNode.elements, t, zNode.elements, 0, t - 1);
        if (!yNode.isLeaf) {
            if (t >= 0) System.arraycopy(yNode.children, t, zNode.children, 0, t);
        }
        yNode.n = t - 1;
        if (xNode.n + 1 - (position + 1) >= 0) {
            System.arraycopy(xNode.children, position + 1, xNode.children, position + 1 + 1, xNode.n + 1 - (position + 1));
        }
        xNode.children[position + 1] = zNode;
        if (xNode.n - position >= 0) {
            System.arraycopy(xNode.elements, position, xNode.elements, position + 1, xNode.n - position);
        }
        xNode.elements[position] = yNode.elements[t-1];
        xNode.n = xNode.n + 1;
    }

    public void insert(final int value) {
        Node current = root;
        if (current.n == 2 * t - 1) {
            Node s = new Node();
            root = s;
            s.isLeaf = false;
            s.n = 0;
            s.children[0] = current;
            split(s, 0, current);
            insert(s, value);
        } else {
            insert(current, value);
        }
    }

    private void insert(Node node, int value) {
        if (node.isLeaf) {
            int i = 0;
            for (i = node.n - 1; i >= 0 && value < node.elements[i]; i--) {
                node.elements[i + 1] = node.elements[i];
            }
            node.elements[i + 1] = value;
            node.n = node.n + 1;
        } else {
            int i = 0;
            for (i = node.n - 1; i >= 0 && value < node.elements[i]; i--) {
            }
            ;
            i++;
            Node tmp = node.children[i];
            if (tmp.n == 2 * t - 1) {
                split(node, i, tmp);
                if (value > node.elements[i]) {
                    i++;
                }
            }
            insert(node.children[i], value);
        }
    }

    public void remove(int value) {
        Node node = search(root, value);
        if (node == null) {
            return;
        } else
        remove (root, value);
    }

    private void remove(Node node, int value) {
        if (node != null) {
            int position = node.find(value);
            if (position != -1) {
                if (node.isLeaf) {
                    int i = 0;
                    while (i < node.n && node.elements[i] != value) {
                        i++;
                    }
                    for (; i < node.n; i++) {
                        if (i != 2 * t - 2) {
                            node.elements[i] = node.elements[i + 1];
                        }
                    }
                    node.n--;
                    return;
                }
                if (!node.isLeaf) {
                    Node predecessor = node.children[position];
                    int predecessorValue = 0;
                    if (predecessor.n >= t) {
                        for (; ; ) {
                            if (predecessor.isLeaf) {
                                predecessorValue = predecessor.elements[predecessor.n - 1];
                                break;
                            } else {
                                predecessor = predecessor.children[predecessor.n];
                            }
                        }
                        remove(predecessor, predecessorValue);
                        node.elements[position] = predecessorValue;
                        return;
                    }
                    Node nextNode = node.children[position + 1];
                    if (nextNode.n >= t) {
                        int nextValue = nextNode.elements[0];
                        if (!nextNode.isLeaf) {
                            nextNode = nextNode.children[0];
                            for (; ; ) {
                                if (nextNode.isLeaf) {
                                    nextValue = nextNode.elements[nextNode.n - 1];
                                    break;
                                } else {
                                    nextNode = nextNode.children[nextNode.n];
                                }
                            }
                        }
                        remove(nextNode, nextValue);
                        node.elements[position] = nextValue;
                        return;
                    }
                    int temp = predecessor.n + 1;
                    predecessor.elements[predecessor.n++] = node.elements[position];
                    for (int i = 0, j = predecessor.n; i < nextNode.n; i++) {
                        predecessor.elements[j++] = nextNode.elements[i];
                        predecessor.n++;
                    }
                    for (int i = 0; i < nextNode.n + 1; i++) {
                        predecessor.children[temp++] = nextNode.children[i];
                    }

                    node.children[position] = predecessor;
                    for (int i = position; i < node.n; i++) {
                        if (i != 2 * t - 2) {
                            node.elements[i] = node.elements[i + 1];
                        }
                    }
                    for (int i = position + 1; i < node.n + 1; i++) {
                        if (i != 2 * t - 1) {
                            node.children[i] = node.children[i + 1];
                        }
                    }
                    node.n--;
                    if (node.n == 0) {
                        if (node == root) {
                            root = node.children[0];
                        }
                        node = node.children[0];
                    }
                    remove(predecessor, value);
                }
            } else {
                for (position = 0; position < node.n; position++) {
                    if (node.elements[position] > value) {
                        break;
                    }
                }
                Node temp = node.children[position];
                if (temp.n >= t) {
                    remove(temp, value);
                    return;
                }
                temp = balance(node, position);
                remove(temp, value);
            }
        }
    }

    private Node balance(Node node, int value) {
        int position = node.find(value);
        if (position != -1) {
            Node temp = node.children[position];
            if (position != node.n && node.children[position + 1].n >= t) {
                return rotateRight(node, position);
            } else if (position != 0 && node.children[position - 1].n >= t) {
                return rotateLeft(node, position);
            } else {
                return balanceMerging(node, position);
            }
        } else return null;
    }

    public void balance(int value) {
        balance(root, value);
    }

   private Node rotateRight(Node node, int position) {
        Node temp = node.children[position];
        int divider = node.elements[position];
        Node current = node.children[position + 1];
        node.elements[position] = current.elements[0];
        temp.elements[temp.n++] = divider;
        temp.children[temp.n] = current.children[0];
        if (current.n - 1 >= 0) System.arraycopy(current.elements, 1, current.elements, 0, current.n - 1);
        if (current.n >= 0) System.arraycopy(current.children, 1, current.children, 0, current.n);
        current.n--;
        return temp;
    }

    private Node rotateLeft(Node node, int position) {
        Node temp = node.children[position];
        int divider = node.elements[position - 1];
        Node current = node.children[position - 1];
        node.elements[position - 1] = current.elements[current.n - 1];
        Node child = current.children[current.n];
        current.n--;
        if (temp.n >= 0) System.arraycopy(temp.elements, 0, temp.elements, 1, temp.n);
        temp.elements[0] = divider;
        if (temp.n + 1 >= 0) System.arraycopy(temp.children, 0, temp.children, 1, temp.n + 1);
        temp.children[0] = child;
        temp.n++;
        return temp;
    }

    private Node balanceMerging(Node node, int position) {
        Node left = null;
        Node right = null;
        int divider = -1;
        boolean last = false;
        if (position != node.n) {
            divider = node.elements[position];
            left = node.children[position];
            right = node.children[position + 1];
        } else {
            divider = node.elements[position - 1];
            right = node.children[position];
            left = node.children[position - 1];
            last = true;
            position--;
        }
        for (int i = position; i < node.n - 1; i++) {
            node.elements[i] = node.elements[i + 1];
        }
        for (int i = position + 1; i < node.n; i++) {
            node.children[i] = node.children[i + 1];
        }
        node.n--;
        left.n++;
        left.elements[left.n] = divider;

        for (int i = 0, j = left.n; i < right.n + 1; i++, j++) {
            if (i < right.n) {
                left.elements[j] = right.elements[i];
            }
            left.children[j] = right.children[i];
        }
        left.n += right.n;
        if (node.n == 0) {
            if (node == root) {
                root = node.children[0];
            }
            node = node.children[0];
        }
        return left;
    }

    public void print() {
        print(root);
    }

    public boolean contains(int value) {
        return this.search(root, value) != null;
    }
    private void print(Node node) {
        assert (node != null);
        for (int i = 0; i < node.n; i++) {
            System.out.print(node.elements[i] + " ");
        }
        if (!node.isLeaf) {
            for (int i = 0; i < node.n + 1; i++) {
                print(node.children[i]);
            }
        }
    }

}
