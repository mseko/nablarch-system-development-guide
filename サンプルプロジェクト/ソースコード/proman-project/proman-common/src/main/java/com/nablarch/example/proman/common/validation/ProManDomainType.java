package com.nablarch.example.proman.common.validation;

import nablarch.common.code.validator.ee.CodeValue;
import nablarch.core.validation.ee.Digits;
import nablarch.core.validation.ee.Length;
import nablarch.core.validation.ee.NumberRange;
import nablarch.core.validation.ee.SystemChar;

/**
 * ドメイン定義を表す列挙型。
 *
 * @author Masaya Seko
 */
@SuppressWarnings("all")
public class ProManDomainType {

    /**
     * ID
     */
    @Digits(integer = 9)
    private String id;

    /**
     * 組織ID
     */
    @Digits(integer = 4)
    private String organizationId;

    /**
     * ログインID
     */
    @Length(max = 20)
    @SystemChar(charsetDef = "半角数字")
    private String loginId;

    /**
     * パスワード
     */
    @Length(max = 44)
    @SystemChar(charsetDef = "ASCII文字")
    private String password;

    /**
     * プロジェクトID
     */
    @Length(max = 20)
    @SystemChar(charsetDef = "半角英数")
    private String projectId;

    /**
     * プロジェクト名
     */
    @Length(max = 128, message = "{domainType.projectName.message}")
    @SystemChar(charsetDef = "全角文字", message = "{domainType.projectName.message}")
    private String projectName;

    /**
     * PJ種別コード値
     */
    @CodeValue(codeId = "C0300001", pattern = "PATTERN01")
    private String projectType;

    /**
     * PJ分類コード値
     */
    @CodeValue(codeId = "C0200001", pattern = "PATTERN01")
    private String projectClass;

    /**
     * 日付
     */
    @YYYYMMDD()
    private String date;

    /**
     * ユーザ氏名（漢字）
     */
    @Length(max = 128, message = "{domainType.userName.message}")
    @SystemChar(charsetDef = "全角文字", message = "{domainType.userName.message}")
    private String userName;

    /**
     * 備考
     */
    @Length(max = 512, message = "{domainType.note.message}")
    @SystemChar(charsetDef = "システム許容文字", allowLineSeparator = true, message = "{domainType.note.message}")
    private String note;

    /**
     * 金額
     */
    @MoneyRange(max = 999999999)
    private String amountOfMoney;

    /**
     * バージョン番号
     */
    @Digits(integer = 4)
    private String version;

    /**
     * ページ番号
     */
    @NumberRange(min = 1, max = 9999)
    private String pageNumber;
}
