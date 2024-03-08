package com.jiggycode.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.DetectModerationLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectModerationLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.jiggycode.model.ModerationLabel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Service
public class AwsRekognitionService {

    private final AmazonRekognition rekognitionClient;

    public AwsRekognitionService(AmazonRekognition client) {
        this.rekognitionClient = client;
    }

    public List<ModerationLabel> detectModerationLabels(MultipartFile imageFile) throws IOException {
        ByteBuffer imageBytes = ByteBuffer.wrap(imageFile.getBytes());

        DetectModerationLabelsRequest request = new DetectModerationLabelsRequest()
                .withImage(new Image().withBytes(imageBytes));

        DetectModerationLabelsResult result = rekognitionClient.detectModerationLabels(request);
        List<ModerationLabel> customLabels = new ArrayList<>();
        for (com.amazonaws.services.rekognition.model.ModerationLabel sdkLabel : result.getModerationLabels()) {
            ModerationLabel customLabel = new ModerationLabel();
            customLabel.setName(sdkLabel.getName());
            customLabel.setConfidence(sdkLabel.getConfidence());
            customLabels.add(customLabel);
        }

        return customLabels;
    }
}
