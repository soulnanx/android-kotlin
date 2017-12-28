package br.com.bee.financaskk.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.bee.financaskk.R
import br.com.bee.financaskk.extension.formatToBrazillianDate
import br.com.bee.financaskk.model.Tipo
import br.com.bee.financaskk.model.Transacao

class AlteraTransacaoDialog(
        viewGroup: ViewGroup,
        private val context: Context) : FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Alterar"

    override fun tituloPor(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.string.altera_receita
        } else {
            R.string.altera_despesa
        }
    }

    fun chama(transacao: Transacao, delegate: (transacao : Transacao) -> Unit) {
        val tipo = transacao.tipo
        super.chama(tipo, delegate)
        inicializaCampos(transacao)
    }

    private fun inicializaCampos(transacao: Transacao) {
        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        inicializaCampoCategoria(transacao)
    }

    private fun inicializaCampoCategoria(transacao: Transacao) {
        val categoriasRetornadas = context.resources.getStringArray(categoriaPor(transacao.tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formatToBrazillianDate())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }
}