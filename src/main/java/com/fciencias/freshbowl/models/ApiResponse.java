package com.fciencias.freshbowl.models;

public class ApiResponse {
    
    private boolean resultState;
    private String resultMessage;
    private Object result;

    
    public ApiResponse() {

        this.resultState = false;
    }

    public boolean isResultState() {
        return resultState;
    }
    public void setResultState(boolean resultState) {
        this.resultState = resultState;
    }
    public String getResultMessage() {
        return resultMessage;
    }
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
    public Object getResult() {
        return result;
    }
    public void setResult(Object result) {
        this.result = result;
    }

    
}
