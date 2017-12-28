package br.com.bee.financaskk.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import br.com.bee.financaskk.R
import br.com.bee.financaskk.dao.TransacaoDAO
import br.com.bee.financaskk.model.Tipo
import br.com.bee.financaskk.model.Transacao
import br.com.bee.financaskk.ui.ResumoView
import br.com.bee.financaskk.ui.adapter.TransacoesAdapter
import br.com.bee.financaskk.ui.dialog.AdicionaTransacaoDialog
import br.com.bee.financaskk.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class TransacoesActivity : AppCompatActivity() {

    private val dao = TransacaoDAO()
    private val transacoes = dao.transacoes
    private val viewGroupDaActivity by lazy {
        window.decorView as ViewGroup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()
        configuraLista()
        configuraFab()
    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita.setOnClickListener {
            chamaDialogDeAdicao(Tipo.RECEITA)
        }

        lista_transacoes_adiciona_despesa.setOnClickListener {
            chamaDialogDeAdicao(Tipo.DESPESA)
        }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(viewGroupDaActivity, this)
                .chama(tipo, { transacaoCriada ->
                    adiciona(transacaoCriada)
                    lista_transacoes_adiciona_menu.close(true)
                })
    }

    private fun adiciona(transacao: Transacao) {
        dao.adiciona(transacao)
        atualizaTransacoes()
    }

    private fun atualizaTransacoes() {
        configuraResumo()
        configuraLista()
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(this, viewGroupDaActivity, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {

        with(lista_transacoes_listview) {
            adapter = TransacoesAdapter(transacoes, this@TransacoesActivity)
            setOnItemClickListener { _, _, position, _ ->
                val transacao = transacoes[position]
                chamaDialogDeAlteracao(transacao, position)
            }
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1 , Menu.NONE, "Remover")
            }
        }

    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val itemId = item?.itemId

        if (itemId == 1){
            val contextMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val posicao = contextMenuInfo.position
            remove(posicao)
        }

        return super.onContextItemSelected(item)
    }

    private fun remove(posicao: Int) {
        dao.remove(posicao)
        atualizaTransacoes()
    }

    private fun chamaDialogDeAlteracao(transacao: Transacao, position: Int) {
        AlteraTransacaoDialog(viewGroupDaActivity, this)
                .chama(transacao, { transacaoAlterada -> 
                    altera(transacaoAlterada, position)
                })
    }

    private fun altera(transacao: Transacao, position: Int) {
        dao.altera(transacao, position)
        atualizaTransacoes()
    }

}