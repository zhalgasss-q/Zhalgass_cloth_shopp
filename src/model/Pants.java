package model;


public class Pants extends ClothingItem {

    private int length;

    public Pants(int id, String name, double price, int length) {
        super(id, name, price);
        this.length = length;
    }

    @Override
    public String getType() {
        return "Pants";
    }

    public int getLength() {
        return length;
    }
}
