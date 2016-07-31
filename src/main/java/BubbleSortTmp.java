public class BubbleSortTmp {
    public String[] Country = {"z", "h", "a"};
    public int[] City = {3, 2, 1};

    public void printCountry() {
        for (String s : Country) {
            System.out.printf("%s ", s);
        }
        System.out.println();
    }

    public void printCity() {
        for (int s : City) {
            System.out.printf("%s ", s);
        }
        System.out.println();
    }

    public void sort() {
        for (int outer = Country.length - 1; outer > 0; outer--) {
            for (int inner = 0; inner < outer; inner++) {
                if (Country[inner].compareTo(Country[inner+1]) > 0) {
                    swapCountry(inner, inner+1);
                    swapCity(inner, inner+1);
                }
            }
        }
    }

    private void swapCountry(int first, int second) {
        String tmp = Country[first];
        Country[first] = Country[second];
        Country[second] = tmp;
    }

    private void swapCity(int first, int second) {
        int tmp = City[first];
        City[first] = City[second];
        City[second] = tmp;
    }

    public static void main(String[] args) {
        BubbleSortTmp bs = new BubbleSortTmp();

        System.out.println("Before: ");
        bs.printCountry();
        bs.printCity();

        bs.sort();

        System.out.println("After: ");
        bs.printCountry();
        bs.printCity();
    }
}
