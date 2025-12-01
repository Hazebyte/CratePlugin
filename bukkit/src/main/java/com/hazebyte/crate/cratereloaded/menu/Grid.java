package com.hazebyte.crate.cratereloaded.menu;

public class Grid {

    private final Button[] items;
    public static int ROWS = 9;
    public static int COLUMN = 1;

    public Grid(int size) {
        items = new Button[size];
    }

    public Grid setItem(int position, Button button) {
        items[position] = button;
        return this;
    }

    public Button get(int position) {
        return items[position];
    }

    public int length() {
        return items.length;
    }

    /**
     * Moves the {@link Button} from a slot.
     *
     * @param from the slot position to move from
     * @param to the slot position to move to
     * @return The button at the old position.
     */
    public Button move(int from, int to) {
        Button original = items[to];
        items[to] = items[from];
        items[from] = null;
        return original;
    }

    public void swap(int positionA, int positionB) {
        Button temp = items[positionA];
        items[positionA] = items[positionB];
        items[positionB] = temp;
    }

    public Grid up(int position) {
        int to = position - ROWS;
        if (isBoundary(to)) {
            move(position, to);
        }
        return this;
    }

    public Grid left(int position) {
        int to = position - COLUMN;
        if (isBoundary(to)) {
            move(position, to);
        }
        return this;
    }

    public Grid right(int position) {
        int to = position + COLUMN;
        if (isBoundary(to)) {
            move(position, to);
        }
        return this;
    }

    public Grid down(int position) {
        int to = position + ROWS;
        if (isBoundary(to)) {
            move(position, to);
        }
        return this;
    }

    private boolean isBoundary(int position) {
        return (position >= 0 && position < items.length);
    }
}
