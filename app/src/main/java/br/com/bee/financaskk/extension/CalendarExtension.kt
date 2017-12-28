package br.com.bee.financaskk.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by renansantos on 17/11/2017.
 */

fun Calendar.formatToBrazillianDate() : String{
    val format = "dd/MM/yyyy"
    val simpleDateFormat = SimpleDateFormat(format)
    return simpleDateFormat.format(this.time)
}