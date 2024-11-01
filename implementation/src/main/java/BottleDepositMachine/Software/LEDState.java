package BottleDepositMachine.Software;

public enum LEDState {
    RED("Blocked"),
    YELLOW("Action needed"),
    GREEN("Ready to receive");

    private final String description;

    LEDState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
