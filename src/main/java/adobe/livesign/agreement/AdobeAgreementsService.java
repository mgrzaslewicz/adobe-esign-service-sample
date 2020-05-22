package adobe.livesign.agreement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AdobeAgreementsService {
    @POST("transientDocuments")
    Call<AdobeAgreementResponse> createAgreement(@Header("Authorization") String authorization, @Body AdobeAgreementRequest adobeAgreement);
}
