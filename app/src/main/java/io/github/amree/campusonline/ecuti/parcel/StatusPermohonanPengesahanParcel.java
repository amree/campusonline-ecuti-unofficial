package io.github.amree.campusonline.ecuti.parcel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amree on 1/21/15.
 */
public class StatusPermohonanPengesahanParcel implements Parcelable {

    String status;
    String url;
    String nama;
    String jenis;
    String masa;

    public StatusPermohonanPengesahanParcel() {
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

    public String getMasa() {
        return masa;
    }

    public void setMasa(String masa) {
        this.masa = masa;
    }

    public StatusPermohonanPengesahanParcel(Parcel in) {
        status = in.readString();
        url = in.readString();
        nama = in.readString();
        jenis = in.readString();
        masa = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(url);
        dest.writeString(nama);
        dest.writeString(jenis);
        dest.writeString(masa);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StatusPermohonanPengesahanParcel> CREATOR = new Parcelable.Creator<StatusPermohonanPengesahanParcel>() {
        @Override
        public StatusPermohonanPengesahanParcel createFromParcel(Parcel in) {
            return new StatusPermohonanPengesahanParcel(in);
        }

        @Override
        public StatusPermohonanPengesahanParcel[] newArray(int size) {
            return new StatusPermohonanPengesahanParcel[size];
        }
    };
}
