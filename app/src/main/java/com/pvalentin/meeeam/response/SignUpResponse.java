package com.pvalentin.meeeam.response;

public class SignUpResponse extends RetrofitResponse {

    public SignUpResponse(String status, String message, String data, String token) {
        super(status, message, data, token);
    }

}