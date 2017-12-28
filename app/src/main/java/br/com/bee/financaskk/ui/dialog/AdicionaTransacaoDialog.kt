package br.com.bee.financaskk.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.bee.financaskk.R
import br.com.bee.financaskk.model.Tipo

class AdicionaTransacaoDialog(viewGroup: ViewGroup,
                              val context: Context) : FormularioTransacaoDialog(context, viewGroup){
    override val tituloBotaoPositivo: String
        get() = "Adicionar"

    override fun tituloPor(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.string.adiciona_receita
        } else {
            R.string.adiciona_despesa
        }
    }
}