package com.wangao.dd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DdApplication {
    public static void main(String[] args) {
        SpringApplication.run(DdApplication.class, args);
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
//            StringBuilder inputBuilder = new StringBuilder();
//            String line;
//
//            System.out.println("Enter text (end with ';'):");
//
//            while ((line = reader.readLine()) != null) {
//                inputBuilder.append(line); // 将读取的内容添加到 StringBuilder 中
//
//                if (line.contains(";")) {
//                    int index = line.indexOf(';');
//                    String input = inputBuilder.substring(0, inputBuilder.length() - (line.length() - index)); // 提取到分号前的内容
//                    System.out.println("Input until semicolon: " + input);
//                    break; // 读取到分号后结束循环
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
