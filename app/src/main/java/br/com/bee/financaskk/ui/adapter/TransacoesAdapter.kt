package br.com.bee.financaskk.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.bee.financaskk.R
import br.com.bee.financaskk.extension.formatToBrazillianDate
import br.com.bee.financaskk.extension.formatoBrasileiro
import br.com.bee.financaskk.extension.limitaAte
import br.com.bee.financaskk.model.Tipo
import br.com.bee.financaskk.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*

class TransacoesAdapter(private val transacoes : List<Transacao>,
                        private val context : Context) : BaseAdapter() {

    private val limiteDaCategoria = 14

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewNova = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)
        val item = transacoes[position]

        setValor(item, viewNova)
        setIcone(item, viewNova)
        setCategoria(viewNova, item)
        setData(viewNova, item)

        return viewNova
    }

    private fun setData(viewNova: View, item: Transacao) {
        viewNova.transacao_data.text = item.data.formatToBrazillianDate()
    }

    private fun setCategoria(viewNova: View, item: Transacao) {
        viewNova.transacao_categoria.text = item.categoria.limitaAte(limiteDaCategoria)
    }

    private fun setIcone(item: Transacao, viewNova: View) {
        viewNova.transacao_icone.setBackgroundResource(iconePor(item.tipo))
    }

    private fun iconePor(tipo : Tipo) : Int{
        if (tipo == Tipo.RECEITA) {
            return R.drawable.icone_transacao_item_receita
        }
        return R.drawable.icone_transacao_item_despesa
    }

    private fun corPor(tipo : Tipo) : Int{
        if (tipo == Tipo.RECEITA) {
            return ContextCompat.getColor(context, R.color.receita)
        }
        return ContextCompat.getColor(context, R.color.despesa)
    }

    private fun setValor(item: Transacao, viewNova: View) {
        viewNova.transacao_valor.setTextColor(corPor(item.tipo))
        viewNova.transacao_valor.text = item.valor.formatoBrasileiro()
    }

    override fun getItem(position: Int): Any {
        return transacoes[position]
    }

    override fun getItemId(p0: Int): Long {
        return 1
    }

    override fun getCount(): Int {
        return transacoes.size
    }
}