package Items;

public class Smartphone {
    private String receipt;


    public Smartphone() {
        this.receipt = "";
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
        System.out.println("Receipt has been set on the smartphone: " + this.receipt);
    }


}
