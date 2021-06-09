public class ItemStash {
    private String itemName;
    private int quantity;
    private double buyPrice;
    private double sellPrice;
    private String category;
    private String barcode;

    private static char latinLetter=65;
    private static int nextSimpleNumber=2;
    private static int neverEndingIndex=0;
    public ItemStash(String name,int quantity,double buyPrice,double sellPrice,String category){
        this.itemName=name;
        this.quantity=quantity;
        this.buyPrice=buyPrice;
        this.sellPrice=sellPrice;
        this.category=category;
        this.barcode=FU666(itemName);
    }
    public static void getAllItems(){
        for(ItemStash item:Menu.itemStashList){
            System.out.println("Име на продукт     Количество        Покупна цена       Продажна цена        Категоряи           Баркод  ");
            System.out.println(item.itemName+"               "+item.quantity+"                   "+item.buyPrice+"             "+item.sellPrice+"            "+item.category+"         "+item.barcode);
        }
    }

    public String getCategory() {
        return category;
    }
    public String getItemName(){
        return itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    private static String FU666(String name){
        int nameLength=name.length();
        String barcode="{"+latinLetter+"}@{"+nameLength+"}%{"+nextSimpleNumber+"}^{"+neverEndingIndex+"}";
        nextSimpleNumber++;
        neverEndingIndex++;
        latinLetter++;
        if(latinLetter==91){
            latinLetter=97;
        }
        return barcode;
    }
}
