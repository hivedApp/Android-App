package com.megthinksolutions.apps.hived.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.megthinksolutions.apps.hived.R;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PreSignedActivity extends AppCompatActivity {
    Regions clientRegion = Regions.DEFAULT_REGION;
    String bucketName = "my-profile-image";
    String objectKey = "*** Object key ***";
    private AmazonS3Client s3Client;
    private BasicAWSCredentials credentials;
    private String accessKey, secretKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_signed);

        credentials = new BasicAWSCredentials("AKIA2WSH67LRCGFWPQOV", "0b3Su1RWU/L7W6JIY9Zw4P5loUbS45jUS96H46HX");
        s3Client = new AmazonS3Client(credentials);

        try {

            // Set the pre-signed URL to expire after one hour.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 60;
            expiration.setTime(expTimeMillis);

            // Generate the pre-signed URL.
            System.out.println("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey)
                    .withMethod(HttpMethod.PUT)
                    .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            Log.d("generateURL", "url: " + url);

            // Create the connection and use it to upload the new object using the pre-signed URL.
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("PUT");
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write("This text uploaded as an object via PreSigned URL.");
                out.close();

                // Check the HTTP response code. To complete the upload and make the object available,
                // you must interact with the connection object in some way.
                connection.getResponseCode();
                System.out.println("HTTP response code: " + connection.getResponseCode());

                // Check to make sure that the object was uploaded successfully.
                S3Object object = s3Client.getObject(bucketName, objectKey);
                System.out.println("Object " + object.getKey() + " created in bucket " + object.getBucketName());

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        }


    }
}