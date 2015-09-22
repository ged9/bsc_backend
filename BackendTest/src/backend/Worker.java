package backend;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Worker implements Runnable {
	private Data data;
	private long period = 1000 * 60;
	private Timer timer;

	public Worker(String[] args) {
		data = new Data();

		if (args.length == 1) {
			initPayments(args[0]);
		}
	}

	private void initPayments(String file) {
		Path path = FileSystems.getDefault().getPath(file);
		try {
			List<String> lines = Files.readAllLines(path);

			for (String line : lines) {
				Payment p = Payment.getPayment(line);

				if (p != null) {
					data.addPayment(p);
				}
			}
		} catch (IOException e) {
			System.out.println("File cannot be read.");
			// e.printStackTrace();
		}
	}

	@Override
	public void run() {
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				data.printData();
			}
		}, 0, period);

		Scanner scanner = new Scanner(System.in);

		while (true) {
			String s = scanner.nextLine();

			if ("quit".equalsIgnoreCase(s)) {
				timer.cancel();
				break;
			}

			Payment p = Payment.getPayment(s);
			if (p != null) {
				data.addPayment(p);
				System.out.println("Payment added");
			} else {
				System.out
						.println("The format is incorrect. Please check the format (for examaple: USD 1500 )");
			}
		}

		scanner.close();
	}

}
