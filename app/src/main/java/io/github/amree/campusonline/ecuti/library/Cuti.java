package io.github.amree.campusonline.ecuti.library;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.github.amree.campusonline.ecuti.parcel.StatusPermohonanParcel;
import io.github.amree.campusonline.ecuti.parcel.StatusPermohonanPengesahanParcel;
import io.github.amree.campusonline.ecuti.pojo.DataApplication;
import io.github.amree.campusonline.ecuti.parcel.AwardWangTunaiParcel;
import io.github.amree.campusonline.ecuti.parcel.CutiDiambilParcel;
import io.github.amree.campusonline.ecuti.pojo.PermohonanCutiBaru;

public class Cuti {

    String email;
    String password;

    public static Map<String, String> cookies;

    Document doc;
    Connection.Response res;

    String coMainURL         = "http://campusonline.usm.my/smus/employee_i.asp?m=1&c=1";
    String campusOnlineURL   = "https://campusonline.usm.my";
    String cutiURL           = "http://e-cuti.usm.my/ecuti_v2/";
    String realCutiURL       = "http://e-cuti.usm.my/ecuti_v2/default.asp";
    String sahCutiURL        = "http://e-cuti.usm.my/ecuti_v2/default.asp?sec=P&fn=1";
    String cutiDiambilURL    = "http://e-cuti.usm.my/ecuti_v2/default.asp?sec=D&semakan=cuti";
    String awardWangTunaiURL = "http://e-cuti.usm.my/ecuti_v2/default.asp?sec=D&semakan=awti";
    String permohonanCutiURL = "http://e-cuti.usm.my/ecuti_v2/default.asp?sec=D&fn=1&tag=46";
    String statusPermohonanURL = "http://e-cuti.usm.my/ecuti_v2/default.asp?sec=D&semakan=pohon";
    String mainCutiURL       = "";
    String loginURL          = "";
    String loginProcURL      = "";
    String processURL        = "";

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

    public void gotoSenaraiCutiDiambil() throws IOException {
        this.res = Jsoup.connect(cutiDiambilURL)
                .timeout(0)
                .cookies(cookies)
                .followRedirects(false)
                .method(Method.GET)
                .execute();

        System.out.println("Current URL: " + res.url());

        setCookies();

        this.doc = this.res.parse();
    }

    public void gotoSenaraiStatusPermohonan() throws IOException {
        this.res = Jsoup.connect(statusPermohonanURL)
                .timeout(0)
                .cookies(cookies)
                .followRedirects(false)
                .method(Method.GET)
                .execute();

        System.out.println("Current URL: " + res.url());

        setCookies();

        this.doc = this.res.parse();
    }

    public void gotoPermohonanCuti() throws IOException {
        this.res = Jsoup.connect(permohonanCutiURL)
                .timeout(0)
                .cookies(cookies)
                .followRedirects(false)
                .method(Method.GET)
                .execute();

        System.out.println("Current URL: " + res.url());

        setCookies();

        this.doc = this.res.parse();
    }

