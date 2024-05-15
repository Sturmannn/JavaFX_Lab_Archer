package org.archer.elements;

import javafx.scene.paint.Color;

public enum PlayerColors {
    BLACK,
    DarkBlue,
    DarkPurple,
    DarkBrown;

    public static Color getColor(final int index) {
        return switch (index) {
            case 0 -> Color.BLACK;
            case 1 -> Color.DARKGREEN;
            case 2 -> Color.DARKMAGENTA;
            case 3 -> Color.SADDLEBROWN;
            default -> Color.BLACK;
        };
    }
}