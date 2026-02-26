package se.miun.lior2300.dt031g.dialer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class Dialpad @JvmOverloads constructor(
    context: Context, attrs : AttributeSet? = null,
    defStyleAttr: Int = 0) : ConstraintLayout(context,
    attrs, defStyleAttr) {

        private val numbersDisplay: TextView
        private val buttons: List<DialpadButton>

        interface CallListener {
            fun onCallReq(number: String)
        }

    private var callListener: CallListener? = null

    fun setCallListener(listener: CallListener) {
        callListener = listener

    }

        init {
            LayoutInflater.from(context).inflate(R.layout.dialpad, this, true)

            numbersDisplay = findViewById<TextView>(R.id.numbersDisplay)

            val listener = object : DialpadButton.OnClickListener {
                override fun onClick(button: DialpadButton) {
                    val number = numbersDisplay.text.toString()
                    numbersDisplay.text = number + button.getTitle()
                }
            }

            val deleteBtn = findViewById<ImageButton>(R.id.deleteBtn)

            deleteBtn.setOnClickListener {
                val number = numbersDisplay.text.toString()
                if(number.isNotEmpty()) {
                    numbersDisplay.text = number.dropLast(1)
                }
            }

            deleteBtn.setOnLongClickListener {
                numbersDisplay.text = ""
                true
            }

            val callBtn = findViewById<ImageButton>(R.id.callBtn)

            callBtn.setOnClickListener {
                val number = numbersDisplay.text.toString()

                if(number.isNotEmpty()) {
                    callListener?.onCallReq(number)
                }
            }

            val b1 = findViewById<DialpadButton>(R.id.dialpadButton1)
            val b2 = findViewById<DialpadButton>(R.id.dialpadButton2)
            val b3 = findViewById<DialpadButton>(R.id.dialpadButton3)
            val b4 = findViewById<DialpadButton>(R.id.dialpadButton4)
            val b5 = findViewById<DialpadButton>(R.id.dialpadButton5)
            val b6 = findViewById<DialpadButton>(R.id.dialpadButton6)
            val b7 = findViewById<DialpadButton>(R.id.dialpadButton7)
            val b8 = findViewById<DialpadButton>(R.id.dialpadButton8)
            val b9 = findViewById<DialpadButton>(R.id.dialpadButton9)
            val b10 = findViewById<DialpadButton>(R.id.dialpadButton10)
            val b11 = findViewById<DialpadButton>(R.id.dialpadButton11)
            val b12 = findViewById<DialpadButton>(R.id.dialpadButton12)

            buttons = listOf(b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12)


            buttons.forEach {
                it.setOnClickListener(listener)
            }

        }

    fun setSoundPlayer(player: SoundPlayer) {
        buttons.forEach {
            it.setSoundPlayer(player)
        }
    }

}