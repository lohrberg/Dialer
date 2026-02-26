package se.miun.lior2300.dt031g.dialer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout


class DialpadButton @JvmOverloads constructor(
    context: Context, attrs : AttributeSet? = null,
    defStyleAttr: Int = 0) : ConstraintLayout(context,
    attrs, defStyleAttr) {


    private val titleView: TextView
    private val messageView: TextView

    private var soundPlayer: SoundPlayer? = null

    private var listener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(button: DialpadButton)
    }

    fun setOnClickListener(l: OnClickListener) {
        listener = l
    }

    fun setSoundPlayer(player: SoundPlayer) {
        soundPlayer = player
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_dialpad_button,this, true)

        isClickable = true
        isFocusable = true

        titleView = findViewById<TextView>(R.id.titleText)
        messageView = findViewById<TextView>(R.id.messageText)

        if(attrs != null) {

            val a = context.obtainStyledAttributes(attrs, R.styleable.DialpadButton)
            try {
                setTitle(a.getString(R.styleable.DialpadButton_title) ?:"")
                setMessage(a.getString(R.styleable.DialpadButton_message) ?: "")
            } finally {
                a.recycle()
            }
        }



        setOnTouchListener { _, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    animate().scaleX(0.90f).scaleY(0.90f).setDuration(80).alpha(0.7f).start()
                }

                MotionEvent.ACTION_UP -> {
                    animate().scaleX(1f).scaleY(1f).setDuration(120).alpha(1f).start()
                    performClick()
                }
            }
            true
        }

    }


    fun setTitle(text: String?) {
        titleView.text = text?.take(1) ?:""
    }

    fun setMessage(text: String?) {
        messageView.text = text?.take(4) ?:""
    }

    override fun performClick(): Boolean {
        super.performClick()
        listener?.onClick(this)
        soundPlayer?.playSound(this)
        return true
    }

    fun getTitle(): String {
        return titleView.text.toString()
    }









}