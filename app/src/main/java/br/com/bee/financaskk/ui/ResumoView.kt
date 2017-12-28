package br.com.bee.financaskk.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import br.com.bee.financaskk.R
import br.com.bee.financaskk.extension.formatoBrasileiro
import br.com.bee.financaskk.model.Resumo
import br.com.bee.financaskk.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(context: Context,
                 private val view: View,
                 listaTransacoes: List<Transacao>) {

    private val resumo : Resumo = Resumo(listaTransacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    private fun adicionaReceita() {
        val totalReceita = resumo.receita
        with(view.resumo_card_receita){
            setTextColor(corReceita)
            text = totalReceita.formatoBrasileiro()
        }
    }

    private fun adicionaDespesa() {
        val totalDespesa = resumo.despesa
        with(view.resumo_card_despesa){
            setTextColor(corDespesa)
            text = totalDespesa.formatoBrasileiro()
        }
    }

    private fun adicionaTotal() {
        val total = resumo.total
        val cor = corPor(total)
        with(view.resumo_card_total){
            setTextColor(cor)
            text = total.formatoBrasileiro()
        }
    }

    private fun corPor(valor: BigDecimal): Int {
        return if (valor >= BigDecimal.ZERO) {
            corReceita
        } else {
            corDespesa
        }
    }

    fun atualiza(){
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }


}