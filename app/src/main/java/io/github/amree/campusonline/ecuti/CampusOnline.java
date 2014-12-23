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
    public static String[][] applications;

    Document doc;
    Connection.Response res;

    String coMainURL        = "http://campusonline.usm.my/smus/employee_i.asp?m=1&c=1";
    String campusOnlineURL  = "https://campusonline.usm.my";
    String idMainURL        = "https://id.usm.my/";
    String cutiURL          = "http://e-cuti.usm.my/ecuti_v2/";
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

        // Remember to skip the first row (first row == header)
        //
        // 0 = Status
        // 1 = URL
        // 2 = Name
        // 3 = Type
        // 4 = Date and Time
        Elements trElements = this.doc.select("table.contacts tr");
        CampusOnline.applications = new String[trElements.size() - 1][5];

        for (int x = 1; x < trElements.size(); x++) {
            System.out.println(x + "-----");

            Elements tdElements = trElements.get(x).select("td");
            for (int y = 0; y < tdElements.size(); y++) {

                switch (y) {
                    case 1:
                        // Status
                        String imageFile = tdElements.get(y).select("img").attr("src");

                        String status;
                        switch (imageFile) {
                            case "img/0.gif":
                                status = "KERANI";
                                break;
                            case "img/1.gif":
                                status = "PENYELIA";
                                break;
                            case "img/4.gif":
                                status = "SEMAKAN";
                                break;
                            default:
                                status = "LAIN-LAIN";
                        }

                        CampusOnline.applications[x - 1][0] = status;

                        break;
                    case 2:
                        // Link
                        CampusOnline.applications[x - 1][1] = this.cutiURL + tdElements.get(y).select("a").attr("href");
                        // Nama
                        CampusOnline.applications[x - 1][2] = tdElements.get(y).text();
                        break;
                    case 3:
                        // Jenis
                        CampusOnline.applications[x - 1][3] = tdElements.get(y).text();
                        break;
                    case 4:
                        // Tarikh dan masa
                        CampusOnline.applications[x - 1][4] = tdElements.get(y).text();
                        break;
                }
            }
        }

        for (int i = 0; i < CampusOnline.applications.length; i++) {
            System.out.println(i + "," + 0 + " -- " + CampusOnline.applications[i][0]);
            System.out.println(i + "," + 1 + " -- " + CampusOnline.applications[i][1]);
            System.out.println(i + "," + 2 + " -- " + CampusOnline.applications[i][2]);
            System.out.println(i + "," + 3 + " -- " + CampusOnline.applications[i][3]);
            System.out.println(i + "," + 4 + " -- " + CampusOnline.applications[i][4]);
        }
    }

    public String[][] getApplications() {
        return CampusOnline.applications;
    }
}
