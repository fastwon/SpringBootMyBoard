package com.fastwon.video;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VideoProcessing {
    public static void main(String[] args) {
        String inputPath = "D:/Exam.mp4";
        String outputPath = "output4.mp4";

        try {
            // 현재 디렉토리 경로 얻기
            String currentDirectory = System.getProperty("user.dir");

            // FFmpeg 명령 생성 및 실행
            String command = currentDirectory + "/src/main/resources/ffmpeg/bin/ffmpeg.exe -i " + inputPath + " -ss 3 -t 2 " + outputPath;

            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // 프로세스의 출력 로그 확인 (선택적)
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			process.waitFor(); // 프로세스 완료 대기

        } catch (IOException | InterruptedException e) {
           e.printStackTrace();
        }
    }
}
