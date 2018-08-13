package advanced.mdo.loginandregister;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import advanced.mdo.loginandregister.model.User;
import advanced.mdo.loginandregister.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG =RegisterActivity.class.getSimpleName() ;
    private final String USER_INFO = "user_info";
    private ActivityRegisterBinding binding;

    String email;
    String password;
    String hashPassword;
    String name;
    String heightString;
    String zipcodeString;
    String dob;
    String gender;
    String ageTarget;
    String interestedGender;
    String race;
    boolean checkInput;
    String imageUri;
    User user;

//    public final static int CAPTURE_IMAGE_ACTIVITY_RESQUEST_CODE = 1001;
    public final static int PICK_IMAGE = 1000;
    public String photoFileName = "photo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);

        binding.includedRegister.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                checkInput = validateInput();
                if(checkInput){
                    user = new User(email,password,hashPassword,name,
                            Integer.parseInt(heightString),Integer.parseInt(zipcodeString),
                            Integer.parseInt(dob),gender,Integer.parseInt(ageTarget),
                            race,interestedGender, imageUri);
                    Intent intent = new Intent(getApplication(), SummaryActivity.class);
                    intent.putExtra(USER_INFO, user);
                    startActivity(intent);
                }
            }
        });
        binding.includedRegister.btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

            }
        });
    }
    private void getData() {
        email = binding.includedRegister.etEmail.getText().toString().trim();
        password = binding.includedRegister.etPassword.getText().toString().trim();
        hashPassword = hashPasswordToHex(password);
        name = binding.includedRegister.etName.getText().toString().trim();
        heightString = binding.includedRegister.etHeight.getText().toString();
        zipcodeString = binding.includedRegister.etZipcode.getText().toString();
        dob = String.valueOf(binding.includedRegister.spDobUser.getSelectedItem());
        gender = String.valueOf(binding.includedRegister.spGender.getSelectedItem());
        ageTarget = String.valueOf(binding.includedRegister.spAgeTarget.getSelectedItem());
        interestedGender = String.valueOf(binding.includedRegister.spInterestedGender.getSelectedItem());
        race = String.valueOf(binding.includedRegister.spRace.getSelectedItem());
        imageUri = binding.includedRegister.tvLinkYourPhoto.getText().toString();
    }



    private void openGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,PICK_IMAGE);
    }

    // When closing gallery or the camera app finishes, the onActivityResult() method will be called
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = Objects.requireNonNull(data.getData()).toString();
            TextView temp = findViewById(R.id.tv_link_your_photo);
            temp.setText(imageUri);
//            binding.includedRegister.tvLinkYourPhoto.setText(imageUri);
        }
        // Get photo from camera
//        if(requestCode == CAPTURE_IMAGE_ACTIVITY_RESQUEST_CODE){
//            if(requestCode == RESULT_OK){
//                userPhoto = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
//                Log.v(APP_TAG,photoFile.getAbsolutePath());
//                //can resize bitmap here
//                takePhotoButton.setText(photoFile.getName());
//
//            }else {
//                makeToast("Picture wasn't taken.");
//            }
//        }

    }
    private boolean validateInput(){
        if(email.equals("")){
            makeToast("Email cannot be null!");
            return false;
        }
        if(!isValidEmailAddress(email)){
//            makeToast("Invalid Email!");
            return false;
        }
        if(password.equals("")){
            makeToast("Password cannot be empty!");
            return false;
        }
        if(name.equals("")){
            makeToast("Name cannot be empty!");
            return false;
        }
        if(heightString.equals("")){
            makeToast("Height cannot be empty!");
            return false;
        }else{
            try {
                int height = Integer.parseInt(heightString);
            }catch (Exception e){
                makeToast("Invalid height!");
                return false;
            }
        }
        if(zipcodeString.equals("")){
            makeToast("ZipCode cannot be empty!");
            return false;
        }else{
            try {
                if(zipcodeString.length() != 5) {makeToast("Invalid Zipcode"); return false;}
                int zipcode = Integer.parseInt(heightString);
            }catch (Exception e){
                makeToast("Invalid Zipcode!");
                return false;
            }
        }
        return true;
    }
    private boolean isValidEmailAddress(String email){
        boolean result = true;
        try{
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException e) {
            Log.v(LOG_TAG,e.getMessage());
            makeToast("Email: "+ e.getMessage());
//            e.printStackTrace();
            result = false;
        }
        return result;
    }
    private void makeToast(String message){
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }
    private static String hashPasswordToHex(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte anEncodedhash : encodedhash) {
            String hex = Integer.toHexString(0xff & anEncodedhash);
            if (hexString.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }
//    public void onLaunchCamera(View view){
//        //Create intent to take a picture and return control to the calling application
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        //Create a file reference to access to future access
//        photoFile = getPhotoFileUri(photoFileName);
//        //Wrap File object into a content provider
////        Uri fileProvider = FileProvider.getUriForFile(RegisterActivity.this,"advanced.mdo.loginandregister",photoFile);
////        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
//
//        if(intent.resolveActivity(getPackageManager()) != null){
////            startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_RESQUEST_CODE);
//        }
//    }

//    public File getPhotoFileUri(String fileName){
//        //Get safe storage directory for photos
//        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),APP_TAG);
//        if(!mediaStorageDir.exists() && !mediaStorageDir.mkdir()){
//            Log.d(APP_TAG, " failed to create directory.");
//        }
//        return new File(mediaStorageDir.getPath() + File.separator+fileName);
//    }
}
