package se.miun.lior2300.dt031g.dialer

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import se.miun.lior2300.dt031g.dialer.data.viewmodel.CallViewModel


class CallListActivity : AppCompatActivity() {

    private lateinit var teleList: RecyclerView
    private lateinit var textView: TextView
    private lateinit var adapter: CallAdapter
    private lateinit var callViewModel: CallViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Call list"

        teleList = findViewById<RecyclerView>(R.id.callList)
        textView = findViewById<TextView>(R.id.textView2)

        adapter = CallAdapter()
        teleList.adapter = adapter
        teleList.layoutManager = LinearLayoutManager(this)

        callViewModel = ViewModelProvider(this)[CallViewModel::class.java]


        callViewModel.allCalls.observe(this) { calls ->

            if(calls.isEmpty()) {
                textView.visibility = View.VISIBLE
                teleList.visibility = View.GONE
            }
            else {
                textView.visibility = View.GONE
                teleList.visibility = View.VISIBLE
                adapter.updateCallList(calls)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.call_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_history -> {

                callViewModel.deleteAllCalls()
                true
            }

            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}