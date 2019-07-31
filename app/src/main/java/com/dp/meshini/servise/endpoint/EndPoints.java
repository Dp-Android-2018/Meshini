package com.dp.meshini.servise.endpoint;

import com.dp.meshini.servise.model.request.AcceptOfferRequest;
import com.dp.meshini.servise.model.request.ActivatePhoneRequest;
import com.dp.meshini.servise.model.request.ChangeLanguageRequest;
import com.dp.meshini.servise.model.request.ChangePasswordRequest;
import com.dp.meshini.servise.model.request.CreateCommentRequest;
import com.dp.meshini.servise.model.request.CreateTripRequest;
import com.dp.meshini.servise.model.request.GetPackagesRequest;
import com.dp.meshini.servise.model.request.NotificationRequest;
import com.dp.meshini.servise.model.request.RegisterRequest;
import com.dp.meshini.servise.model.request.ForgetPasswordRequest;
import com.dp.meshini.servise.model.request.LoginRequest;
import com.dp.meshini.servise.model.request.ResetPasswordRequest;
import com.dp.meshini.servise.model.request.SendActivationCodeRequest;
import com.dp.meshini.servise.model.request.UpdateProfileRequest;
import com.dp.meshini.servise.model.response.ActiveTripDetailResponse;
import com.dp.meshini.servise.model.response.ActiveTripResponse;
import com.dp.meshini.servise.model.response.CountryCityResponse;
import com.dp.meshini.servise.model.response.CreateTripResponse;
import com.dp.meshini.servise.model.response.ForgetPasswordResponse;
import com.dp.meshini.servise.model.response.LoginRegisterResponse;
import com.dp.meshini.servise.model.response.OffersResponse;
import com.dp.meshini.servise.model.response.PackageDetailResponse;
import com.dp.meshini.servise.model.response.PackagesResponse;
import com.dp.meshini.servise.model.response.PastUpcomingRequestsResponse;
import com.dp.meshini.servise.model.response.PendingRequestsResponse;
import com.dp.meshini.servise.model.response.PlacesResponse;
import com.dp.meshini.servise.model.response.StringMessageResponse;
import com.dp.meshini.servise.model.response.TripDetailResponse;
import com.dp.meshini.utils.ConstantsFile;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.dp.meshini.utils.ConstantsFile.Urls.CHANGE_LANGUAGE_URL;
import static com.dp.meshini.utils.ConstantsFile.Urls.CREATE_COMMENT_URL;
import static com.dp.meshini.utils.ConstantsFile.Urls.GET_ALL_PACKAGES_URL;
import static com.dp.meshini.utils.ConstantsFile.Urls.PACKAGE_DETAIL_URL;

public interface EndPoints {

    @POST(ConstantsFile.Urls.REGISTER_CLIENT_URL)
    Observable<Response<LoginRegisterResponse>>register(@Body RegisterRequest request);

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


    @GET(ConstantsFile.Urls.COUNTRY_URL)
    Observable<Response<CountryCityResponse>>getCountries();

    @GET(ConstantsFile.Urls.TCOUNTRY_URL)
    Observable<Response<CountryCityResponse>>getTCountries();

    @GET(ConstantsFile.Urls.LANGUAGES_URL)
    Observable<Response<CountryCityResponse>>getLanguages();


    @GET(ConstantsFile.Urls.CITY_URL)
    Observable<Response<CountryCityResponse>>getCities(@Query("world_country_id")int id);


    @PUT(ConstantsFile.Urls.EDIT_PROFILE_URL)
    Observable<Response<StringMessageResponse>>editProfile(@Body UpdateProfileRequest request);

    @POST(ConstantsFile.Urls.CREATE_TRIP_URL)
    Observable<Response<CreateTripResponse>>createTrip(@Body CreateTripRequest request);

    @GET(ConstantsFile.Urls.PLACES_URL)
    Observable<Response<PlacesResponse>>getPlaces(@Query("country") int id);

    @GET(ConstantsFile.Urls.OFFERS_URL)
    Observable<Response<OffersResponse>>getOffers(@Path("id") int tripId,@Query("page")int page);

    @GET(ConstantsFile.Urls.PENDING_REQUESTS_URL)
    Observable<Response<PendingRequestsResponse>>getPendingRequests(@Query("page")int page);

    @POST(ConstantsFile.Urls.ACCEPT_OFFER_URL)
    Observable<Response<StringMessageResponse>>acceptOffer(@Body AcceptOfferRequest request);

    @GET(ConstantsFile.Urls.REQUEST_DETAIL_URL)
    Observable<Response<TripDetailResponse>>getTripDetail(@Path("request_id") int id);

    @DELETE(ConstantsFile.Urls.DELETE_REQUEST_URL)
    Observable<Response<StringMessageResponse>>deleteRequest(@Path("request_id")int requestId);

    @GET(ConstantsFile.Urls.PAST_REQUESTS_URL)
    Observable<Response<PastUpcomingRequestsResponse>>getPastRequests(@Query("page")int page);

    @GET(ConstantsFile.Urls.UPCOMING_REQUESTS_URL)
    Observable<Response<PastUpcomingRequestsResponse>>getUpcomingRequests(@Query("page")int page);

    @GET(ConstantsFile.Urls.GET_ACTIVE_TRIP_URL)
    Observable<Response<ActiveTripResponse>>getActiveTrip();

    @GET(ConstantsFile.Urls.ACTIVE_TRIP_DETAIL)
    Observable<Response<ActiveTripDetailResponse>>activeTripDetail();

    @POST(CREATE_COMMENT_URL)
    Observable<Response<StringMessageResponse>>createComment(@Body CreateCommentRequest request,@Path("request") int requestId);

    @POST(CHANGE_LANGUAGE_URL)
    Observable<Response<StringMessageResponse>>changeLanguage(@Body ChangeLanguageRequest request);

    //Notify the serviceprovider (new message)
    @POST("/api/client/request/chat/notify")
    Observable<Response<Void>> sendNotification(@Body NotificationRequest notificationRequest);

    @GET(GET_ALL_PACKAGES_URL)
    Observable<Response<PackagesResponse>>getPackages(@Query("country_id")int id,@Query("start_date")String startDate,@Query("city_id")String cityId,@Query("seats_number")int noSeats,@Query("page")int page);

    @GET(PACKAGE_DETAIL_URL)
    Observable<Response<PackageDetailResponse>>packageDetail(@Path("package")int packageID);
}
