import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Assignment2 {

    private static List<Integer> list=new ArrayList<>(5);
    private static final int upperlimit=5;
    private static final int lowerlimit=0;
    private static final Object lock=new Object();
    private static int negative=0;
    private static int positive_even=0;
    private static int positive_odd=0;

    public static void main(String[] args) {

        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock)
                {
                    while(true)
                    {
                        System.out.println("Creating an Array with Random elements");
                    Random rd = new Random(); // creating Random object
                    for (int i = lowerlimit; i < upperlimit; i++) {
                        list.add(rd.nextInt()); // storing random integers in an array
                        System.out.println(list.get(i)); // printing each array element
                    }
                    System.out.println("Waiting to Calculate");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
            }}
        });

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock)
                {
                    while(true)
                    {
                       for(int i=0;i<list.size();i++)
                       {
                           int num=list.get(i);

                           if (num>0 && num%2==0)
                           {
                               positive_even++;
                           }
                           else if(num>0 && num%2!=0)
                           {
                               positive_odd++;
                           }
                           else if(num<0)
                           {
                               negative++;
                           }
                       }
                       System.out.println("Positive Even : "+positive_even);
                        System.out.println(("Positive Odd :"+positive_odd));
                        System.out.println(("Negative :"+negative));list.clear();
                        positive_odd=0;
                        positive_even=0;
                        negative=0;
                        try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                     lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }

                }}
        });

        t1.start();
        t2.start();

    }
}
