package io.github.amree.campusonline.ecuti.parcel;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amree on 1/18/15.
 */
public class StatusPermohonanParcel implements Parcelable {

    String status;
    String tarikh;
    String jenis;

    public StatusPermohonanParcel() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTarikh() {
        return tarikh;
    }

    public void setTarikh(String tarikh) {
        this.tarikh = tarikh;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public StatusPermohonanParcel(Parcel in) {
        status = in.readString();
        tarikh = in.readString();
        jenis = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(tarikh);
        dest.writeString(jenis);
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