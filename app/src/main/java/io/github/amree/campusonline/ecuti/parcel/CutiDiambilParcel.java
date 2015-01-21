package io.github.amree.campusonline.ecuti.parcel;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amree on 1/18/15.
 */
public class CutiDiambilParcel implements Parcelable {

    String tarikhAkhir;
    String tarikhMula;
    String jenis;
    String jumlah;

    public CutiDiambilParcel() {

    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTarikhAkhir() {
        return tarikhAkhir;
    }

    public void setTarikhAkhir(String tarikhAkhir) {
        this.tarikhAkhir = tarikhAkhir;
    }

    public String getTarikhMula() {
        return tarikhMula;
    }

    public void setTarikhMula(String tarikhMula) {
        this.tarikhMula = tarikhMula;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public CutiDiambilParcel(Parcel in) {
        tarikhAkhir = in.readString();
        tarikhMula = in.readString();
        jenis = in.readString();
        jumlah = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tarikhAkhir);
        dest.writeString(tarikhMula);
        dest.writeString(jenis);
        dest.writeString(jumlah);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CutiDiambilParcel> CREATOR = new Parcelable.Creator<CutiDiambilParcel>() {
        @Override
        public CutiDiambilParcel createFromParcel(Parcel in) {
            return new CutiDiambilParcel(in);
        }

        @Override
        public CutiDiambilParcel[] newArray(int size) {
            return new CutiDiambilParcel[size];
        }
    };
}