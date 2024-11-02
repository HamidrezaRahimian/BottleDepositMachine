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

    //due to the specification however i added them in ListOfItem as well cause it make sense :D
    private void initializeDatabase() {
        itemDatabase.put("r8yz7clkz4", new Item("ABC Can | 0.33L", "r8yz7clkz4", "Disposable", "Metal", 0.25));
        itemDatabase.put("tmvkrw69le", new Item("ABC Can | 0.5L", "tmvkrw69le", "Disposable", "Metal", 0.25));
        itemDatabase.put("bzfi339nsy", new Item("DE Bottle | 0.33L", "bzfi339nsy", "Reusable", "Glas", 0.25));
        itemDatabase.put("3jdwml7w52", new Item("DE Bottle | 0.5L", "3jdwml7w52", "Reusable", "Glas", 0.30));
        itemDatabase.put("4xpokcvb7c", new Item("DE Bottle | 0.75L", "4xpokcvb7c", "Reusable", "Glas", 0.50));
        itemDatabase.put("xvjix0xaue", new Item("DE Bottle | 1L", "xvjix0xaue", "Reusable", "Glas", 0.75));
        itemDatabase.put("js92hp13rp", new Item("FG Bottle | 0.5L", "js92hp13rp", "Reusable", "Plastic", 1.00));
        itemDatabase.put("8hgij9rqv5", new Item("FG Bottle | 1L", "8hgij9rqv5", "Reusable", "Plastic", 2.00));
    }


}
