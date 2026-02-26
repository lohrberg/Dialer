package se.miun.lior2300.dt031g.dialer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class DialpadFragment : Fragment() {


    private var soundPlayer: SoundPlayer? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialpad, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        soundPlayer = SoundPlayer(requireContext())

        val dialpad = view.findViewById<Dialpad>(R.id.dialpad)

        soundPlayer?.let { player -> dialpad.setSoundPlayer(player) }

        dialpad.setCallListener(object : Dialpad.CallListener {
            override fun onCallReq(number: String) {

                (activity as? DialActivity)?.onCallReq(number)

            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        soundPlayer?.destroy()
        soundPlayer = null
    }


}