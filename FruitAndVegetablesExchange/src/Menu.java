import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {
    private static Scanner scanner =new Scanner(System.in);
    private static int days=1;
    private static double balance=1000;
    private static double DDS=0.2;
    private static double ddsDiscount=0;

    public static List<ItemStash> itemStashList=new ArrayList<>();
    public static List<Double> turnoverContainer=new ArrayList<>();

    private static int previousDayOperations=0;
    private static int previousDayPurchases=0;
    private static double previousDayEarnings=0;
    private static double expenses=0;
    private static double profit=0;

    private static String secretCode=" ";
    private static boolean isCaptainsBasementActive=false;
    private static boolean isKarakudaActive=false;
    private static boolean isSharanActive=false;
    private static boolean isDayStarted=false;

    public static void menuMethod(){
        System.out.println("1. Стартиране на деня");
        System.out.println("2. Покупка");
        System.out.println("3. Продажба");
        System.out.println("4. Справка");
        System.out.println("5. Рушвети");
        System.out.println("6. Приключване на деня");
        System.out.println("7. Изход от системата");
        int commandNumber=scanner.nextInt();
        if(commandNumber==1){
            if(!isDayStarted) {
                isDayStarted = true;
                generateSecretCode();
                System.out.println("Ващия таен код е : " + secretCode);
                menuMethod();
            }
            else {
                System.out.println("Не може да отворите деня си повторно");
                menuMethod();
            }
        }
        if(commandNumber==2){
            if(isDayStarted) {
                buyItem();
                previousDayOperations++;
            }
            else{
                System.out.println("Трябва да стартирате деня , за да може да изпълните тази команда");
                menuMethod();
            }
        }
        if(commandNumber==3){
            if(isDayStarted) {
                sellItem();
                previousDayOperations++;
            }
            else{
                System.out.println("Трябва да стартирате деня , за да може да изпълните тази команда");
                menuMethod();
            }
        }
        if(commandNumber==4){
            if(isDayStarted) {
                turnover();
                previousDayOperations++;
            }
            else{
                System.out.println("Трябва да стартирате деня , за да може да изпълните тази команда");
                menuMethod();
            }
        }
        if(commandNumber==5){
            if(isDayStarted) {
                bribe();
                previousDayOperations++;
            }
            else{
                System.out.println("Трябва да стартирате деня , за да може да изпълните тази команда");
                menuMethod();
            }
        }
        if(commandNumber==6){
            if(isDayStarted) {
                isDayStarted=false;
                days++;
            }
            else{
                System.out.println("Трябва да стартирате деня , за да го приключите");
                menuMethod();
            }
        }
        if(commandNumber==7){
            return;
        }
    }
    private static void generateSecretCode(){
        String prevDayEarn = " ";
        if(previousDayEarnings==0){
            prevDayEarn ="00";
        }
        else{
            prevDayEarn =String.valueOf(previousDayEarnings);
        }
        String prevDayPurchases = " ";
        if(previousDayPurchases==0){
            prevDayPurchases ="**";
        }
        else {
            prevDayPurchases =String.valueOf(previousDayPurchases);
        }
        String prevDayOp = " ";
        if(previousDayOperations==0){
            prevDayOp ="#@";
        }
        else {
            prevDayOp =String.valueOf(previousDayOperations);
        }
        secretCode= prevDayOp +"%%"+ prevDayPurchases +"%%"+ prevDayEarn;
    }
    private static void buyItem(){
        System.out.println("Въведете тайния си код");
        String secretText=scanner.next();
        if(!secretText.equals(secretCode)){
            System.out.println("Тайния код не съвпада!");
            menuMethod();
        }
        System.out.println("Изберете категория на продукта");
        String category=scanner.next();
        System.out.println("Въведете име на стоката");
        String name=scanner.next();
        System.out.println("Въведете количество продукти");
        int quantity=scanner.nextInt();
        System.out.println("Въведете цената , ня която закупувате стоката");
        double buyPrice=scanner.nextDouble();
        System.out.println("Въведете цената , ня която ще продадете стоката");
        double sellPrice=scanner.nextDouble();

        System.out.println("1.Име на продукт: "+name);
        System.out.println("2.Количество: "+quantity);
        System.out.println("3.Цена за покупка: "+buyPrice);
        System.out.println("4.Цена за продажба: "+sellPrice);
        System.out.println("5.Категория на продукт: "+category);
        System.out.println("Искате ли да редактирате нещо ?(Y/N)");
        String  command=scanner.next();
        while (!command.equals("N")){
            System.out.println("Изберете кое искате да редактирате");
            int number=scanner.nextInt();
            if(number==1){
                System.out.println("Въведете ново име на продукта");
                name=scanner.nextLine();
            }
            if(number==2){
                System.out.println("Въведете ново количество");
                quantity=scanner.nextInt();
            }
            if(number==3){
                System.out.println("Въведете нова цена на покупка");
                buyPrice=scanner.nextDouble();
            }
            if(number==4){
                System.out.println("Въведете нова цена на продажба");
                sellPrice=scanner.nextDouble();
            }
            if(number==5){
                System.out.println("Въведете нова категория на продукта");
                category=scanner.nextLine();
            }
            System.out.println("Искате ли да редактирате нещо ?(Y/N)");
            command=scanner.next();
        }
        balance-=(buyPrice*quantity)+(buyPrice*quantity*DDS);
        System.out.println("Покупката е успешна!");
        expenses-=(buyPrice*quantity)+(buyPrice*quantity*DDS);
        ItemStash newItem=new ItemStash(name,quantity,buyPrice,sellPrice,category);
        itemStashList.add(newItem);
        menuMethod();
    }
    private static void sellItem(){
        System.out.println("1.Ръчно");
        System.out.println("2.С бар код");
        int command=scanner.nextInt();
        double fullPrice=0;
        if(command==1){
            System.out.println("Изберете една от следните категории");
            for (ItemStash item:itemStashList){
                System.out.println(item.getCategory());
            }
            String category=scanner.next();
            System.out.println("Изберете един от следните продукти");
            for (ItemStash item:itemStashList){
                if(category.equals(item.getCategory())){
                    System.out.println(item.getItemName());
                }
            }
            String name=scanner.next();
            System.out.println("Въведете количество");
            int quantity=scanner.nextInt();
            double itemPrice=0;
            for (ItemStash item:itemStashList){
                if(name.equals(item.getItemName())){
                    itemPrice=item.getSellPrice();
                    if(quantity<item.getQuantity()){
                        item.setQuantity(item.getQuantity()-quantity);
                    }
                    else {
                        System.out.println("Няма толкова налични "+item.getItemName());
                        menuMethod();
                    }
                }
            }
            if(isCaptainsBasementActive){
                ddsDiscount+=0.05;
                fullPrice=(itemPrice*quantity)-((itemPrice*quantity*0.01)-(itemPrice*quantity*0.01)*ddsDiscount);
            }
            if(isKarakudaActive){
                ddsDiscount+=0.1;
                fullPrice=(itemPrice*quantity)-((itemPrice*quantity*0.01)-(itemPrice*quantity*0.01)*ddsDiscount);
            }
            if(isSharanActive){
                ddsDiscount+=0.15;
                fullPrice=(itemPrice*quantity)-((itemPrice*quantity*0.01)-(itemPrice*quantity*0.01)*ddsDiscount);
            }
            else {
                fullPrice=(itemPrice*quantity)-(itemPrice*quantity*0.01);
            }
            System.out.println("Крайна цена: "+fullPrice);
            System.out.println("Сигурни ли сте , че искате да направите тази поръчка? (Y/N)");
            String yN=scanner.next();
            if(yN.equals("Y")){
                System.out.println("Поръчката е потвърдена");
                balance+=fullPrice;
                profit+=fullPrice;
                menuMethod();
            }
            if(yN.equals("N")){
                System.out.println("Поръчката е отменена");
                menuMethod();
            }
        }
        if(command==2){
            System.out.println("Въведете баркода на избран от вас продукт");
            ItemStash.getAllItems();
            String barcode=scanner.next();
            System.out.println("Въведете количество");
            int quantity=scanner.nextInt();
            double itemPrice=0;
            for (ItemStash item:itemStashList){
                if (barcode.equals(item.getBarcode())){
                    itemPrice=item.getSellPrice();
                    if(quantity<item.getQuantity()){
                        item.setQuantity(item.getQuantity()-quantity);
                    }
                    else {
                        System.out.println("Няма толкова налични "+item.getItemName());
                        menuMethod();
                    }
                }
            }
            if(isCaptainsBasementActive){
                fullPrice=(itemPrice*quantity)-((itemPrice*quantity*0.01)-(itemPrice*quantity*0.01)*0.05);
            }
            if(isKarakudaActive){
                ddsDiscount+=0.1;
                fullPrice=(itemPrice*quantity)-((itemPrice*quantity*0.01)-(itemPrice*quantity*0.01)*ddsDiscount);
            }
            if(isSharanActive){
                ddsDiscount+=0.15;
                fullPrice=(itemPrice*quantity)-((itemPrice*quantity*0.01)-(itemPrice*quantity*0.01)*ddsDiscount);
            }
            else {
                fullPrice=(itemPrice*quantity)-(itemPrice*quantity*0.01);
            }

            System.out.println("Крайна цена: "+fullPrice);
            System.out.println("Сигурни ли сте , че искате да направите тази поръчка? (Y/N)");
            String yN=scanner.next();
            if(yN.equals("Y")){
                System.out.println("Поръчката е потвърдена");
                balance+=fullPrice;
                profit+=fullPrice;
                previousDayPurchases++;
                menuMethod();
            }
            if(yN.equals("N")){
                System.out.println("Поръчката е отменена");
                menuMethod();
            }
        }
    }
    private static void turnover(){
        System.out.println("1.Справка - Финансов Резултат");
        System.out.println("2.Справка - Оборот");
        System.out.println("3.Справка - Други");
        System.out.println("4.Назад");
        int command = scanner.nextInt();
        if(command==1){
            System.out.println("1.Листване на текущ дневен финансов резултат");
            System.out.println("2.Листване на всички вчерашни финансови резултати последователно");
            System.out.println("3.Листване на всички вчерашни финансови резултати във възходящ ред");
            System.out.println("4.Листване на всички вчерашни финансови резултати в низходящ ред");
            System.out.println("5.Средно аритметичната стойност на всички вчерашни финансови резултати");
            System.out.println("6.Назад");
            int number=scanner.nextInt();
            if(number==1){
                previousDayEarnings=profit-expenses;
                System.out.println("Пари от продажби: "+profit);
                System.out.println("Разходи: "+expenses);
                System.out.println("Текуща печалба "+previousDayEarnings);
                turnover();
            }
            if(number==2){
                for (int i=0;i<turnoverContainer.size();i++){
                    System.out.println("Ден "+i+1+" "+turnoverContainer.get(i));
                }
                turnover();
            }
            if(number==3){
                List<Double> tempList= turnoverContainer.stream().sorted().collect(Collectors.toList());
                tempList.forEach(System.out::println);
            }
            if(number==4){
                List<Double> tempList= turnoverContainer.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                tempList.forEach(System.out::println);
            }
            if(number==5){
                int sum=0;
                for (int i=0;i<turnoverContainer.size()-1;i++){
                    sum+=turnoverContainer.get(i);
                }
                System.out.println("Средно аритметично от всички дни до сега: "+sum);
            }
            if(number==6){
                menuMethod();
            }

        }
        if(command==2){
            System.out.println("1.Справка дневен оборот");
            System.out.println("2.Справка дневен оборот по категория");
            System.out.println("3.Справка вчерашен оборот");
            System.out.println("4.Справка за конкретен вчерашен оборот");
            System.out.println("5.Справка месечни вчерашни обороти във възходящ ред");
            System.out.println("6.Справка месечни вчерашни обороти в низходящ ред");
            System.out.println("7.Изход");
            int number=scanner.nextInt();
            if(number==1){
                System.out.println("Вашия оборот: "+balance);
            }
            if(number==2){

            }
            if(number==3){
                System.out.println("Вчерашния ви оборот : "+turnoverContainer.get(turnoverContainer.size()-2));
            }
            if(number==4){
                System.out.println("Изберете конкретен ден");
                int day=scanner.nextInt();
                System.out.println("Оборота за този ден е "+turnoverContainer.get(day-1));
            }
            if(number==5){
                if(days>=30){
                    List<Double> tempList= turnoverContainer.stream().sorted().collect(Collectors.toList());
                    tempList.forEach(System.out::println);
                }
            }
            if(number==6){
                if(days>=30){
                    List<Double> tempList= turnoverContainer.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                    tempList.forEach(System.out::println);
                }
            }
            if(number==7){
                turnover();
            }
        }
        if(command==3){
            ItemStash.getAllItems();
            turnover();
        }
        if(command==4){
            menuMethod();
        }
    }
    private static void bribe(){
        System.out.println("Въведете идентификационния си код");
        String idCode=scanner.next();
        if(!idCode.equals(secretCode)){
            System.out.println("Господин честен данъчен тук нищо не се случило");
            menuMethod();
        }
        String bribeName=" ";
        System.out.println("Изберете тарифен план");
        System.out.println("    название                                 облаги                                       разход");
        System.out.println("1.Мазето на капитана     премахва 5% от всички начислени данъци при продажба               50 ");
        System.out.println("2.Каракуда               премахва 10% от всички начислени данъци\n" +
                                                   "продажба и намалява ДДС покупка с 1% за\n" +
                                                        "всички бъдещи периоди                                       200");
        System.out.println("3.Шаран                   премахва 15% от всички начислени данъци\n" +
                                                    "продажба намалява ДДС покупка с 5%\n" +
                                                    "следващата стока която закупите не се\n" +
                                                        "облага с ДДС                                                300");
        int number=scanner.nextInt();
        if(number==1){
            isCaptainsBasementActive=true;
            balance-=50;
        }
        if(number==2){
            isKarakudaActive=true;
            balance-=200;
        }
        if(number==3){
            isSharanActive=true;
            balance-=300;
        }
    }
}
