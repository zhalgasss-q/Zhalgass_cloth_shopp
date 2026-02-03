package model;

public abstract class ClothingItem implements Discountable {
    protected int id;
    protected String name;
    protected double price;

    public ClothingItem(int id, String name, double price) {
        this.id = id;
        setName(name);
        setPrice(price);
    }

    public abstract String getType();

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название не может быть пустым!");
        }
        this.name = name;
    }

    public double getPrice() { return price; }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной!");
        }
        this.price = price;
    }

    @Override
    public double applyDiscount(double percent) {
        return price - (price * percent / 100);
    }

    @Override
    public String toString() {
        return String.format("[%s] ID: %d | Name: %-15s | Price: %.2f", getType(), id, name, price);
    }
}