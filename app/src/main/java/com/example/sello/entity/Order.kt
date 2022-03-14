package com.example.sello.entity
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.sello.database.DatabaseConstants.ORDER_ID
import com.example.sello.database.DatabaseConstants.ORDER_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = ORDER_TABLE,
    indices = [Index(value = [ORDER_ID], unique = true)])
data class Order(
    @PrimaryKey @ColumnInfo(name = ORDER_ID) val orderID: String,
    val customerID: String,
    val status: OrderStatus
):Parcelable

enum class OrderStatus {
    Pending, Confirmed, Shipped
}