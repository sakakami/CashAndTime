package mysoftstudio.cashandtime.data

import android.os.Parcel
import android.os.Parcelable

class TimeData() : Parcelable {
    var timeId = ""
    var timeDate = ""
    var childId = ""
    var childName = ""
    var timeIn = ""
    var timeOut = ""
    var timeTotal = ""
    var timeCreator = ""
    var timeInfo = ""

    constructor(parcel: Parcel) : this() {
        timeId = parcel.readString().toString()
        timeDate = parcel.readString().toString()
        childId = parcel.readString().toString()
        childName = parcel.readString().toString()
        timeIn = parcel.readString().toString()
        timeOut = parcel.readString().toString()
        timeTotal = parcel.readString().toString()
        timeCreator = parcel.readString().toString()
        timeInfo = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(timeId)
        parcel.writeString(timeDate)
        parcel.writeString(childId)
        parcel.writeString(childName)
        parcel.writeString(timeIn)
        parcel.writeString(timeOut)
        parcel.writeString(timeTotal)
        parcel.writeString(timeCreator)
        parcel.writeString(timeInfo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TimeData> {
        override fun createFromParcel(parcel: Parcel): TimeData {
            return TimeData(parcel)
        }

        override fun newArray(size: Int): Array<TimeData?> {
            return arrayOfNulls(size)
        }
    }
}