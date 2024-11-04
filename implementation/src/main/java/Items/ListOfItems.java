package Items;

import java.util.ArrayList;
import java.util.List;

public class ListOfItems {

    private List<Item> myListOfItems;
    private List<Item> listOfNotAcceptableItems;


    // this data are used mostly for testing the machine , there is a InMemoryDatabase as spcification so dont worry :D
    public ListOfItems() {
        myListOfItems = new ArrayList<>();
        listOfNotAcceptableItems = new ArrayList<>();

        // Initialize acceptable items
        myListOfItems.add(new Item("ABC Can | 0.33L", "r8yz7clkz4", "Disposable", "Metal", 0.25));
        myListOfItems.add(new Item("ABC Can | 0.5L", "tmvkrw69le", "Disposable", "Metal", 0.25));
        myListOfItems.add(new Item("DE Bottle | 0.33L", "bzfi339nsy", "Reusable", "Glas", 0.25));
        myListOfItems.add(new Item("DE Bottle | 0.5L", "3jdwml7w52", "Reusable", "Glas", 0.30));
        myListOfItems.add(new Item("DE Bottle | 0.75L", "4xpokcvb7c", "Reusable", "Glas", 0.50));
        myListOfItems.add(new Item("DE Bottle | 1L", "xvjix0xaue", "Reusable", "Glas", 0.75));
        myListOfItems.add(new Item("FG Bottle | 0.5L", "js92hp13rp", "Reusable", "Plastic", 1.00));
        myListOfItems.add(new Item("FG Bottle | 1L", "8hgij9rqv5", "Reusable", "Plastic", 2.00));

        // Initialize non-acceptable items
        listOfNotAcceptableItems.add(new Item("X Can | 0.5L", "3a6mr6o7sl", "Not Accepted", "Metal", 0.00));
        listOfNotAcceptableItems.add(new Item("Y Bottle | 0.33L", "ha3pven757", "Not Accepted", "Glas", 0.00));
        listOfNotAcceptableItems.add(new Item("Y Bottle | 0.5L", "447ds7u9j4", "Not Accepted", "Glas", 0.00));
        listOfNotAcceptableItems.add(new Item("Y Bottle | 1L ", "5hqoa0ean4", "Not Accepted", "Glas", 0.00));
        listOfNotAcceptableItems.add(new Item("Z Bottle | 0.33L ", "j03eiqtm2t", "Not Accepted", "Glas", 0.00));
        listOfNotAcceptableItems.add(new Item("Z Bottle | 0.5L", "5jrsqeg201", "Not Accepted", "Plastic", 0.00));
        listOfNotAcceptableItems.add(new Item("Z Bottle | 1L ", "pv4og90ymi", "Not Accepted", "Plastic", 0.00));
    }

    public List<Item> getItems() {
        List<Item> combinedList = new ArrayList<>(myListOfItems);
        combinedList.addAll(listOfNotAcceptableItems);
        return combinedList;
    }
}











/*      Test 1
        myListOfItems.add(new Item("DE Bottle | 1L", "xvjix0xaue", "Reusable", "Glas", 0.75));
        listOfNotAcceptableItems.add(new Item("Z Bottle | 0.33L ", "j03eiqtm2t", "Not Accepted", "Glas", 0.00));
        myListOfItems.add(new Item("ABC Can | 0.5L", "tmvkrw69le", "Disposable", "Metal", 0.25));
        myListOfItems.add(new Item("FG Bottle | 1L", "8hgij9rqv5", "Reusable", "Plastic", 2.00));
        listOfNotAcceptableItems.add(new Item("X Can | 0.5L", "3a6mr6o7sl", "Not Accepted", "Metal", 0.00));
        myListOfItems.add(new Item("DE Bottle | 0.75L", "4xpokcvb7c", "Reusable", "Glas", 0.50));

 test check if :

        receipt.add("#accepted items: " + acceptedItemsCount);
        receipt.add("#disposable: " + disposableItemsCount + " (" + String.format("%.2f", disposableTotal) + " €)");
        receipt.add("#reusable: " + reusableItemsCount + " (" + String.format("%.2f", reusableTotal) + " €)");
        receipt.add("#non-accepted items: " + nonAcceptedItemsCount);
        receipt.add("> total: " + String.format("%.2f", total) + " €");




        Disposable Count: 1,
        Reusable Count: 3,
        Not Accepted Count: 2,
        Disposable Total: 0,25 €,
        Reusable Total: 3,25 €,
        Not Accepted Total: 0 €,
        Total for All: 3,50


 */