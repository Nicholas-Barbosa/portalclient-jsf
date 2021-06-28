package com.portal.jse;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

class InvokeAllTest {

	@Test
	void test() {
		ExecutorService executor = null;
		try {
			executor = Executors.newFixedThreadPool(3);
			Callable<Integer> callable1 = () -> {
				System.out.println("callable1 sleeping!");
				Thread.sleep(2000);
				return 1;
			};
			Callable<Integer> callable2 = () -> {
				System.out.println("callable2 sleeping!");
				Thread.sleep(2000);
				return 2;
			};
			Callable<Integer> callable3 = () -> {
				System.out.println("callable3 sleeping!");
				Thread.sleep(2000);
				return 3;
			};
			try {
				executor.invokeAll(List.of(callable1, callable2, callable3)).forEach(f->{
					try {
						System.out.println(f.get());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			executor.shutdown();
		}
	}


}
