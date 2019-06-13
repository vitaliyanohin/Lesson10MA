public class Application {
  public static void main(String[] args) {
//    test500Key();
//    testBasic();
    testCollision();
    System.out.println("Line for Breakpoint");
  }

 private static void test500Key() {
    HashMapTest<Integer, Integer> mapTest = new HashMapTest<>();
    for (int i = 0; i < 501; i++) {
      mapTest.put(i, i);
    }
    for (int k = 0; k < 501; k++) {
      System.out.println(mapTest.get(k));
    }
  }

  private static void testCollision() {
    HashMapTest<String, Integer> mapTestCollision = new HashMapTest<>();
    mapTestCollision.put("00", 0);
    mapTestCollision.put("22", 4);
    mapTestCollision.put("23", 5);
    mapTestCollision.put("100", 10);
    mapTestCollision.put("110", 11);
    System.out.println(mapTestCollision.get("00"));
    System.out.println(mapTestCollision.get("22"));
    System.out.println(mapTestCollision.get("110"));
    System.out.println(mapTestCollision.get("23"));
    System.out.println(mapTestCollision.get("100"));
    System.out.println("Line for Breakpoint");
  }

  private static void testBasic() {
    HashMapTest<String, Integer> mapTestBasic = new HashMapTest<>();
    String[] key = {"00", "22", "23", "24", "25", "10", "20", "30", "40", "50", "60",
                    "70", "80", "90", "100", "110", "120", "130"};
    mapTestBasic.put("00", 0);
    mapTestBasic.put("22", 4);
    mapTestBasic.put("23", 5);
    mapTestBasic.put("24", 6);
    mapTestBasic.put("25", 6);
    mapTestBasic.put("10", 1);
    mapTestBasic.put("20", 2);
    mapTestBasic.put("30", 3);
    mapTestBasic.put("40", 4);
    mapTestBasic.put("50", 5);
    mapTestBasic.put("60", 6);
    mapTestBasic.put("70", 7);
    mapTestBasic.put("80", 8);
    mapTestBasic.put("90", 9);
    mapTestBasic.put("100", 10);
    mapTestBasic.put("110", 11);
    mapTestBasic.put("120", 12);
    mapTestBasic.put("130", 13);
    for (String string : key) {
      System.out.println(mapTestBasic.get(string));
    }
    System.out.println("Line for Breakpoint");
  }
}
