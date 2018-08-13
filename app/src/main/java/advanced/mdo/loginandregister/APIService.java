package advanced.mdo.loginandregister;

import advanced.mdo.loginandregister.model.UploadPhoto;
import advanced.mdo.loginandregister.model.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {

    @POST("/posts")
    @FormUrlEncoded
    Call<User> saveUser(@Field("email") String email,
                        @Field("hashPassword")String pass,
                        @Field("fullname")String name,
                        @Field("height")int height,
                        @Field("zipcode")int zipcode,
                        @Field("year")int year,
                        @Field("gender")String gender,
                        @Field("ageTarget")int ageTarget,
                        @Field("race")String race,
                        @Field("genderInterested")String genderInterested,
                        @Field("UserPhotoUri")String UserPhotoUri
                        );
//    Call<User> saveUser(@Body User user);

    @Multipart
    @POST("/posts")
    Call<UploadPhoto> uploadFile(@Part MultipartBody.Part file,
                                 @Part("name")RequestBody name);
}