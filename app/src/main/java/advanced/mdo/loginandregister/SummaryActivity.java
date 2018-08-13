package advanced.mdo.loginandregister;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import java.util.Objects;
import advanced.mdo.loginandregister.databinding.ActivitySummaryBinding;
import advanced.mdo.loginandregister.model.UploadPhoto;
import advanced.mdo.loginandregister.model.User;
import advanced.mdo.loginandregister.utils.ApiUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryActivity extends AppCompatActivity {
    private static final String LOG_TAG = SummaryActivity.class.getSimpleName();
    private final String USER_INFO = "user_info";
    private ActivitySummaryBinding binding;


    User user;
    private APIService myAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_summary);

        Intent i = getIntent();
        user = i.getParcelableExtra(USER_INFO);
        getDataFromIntent();


    }
    private void getDataFromIntent(){
        if(user == null) return;
        binding.includedSummary.ivSummaryUser.setImageURI(Uri.parse(user.getUserPhotoUri()));
        binding.includedSummary.etSummaryEmail.setText(user.getEmail());

        binding.includedSummary.etSummaryPassword.setText("**********");
        binding.includedSummary.etSummaryPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.includedSummary.etSummaryPassword.setText(user.getPassword()); }
        });

        binding.includedSummary.etHashPassword.setText(user.getHashPassword());
        binding.includedSummary.etSummaryName.setText(user.getFullname());
        binding.includedSummary.etSummaryHeight.setText(String.valueOf(user.getHeight()));
        binding.includedSummary.etSummaryZipcode.setText(String.valueOf(user.getZipcode()));
        binding.includedSummary.tvSummaryDobUser.setText(String.valueOf(user.getYear()));
        binding.includedSummary.spSummaryGender.setText(user.getGender());
        binding.includedSummary.spSummaryAgeTarget.setText(String.valueOf(user.getAgeTarget()));
        binding.includedSummary.tvSummaryRace.setText(user.getRace());
        binding.includedSummary.spSummaryInterestedGender.setText(user.getGenderInterested());

        myAPIService = ApiUtils.getAPIService();

        binding.includedSummary.btnSummaryUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Upload data
                uploadDataToAPI(user);

                // Upload photo
                uploadPhotoToServer(user.getUserPhotoUri());
                Toast.makeText(getApplication(),"Uploading....!",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void uploadDataToAPI(User user){

        myAPIService.saveUser(user.getEmail(),user.getHashPassword(),user.getFullname(),
                user.getHeight(), user.getZipcode(),user.getYear(),user.getGender(),
                user.getAgeTarget(),user.getRace(),user.getGenderInterested(),
                user.getUserPhotoUri())
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            showResponse(response.body().toString());
                            Log.i(LOG_TAG, " post submitted to API." + response.body().toString());
                        }
                        Log.v(LOG_TAG,response.toString());
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e(LOG_TAG, "Unable to submit");
                        Toast.makeText(getApplication(),"Submitted is fail",Toast.LENGTH_LONG ).show();
                    }
                });

    }
    private void showResponse(String response){
        Toast.makeText(getApplication(),"Submitted Successfully",Toast.LENGTH_LONG).show();
    }

    private void uploadPhotoToServer(String uri){
        File file = new File(uri);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part fileToUpLoad = MultipartBody.Part.createFormData("file",file.getName(),mFile);
        RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"),file.getName());
        myAPIService.uploadFile(fileToUpLoad,fileName).enqueue(new Callback<UploadPhoto>() {
            @Override
            public void onResponse(Call<UploadPhoto> call, Response<UploadPhoto> response) {
                showResponse(Objects.requireNonNull(response.body()).toString());
                Log.i(LOG_TAG, " post submitted to API." + Objects.requireNonNull(response.body()).toString());
            }

            @Override
            public void onFailure(Call<UploadPhoto> call, Throwable t) {
                Log.e(LOG_TAG, "Unable to submit");
                Toast.makeText(getApplication(),"Submitted is fail",Toast.LENGTH_LONG ).show();
            }
        });
    }

}
