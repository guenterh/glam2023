package com.immerok.cookbook.events;

import java.util.Objects;

public class OneColumn {

    public String column;

    public OneColumn(
            final String column
    ) {
        this.column = column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column);
    }

    @Override
    public String toString() {
        return "Column Event(\n"
                + "column: " + column + "\n" +
                ")\n";
    }

}
