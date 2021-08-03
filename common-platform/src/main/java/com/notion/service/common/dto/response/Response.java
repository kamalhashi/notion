package com.notion.service.common.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.notion.service.common.enums.ResponseStatus;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private ResponseStatus status;
    private String message;
    private T data;
    private String errorCode;
    private Long totalCount;

    @JsonIgnore
    private HttpStatus httpStatus;

    public boolean success() {
        return Objects.nonNull(this.status) && this.status == ResponseStatus.SUCCESS;
    }

    public Response(ResponseStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Response(ResponseStatus status, String message, T data, Long totalCount) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.totalCount = totalCount;
    }

    public boolean hasData() {
        return Objects.nonNull(this.data);
    }

    public static <T> ResponseBuilder<T> builder() {
        return new ResponseBuilder();
    }

    public ResponseStatus getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    @JsonIgnore
    public HttpStatus getHttpStatus() {return this.httpStatus;}

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Response)) {
            return false;
        } else {
            Response<?> other = (Response)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$status = this.getStatus();
                    Object other$status = other.getStatus();
                    if (this$status == null) {
                        if (other$status == null) {
                            break label59;
                        }
                    } else if (this$status.equals(other$status)) {
                        break label59;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                Object this$errorCode = this.getErrorCode();
                Object other$errorCode = other.getErrorCode();
                if (this$errorCode == null) {
                    if (other$errorCode != null) {
                        return false;
                    }
                } else if (!this$errorCode.equals(other$errorCode)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Response;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        Object $errorCode = this.getErrorCode();
        result = result * 59 + ($errorCode == null ? 43 : $errorCode.hashCode());
        return result;
    }

    public String toString() {
        return "Response(status=" + this.getStatus() + ", message=" + this.getMessage() + ", data=" + this.getData() + ", errorCode=" + this.getErrorCode() + ")";
    }

    public Response() {
    }

    public Response(ResponseStatus status, String message, T data, String errorCode) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
    }

    public Response(ResponseStatus status, String message, T data, String errorCode, Long totalCount) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
        this.totalCount = totalCount;
    }

    public static class ResponseBuilder<T> {
        private ResponseStatus status;
        private String message;
        private T data;
        private String errorCode;
        private Long totalCount;

        ResponseBuilder() {
        }

        public ResponseBuilder<T> status(ResponseStatus status) {
            this.status = status;
            return this;
        }

        public ResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResponseBuilder<T> totalCount(Long totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        public ResponseBuilder<T> errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Response<T> build() {
            return new Response(this.status, this.message, this.data, this.errorCode, this.totalCount);
        }

        public String toString() {
            return "Response.ResponseBuilder(status=" + this.status + ", message=" + this.message + ", data=" + this.data + ", errorCode=" + this.errorCode + ")";
        }
    }

    public Response(Map<String, Object> map ) {
        this.status = (ResponseStatus) map.get(Fields.status.toString());
        this.message = map.get(Fields.message.toString()).toString();
        this.data = map.containsKey(Fields.data.toString()) ? (T) map.get(Fields.data.toString()) : null;
        this.errorCode = map.containsKey(Fields.errorCode.toString()) ? (String) map.get(Fields.errorCode.toString()) : null;
        this.httpStatus = map.containsKey(Fields.httpStatus.toString()) ? (HttpStatus) map.get(Fields.httpStatus.toString()) : null;
    }

    public static enum Fields {
        status, message, data, errorCode, httpStatus
    }
}
