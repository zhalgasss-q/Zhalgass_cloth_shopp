package model;

public class Shirt extends ClothingItem {

    private String size;

    public Shirt(int id, String name, double price, String size) {
        super(id, name, price);
        this.size = size;
    }

    @Override
    public String getType() {
        return "Shirt";
    }

    public String getSize() {
        return size;
    }
}
