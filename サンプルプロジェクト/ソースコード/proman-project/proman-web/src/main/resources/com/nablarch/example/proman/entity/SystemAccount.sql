--------------------------------------------------------------------------------
-- 利用可能なシステムアカウント情報を取得するSQL
--------------------------------------------------------------------------------
FIND_SYSTEM_ACCOUNT =
SELECT
  *
FROM
  system_account
WHERE
  login_id = ?
  AND ? BETWEEN effective_date_from AND effective_date_to

--------------------------------------------------------------------------------
-- ログインIDを元にシステムアカウント情報を取得するSQL
--------------------------------------------------------------------------------
FIND_SYSTEM_ACCOUNT_BY_AK =
SELECT
  *
FROM
  system_account
WHERE
  login_id = ?
