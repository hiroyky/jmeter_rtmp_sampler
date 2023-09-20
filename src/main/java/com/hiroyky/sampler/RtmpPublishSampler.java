package com.hiroyky.sampler;


import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;


public class RtmpPublishSampler implements JavaSamplerClient {
    static final String DESTINATION_URL = "Destination RTMP URL";
    static final String SOURCE_FILE = "Source file";
    static final String FFMPEG_PATH = "FFMPEG Binary Path";
    static final String IS_LOOP = "loop?";

    @Override
    public void setupTest(JavaSamplerContext context) {
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        String destination = context.getParameter(DESTINATION_URL);
        String source = context.getParameter(SOURCE_FILE);
        boolean isLoop = context.getParameter(IS_LOOP).equalsIgnoreCase("true");
        String ffmpegBin = context.getParameter(FFMPEG_PATH);

        StringBuilder builder = new StringBuilder();
        builder
                .append("this is ResponseData\n")
                .append("source: ").append(source).append("\n")
                .append("destination: ").append(destination).append("\n");

        SampleResult result = new SampleResult();

        try {
            RtmpPublisher publisher = new RtmpPublisher(source, destination, ffmpegBin, isLoop);
            String cmd = publisher.init();
            builder.append("cmd: ").append(cmd).append("\n");
            result.sampleStart();
            publisher.run();
            result.sampleEnd();
            result.setResponseCodeOK();
            result.setResponseMessage(builder.toString());
            result.setSuccessful(true);
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setResponseCode("500");
            builder.append("error: ").append(e.getMessage()).append("\n");
            result.setResponseMessage(builder.toString());
        }
        return result;
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {
    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments defaultParameters = new Arguments();
        defaultParameters.addArgument(DESTINATION_URL, "rtmp://localhost:1935/app/stream_key");
        defaultParameters.addArgument(SOURCE_FILE, "/absolute/path/to/file.mp4");
        defaultParameters.addArgument(FFMPEG_PATH, "ffmpeg");
        defaultParameters.addArgument(IS_LOOP, "true");
        return defaultParameters;
    }
}
