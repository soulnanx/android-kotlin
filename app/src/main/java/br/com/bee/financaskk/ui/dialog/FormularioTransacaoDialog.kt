package br.com.bee.financaskk.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import br.com.bee.financaskk.R
import br.com.bee.financaskk.extension.converteParaCalendar
import br.com.bee.financaskk.extension.formatToBrazillianDate
import br.com.bee.financaskk.model.Tipo
import br.com.bee.financaskk.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

abstract class FormularioTransacaoDialog(
        private val context: Context,
        private val viewGroup: ViewGroup) {

    private val viewCriada = criaLayout()
    protected val campoValor = viewCriada.form_transacao_valor
    protected val campoCategoria: Spinner = viewCriada.form_transacao_categoria
    protected val campoData = viewCriada.form_transacao_data
    abstract val tituloBotaoPositivo : String

    fun chama(tipo: Tipo, delegate: (trasacao : Transacao) -> Unit) {
        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, delegate)
    }

    private fun configuraFormulario(tipo: Tipo, delegate: (trasacao: Transacao) -> Unit) {
        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
                .setTitle(titulo)
                .setView(viewCriada)
                .setPositiveButton(tituloBotaoPositivo,
                        { _, _ ->
                            val valorEmTexto = campoValor.text.toString()
                            val dataEmTexto = campoData.text.toString()
                            val categoriaEmTexto = campoCategoria.selectedItem.toString()

                            val valor = converteCampoValor(valorEmTexto)
                            val data = dataEmTexto.converteParaCalendar()

                            val transacaoCriada = Transacao(tipo = tipo,
                                    valor = valor,
                                    data = data,
                                    categoria = categoriaEmTexto)
                            delegate(transacaoCriada)
                        })
                .setNegativeButton("Cancelar", null)
                .show()
    }

    abstract protected fun tituloPor(tipo: Tipo): Int

    protected fun categoriaPor(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.array.categorias_de_receita
        } else {
            R.array.categorias_de_despesa
        }
    }

    private fun converteCampoValor(valorEmTexto : String) : BigDecimal {
        return try {
            BigDecimal(valorEmTexto)
        } catch (ex: NumberFormatException) {
            Toast.makeText(
                    context,
                    "Falha na conversão de valores!",
                    Toast.LENGTH_SHORT).show()
            BigDecimal.ZERO
        }
    }

    private fun configuraCampoCategoria(tipo: Tipo) {
        val categorias = categoriaPor(tipo)

        val adapter = ArrayAdapter.createFromResource(
                context,
                categorias,
                android.R.layout.simple_spinner_dropdown_item)

        campoCategoria.adapter = adapter
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData.setText(hoje.formatToBrazillianDate())
        campoData.setOnClickListener {
            DatePickerDialog(
                    context,
                    { _, ano, mes, dia ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(ano, mes, dia)
                        campoData.setText(dataSelecionada.formatToBrazillianDate())
                    }, ano, mes, dia)
                    .show()
        }
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context)
                .inflate(R.layout.form_transacao,
                        viewGroup,
                        false)
    }
}