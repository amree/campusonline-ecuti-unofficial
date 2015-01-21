package io.github.amree.campusonline.ecuti.parcel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amree on 1/19/15.
 */
public class AwardWangTunaiParcel implements Parcelable {

    String tahun;
    String bilangan;

    public AwardWangTunaiParcel() {

    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getBilangan() {
        return bilangan;
    }

    public void setBilangan(String bilangan) {
        this.bilangan = bilangan;
    }

    protected AwardWangTunaiParcel(Parcel in) {
        tahun = in.readString();
        bilangan = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tahun);
        dest.writeString(bilangan);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AwardWangTunaiParcel> CREATOR = new Parcelable.Creator<AwardWangTunaiParcel>() {
        @Override
        public AwardWangTunaiParcel createFromParcel(Parcel in) {
            return new AwardWangTunaiParcel(in);
        }

        @Override
        public AwardWangTunaiParcel[] newArray(int size) {
            return new AwardWangTunaiParcel[size];
        }
    };
}