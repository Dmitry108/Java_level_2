//Dmitrii Babanov, 5.12.2019
//Java level 2, lesson 5, Homework

package homework5;

public class ArrayAndThreads {
    private static final int SIZE = 10000000;
    private static final int half = SIZE/2;
    private float[] arr;

    public static void main (String[] args) {
        ArrayAndThreads at1 = new ArrayAndThreads();
        ArrayAndThreads at2 = new ArrayAndThreads();
        System.out.printf("Время последовательного вычисления: %d\nВремя параллельного вычисления: %d\n", at1.method1(), at2.method2());
    }
    public ArrayAndThreads() {
        arr = new float[SIZE];
        for (int i = 0; i < SIZE; arr[i++] = 1) ;
    }
    public long method1(){
        long t1 = System.currentTimeMillis();
        for (int i=0; i<SIZE; arr[i] = (float)(arr[i] * Math.sin(0.2f + (float)i / 5) * Math.cos(0.2f + (float)i / 5) * Math.cos(0.4f + (float)i / 2)), i++);
        long t2 = System.currentTimeMillis();
        return t2-t1;
    }
    public void printArr() {
        for (float a : arr) System.out.print(a + "\t");
        System.out.println();
    }
    public long method2(){
        long t1 = System.currentTimeMillis();
        float[] a1 = new float[half];
        float[] a2 = new float[half + SIZE%2];
        System.arraycopy(arr, 0, a1, 0, half);
        System.arraycopy(arr, half, a2, 0, a2.length);
        Thread tr1 = new Thread(()->{
            for (int i=0; i<half;
                 a1[i] = (float)(a1[i] * Math.sin(0.2f + (float)i / 5) * Math.cos(0.2f + (float)i / 5) * Math.cos(0.4f + (float)i++ / 2)));
        });
        Thread tr2 = new Thread(()->{
            for (int i=half; i<SIZE;
                 a2[i-half] = (float)(a2[i-half] * Math.sin(0.2f + (float)i / 5) * Math.cos(0.2f + (float)i / 5) * Math.cos(0.4f + (float)i++ / 2)));
        });
        tr1.start();
        tr2.start();
        try{tr1.join();
            tr2.join();
        } catch(Exception e) {e.printStackTrace();}
        System.arraycopy(a1, 0, arr, 0, half);
        System.arraycopy(a2, 0, arr, half, a2.length);
        long t2 = System.currentTimeMillis();
        return t2-t1;
    }
}
