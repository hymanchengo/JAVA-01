package com.hymanting.concurrency.geekjavatrain.week04homework;

import java.util.concurrent.*;

/**
 * @Author hxchen
 * @Date 2021/2/6
 */
public class JavaAsyncDemo {
    public static void main(String[] args) {
        method1();
        method2();
        method3();
        method4();
        method5();
        method6();
    }

    private static void method1() {
        long start = System.currentTimeMillis();
        final int[] value = new int[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Thread computeSumThread = new Thread() {
            @Override
            public void run() {
                value[0] = sum(); //这是得到的返回值
                latch.countDown();
            }
        };
        computeSumThread.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int result = value[0];
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static void method2() {
        long start = System.currentTimeMillis();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                return sum();
            }
        };
        Future<Integer> future = executor.submit(callable);
        try {
            int result = future.get();
            System.out.println("异步计算结果为：" + result);
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    private static void method3() {
        long start = System.currentTimeMillis();
        SumComputer sumComputer =  new SumComputer();
        Thread computeSumThread = new Thread(sumComputer);
        computeSumThread.start();
        try {
            computeSumThread.join();
            int result = sumComputer.getValue();
            System.out.println("异步计算结果为：" + result);
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void method4() {
        long start = System.currentTimeMillis();
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Future<Integer> value = pool.submit(new Callable<Integer>() {
            @Override
            public Integer call() {return sum();}
        });
        try {
            int result = value.get();
            System.out.println("异步计算结果为：" + result);
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void method5() {
        long start = System.currentTimeMillis();
        CompletableFuture<Integer> completableFuture
                = CompletableFuture.supplyAsync(() -> sum());
        try {
            int result = completableFuture.get();
            System.out.println("异步计算结果为：" + result);
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class Lock {
        private int value;
        public int getValue() {
            return value;
        }
        public void setValue(int v) {
            this.value =  v;
        }
    }

    private static void method6() {
        long start = System.currentTimeMillis();
        Lock lock = new Lock();
        Thread t = new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    lock.setValue(sum());
                    lock.notify();
                }
            }
        };
        t.start();
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int result = lock.getValue();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}

class SumComputer implements Runnable {
    private volatile int value;

    @Override
    public void run() {
        value = sum();
    }

    public int getValue() {
        return value;
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}