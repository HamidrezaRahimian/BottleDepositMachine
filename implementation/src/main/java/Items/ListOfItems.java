package Items;

import java.util.ArrayList;
import java.util.List;

public class ListOfItems {

    private List<Item> myListOfItems;

    public ListOfItems() {
        myListOfItems = new ArrayList<>();
        myListOfItems.add(new Item("ABC Can | 0.33L", "r8yz7clkz4", "Einweg", "Metall", 0.25));
        myListOfItems.add(new Item("ABC Can | 0.5L", "tmvkrw69le", "Einweg", "Metall", 0.25));
        myListOfItems.add(new Item("DE Bottle | 0.33L", "bzfi339nsy", "Mehrweg", "Glas", 0.25));
        myListOfItems.add(new Item("DE Bottle | 0.5L", "3jdwml7w52", "Mehrweg", "Glas", 0.30));
        myListOfItems.add(new Item("DE Bottle | 0.75L", "4xpokcvb7c", "Mehrweg", "Glas", 0.50));
        myListOfItems.add(new Item("DE Bottle | 1L", "xvjix0xaue", "Mehrweg", "Glas", 0.75));
        myListOfItems.add(new Item("FG Bottle | 0.5L", "js92hp13rp", "Einweg", "Plastik", 1.00));
        myListOfItems.add(new Item("FG Bottle | 1L", "8hgij9rqv5", "Einweg", "Plastik", 2.00));
    }

    public List<Item> getItems() {
        return myListOfItems;
    }
}
