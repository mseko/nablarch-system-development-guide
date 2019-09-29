package com.nablarch.example.climan.common.jaxrs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nablarch.example.climan.common.exception.DuplicateRegistrationException;
import com.nablarch.example.climan.common.exception.SearchResultUpperLimitException;
import nablarch.common.dao.NoDataException;
import nablarch.core.message.ApplicationException;
import nablarch.core.message.MessageLevel;
import nablarch.core.message.MessageUtil;
import nablarch.core.validation.ValidationResultMessage;
import nablarch.fw.ExecutionContext;
import nablarch.fw.jaxrs.ErrorResponseBuilder;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link ErrorResponseBuilder}のカスタマイズを行うクラス。
 *
 * 共通で処理する例外を対象に共通エラー応答電文を返す。
 *
 * @author TIS
 */
public class ClimanErrorResponseBuilder extends ErrorResponseBuilder {

    @Override
    public HttpResponse build(HttpRequest request, ExecutionContext context, Throwable throwable) {
        if (throwable instanceof ApplicationException) {
            return creataHttpResponse((ApplicationException) throwable);
        } else if (throwable instanceof SearchResultUpperLimitException) {
            return createHttpResponse(
                    HttpResponse.Status.BAD_REQUEST, "FB1999902", "errors.upper.limit",
                    ((SearchResultUpperLimitException) throwable).getLimit());
        } else if (throwable instanceof NoDataException) {
            return createHttpResponse(
                    HttpResponse.Status.NOT_FOUND, "FB1999903", "errors.nothing");
        } else if (throwable instanceof DuplicateRegistrationException) {
            return createHttpResponse(
                    HttpResponse.Status.CONFLICT, "FB1999904", "errors.register.duplicate");
        }
        return super.build(request, context, throwable);
    }

    /**
     * 共通エラー応答電文のHTTPレスポンスを生成する。
     * @param e アプリケーション例外
     * @return 共通エラー応答電文のHTTPレスポンス
     */
    private HttpResponse creataHttpResponse(ApplicationException e) {
        List<String> messages = e.getMessages().stream().map(message -> {
                                            if (message instanceof ValidationResultMessage) {
                                                ValidationResultMessage vrm = (ValidationResultMessage) message;
                                                return vrm.getPropertyName() + ":" + vrm.formatMessage();
                                            }
                                            return message.formatMessage();
                                        }).collect(Collectors.toList());
        Error error = new Error("FB1999901", messages);
        return new HttpResponse().setStatusCode(HttpResponse.Status.BAD_REQUEST.getStatusCode())
                .write(writeToJson(error));
    }

    /**
     * 共通エラー応答電文のHTTPレスポンスを生成する。
     * @param status ステータスコード
     * @param faultCode 障害コード
     * @param messageId メッセージID
     * @param options メッセージの埋め込みオプション
     * @return 共通エラー応答電文のHTTPレスポンス
     */
    private HttpResponse createHttpResponse(
            HttpResponse.Status status, String faultCode, String messageId, Object... options) {
        String message = MessageUtil.createMessage(MessageLevel.ERROR, messageId, options).formatMessage();
        Error error = new Error(faultCode, message);
        return new HttpResponse().setStatusCode(status.getStatusCode())
                .write(writeToJson(error));
    }

    /**
     * オブジェクトからJSON文字列に書き出す。
     * @param object オブジェクト
     * @return JSON文字列
     */
    private String writeToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * エラー内容を保持するクラス。
     */
    public static class Error {

        /** 障害コード */
        private String faultCode;

        /** メッセージ */
        private List<String> messages;

        /**
         * エラー内容を生成する。
         * @param faultCode 障害コード
         * @param message メッセージ
         */
        public Error(String faultCode, String message) {
            this(faultCode, Arrays.asList(message));
        }

        /**
         * エラー内容を生成する。
         * @param faultCode 障害コード
         * @param messages メッセージ
         */
        public Error(String faultCode, List<String> messages) {
            this.faultCode = faultCode;
            this.messages = messages;
        }

        /**
         * 障害コードを返す。
         * @return 障害コード
         */
        public String getFaultCode() {
            return faultCode;
        }

        /**
         * メッセージを返す。
         * @return メッセージ
         */
        public List<String> getMessages() {
            return messages;
        }
    }
}
