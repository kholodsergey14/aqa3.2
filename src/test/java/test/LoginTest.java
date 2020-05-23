package test;

import lombok.val;
import org.junit.jupiter.api.*;
import data.DataHelper;
import page.DashboardPage;
import page.LoginPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

public class LoginTest {
    @AfterEach
    @DisplayName("Should clean Data Base after login")
    void cleanBase() throws SQLException {
        val dataHelperPage = new DataHelper();
        dataHelperPage.cleanDataBase();
    }

    @BeforeEach
    void login() {
        val loginPage = new LoginPage();
        loginPage.openLoginPage();
    }


    @Test
    @DisplayName("Should login with code from sms")
    void shouldLoginWithSmsCode() throws SQLException {
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPage();
        val verificationCode = DataHelper.getVerificationCode();
        val dashboardPage = verificationPage.validVerify(verificationCode.getCode());
        dashboardPage.verifyDashboardPageVisiblity();
    }

    @Test
    @DisplayName("Should be error notification if login with wrong password")
    void loginWithWrongPassword() {
        val authInfo = DataHelper.getAuthInfoWithWrongPassword();
        loginPage.validLogin(authInfo);
        loginPage.errorNotificationCreate();
    }

    @Test
    @DisplayName("Should login with random user and status active")
    void shouldLoginWithRandomUser() throws SQLException {
        val authInfo = DataHelper.getRandomAuthInfo("active");
        val verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPage();
        val verificationCode = DataHelper.getVerificationCode();
        val dashboardPage = verificationPage.validVerify(verificationCode.getCode());
        dashboardPage.verifyDashboardPageVisiblity();
    }
}