package adobe.livesign.agreement;

import java.util.List;

public class AdobeAgreementRequest {
    AdobeAgreementRequest(List<AdobeAgreementParticipant> participantSetInfo, String name, String signatureType, String state, List<AdobeTransientDocument> fileInfos, AdobePostSignOption postSignOption, String message) {
        this.participantSetInfo = participantSetInfo;
        this.name = name;
        this.signatureType = signatureType;
        this.state = state;
        this.fileInfos = fileInfos;
        this.postSignOption = postSignOption;
        this.message = message;
    }

    public static AdobeAgreementRequestBuilder builder() {
        return new AdobeAgreementRequestBuilder();
    }

    public List<AdobeAgreementParticipant> getParticipantSetInfo() {
        return this.participantSetInfo;
    }

    public String getName() {
        return this.name;
    }

    public String getSignatureType() {
        return this.signatureType;
    }

    public String getState() {
        return this.state;
    }

    public List<AdobeTransientDocument> getFileInfos() {
        return this.fileInfos;
    }

    public AdobePostSignOption getPostSignOption() {
        return this.postSignOption;
    }

    public AdobeEmailOption getEmailOption() {
        return this.emailOption;
    }

    public String getMessage() {
        return this.message;
    }

    public static class AdobeAgreementParticipant {
        private final String role = "SIGNER";
        private final List<AdobeAgreementMember> memberInfos;
        private final int order;

        AdobeAgreementParticipant(List<AdobeAgreementMember> memberInfos, int order) {
            this.memberInfos = memberInfos;
            this.order = order;
        }

        public static AdobeAgreementParticipantBuilder builder() {
            return new AdobeAgreementParticipantBuilder();
        }

        public String getRole() {
            return this.role;
        }

        public List<AdobeAgreementMember> getMemberInfos() {
            return this.memberInfos;
        }

        public int getOrder() {
            return this.order;
        }

        public static class AdobeAgreementParticipantBuilder {
            private List<AdobeAgreementMember> memberInfos;
            private int order;

            AdobeAgreementParticipantBuilder() {
            }

            public AdobeAgreementParticipant.AdobeAgreementParticipantBuilder memberInfos(List<AdobeAgreementMember> memberInfos) {
                this.memberInfos = memberInfos;
                return this;
            }

            public AdobeAgreementParticipant.AdobeAgreementParticipantBuilder order(int order) {
                this.order = order;
                return this;
            }

            public AdobeAgreementParticipant build() {
                return new AdobeAgreementParticipant(memberInfos, order);
            }

            public String toString() {
                return "AdobeAgreementRequest.AdobeAgreementParticipant.AdobeAgreementParticipantBuilder(memberInfos=" + this.memberInfos + ", order=" + this.order + ")";
            }
        }
    }

    public static class AdobeAgreementMember {
        private final String email;

        public AdobeAgreementMember(String email) {
            this.email = email;
        }

        public String getEmail() {
            return this.email;
        }
    }


    public static class AdobeTransientDocument {
        private final String transientDocumentId;

        public AdobeTransientDocument(String transientDocumentId) {
            this.transientDocumentId = transientDocumentId;
        }

        public String getTransientDocumentId() {
            return this.transientDocumentId;
        }
    }

    public static class AdobePostSignOption {
        private final String redirectUrl;

        public AdobePostSignOption(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        public String getRedirectUrl() {
            return this.redirectUrl;
        }
    }

    public static class AdobeEmailOption {
        private final AdobeEmailSendOptions sendOptions;

        public AdobeEmailOption(AdobeEmailSendOptions sendOptions) {
            this.sendOptions = sendOptions;
        }

        public AdobeEmailSendOptions getSendOptions() {
            return this.sendOptions;
        }
    }

    public static class AdobeEmailSendOptions {
        private final String initEmails = "NONE";

        public String getInitEmails() {
            return this.initEmails;
        }
    }

    private final List<AdobeAgreementParticipant> participantSetInfo;
    private final String name;
    private final String signatureType;
    private final String state;
    private final List<AdobeTransientDocument> fileInfos;
    private final AdobePostSignOption postSignOption;
    private final AdobeEmailOption emailOption = new AdobeEmailOption(new AdobeEmailSendOptions());
    private final String message;

    public static class AdobeAgreementRequestBuilder {
        private List<AdobeAgreementParticipant> participantSetInfo;
        private String name;
        private String signatureType;
        private String state;
        private List<AdobeTransientDocument> fileInfos;
        private AdobePostSignOption postSignOption;
        private String message;

        AdobeAgreementRequestBuilder() {
        }

        public AdobeAgreementRequest.AdobeAgreementRequestBuilder participantSetInfo(List<AdobeAgreementParticipant> participantSetInfo) {
            this.participantSetInfo = participantSetInfo;
            return this;
        }

        public AdobeAgreementRequest.AdobeAgreementRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AdobeAgreementRequest.AdobeAgreementRequestBuilder signatureType(String signatureType) {
            this.signatureType = signatureType;
            return this;
        }

        public AdobeAgreementRequest.AdobeAgreementRequestBuilder state(String state) {
            this.state = state;
            return this;
        }

        public AdobeAgreementRequest.AdobeAgreementRequestBuilder fileInfos(List<AdobeTransientDocument> fileInfos) {
            this.fileInfos = fileInfos;
            return this;
        }

        public AdobeAgreementRequest.AdobeAgreementRequestBuilder postSignOption(AdobePostSignOption postSignOption) {
            this.postSignOption = postSignOption;
            return this;
        }

        public AdobeAgreementRequest.AdobeAgreementRequestBuilder message(String message) {
            this.message = message;
            return this;
        }

        public AdobeAgreementRequest build() {
            return new AdobeAgreementRequest(participantSetInfo, name, signatureType, state, fileInfos, postSignOption, message);
        }

        public String toString() {
            return "AdobeAgreementRequest.AdobeAgreementRequestBuilder(participantSetInfo=" + this.participantSetInfo + ", name=" + this.name + ", signatureType=" + this.signatureType + ", state=" + this.state + ", fileInfos=" + this.fileInfos + ", postSignOption=" + this.postSignOption + ", message=" + this.message + ")";
        }
    }
}



