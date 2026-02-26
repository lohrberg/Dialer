package se.miun.lior2300.dt031g.dialer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import se.miun.lior2300.dt031g.dialer.data.local.entity.Call
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CallAdapter : RecyclerView.Adapter<CallAdapter.CallViewHolder>() {

    private var calls: List<Call> = emptyList()


    inner class CallViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textNumber: TextView = itemView.findViewById<TextView>(R.id.number)
        val textDate: TextView = itemView.findViewById<TextView>(R.id.date)

        val textLocation: TextView = itemView.findViewById<TextView>(R.id.location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallViewHolder {
        val itemView = LayoutInflater.from((parent.context)).inflate(R.layout.call_item, parent, false)
        return CallViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CallViewHolder, position: Int) {
        val call = calls[position]
        holder.textNumber.text = call.number

        val date = Date(call.timestamp)
        val format = SimpleDateFormat("MMM d, yyyy hh:mm:ss a", Locale.getDefault())
        holder.textDate.text = format.format(date)

        val lat = call.latitude
        val long = call.longitude

        holder.textLocation.text =
            if (lat != null && long != null) {
                "[" + lat.toString() + ", " + long.toString() + "]"
            }
            else {
                "[??, ??]"
            }

    }

    override fun getItemCount(): Int = calls.size

    fun updateCallList(newCalls: List<Call>) {
        calls = newCalls
        notifyDataSetChanged()
    }
}