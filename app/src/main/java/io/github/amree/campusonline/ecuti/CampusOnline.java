package io.github.amree.campusonline.ecuti;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CampusOnline {

    String email;
    String password;

    Map<String, String> cookies;
    public static Map<String, String> applications;

    Document doc;
    Connection.Response res;

    String coMainURL        = "http://campusonline.usm.my/smus/employee_i.asp?m=1&c=1";
    String campusOnlineURL  = "https://campusonline.usm.my";
    String idMainURL        = "https://id.usm.my/";
    String realCutiURL      = "http://e-cuti.usm.my/ecuti_v2/default.asp";
    String sahCutiURL       = "http://e-cuti.usm.my/ecuti_v2/default.asp?sec=P&fn=1";
    String mainCutiURL      = "";
    String loginURL         = "";
    String loginProcURL     = "";
    String processURL       = "";

    public CampusOnline(String email, String password) {
        this.email = email;
        this.password = password;

        this.cookies = new HashMap();
        this.applications = new HashMap();
    }

    public void gotoLogin() throws IOException {
        this.res = Jsoup.connect(campusOnlineURL)
                .timeout(0)
                .followRedirects(false)
                .method(Method.GET)
                .execute();

        // https://campusonline.usm.my
        System.out.println("Current URL: " + this.res.url());

        setCookies();

        this.doc = this.res.parse();

        this.loginURL = doc.select("a[href]").get(0).attr("abs:href");

        this.res = Jsoup.connect(this.loginURL)
                .timeout(0)
                .cookies(cookies)
                .followRedirects(false)
                .method(Method.GET)
                .execute();

        // https://id.usm.my/login.aspx?p=abc
        System.out.println("Current URL: " + res.url());

        setCookies();

        this.doc = this.res.parse();
    }

    public void doLogin() throws IOException {
        // We're in the page with the login form. Extract required forms
        Elements forms = this.doc.select("#MainForm input");

        Map<String, String> formValues = new HashMap();

        for (Element form : forms) {
            formValues.put(form.attr("name"), form.val());
        }

        // Set the credentials
        formValues.put("txtAccountID", this.email);
        formValues.put("txtPassword", this.password);

        this.loginProcURL = this.doc.select("form").attr("action");

        this.res = Jsoup.connect(loginURL)
                .timeout(0)
                .followRedirects(false)
                .cookies(cookies)
                .data(formValues)
                .method(Connection.Method.POST)
                .execute();

        System.out.println("Current URL: " + this.res.url());

        setCookies();

        this.doc = this.res.parse();
        this.processURL = this.doc.select("a[href]").get(0).attr("abs:href");

        this.res = Jsoup.connect(this.processURL)
                .followRedirects(false)
                .cookies(cookies)
                .timeout(0)
                .method(Method.GET)
                .execute();

        setCookies();

        // Now, we enter the real CampusOnline page
        // Had to manually put the URL (absolute vs relative)
        this.res = Jsoup.connect(coMainURL)
                .followRedirects(false)
                .cookies(cookies)
                .timeout(0)
                .method(Method.GET)
                .execute();

        // http://campusonline.usm.my/smus/employee_i.asp?m=1&c=1
        System.out.println("Current URL: " + res.url());

        setCookies();

        this.doc = this.res.parse();
    }

    public void gotoCuti() throws IOException {

        // We're in! Get the e-Cuti URL
        Element link = this.doc.select("a[href*=/ecuti_v2/]").get(0);

        this.mainCutiURL = link.attr("href");

        System.out.println("Cuti URL: " + this.mainCutiURL);

        // Open the e-Cuti main page
        this.res = Jsoup.connect(this.mainCutiURL)
                .followRedirects(false)
                .cookies(cookies)
                .timeout(0)
                .method(Method.GET)
                .execute();

        System.out.println("Current URL: " + this.res.url());

        setCookies();

        this.doc = this.res.parse();

        String url = doc.select("a[href]").get(0).attr("abs:href");

        this.res = Jsoup.connect(url)
                .timeout(0)
                .cookies(cookies)
                .followRedirects(false)
                .method(Method.GET)
                .execute();

        // https://id.usm.my/rlogin.aspx?p=abc
        System.out.println("Current URL: " + res.url());

        setCookies();

        this.doc = this.res.parse();

        url = this.doc.select("a[href]").get(0).attr("abs:href");

        this.res = Jsoup.connect(url)
                .timeout(0)
                .cookies(cookies)
                .followRedirects(false)
                .method(Method.GET)
                .execute();

        // http://e-cuti.usm.my/ecuti_v2/process.asp?p=abc
        System.out.println("Current URL: " + res.url());

        setCookies();

        this.res = Jsoup.connect(realCutiURL)
                .timeout(0)
                .cookies(cookies)
                .followRedirects(false)
                .method(Method.GET)
                .execute();

        // http://e-cuti.usm.my/ecuti_v2/default.asp
        System.out.println("Current URL: " + res.url());

        setCookies();

        this.doc = this.res.parse();
    }

    public void gotoSahCuti() throws IOException {
        this.res = Jsoup.connect(sahCutiURL)
                .timeout(0)
                .cookies(cookies)
                .followRedirects(false)
                .method(Method.GET)
                .execute();

        System.out.println("Current URL: " + res.url());

        setCookies();

        this.doc = this.res.parse();
    }

    private void setCookies() {
        // Get the cookies
        for (String key : this.res.cookies().keySet()) {
            String value = this.res.cookies().get(key);

            this.cookies.put(key, value);
        }

        System.out.println("All cookies (SERVER): " + this.res.cookies());
        System.out.println("All cookies (STORED): " + this.cookies);
    }

    public void setApplications() {
        Elements elements = this.doc.select("table.contacts a");

        for (Element element: elements) {
            this.applications.put(element.text(), element.attr("href"));
        }

        System.out.println(this.applications);
    }

    public Map<String, String> getApplications() {
        return this.applications;
    }
}