    public void gotoAwardWangTunai() throws IOException {
        this.res = Jsoup.connect(awardWangTunaiURL)
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

//    public CutiDiambilParcel[] getSenaraiCutiDiambil() {

    public StatusPermohonanPengesahanParcel[] getSenaraiStatusPermohonanPengesahan() {

        StatusPermohonanPengesahanParcel[] arr = null;

        Elements trElements = this.doc.select("table.contacts tr");

        // Walaupun tiada rekod, tetap akan dua row
        // jadi, kena periksa teks row terakhir
        if (trElements.last().text().equals("Tiada Rekod")) {

            arr = new StatusPermohonanPengesahanParcel[0];

        } else {

            // Remember to skip the first row (first row == header)
            arr = new StatusPermohonanPengesahanParcel[trElements.size() - 1];

            for (int x = 1; x < trElements.size(); x++) {
                arr[x - 1] = new StatusPermohonanPengesahanParcel();

                Elements tdElements = trElements.get(x).select("td");
                for (int y = 0; y < tdElements.size(); y++) {

                    switch (y) {
                        case 1:
                            // Status
                            String imageFile = tdElements.get(y).select("img").attr("src");

                            String status;
                            switch (imageFile) {
                                case "img/0.gif":
                                    status = "Belum diproses oleh kerani";
                                    break;
                                case "img/1.gif":
                                    status = "Belum diproses oleh penyelia";
                                    break;
                                case "img/2.gif":
                                    status = "Telah diluluskan";
                                    break;
                                case "img/3.gif":
                                    status = "Tidak diluluskan";
                                    break;
                                case "img/4.gif":
                                    status = "Dihantar semula untuk semakan";
                                    break;
                                default:
                                    status = "LAIN-LAIN";
                            }

                            arr[x - 1].setStatus(status);

                            break;
                        case 2:
                            // URL
                            arr[x - 1].setUrl(this.cutiURL + tdElements.get(y).select("a").attr("href"));
                            // Nama
                            arr[x - 1].setNama(tdElements.get(y).text());
                            break;
                        case 3:
                            // Jenis
                            arr[x - 1].setJenis(tdElements.get(y).text());
                            break;
                        case 4:
                            // Tarikh dan masa
                            arr[x - 1].setMasa(tdElements.get(y).text());
                            break;
                    }
                }
            }
        }

        return arr;
    }

    public CutiDiambilParcel[] getSenaraiCutiDiambil() {

        CutiDiambilParcel[] arr = null;


        Elements trElements = this.doc.select("table.contacts tr");

        // Walaupun tiada rekod, tetap akan dua row
        // jadi, kena periksa teks row terakhir
        if (trElements.size() > 4) {

            // Remember to skip the first two rows
            // First row is the title
            // Second row is the header
            // The last three rows are used for the summary
            arr = new CutiDiambilParcel[trElements.size() - 5];

            for (int x = 2; x < trElements.size() - 3; x++) {
                arr[x - 2] = new CutiDiambilParcel();

                Elements tdElements = trElements.get(x).select("td");
                for (int y = 0; y < tdElements.size(); y++) {

                    System.out.println(tdElements.get(y).text());

                    switch (y) {
                        case 1:
                            // Tarikh Mula
                            arr[x - 2].setTarikhMula(tdElements.get(y).text());

                            break;
                        case 2:
                            // Tarikh Akhir
                            arr[x - 2].setTarikhAkhir(tdElements.get(y).text());
                            break;
                        case 3:
                            // Jenis
                            arr[x - 2].setJenis(tdElements.get(y).text());
                            break;
                        case 4:
                            // Jumlah
                            arr[x - 2].setJumlah(tdElements.get(y).text());
                            break;
                    }
                }

            }

            for (int i = 0; i < arr.length; i++) {
                System.out.println("************* Cuti Diambil");
                System.out.println("Status: " + arr[i].getTarikhAkhir());
                System.out.println("Tarikh: " + arr[i].getTarikhMula());
                System.out.println("Jenis: " + arr[i].getJenis());
            }

        } else {

            arr = new CutiDiambilParcel[0];
        }

        return arr;
    }

    public AwardWangTunaiParcel[] getAwardWangTunai() {

        AwardWangTunaiParcel[] arr = null;


        Elements trElements = this.doc.select("table[bgcolor=#ffffff][cellpadding=1][cellspacing=1] tr");

        System.out.println("Size: " + trElements.size());

        System.out.println(trElements);

        // Walaupun tiada rekod, tetap akan dua row
        // jadi, kena periksa teks row terakhir
        if (trElements.size() > 2) {

            // Remember to skip the first two rows and the last row
            // First row is the title
            // Second row is the header
            // Last row is the total count
            arr = new AwardWangTunaiParcel[trElements.size() - 3];

            for (int x = 2; x < trElements.size() - 1; x++) {
                arr[x - 2] = new AwardWangTunaiParcel();

                Elements tdElements = trElements.get(x).select("td");
                for (int y = 0; y < tdElements.size(); y++) {

                    switch (y) {
                        case 1:
                            arr[x - 2].setTahun(tdElements.get(y).text());

                            break;
                        case 2:
                            // Tarikh
                            arr[x - 2].setBilangan(tdElements.get(y).text());
                            break;
                    }
                }
            }

            for (int i = 0; i < arr.length; i++) {
                System.out.println("************* Award Wang Tunai");
                System.out.println("Status: " + arr[i].getTahun());
                System.out.println("Tarikh: " + arr[i].getBilangan());
            }

        } else {
            arr = new AwardWangTunaiParcel[0];
        }

        return arr;
    }

    public StatusPermohonanParcel[] getSenaraiStatusPermohonan() {

        StatusPermohonanParcel[] arr = null;


        Elements trElements = this.doc.select("table.contacts tr");

        // Walaupun tiada rekod, tetap akan dua row
        // jadi, kena periksa teks row terakhir
        if (trElements.size() == 2) {

            arr = new StatusPermohonanParcel[0];

        } else if (trElements.size() > 2) {

            // Remember to skip the first two rows
            // First row is the title
            // Second row is the header
            arr = new StatusPermohonanParcel[trElements.size() - 2];

            for (int x = 2; x < trElements.size(); x++) {
                arr[x - 2] = new StatusPermohonanParcel();

                Elements tdElements = trElements.get(x).select("td");
                for (int y = 0; y < tdElements.size(); y++) {

                    switch (y) {
                        case 1:
                            // Status
                            String imageFile = tdElements.get(y).select("img").attr("src");

                            String status;
                            switch (imageFile) {
                                case "img/0.gif":
                                    status = "Belum diproses kerani";
                                    break;
                                case "img/1.gif":
                                    status = "Belum diproses oleh penyelia";
                                    break;
                                case "img/2.gif":
                                    status = "Telah diluluskan";
                                    break;
                                case "img/3.gif":
                                    status = "Tidak diluluskan";
                                    break;
                                case "img/4.gif":
                                    status = "Dihantar semula untuk semakan";
                                    break;
                                default:
                                    status = "LAIN-LAIN";
                            }

                            arr[x - 2].setStatus(status);

                            break;
                        case 2:
                            // Tarikh
                            arr[x - 2].setTarikh(tdElements.get(y).text());
                            break;
                        case 3:
                            // Jenis
                            arr[x - 2].setJenis(tdElements.get(y).text());
                            break;
                    }
                }
            }

            for (int i = 0; i < arr.length; i++) {
                System.out.println("************* Status Permohonan");
                System.out.println("Status: " + arr[i].getStatus());
                System.out.println("Tarikh: " + arr[i].getTarikh());
                System.out.println("Jenis: " + arr[i].getJenis());
            }
        }

        return arr;
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

    public void doHantarPermohonanCuti(PermohonanCutiBaru cutiBaru) throws IOException, PermohonanCutiException {

        // Open page permohonan cuti
        // Save semua field yang perlu
        // Campur field tadi dengan field value dari user
        // post ke url
        // check dia redirect ke mana
        // throw error kalau error

        gotoPermohonanCuti();

        Elements forms = this.doc.select("form input");

        Map<String, String> formValues = new HashMap();

        for (Element form : forms) {

            if ((form.attr("name").equalsIgnoreCase("R_StatusAmbilInsuran")) ||
                    (form.attr("name").equalsIgnoreCase("R_Insuran")) ||
                    (form.attr("name").equalsIgnoreCase("R_Notis"))) {

                continue;
            }


            if ((!form.val().trim().isEmpty()) && (form.attr("name") != "")) {
                formValues.put(form.attr("name"), form.val());
            }
        }

        // Other fields
        String jenisCuti = this.doc.select("form select[name=D_JenisCuti] option[selected]").first().attr("value");
        String negara = this.doc.select("form select[name=D_KodNegara] option[selected]").first().attr("value");
        String alamat = this.doc.select("form textarea[name=S_AlamatCuti]").first().text();

        // Set values from user
        formValues.put("T_TkhMula[]", cutiBaru.getTarikhMula());
        formValues.put("T_TkhTamat[]", cutiBaru.getTarikhAkhir());
        formValues.put("D_JenisCuti", jenisCuti);
        formValues.put("D_KodNegara", negara);
        formValues.put("S_AlamatCuti", alamat);

        String postURL = cutiURL + this.doc.select("form").attr("action");

        this.res = Jsoup.connect(postURL)
                .timeout(0)
                .cookies(cookies)
                .data(formValues)
                .method(Connection.Method.POST)
                .execute();

        System.out.println("Current URL: " + this.res.url());

        setCookies();

        this.doc = this.res.parse();

        if (this.doc.select("title").html().trim().equals("")) {

            String errorMsg = this.doc.select("#table1 td font").first().text();

            if (errorMsg.equalsIgnoreCase("")) {
                errorMsg = "Masalah tidak dapat dikenalpasti. Sila cuba lagi.";
            }

            throw new PermohonanCutiException(errorMsg);
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
