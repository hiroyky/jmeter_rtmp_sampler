package com.hiroyky.sampler;


import java.io.IOException;

public class RtmpPublisher {

    String source;
    String destination;
    String ffmpegBin;
    public RtmpPublisher(String src, String destination, String ffmpegBin) {
        this.source = src;
        this.destination = destination;
        this.ffmpegBin = ffmpegBin;
    }

    public void run() throws InterruptedException, IOException {

        String[] command = {
                ffmpegBin,
                "-re",
                "-stream_loop", "-1",
                "-i", this.source,
                "-c:v", "copy",
                "-c:a", "copy",
                "-f", "flv",
                destination,
        };

        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();
        process.waitFor();
    }
}
