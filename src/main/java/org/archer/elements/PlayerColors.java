package org.archer.elements;

import javafx.scene.paint.Color;

public enum PlayerColors {
    BLACK,
    DarkBlue,
    DarkPurple,
    DarkBrown;

    public static Color getColor(int index) {
        switch (index) {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.DARKBLUE;
            case 2:
                return Color.DARKMAGENTA;
            case 3:
                return Color.SADDLEBROWN;
            default:
                return Color.BLACK;
        }
    }
}
