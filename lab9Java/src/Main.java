import java.util.*;

public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new MyRunnable());
        System.out.println("Состояние потока перед запуском: " + thread.getState());
        thread.start();
        System.out.println("Состояние потока после запуска: " + thread.getState());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Состояние потока во время выполнения: " + thread.getState());
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread thread1 = new Thread(new Task());
        Thread thread2 = new Thread(new Task());
        thread1.setName("Поток 1");
        thread2.setName("Поток 2");
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int countObj = 5;
        Random rng = new Random();
        Deque<Integer> queue = new ArrayDeque<>(countObj);
        Thread sender = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                do {
                    if (Thread.interrupted())
                        return;
                    if (queue.size() < countObj) {
                        int str = rng.nextInt(50);
                        System.out.println("Отправитель отправил сообщение: " + str);
                        queue.addLast(str);
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        return;
                    }
                } while (true);
            }
        });
        Thread getter = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    if (Thread.interrupted())
                        return;
                    if (queue.size() > 0) {
                        int getMess = queue.getFirst();
                        queue.removeFirst();
                        System.out.println("Получатель 1 взял сообщение: " + getMess);
                    }
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        return;
                    }
                } while (true);
            }
        });

        sender.start();
        getter.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sender.interrupt();
        getter.interrupt();
    }
}