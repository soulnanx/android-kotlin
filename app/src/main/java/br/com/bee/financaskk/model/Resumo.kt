package br.com.bee.financaskk.model

import java.math.BigDecimal

class Resumo(private val listaTransacoes: List<Transacao>) {

    val receita get() = somaPor(Tipo.RECEITA)

    val despesa get() = somaPor(Tipo.DESPESA)

    val total get() = receita.subtract(despesa)

    private fun somaPor(tipo: Tipo) : BigDecimal{
        val somaPorTipo = listaTransacoes
                .filter { it.tipo == tipo }
                .sumByDouble { it.valor.toDouble() }
        return BigDecimal(somaPorTipo)
    }

//    private fun somaPor(tipo: Tipo) : BigDecimal{
//        val somaPorTipo = listaTransacoes
//                .filter { transacao -> transacao.tipo == tipo }
//                .sumByDouble{ transacao -> transacao.valor.toDouble() }
//        return BigDecimal(somaPorTipo)
//    }
}