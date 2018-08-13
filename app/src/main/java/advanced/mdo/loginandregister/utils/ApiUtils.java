package advanced.mdo.loginandregister.utils;

import advanced.mdo.loginandregister.APIService;
import advanced.mdo.loginandregister.RetrofitClient;

public class ApiUtils {

    private ApiUtils(){}
    public static final String BASE_URL = "https://external.dev.pheramor.com";
    public static APIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

}
