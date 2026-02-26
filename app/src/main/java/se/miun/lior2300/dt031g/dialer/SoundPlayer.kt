package se.miun.lior2300.dt031g.dialer

import android.content.Context
import android.media.SoundPool
import java.io.File

class SoundPlayer(context: Context) {

    private var soundPool = SoundPool.Builder().setMaxStreams(12).build()
    val soundIds = mutableMapOf<String, Int>()

    init {
        val voiceDir = Util.getDirForDefaultVoice(context)


        for ((title, filename) in Util.DEFAULT_VOICE_FILE_NAMES) {
            val file = File(voiceDir, filename)

            if (file.exists()) {
                val soundId = soundPool.load(file.absolutePath, 1)
                soundIds[title] = soundId
            }
        }
    }

    fun playSound(button: DialpadButton) {
        val title = button.getTitle()
        val soundId = soundIds[title] ?: return

        soundPool.play(soundId, 1f, 1f, 1, 0, 1f)

    }

    fun destroy() {
        soundPool.release()
        soundPool = null
    }


}