public class Task implements Runnable {
    public Task () { }

    @Override
    public synchronized void run()
    {
        for (int i = 0; i < 5; i++)
        {
            try {
                System.out.print(Thread.currentThread().getName() + "\n");
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {

            }
        }
    }
}
