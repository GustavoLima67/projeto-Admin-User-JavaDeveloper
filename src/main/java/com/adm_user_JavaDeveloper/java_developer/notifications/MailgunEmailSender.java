package com.adm_user_JavaDeveloper.java_developer.notifications;

import java.io.IOException;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MailgunEmailSender {

   
    public static final String API_KEY = "efe7ce7c454063132f50901c9e239a11-826eddfb-84528c31"; 
   
    public static final String DOMAIN_NAME = "f7f382d604512da182ea8dce60dce908-826eddfb-20d5f6dd";  

    public static void sendEmail(String toEmail, String subject, String body) throws IOException {
        OkHttpClient client = new OkHttpClient();

      
        RequestBody formBody = new FormBody.Builder()
                .add("from", "Excited User <mailgun@" + DOMAIN_NAME + ">")
                .add("to", toEmail)
                .add("subject", subject)
                .add("text", body)
                .build();

        Request request = new Request.Builder()
                .url("https://api.mailgun.net/v3/" + DOMAIN_NAME + "/messages")
                .addHeader("Authorization", Credentials.basic("api", API_KEY))
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            System.out.println("Resposta: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
