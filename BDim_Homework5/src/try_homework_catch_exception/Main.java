//Нерациональная попытка реализации алгоритма с произвольным количеством потоков
//Получается не выгодно по времени исполнения

package try_homework_catch_exception;
public class Main {
	
	public static void main (String[] args) {
		Array at1 = new Array();
		Array at2 = new ArrayThreads(50);
		System.out.printf("Время последовательного вычисления: %d\nВремя параллельного вычисления: %d\n", at1.method(), at2.method());
		}
}
class Array {
	private static final int SIZE = 10000000;
	private float[] arr;
	
	public Array(){
		arr = new float[SIZE];
		for (int i=0; i<SIZE; arr[i++]=1);
	}
	public int getSize(){return SIZE;}
	public float[] getArr(){return arr;}
	public long method(){
		long t1 = System.currentTimeMillis();
		for (int i=0; i<SIZE; arr[i] = (float)(arr[i] * Math.sin(0.2f + (float)i / 5) * Math.cos(0.2f + (float)i / 5) * Math.cos(0.4f + (float)i / 2)), i++);
		long t2 = System.currentTimeMillis();
		return t2-t1;
	}
	public void printArr(){
		for (float a: arr) System.out.print(a + "\t");
		System.out.println();
	}
}
class ArrayThreads extends Array {
	private int half;
	public ArrayThreads (int half){
		super();
		this.half = half;
	}
	@Override
	public long method(){
		long t1 = System.currentTimeMillis();
		float[][] a = new float[half][];
		for (int h=0, newSize; h<half; h++){
			newSize = getSize()/half + (h!=half-1?0:getSize()%half);
			a[h] = new float[newSize];
			System.arraycopy(getArr(), h*(getSize()/half), a[h], 0, newSize);
			final int hh = h;
			Thread tr = new Thread(()->{
					for (int j=0, i = hh*(getSize()/half); j<a[hh].length;
				a[hh][j] = (float)(a[hh][j] * Math.sin(0.2f + (float)i / 5) * Math.cos(0.2f + (float)i / 5) * Math.cos(0.4f + (float)i / 2)), j++, i++);
			});
			tr.start();
			try{tr.join();
			} catch(Exception e) {}
		}
		for (int h=0; h<half; h++){
			System.arraycopy(a[h], 0, getArr(), h*(getSize()/half), a[h].length);
		}	
		long t2 = System.currentTimeMillis();
		return t2-t1;
	}
}