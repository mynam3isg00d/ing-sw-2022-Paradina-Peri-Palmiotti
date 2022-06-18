package Model;

import java.util.Objects;

public class Assistant {

    /**
     * Number written on the card that determines the order in which players play the round:
     * lower number means higher priority.
     * orderNumber goes from 1 to 10
     */
    private int orderNumber;

    /**
     * Number that says by how many positions a player can move mother nature when playing this assistant.
     */
    private int motherNumber;

    @Override
    public String toString() {
        return "Assistant{" +
                "orderNumber=" + orderNumber +
                ", motherNumber=" + motherNumber +
                '}';
    }

    public Assistant(int orderNumber) {
        this.orderNumber = orderNumber;
        motherNumber = ((orderNumber - 1) / 2) + 1;
    }

    public int getMotherNumber() {
        return motherNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assistant assistant = (Assistant) o;
        return orderNumber == assistant.orderNumber && motherNumber == assistant.motherNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, motherNumber);
    }
}
