package BottleDepositMachine.Software;

import Items.Item;
import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase {
    private Map<String, Item> itemDatabase;

    public InMemoryDatabase() {
        itemDatabase = new HashMap<>();
        initializeDatabase();
    }

    public Item getItemByBarcode(String barcode) {
        return itemDatabase.get(barcode);
    }

    private void initializeDatabase() {
        for (InMemoryDatebaseEnum itemType : InMemoryDatebaseEnum.values()) {
            Item item = new Item(
                    itemType.getLabel(),
                    itemType.getBarcode(),
                    itemType.getRecyclingType(),
                    itemType.getMaterialType(),
                    itemType.getDepositAmount()
            );
            itemDatabase.put(itemType.getBarcode(), item);
        }
    }
}
