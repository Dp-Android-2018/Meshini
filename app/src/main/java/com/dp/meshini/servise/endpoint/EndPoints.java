package com.dp.meshini.servise.endpoint;

import com.dp.meshini.servise.model.request.ActivatePhoneRequest;
import com.dp.meshini.servise.model.request.ChangePasswordRequest;
import com.dp.meshini.servise.model.request.ClientRegisterRequest;
import com.dp.meshini.servise.model.request.ForgetPasswordRequest;
import com.dp.meshini.servise.model.request.LoginRequest;
import com.dp.meshini.servise.model.request.ResetPasswordRequest;
import com.dp.meshini.servise.model.request.SendActivationCodeRequest;
import com.dp.meshini.servise.model.response.ForgetPasswordResponse;
import com.dp.meshini.servise.model.response.LoginRegisterResponse;
import com.dp.meshini.servise.model.response.StringMessageResponse;
import com.dp.meshini.utils.ConstantsFile;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EndPoints {

    @POST(ConstantsFile.Urls.REGISTER_CLIENT_URL)
    Observable<Response<LoginRegisterResponse>>register(@Body ClientRegisterRequest request);

    @POST(ConstantsFile.Urls.LOGIN_URL)
    Observable<Response<LoginRegisterResponse>>login(@Body LoginRequest request);

    @POST(ConstantsFile.Urls.SEND_MAIL_ACTIVATION_URL)
    Observable<Response<StringMessageResponse>>activeMail();

    @POST(ConstantsFile.Urls.ACTIVE_PHONE_URL)
    Observable<Response<StringMessageResponse>> activatePhone(@Body ActivatePhoneRequest request);

    @POST(ConstantsFile.Urls.SEND_PHONE_ACTIVATION_CODE_URL)
    Observable<Response<StringMessageResponse>> sendActivationCode(@Body SendActivationCodeRequest request);

    @POST(ConstantsFile.Urls.FORGET_PASSWORD_URL)
    Observable<Response<ForgetPasswordResponse>> forgetPassword(@Body ForgetPasswordRequest request);

    @POST(ConstantsFile.Urls.RESET_PASSWORD_URL)
    Observable<Response<StringMessageResponse>> resetPassword(@Path("token") String token, @Body ResetPasswordRequest resetPasswordRequest);

    @POST(ConstantsFile.Urls.CHANGE_PASSWORD_URL)
    Observable<Response<StringMessageResponse>> changePassword(@Body ChangePasswordRequest request);









}
