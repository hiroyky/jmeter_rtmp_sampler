package com.hiroyky.sampler;

import java.io.IOException;

public class RtmpSubscriber {
    String source;
    String ffmpegBin;
    public RtmpSubscriber(String source, String ffmpegBin) {
        this.source = source;
        this.ffmpegBin = ffmpegBin;
    }

    public void run() throws IOException, InterruptedException {
        String[] command = {
                ffmpegBin,
                "-i", this.source,
                "-f", "null",
                "-",
        };
        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();
        process.waitFor();
    }
}
