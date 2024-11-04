package BottleDepositMachine.Software;

public enum InMemoryDatebaseEnum {

    ABC_CAN_033L("ABC Can | 0.33L", "r8yz7clkz4", "Disposable", "Metal", 0.25),
    ABC_CAN_05L("ABC Can | 0.5L", "tmvkrw69le", "Disposable", "Metal", 0.25),
    DE_BOTTLE_033L("DE Bottle | 0.33L", "bzfi339nsy", "Reusable", "Glas", 0.25),
    DE_BOTTLE_05L("DE Bottle | 0.5L", "3jdwml7w52", "Reusable", "Glas", 0.30),
    DE_BOTTLE_075L("DE Bottle | 0.75L", "4xpokcvb7c", "Reusable", "Glas", 0.50),
    DE_BOTTLE_1L("DE Bottle | 1L", "xvjix0xaue", "Reusable", "Glas", 0.75),
    FG_BOTTLE_05L("FG Bottle | 0.5L", "js92hp13rp", "Reusable", "Plastic", 1.00),
    FG_BOTTLE_1L("FG Bottle | 1L", "8hgij9rqv5", "Reusable", "Plastic", 2.00);

    private final String label;
    private final String barcode;
    private final String recyclingType;
    private final String materialType;
    private final double depositAmount;

    InMemoryDatebaseEnum(String label, String barcode, String recyclingType, String materialType, double depositAmount) {
        this.label = label;
        this.barcode = barcode;
        this.recyclingType = recyclingType;
        this.materialType = materialType;
        this.depositAmount = depositAmount;
    }

    // Getters for each field
    public String getLabel() { return label; }
    public String getBarcode() { return barcode; }
    public String getRecyclingType() { return recyclingType; }
    public String getMaterialType() { return materialType; }
    public double getDepositAmount() { return depositAmount; }
}
