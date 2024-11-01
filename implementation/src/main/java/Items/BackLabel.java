package Items;

import lombok.Data;
import lombok.NonNull;

@Data
public class BackLabel {
    @NonNull private String barcode;

    public BackLabel(String barcode) {
        if (barcode.length() != 10) {
            throw new IllegalArgumentException("if the barcode is not 10 digit we cant do anything about that - LG ALDI ");

        }
        this.barcode = barcode;
    }
}
