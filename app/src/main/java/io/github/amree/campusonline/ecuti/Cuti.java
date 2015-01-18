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

public class Cuti {

    String email;
    String password;

    public static Map<String, String> cookies;
    public static String[][] applications;

    Document doc;
    Connection.Response res;

    String coMainURL        = "http://campusonline.usm.my/smus/employee_i.asp?m=1&c=1";
    String campusOnlineURL  = "https://campusonline.usm.my";
    String cutiURL          = "http://e-cuti.usm.my/ecuti_v2/";
    String realCutiURL      = "http://e-cuti.usm.my/ecuti_v2/default.asp";
    String sahCutiURL       = "http://e-cuti.usm.my/ecuti_v2/default.asp?sec=P&fn=1";
    String mainCutiURL      = "";
    String loginURL         = "";
    String loginProcURL     = "";
    String processURL       = "";

    public Cuti(String email, String password) {
        this.email = email;
        this.password = password;

        Cuti.cookies = new HashMap();
    }

    public Cuti() {
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

    public void doLogin() throws IOException, LoginException {
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

        if (this.doc.select("title").html().trim().equals("Universiti Sains Malaysia, Pulau Pinang")) {
            throw new LoginException("Email atau kata laluan anda salah.");
        }

        this.processURL = this.doc.select("a[href]").get(0).attr("abs:href");

        this.res = Jsoup.connect(this.processURL)
                .followRedirects(false)
                .cookies(cookies)
                .timeout(0)
                .method(Method.GET)
                .execute();

        setCookies();

        // Now, we enter the real Cuti page
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

    public void gotoCuti() throws IOException, LoginException {

        // We're in! Get the e-Cuti URL
        Element link;
        Elements links = this.doc.select("a[href*=/ecuti_v2/]");

        if (links.size() > 0) {
            link = this.doc.select("a[href*=/ecuti_v2/]").get(0);
        } else {
            throw new LoginException("Ada masalah dengan kemasukan anda. Sila cuba sekali lagi.");
        }


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

        Elements trElements = this.doc.select("table.contacts tr");

        // Walaupun tiada rekod, tetap akan dua row
        // jadi, kena periksa teks row terakhir
        if (trElements.last().text().equals("Tiada Rekod")) {

            Cuti.applications = new String[0][5];

        } else {

            // Remember to skip the first row (first row == header)
            Cuti.applications = new String[trElements.size() - 1][5];

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

                            Cuti.applications[x - 1][0] = status;

                            break;
                        case 2:
                            // Link
                            Cuti.applications[x - 1][1] = this.cutiURL + tdElements.get(y).select("a").attr("href");
                            // Nama
                            Cuti.applications[x - 1][2] = tdElements.get(y).text();
                            break;
                        case 3:
                            // Jenis
                            Cuti.applications[x - 1][3] = tdElements.get(y).text();
                            break;
                        case 4:
                            // Tarikh dan masa
                            Cuti.applications[x - 1][4] = tdElements.get(y).text();
                            break;
                    }
                }
            }

            for (int i = 0; i < Cuti.applications.length; i++) {
                System.out.println(i + "," + 0 + " -- " + Cuti.applications[i][0]);
                System.out.println(i + "," + 1 + " -- " + Cuti.applications[i][1]);
                System.out.println(i + "," + 2 + " -- " + Cuti.applications[i][2]);
                System.out.println(i + "," + 3 + " -- " + Cuti.applications[i][3]);
                System.out.println(i + "," + 4 + " -- " + Cuti.applications[i][4]);
            }
        }
    }

    public String[][] getApplications() {
        return Cuti.applications;
    }

    public DataApplication openPermohonananSah(String url) throws IOException {
        // Buka page permohonan individu
        this.res = Jsoup.connect(url)
                .followRedirects(false)
                .cookies(cookies)
                .timeout(0)
                .method(Method.GET)
                .execute();

        System.out.println("Current URL: " + this.res.url());

        setCookies();

        this.doc = this.res.parse();

        String namaSelect = "body > div > table > tbody > tr:nth-child(2) >"
                + " td > table > tbody > tr > td:nth-child(2) > table > tbody >"
                + " tr:nth-child(2) > td > table > tbody > tr >"
                + " td:nth-child(2) > table > tbody > tr:nth-child(2) > td >"
                + " table > tbody > tr:nth-child(1) > td > table:nth-child(9) >"
                + " tbody > tr > td > table > tbody > tr:nth-child(2) > td >"
                + " table > tbody > tr:nth-child(1) > td:nth-child(2)";

        String infoCutiSelector = "body > div > table > tbody >"
                + " tr:nth-child(2) > td > table > tbody > tr >"
                + " td:nth-child(2) > table > tbody > tr:nth-child(2) > td >"
                + " table > tbody > tr > td:nth-child(2) > table > tbody >"
                + " tr:nth-child(2) > td > table > tbody > tr:nth-child(1) >"
                + " td > table:nth-child(10) > tbody > tr:nth-child(1) > td >"
                + " table > tbody > tr:nth-child(2) > td > table > tbody";

        String jenisCutiSelect    = infoCutiSelector + " > tr:nth-child(1) > td:nth-child(2)";
        String tarikhDariSelect   = infoCutiSelector + " > tr:nth-child(2) > td:nth-child(2) >" +
                " table > tbody > tr:nth-child(2) > td:nth-child(1)";
        String tarikhHinggaSelect = infoCutiSelector + " > tr:nth-child(2) > td:nth-child(2) >" +
                " table > tbody > tr:nth-child(2) > td:nth-child(2)";
        String jumlahHariSelect   = infoCutiSelector + " > tr:nth-child(2) > td:nth-child(2) >" +
                " table > tbody > tr:nth-child(2) > td:nth-child(3)";
        String sebabCutiSelect    = infoCutiSelector + " > tr:nth-child(8) > td:nth-child(2)";

        String nama = cleanText(this.doc.select(namaSelect).text());
        String jenisCuti = cleanText(this.doc.select(jenisCutiSelect).text());
        String tarikhDari = cleanText(this.doc.select(tarikhDariSelect).text());
        String tarikhHingga = cleanText(this.doc.select(tarikhHinggaSelect).text());
        String jumlahHari = cleanText(this.doc.select(jumlahHariSelect).text());
        String sebabCuti = cleanText(this.doc.select(sebabCutiSelect).text());

        DataApplication dataApplication = new DataApplication();

        dataApplication.setNama(nama);
        dataApplication.setJenis(jenisCuti);
        dataApplication.setTarikhDari(tarikhDari);
        dataApplication.setTarikhHingga(tarikhHingga);
        dataApplication.setJumlahHari(jumlahHari);
        dataApplication.setSebabCuti(sebabCuti);
        dataApplication.setUrl(url);

        return dataApplication;
    }

    public void openSenaraiPermohonan() throws IOException {
        gotoSahCuti();
        setApplications();
    }

    public boolean doSahPermohonan(String url) throws IOException {
        openPermohonananSah(url);

        String idCuti  = this.doc.select("input[name=IDCuti]").val();
        String rLulus  = this.doc.select("input[name=R_Lulus]").val();
        String sUlasan = this.doc.select("input[name=S_Ulasan]").val();
        String b1      = "SAHKAN";
        String mn      = "";

        this.res = Jsoup.connect(cutiURL + "semakan/sv_proses_cuti_v2.asp")
                        .followRedirects(false)
                        .data("IDCuti", idCuti)
                        .data("R_Lulus", rLulus)
                        .data("S_Ulasan", sUlasan)
                        .data("B1", b1)
                        .data("mn", mn)
                        .cookies(cookies)
                        .timeout(0)
                        .method(Method.POST)
                        .execute();

        System.out.println("Current URL: " + this.res.url());

        setCookies();

        this.doc = this.res.parse();

        String urlRedirect = doc.select("a[href]").get(0).attr("abs:href");

        if (urlRedirect.equals("../default.asp?sec=K&fn=2&mn=")) {
            return true;
        } else {
            return false;
        }
    }

    private String cleanText(String str) {
        if ((str.length() == 0) || (str.length() == 1)) {
            return "";
        } else {
            return str.trim().substring(1);
        }
    }
}
