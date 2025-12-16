package ec.edu.monster.models;

public class ErrorViewModel {
    private String requestId;

    // Getters and Setters
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    // Computed property
    public boolean isShowRequestId() {
        return requestId != null && !requestId.isEmpty();
    }
}