import java.util.ArrayList;
import java.util.List;



public class Assignment1 {

    private static List<Integer> buffer=new ArrayList<>();
    private static final int upperlimit=5;
    private static final int lowerlimit=0;
    private static final Object lock=new Object();
    private static int value=0;
    private static int counter=0;

    public static void main(String[] args) {


        Thread producer=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    while (counter<=10)
                    {
                        if(buffer.size()==upperlimit)
                        {
                            value=0;
                            System.out.println("Waiting for Removing items");
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }counter++;
                        }
                        else
                        {
                            System.out.println("Adding items " +value);
                            buffer.add(value);
                            value++;
                            lock.notify();
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });

        Thread consumer=new Thread(new Runnable() {
            @Override
            public void run() {
                        synchronized (lock){
                            while (true)
                            {
                                if(buffer.size()==lowerlimit)
                                {
                                    System.out.println("Waiting for Adding items");
                                    try {
                                        lock.wait();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }counter++;
                                }
                                else
                                {
                                    System.out.println("Removing items "+buffer.remove(buffer.size()-1));
                                    lock.notify();
                                }
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }

                        }

                    }

            }
        );

        producer.start();
        consumer.start();

    }
}
