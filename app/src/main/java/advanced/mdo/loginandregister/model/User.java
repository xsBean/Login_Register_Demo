package advanced.mdo.loginandregister.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class User implements Parcelable {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("hashPassword")
    @Expose
    private String hashPassword;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    @SerializedName("height")
    @Expose
    private int height;

    @SerializedName("zipcode")
    @Expose
    private int zipcode;

    @SerializedName("year")
    @Expose
    private int year;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("AgeTarget")
    @Expose
    private int ageTarget;

    @SerializedName("race")
    @Expose
    private String race;

    @SerializedName("GenderInterested")
    @Expose
    private String genderInterested;

    @SerializedName("UserPhotoUri")
    @Expose
    private String userPhotoUri;

    public User(String email, String password,String hashPassword, String fullname, int height, int zipcode, int year, String gender, int ageTarget, String race, String genderInterested, String userPhotoUri) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.height = height;
        this.zipcode = zipcode;
        this.year = year;
        this.gender = gender;
        this.ageTarget = ageTarget;
        this.race = race;
        this.genderInterested = genderInterested;
        this.userPhotoUri = userPhotoUri;
        this.hashPassword = hashPassword;
    }

    protected User(Parcel in) {
        email = in.readString();
        password = in.readString();
        hashPassword = in.readString();
        fullname = in.readString();
        height = in.readInt();
        zipcode = in.readInt();
        year = in.readInt();
        gender = in.readString();
        ageTarget = in.readInt();
        race = in.readString();
        genderInterested = in.readString();
        userPhotoUri = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(hashPassword);
        dest.writeString(fullname);
        dest.writeInt(height);
        dest.writeInt(zipcode);
        dest.writeInt(year);
        dest.writeString(gender);
        dest.writeInt(ageTarget);
        dest.writeString(race);
        dest.writeString(genderInterested);
        dest.writeString(userPhotoUri);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAgeTarget() {
        return ageTarget;
    }

    public void setAgeTarget(int ageTarget) {
        this.ageTarget = ageTarget;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getGenderInterested() {
        return genderInterested;
    }

    public void setGenderInterested(String genderInterested) {
        this.genderInterested = genderInterested;
    }

    public String getUserPhotoUri() {
        return userPhotoUri;
    }

    public void setUserPhotoUri(String userPhotoUri) {
        this.userPhotoUri = userPhotoUri;
    }

    public String getHashPassword() {
        return hashPassword;
    }
    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    @Override
    public String toString() {
        return "Post{"+
                "email='" + email+'\'' +
                ", password='" + hashPassword+'\''+
                ", name='" + fullname+'\''+
                ", height='" + height+'\''+
                ", zipcode='" + zipcode+'\''+
                ", year_of_birth='" + year+'\''+
                ", gender='" + gender+'\''+
                ", tager_age='" + ageTarget+'\''+
                ", race='" + race+'\''+
                ", gender_interested='" + genderInterested+'\''
                ;
    }
}
