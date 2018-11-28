package com.nibrasco.freshapp.Model;

import android.content.Context;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    public enum eWeight
    {
        None(-1),
        ExtraSmall(0),
        Small(1),
        Medium(2),
        Big(3),
        ExtraBig(4);
        private final int value;
        eWeight(int value) {
            this.value = value;
        }

        public int Value() {
            return value;
        }
        public static eWeight Get(int value){
            for(eWeight t : values()){
                if(t.value == value) return t;
            }
            return None;
        }
    }

    public enum eSlicing
    {
        None(0),
        Fridge(1),
        Halfs(2),
        Quarters(3),
        Whole(4);
        private final int value;
        eSlicing(int value) {
            this.value = value;
        }

        public int Value() {
            return value;
        }
        public static eSlicing Get(int value){
            for(eSlicing t : values()){
                if(t.value == value) return t;
            }
            return None;
        }
    }

    public enum ePackaging
    {
        None(0),
        Bags(1),
        Plates(2);
        private final int value;
        ePackaging(int value) {
            this.value = value;
        }

        public int Value() {
            return value;
        }
        public static ePackaging Get(int value){
            for(ePackaging t : values()){
                if(t.value == value) return t;
            }
            return None;
        }
    }

    public enum eCategory
    {
        None(-1),
        Sheep(0),
        Goat(1),
        Lamb(2),
        Camel(3),
        GroundMeat(4);
        private final int value;
        eCategory(int value) {
            this.value = value;
        }

        public int Value() {
            return value;
        }
        public static eCategory Get(int value){
            for(eCategory t : values()){
                if(t.value == value) return t;
            }
            return None;
        }
    }

    public static class Item {

        private eCategory Category;
        private int Quantity = 0;
        private boolean Intestine = false;
        private String Notes = "";
        private eSlicing Slicing = eSlicing.None;
        private ePackaging Packaging = ePackaging.None;
        private eWeight Weight = eWeight.ExtraSmall;
        private float Total = 0.0f;

        public eCategory getCategory() {
            return Category;
        }

        public void setCategory(eCategory category) {
            Category = category;
        }

        public int getQuantity() {
            return Quantity;
        }

        public void setQuantity(int quantity) {
            Quantity = quantity;
        }

        public boolean isIntestine() {
            return Intestine;
        }

        public void setIntestine(boolean intestine) {
            Intestine = intestine;
        }

        public String getNotes() {
            return Notes;
        }

        public void setNotes(String notes) {
            Notes = notes;
        }

        public eSlicing getSlicing() {
            return Slicing;
        }

        public void setSlicing(eSlicing slicing) {
            Slicing = slicing;
        }

        public ePackaging getPackaging() {
            return Packaging;
        }

        public void setPackaging(ePackaging packaging) {
            Packaging = packaging;
        }

        public eWeight getWeight() {
            return Weight;
        }

        public void setWeight(eWeight weight) {
            Weight = weight;
        }

        public float getTotal() {
            return Total;
        }

        public void setTotal(float total) {
            Total = total;
        }

        public Item(eCategory category, int quantity, boolean intestine,
                    String notes, eSlicing slicing, ePackaging packaging,
                    eWeight weight, float total) {
            Category = category;
            Quantity = quantity;
            Intestine = intestine;
            Notes = notes;
            Slicing = slicing;
            Packaging = packaging;
            Weight = weight;
            Total = total;
        }

        public Item(DataSnapshot itemSnap)
        {
            Category = eCategory.Get(Integer.parseInt(itemSnap.getKey()));
            Intestine = (itemSnap.child("Intestine").getValue(Integer.class) != 0);
            Notes = itemSnap.child("Notes").getValue(String.class);
            Packaging  = ePackaging.Get(itemSnap.child("Packaging").getValue(Integer.class));
            Quantity = itemSnap.child("Quantity").getValue(Integer.class);
            Slicing = eSlicing.Get(itemSnap.child("Slicing").getValue(Integer.class));
            Total = itemSnap.child("Total").getValue(Float.class);
            Weight = eWeight.Get(itemSnap.child("Weight").getValue(Integer.class));
        }
        public void MapToDbRef(DatabaseReference itemsRef)
        {
            DatabaseReference itemRef = itemsRef.child(Integer.toString(Category.value));
            itemRef.child("Intestine").setValue(Intestine ? 1 : 0);
            itemRef.child("Notes").setValue(Notes);
            itemRef.child("Packaging").setValue(Packaging.value);
            itemRef.child("Quantity").setValue(Quantity);
            itemRef.child("Slicing").setValue(Slicing.value);
            itemRef.child("Total").setValue(Total);
            itemRef.child("Weight").setValue(Weight.value);
        }

        public String ToString()
        {
            return "\nCategory : " + Category.value
                    + "\nQuantity : " +  Quantity
                    + "\nPackaging : " + Packaging.value
                    + "\nSlicing : " + Slicing.value
                    + "\nWeight : " + Weight.value
                    + "\nNotes : " + Notes
                    + "\nTotal : " + Total;
        }

    }

    private String Address;
    private List<Item> Items;

    public Item GetItem(int index)
    {
        return Items.get(index);
    }
    public void AddItem(Item item)
    {
        Items.add(item);
    }
    public void RemoveItem(Item item)
    {
        Items.remove(item);
    }
    public void ModifyItem(int index, Item newItem)
    {
        Items.set(index, newItem);
    }

    public Cart() {
        Address = "N/A"; Items = new ArrayList<Item>();
    }

    public Cart(String address) {
        Address = address;
        Items = new ArrayList<Item>();
    }

    /*
    public Cart(String address, ArrayList<Item> items) {
        Address = address;
        Items = items;
    }
    */
    public Cart(DataSnapshot cart)
    {
        Address = cart.child("Address").getValue(String.class);
        Items = new ArrayList<Item>();
        DataSnapshot itemsSnap = cart.child("Items");
        if(itemsSnap.getChildrenCount() >= 1)
        {
            for(DataSnapshot itemSnap : itemsSnap.getChildren())
            {
                Items.add(new Item(itemSnap));
            }
        }
    }

    public void MapToDbRef(DatabaseReference cartRef)
    {
        cartRef.child("Address").setValue(Address);
        DatabaseReference itemsRef = cartRef.child("Items");
        for (Item item : Items) {
            item.MapToDbRef(itemsRef);
        }
    }

    public String ToString()
    {
        String obj = "Address = " + Address + "\nItems(Count = " + Items.size() + "):\n";
        for(Item item : Items)
        {
            obj += item.ToString();
        }
        return obj;
    }

}
