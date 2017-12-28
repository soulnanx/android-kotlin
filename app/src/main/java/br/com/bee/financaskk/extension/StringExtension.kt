package br.com.bee.financaskk.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitaAte(limite : Int) : String {
    if (this.length > limite){
        return "${this.substring(0, limite)}..."
    }
    return this
}

fun String.converteParaCalendar() : Calendar {
    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
    val dataConvertida = formatoBrasileiro.parse(this)
    val data = Calendar.getInstance()
    data.time = dataConvertida
    return data
}
