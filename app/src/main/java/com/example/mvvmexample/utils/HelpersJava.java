package com.example.mvvmexample.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;


public class HelpersJava {


    public static Gson getCustomGson(Context context) {
        return new GsonBuilder()
                //.setDateFormat(context.getString(R.string.default_date_time_format))
                .create();
    }

    public static String doEncrypt(String value, String pubKey) {
        String result = null;
        try {
//            String hashString = doHash256Hex(value);
            final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // encrypt the plain text using the public key
            PublicKey publicKey = getPublicKey(pubKey);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            final byte[] cipherText = cipher.doFinal(value.getBytes("UTF-8"));
            //Set Base64.NO_WRAP - with intention to remove newline e.g : \n
            result = Base64.encodeToString(cipherText, Base64.NO_WRAP);
            result = removeSpecialChar(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /*public static String doPasswordEncrypt(String passwordValue)
    {
        try{
            byte[] encoded= passwordValue.getBytes(StandardCharsets.UTF_8);
            return Base64.encodeToString(encoded, Base64.DEFAULT);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return "NA";
        }

    }*/

    private static String removeSpecialChar(String value) {
        if (!TextUtils.isEmpty(value)) {
            //remove char with "="
            value = value.replaceAll("=", "");
            //replace char "+" to be "-"
            value = value.replace("+", "-");
            //replace char "/" to be "_"
            value = value.replace("/", "_");
        }
        return value;
    }

    private static PublicKey getPublicKey(String publicKey) {
        PublicKey pubKey = null;
        try {
            byte[] publicBytes = Base64.decode(publicKey, Base64.DEFAULT);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            pubKey = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pubKey;
    }

    public static OkHttpClient.Builder getUnsafeOkHttpBuilderClient()
    {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return builder;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
