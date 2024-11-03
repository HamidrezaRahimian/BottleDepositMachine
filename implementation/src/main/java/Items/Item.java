package Items;

import lombok.Data;


import java.util.Random;

@Data
public class Item {
    private double depositAmount;
    private int rotation;
    private boolean crushed;
     private String frontLabel;
    private BackLabel backLabel;
     private String recyclingType;
    private String materialType;
    public static final Random random = new Random();

       // app will randomly choose the right degree of Rotation to scan
    private int getRandomRotationRightDegree() {
        return switch (random.nextInt(4)) {
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
            default -> "no label detected"; // in other degree there is no Label
        };
    }

    public void setRotation(int rotation) {
        if (rotation == 0 || rotation == 90 || rotation == 180 || rotation == 270) {
            this.rotation = rotation;
        } else {
            throw new IllegalArgumentException("shape of bottle is super weird so that we can define any specific degree and it so it's not cubic form ");
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
