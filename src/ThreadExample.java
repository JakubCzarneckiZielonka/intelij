import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExample {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(()-> numbers());
        executorService.submit(()-> numbers());
    }
    private static void numbers(){
        for (int i = 0; i< 999; i++){
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }
}
