package com.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportRenamer {

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                Path source = Paths.get("target/cucumber-reports.html");
                Path target = Paths.get("target/cucumber-reports-" + timestamp + ".html");

                Thread.sleep(1000);

                if (Files.exists(source)) {
                    Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("✔️ Report renamed to: " + target.getFileName());
                } else {
                    System.out.println("⚠️ Report file not found at: " + source);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }

    public static void init() {
        // This ensures the static block runs
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(ReportGenerator::generateReport));
    }

}
