public class teste_Split {
    public static void main(String[] args) {
        String array = "x00=20, x01=10, x02=40, x03=30";
        String array1[]= array.split(", ");
        //change array1 for integer
        int index =array1[0].indexOf("=");
        for (int i=0; i<array1.length; i++){
            array1[i]=array1[i].substring(index+1);
            System.out.println(array1[i]);
        }

    }


}
