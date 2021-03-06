package stuba.fei.gono.java.blocking.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorResponse {
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ErrorResponse(String error) {
        this.error = error;
    }


}
