package cn.edu.sjtu.tanghaoran.kotlinChatter

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cn.edu.sjtu.tanghaoran.kotlinChatter.ChattStore.chatts
import cn.edu.sjtu.tanghaoran.kotlinChatter.ChattStore.getChatts
import cn.edu.sjtu.tanghaoran.kotlinChatter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding
    private lateinit var chattListAdapter: ChattListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        view.root.setBackgroundColor(Color.parseColor("#E0E0E0"))
        setContentView(view.root)

        chattListAdapter = ChattListAdapter(this, chatts)
        view.chattListView.setAdapter(chattListAdapter)

        view.refreshContainer.setOnRefreshListener {
            refreshTimeline()
        }

        refreshTimeline()
        // setup refreshContainer here later
    }

    private fun refreshTimeline() {
        getChatts(applicationContext) {
            runOnUiThread {
                // inform the list adapter that data set has changed
                // so that it can redraw the screen.
                chattListAdapter.notifyDataSetChanged()
            }
            // stop the refreshing animation upon completion:
            view.refreshContainer.isRefreshing = false
        }
    }

    fun startPost(view: View?) = startActivity(Intent(this, PostActivity::class.java))
}