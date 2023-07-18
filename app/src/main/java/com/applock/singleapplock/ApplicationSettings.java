package com.applock.singleapplock;

public class ApplicationSettings {
    public static final String ABOUT_US_FACEBOOK_URL = "https://www.facebook.com/totpauth/";
    public static final String ABOUT_US_INSTAGRAM_URL = "https://www.instagram.com/totpauth/";
    public static final String ABOUT_US_LINKEDIN_URL = "https://www.linkedin.com/company/binaryboot/";
    public static final String ABOUT_US_TWITTER_URL = "https://twitter.com/totpauth";
    public static final String ABOUT_US_URL = "https://www.binaryboot.com/";
    public static final String APPSTORE_LINK = "https://itunes.apple.com/us/app/totp-authenticator-pro-2fa/id1404230533?ls=1&mt=8 ";
    public static final String APP_LINK = "https://www.binaryboot.com/totp-authenticator/download";
    public static final String BACKUP_FORMAT = ".encrypt";
    public static final String BASIC_SETUP_GUIDE = "https://www.binaryboot.com/totp-authenticator/guide/2fa-basic";
    public static final String CHROME_SETUP_GUIDE = "https://www.binaryboot.com/totp-authenticator/guide/android/browser-extension";
    public static final String CLOUD_SETUP_GUIDE = "https://www.binaryboot.com/totp-authenticator/guide/android/cloud-sync";
    public static final String CURRENT_ACTIVE_IN_APP_PRODUCT = "https://www.binaryboot.com/totp-authenticator/android/get-premium-product-details/v1?auth_key=95117f5b-1fab-4894-b5d1-706d1474f863";
    public static final String[] DEFAULT_DRIVE_BACKUP_FILE_NAME = {"synced_backup_offline_copy.txt", "synced_backup_offline_key.encrypt"};
    public static final String[] DEFAULT_FILE_NAME = {"TOTP_Backup_"};
    public static final String[] DEFAULT_FOLDER_NAME = {"TotpAuthenticator"};
    public static final String[] DEFAULT_SHARE_FOLDER = {"TotpSharedBackup"};
    public static final String[] DEFAULT_SYNC_FOLDER = {"SyncedBackup"};
    public static String DRIVE_PASSWORD = null;
    public static final String ENTER_FINGERPRINT_DIALOG_FRAGMENT = "ENTER_FINGERPRINT_DIALOG_FRAGMENT";
    public static final String EXTENSION_DOWNLOAD_LINK = "https://www.binaryboot.com/totp-authenticator/extension/download";
    public static final String EXTENSION_SYNC_PURCHASE_ID = "authservice2.inapp.premium";
    public static final String FACEBOOK_SETTINGS_URI = "https://www.facebook.com/settings?tab=security";
    public static final String GITHUB_SETTINGS_URI = "https://github.com/settings/two_factor_authentication/intro";
    public static final String GOOGLE_SETTINGS_URI = "https://myaccount.google.com/security";
    public static final String MARKET_URL = "https://play.google.com/store/apps/details?id=com.authenticator.authservice&hl=en";
    public static final String MICROSOFT_SETTINGS_URI = "https://account.live.com/proofs/manage/additional?mkt=en-US&refd=account.microsoft.com&refp=security";
    public static final String[] NATIVE_LANGUAGE = {"English", "Français", "Deutsche", "हिंदी", "italiano", "Português", "русский", "Español"};
    public static final String NOTIFICATION_GROUP = "NOTIFICATION_PRIORITY";
    public static final String PAPER_DB_REF = "tempDB";
    public static final String[] PASSWORD;
    public static final String PLAYSTORE_LINK = "https://play.google.com/store/apps/details?id=com.authenticator.authservice2&hl=en";
    public static final String REPORT_LANG_ISSUE_URL = "https://www.binaryboot.com/totp-authenticator/translations/form";
    public static final String SHARE_TEXT = "Download the TOTP Authenticator app for making 2-factor authentication simple and easy.\n";
    public static final String SHOWCASE_ID = "TOTP_AUTH_SCS_07";
    public static final String[] SUPPORTED_LANGUAGE = {"English (default)", "French (Français)", "German (Deutsche)", "Hindi (हिंदी)", "Italian (italiano)", "Portugese (Português)", "Russian (русский)", "Spanish (Español)"};
    public static final String[] SUPPORTED_LANGUAGE_CODE = {"en", "fr", "de", "hi", "it", "pt", "ru", "es"};
    public static final int SYNCED_HISTORY_ACCOUNT_COUNT = 30;
    public static final int SYNCED_HISTORY_DAY_COUNT = 30;
    public static final String TUTORIAL_COMPLETE_DIALOG_FRAGMENT = "TUTORIAL_COMPLETE_DIALOG_FRAGMENT";
    public static final int VIEW_TYPE_HEADER = 1;
    public static final int VIEW_TYPE_ITEM = 0;

    public enum Themes {
        DEFAULT,
        DARK,
        LIGHT
    }

    static {
        String[] strArr = {"TotpAuthenticator"};
        PASSWORD = strArr;
        DRIVE_PASSWORD = strArr[0];
    }
}
