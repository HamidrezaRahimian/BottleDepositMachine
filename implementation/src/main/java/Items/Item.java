package Items;

import lombok.Data;

// Import the custom random class
import BottleDepositMachine.Software.MersennerTwisterFast;

@Data
public class Item {
    private double depositAmount;
    private int rotation;
    private boolean crushed;
    private String frontLabel;
    private BackLabel backLabel;
    private String recyclingType;
    private String materialType;

    // Initialize the custom random generator
    private static final MersennerTwisterFast customRandom = new MersennerTwisterFast();

    // App will randomly choose the right degree of rotation to scan
    private int getRandomRotationRightDegree() {
        return switch (customRandom.nextInt(4)) {
            case 0 -> 0;
            case 1 -> 90;
            case 2 -> 180;
            case 3 -> 270;
            default -> 0;
        };
    }

    public String getCurrentLabel() {
        return switch (rotation) {
            case 0 -> frontLabel;
            case 180 -> backLabel.getBarcode();
            default -> "no label detected"; // In other degrees, there is no label
        };
    }

    public void setRotation(int rotation) {
        if (rotation == 0 || rotation == 90 || rotation == 180 || rotation == 270) {
            this.rotation = rotation;
        } else {
            throw new IllegalArgumentException("Shape of bottle is super weird, cannot define any specific degree as it's not in cubic form.");
        }
    }

    public Item(String frontLabel, String barcode, String recyclingType, String materialType, double depositAmount) {
        this.frontLabel = frontLabel;
        this.backLabel = new BackLabel(barcode);
        this.recyclingType = recyclingType;
        this.materialType = materialType;
        this.depositAmount = depositAmount;
        this.rotation = getRandomRotationRightDegree();
    }
}
