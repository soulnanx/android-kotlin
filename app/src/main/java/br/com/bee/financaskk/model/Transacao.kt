package br.com.bee.financaskk.model

import java.util.Calendar
import java.math.BigDecimal

class Transacao (val categoria : String = "Indefinida",
                 val valor : BigDecimal,
                 val tipo : Tipo,
                 val data : Calendar = Calendar.getInstance())
