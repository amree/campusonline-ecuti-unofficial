package io.github.amree.campusonline.ecuti;

public class DataApplication {

    String status;
    String url;
    String nama;
    String jenis;
    String masaMinta;

    public DataApplication(String params[]) {

        this.status    = params[0];
        this.url       = params[1];
        this.nama      = params[2];
        this.jenis     = params[3];
        this.masaMinta = params[4];
    }
}

