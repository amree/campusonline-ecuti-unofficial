package io.github.amree.campusonline.ecuti;

public class DataApplication {

    String status;
    String url;
    String nama;
    String jenis;
    String masaMinta;
    String tarikhDari;
    String tarikhHingga;
    String jumlahHari;
    String sebabCuti;

    public String getTarikhDari() {
        return tarikhDari;
    }

    public void setTarikhDari(String tarikhDari) {
        this.tarikhDari = tarikhDari;
    }

    public String getTarikhHingga() {
        return tarikhHingga;
    }

    public void setTarikhHingga(String tarikhHingga) {
        this.tarikhHingga = tarikhHingga;
    }

    public String getJumlahHari() {
        return jumlahHari;
    }

    public void setJumlahHari(String jumlahHari) {
        this.jumlahHari = jumlahHari;
    }

    public String getSebabCuti() {
        return sebabCuti;
    }

    public void setSebabCuti(String sebabCuti) {
        this.sebabCuti = sebabCuti;
    }

    public DataApplication() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getMasaMinta() {
        return masaMinta;
    }

    public void setMasaMinta(String masaMinta) {
        this.masaMinta = masaMinta;
    }
}

